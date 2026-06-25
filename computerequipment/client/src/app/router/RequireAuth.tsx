import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAppSelector } from "../store/configureStore";

export default function RequireAuth(){
        const {user} = useAppSelector(State=> State.account)
        const location = useLocation();
        if(!user){
            return <Navigate to='/login' state={{from:location}}/>
        }
        return <Outlet/>
        
}