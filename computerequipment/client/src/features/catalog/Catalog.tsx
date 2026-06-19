import { useEffect, useState } from "react";
import type { Proizvod } from "../../app/models/proizvod";
import ProductList from "./ProductList";
import agent from "../../app/api/agent";
import Spinner from "../../app/layout/Spinner";

export default function Catalog() {
  const [proizvodi, setProizvode] = useState<Proizvod[]>([]);
  const [loading, setLoading] = useState(true);
 useEffect(() => {
  agent.Store.list()
  .then((proizvodi)=> setProizvode(proizvodi.content))
  .catch(error=>console.log(error))
  .finally(()=> setLoading(false));
 }, []);
 
 if(!proizvodi) return <h3>Nemoguće je dobaviti proizvod.</h3>
 if(loading) return <Spinner message='Loading Products...'/>
  return (
    <>
    <ProductList proizvodi={proizvodi}/>
    </>
  );
}
