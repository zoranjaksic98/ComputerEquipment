import axios, { AxiosError, type AxiosResponse } from "axios";
import { router } from "../router/Routes";
import { toast } from "react-toastify";
import basketService from "./basketService";
import type { Proizvod } from "../models/proizvod";
import type { Dispatch } from "redux";
import type { Basket } from "../models/basket";

axios.defaults.baseURL = 'http://localhost:8081/api/';

const idle = () => new Promise(resolve => setTimeout(resolve, 100));
const responseBody = (response: AxiosResponse) => response.data;

axios.interceptors.response.use(async response=>{
    await idle();
    return response
}, (error: AxiosError)=>{
    const {status} = error.response as AxiosResponse;
    switch(status){
        case 404:
            toast.error("Resource not found");
            router.navigate('/not-found');
            break;
        case 500:
            toast.error("Internal server error occurred");
            router.navigate('/server-error');
            break;
        default:
            break;
    }
    return Promise.reject(error.message);
})

const request = {
    get: (url: string) => axios.get(url).then(responseBody),
    post: (url: string, body: object) => axios.post(url, body).then(responseBody),
    put: (url: string, body:object) => axios.put(url, body).then(responseBody),
    delete: (url: string) => axios.put(url).then(responseBody)
}

const Store = {
    apiUrl: 'http://localhost:8081/api/proizvodi',
    list: (page?: number, size?: number, brandId?: number, typeId?: number, url?: string)=> {
        let requestUrl = url || `proizvodi?page=${(page ?? 0) - 1}&size=${size ?? 10}`;
        if(brandId!==undefined){
            requestUrl += `&brandId=${brandId}`;
        }
        if(typeId!==undefined){
            requestUrl += `&typeId=${typeId}`;
        }
        return request.get(requestUrl);
    },
    details: (id: number)=> request.get(`proizvodi/${id}`),
    types: ()=> request.get('proizvodi/tipovi').then(types => [{id:0, naziv: 'All'}, ...types]),
    brands: ()=> request.get('proizvodi/marke').then(brands => [{id:0, naziv: 'All'}, ...brands]),
    search: (keyword: string) => request.get(`proizvodi?kljucnaRec=${keyword}`)
}

const Basket = {
    get: async() => {
        try{
            return await basketService.getBasket();
        } catch(error){
            console.error("Greska prilikom dobavljanja korpe. ", error);
        }
    },
    addItem: async(product: Proizvod, quantity: number = 1, dispatch: Dispatch)=>{
    try{
        const result = await basketService.addItemToBasket(product, quantity, dispatch);
        console.log(result);
        return result;
    } catch(error){
        console.log("Greska prilikom dodavanja novog proizvoda u korpu. ", error);
        throw error;
    }
},
    removeItem: async (itemId: number, dispatch: Dispatch)=> {
        try{
            await basketService.remove(itemId, dispatch);
        } catch(error) {
            console.log("Greska prilikom brisanja proizvoda iz korpe. ", error);
            throw error;
        }
    },
    incrementItemQuantity: async (itemId: number, kolicina: number = 1, dispatch: Dispatch) => {
        try {
          await basketService.incrementItemQuantity(itemId, kolicina, dispatch);
        } catch (error) {
          console.error("Greska prilikom povecanja kolicine proizvoda:", error);
          throw error;
        }
      },
      decrementItemQuantity: async (itemId: number, kolicina: number = 1, dispatch: Dispatch) => {
        try {
          await basketService.decrementItemQuantity(itemId, kolicina, dispatch);
        } catch (error) {
          console.error("Greska prilikom smanjivanja kolicine proizvoda:", error);
          throw error;
        }
      },
      setBasket: async (basket: Basket, dispatch: Dispatch) => {
        try {
          await basketService.setBasket(basket, dispatch);
        } catch (error) {
          console.error("Greska prilikom setovanja korpe:", error);
          throw error;
        }
      },
      deleteBasket: async(basketId: string) =>{
        try{
          await basketService.deleteBasket(basketId);
        } catch(error){
          console.log("Greska prilikom brisanja korpe.");
          throw error;
        }
      }
}

const Account = {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    login: (values: any) => request.post('auth/login', values)
}

const Orders ={
    list:() => request.get('orders'),
    fetch:(id:number) => request.get(`orders/${id}`),
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    create:(values:any) => {
        
        request.post('orders', values)}
}

const agent = {
    Store,
    Basket,
    Account,
    Orders
}

export default agent;