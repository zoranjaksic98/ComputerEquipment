import { createSlice } from "@reduxjs/toolkit";
import type { Basket } from "../../app/models/basket";
import { logOut } from "../account/accountSlice";

interface BasketState {
    basket: Basket | null
}

const initialState: BasketState = {
    basket: null
}

export const basketSlice = createSlice({
    name: 'basket',
    initialState,
    reducers:{
        setBasket: (state, action) =>{
            console.log('new basket state', action.payload);
            state.basket = action.payload
        },
        clearBasket: (state) => {
            state.basket = null;
        }
    },
    extraReducers: (builder) => {
        builder.addCase(logOut, (state) => {  // ← automatski čisti korpu pri logout-u
            state.basket = null;
        });
    }
})
export const {setBasket, clearBasket} = basketSlice.actions;