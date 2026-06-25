import { createAsyncThunk, createSlice, isAnyOf } from "@reduxjs/toolkit";
import type { User } from "../../app/models/user";
import type { FieldValues } from "react-hook-form";
import agent from "../../app/api/agent";
import { router } from "../../app/router/Routes";
import { toast } from "react-toastify";

interface AccountState {
    user: User | null;
    error: string | null;
}

const initialState: AccountState = {
    user: null,
    error: null
}

export const signInUser = createAsyncThunk<User, FieldValues>(
    'auth/login',
    async (data, thunkAPI) => {
        try{
            const user = await agent.Account.login(data);
            localStorage.setItem('user', JSON.stringify(user))
            return user;
            // eslint-disable-next-line @typescript-eslint/no-explicit-any
        }catch(error: any){
            return thunkAPI.rejectWithValue({error: error.data})
        }
    }
)

export const fetchCurrentUser = createAsyncThunk<User | null>(
    'auth/fetchCurrentUser',
    async(_, thunkAPI) => {
        try{
            const userString = localStorage.getItem('user');
            if(userString){
                const user = JSON.parse(userString) as User;
                return user;
            }
            return null;
        }catch(error){
            console.error("Error Fetching current User: ", error);
            return null;
        }
    }
)

export const logoutUser = createAsyncThunk<void>(
    'auth/logout',
    async(_, thunkAPI) => {
        try{
            // Remove user from local storage
            localStorage.removeItem('user');
        }catch(error){
            console.error("Error logging out user", error);
        }
    }
)

export const accountSlice = createSlice({
    name: 'account',
    initialState,
    reducers:{
        logOut:(state)=> {
            state.user = null;
            state.error = null;
            localStorage.removeItem('user');
            localStorage.removeItem('basket');
            router.navigate('/');
        }, clearError:(state)=>{
            state.error = null;
        }
    },
    extraReducers:(builder => {
        builder.addMatcher(isAnyOf(signInUser.fulfilled), (state, action) => {
            state.user = action.payload;
            state.error = null;
            toast.success('Sign in successful');
        });
        builder.addMatcher(isAnyOf(fetchCurrentUser.fulfilled), (state, action) => {
            state.user = action.payload;
            state.error = null;
        });
        builder.addMatcher(isAnyOf(signInUser.rejected, fetchCurrentUser.rejected), (state, action) => {
            const payload = action.payload as string | null;
            state.error = payload;
            toast.error('Sign in faild. Please try again.');
        });
    })
})

export const {logOut, clearError} = accountSlice.actions;
