export interface Basket {
  id: string;
  items: BasketItem[];
}

export interface BasketItem {
  id: number;
  naziv: string;
  opis: string;
  cena: number;
  urlSlike: string;
  markaProizvoda: string;
  tipProizvoda: string;
  kolicina: number;
}
export interface BasketTotals{
    shipping: number;
    subTotal: number;
    total: number;
}