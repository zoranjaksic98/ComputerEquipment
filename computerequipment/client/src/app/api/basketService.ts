import axios from "axios";
import type { Basket, BasketItem, BasketTotals } from "../models/basket";
import type { Proizvod } from "../models/proizvod";
import type { Dispatch } from "redux";
import { setBasket } from "../../features/basket/basketSlice";
import { createId } from '@paralleldrive/cuid2';

class BasketService {
    apiUrl = "http://localhost:8081/api/baskets";

    async getBasketFromApi(){
        try{
            const response = await axios.get<Basket>(`${this.apiUrl}`);
            return response.data;
        }catch{
            throw new Error("Greska u dobavljanju korpe.");
        }
    }

    async getBasket(){
        try{
            const basket = localStorage.getItem('basket');
            if(basket){
                return JSON.parse(basket) as Basket;
            }else {
                throw new Error("Korpa nije pronadjena.");
            }
        } catch(error){
            throw new Error("Greska prilikom dobavljanja korpe:" + error, { cause: error });
        }
    }

    async addItemToBasket(item: Proizvod, kolicina=1, dispatch: Dispatch){
        try{
            let basket = this.getCurrentBasket();
            if(!basket){
                basket = await this.createBasket();
            }
            const itemToAdd = this.mapProductToBasket(item);
            basket.items = this.upsertItems(basket.items, itemToAdd, kolicina);
            this.setBasket(basket, dispatch);
            // calculate totals
            const totals = this.calculateTotals(basket);
            return {basket, totals};
        } catch(error){
            throw new Error("Greska prilikom dodavanja proizvoda u korpu.", { cause: error })
        }
    }

    async remove(itemId: number, dispatch: Dispatch){
        const basket = this.getCurrentBasket();
        if(basket){
            const itemIndex = basket.items.findIndex((p)=>p.id === itemId);
            if(itemIndex!==-1){
                basket.items.splice(itemIndex, 1);
                this.setBasket(basket, dispatch);
            }
            //provera da li korpa prazna posle brisanja proizvoda
            if(basket.items.length=== 0){
                //brisanje korpe iz local storage
                localStorage.removeItem('basket_id');
                localStorage.removeItem('basket');
            }
        }
    }

    async incrementItemQuantity(itemId: number, kolicina: number = 1, dispatch: Dispatch){
        const basket = this.getCurrentBasket();
        if(basket){
            const item = basket.items.find((p)=>p.id === itemId);
            if(item){
                item.kolicina += kolicina;
                if(item.kolicina<1){
                    item.kolicina = 1;
                }
                this.setBasket(basket, dispatch);
            }
        }
    }

    async decrementItemQuantity(itemId: number, kolicina:number = 1, dispatch: Dispatch){
        const basket = this.getCurrentBasket();
        if(basket){
            const item = basket.items.find((p)=>p.id === itemId);
            if(item && item.kolicina>1){
                item.kolicina -= kolicina;
                this.setBasket(basket, dispatch);
            }
        }
    }

    async deleteBasket(basketId: string): Promise<void>{
        try{
            await axios.delete(`${this.apiUrl}/${basketId}`);
        } catch(error){
            throw new Error("Greska prilikom brisanja korpe.", { cause: error });
        }
    }

    async setBasket(basket: Basket, dispatch: Dispatch){
        try{
            await axios.post<Basket>(this.apiUrl, basket);
            localStorage.setItem('basket', JSON.stringify(basket));
            dispatch(setBasket(basket));
        }catch (error){
            throw new Error("Greska prilikom update-a korpe.", { cause: error });
        }
    }  

    private getCurrentBasket(){
        const basket = localStorage.getItem('basket');
        return basket ? JSON.parse(basket) as Basket : null;
    }

    private async createBasket(): Promise<Basket>{
        try{
            const newBasket: Basket = {
                id: createId(),
                items: []
            }
            localStorage.setItem('basket_id', newBasket.id);
            return newBasket;
        }catch(error){
            throw new Error("Greska prilikom kreiranja korpe.", { cause: error });
        }
    }

    private mapProductToBasket(item: Proizvod): BasketItem {
        return {
            id: item.id,
            naziv: item.naziv,
            cena: item.cena,
            opis: item.opis,
            kolicina: 0,
            urlSlike: item.urlSlike,
            markaProizvoda: item.nazivTipa,
            tipProizvoda: item.nazivMarke
        }
    }

    private upsertItems(items: BasketItem[], itemToAdd: BasketItem, kolicina:number): BasketItem[]{
        const existingItem = items.find(x=>x.id == itemToAdd.id);
        if(existingItem){
            existingItem.kolicina += kolicina;
        }else {
            itemToAdd.kolicina = kolicina;
            items.push(itemToAdd);
        }
        return items;
    }

    private calculateTotals(basket: Basket): BasketTotals{
        const shipping = 0;
        const subTotal = basket.items.reduce((acc, item)=>acc+(item.cena*item.kolicina), 0);
        const total = shipping + subTotal;
        return {shipping, subTotal, total};
    }

}

export default new BasketService();