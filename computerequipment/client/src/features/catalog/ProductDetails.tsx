import { Divider, Grid, Table, TableBody, TableCell, TableContainer, TableRow, TextField, Typography } from "@mui/material";

import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import type { Proizvod } from "../../app/models/proizvod";
import agent from "../../app/api/agent";
import NotFound from "../../app/errors/NotFoundError";
import Spinner from "../../app/layout/Spinner";
import { useAppDispatch, useAppSelector } from "../../app/store/configureStore";
import { LoadingButton } from "@mui/lab";

export default function ProductDetails(){
    const { basket } = useAppSelector(state=>state.basket);
    const dispatch = useAppDispatch();
    const {id} = useParams<{id:string}>();
    const [proizvod, setProizvod] = useState<Proizvod | null>();
    const [loading, setLoading] = useState(true);
    const [quantity, setQuantity] = useState(0);
    const [submitting, setSubmitting] = useState(false);
    const item = basket?.items.find(i=> i.id === proizvod?.id);


    const extractImageName = (item: Proizvod): string | null =>{
    if(item && item.urlSlike){
      const parts = item.urlSlike.split('/');
      if(parts.length>0){
        return parts[parts.length-1]
      }
    }
    return null;
  }

  const formatPrice = (cena: number): string=>{
    return new Intl.NumberFormat('sr-RS', {
      style:'currency',
      currency: 'RSD',
      minimumFractionDigits: 2
    }).format(cena);
  };

    useEffect(()=>{
        if(!id) return;
        agent.Store.details(parseInt(id))
        .then(response=>setProizvod(response))
        .catch(error=>console.error(error))
        .finally(()=> setLoading(false));
    }, [id])

     const inputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const value = parseInt(event.target.value);
        if (!isNaN(value) && value > 0) {
            setQuantity(value);
        }
    };
    useEffect(() => {
    if (item) {
        setQuantity(item.kolicina);
    }
}, [item]);

    const updateQuantity = async () => {
        try {
            setSubmitting(true);
            if (item) {
                const quantityDifference = quantity - item.kolicina;
                if (quantityDifference > 0) {
                    // Increment the quantity of an existing item in the basket
                    await agent.Basket.incrementItemQuantity(item.id, quantityDifference, dispatch);
                } else if (quantityDifference < 0) {
                    // Decrement the quantity of an existing item in the basket
                    await agent.Basket.decrementItemQuantity(item.id, Math.abs(quantityDifference), dispatch);
                }
            } else {
                // Add a new item to the basket
                await agent.Basket.addItem(proizvod!, quantity, dispatch);
            }
            setSubmitting(false);
        } catch (error) {
            console.error("Failed to update quantity:", error);
            // Handle error
            setSubmitting(false);
        }
    };

    if(loading) return <Spinner message='Loading Product...'/>
    if(!proizvod) return <NotFound/>
    return (
        <Grid container spacing={6}>
            <Grid size={6}>
                <img src={"/images/products/"+extractImageName(proizvod)} alt={proizvod.naziv} style={{ width: '100%' }}/>
            </Grid>
            <Grid size={6}>
                <Typography variant='h3'>{proizvod.naziv}</Typography>
                <Divider sx={{ mb:2 }}/>
                <Typography gutterBottom color='secondary' variant="h4">{formatPrice(proizvod.cena)}</Typography>
                <TableContainer>
                    <Table>
                        <TableBody>
                            <TableRow>
                                <TableCell>Naziv</TableCell>
                                <TableCell>{proizvod.naziv}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell>Opis</TableCell>
                                <TableCell>{proizvod.opis}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell>Tip proizvoda</TableCell>
                                <TableCell>{proizvod.nazivTipa}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell>Marka proizvoda</TableCell>
                                <TableCell>{proizvod.nazivMarke}</TableCell>
                            </TableRow>
                        </TableBody>
                    </Table>
                </TableContainer>
                <Grid container spacing={2}>
                    <Grid size={6}>
                        <TextField
                            onChange={inputChange} 
                            variant='outlined'
                            type='number'
                            label='Quantity in Cart'
                            fullWidth
                            value={quantity}
                        />
                    </Grid>
                    <Grid size={6}>
                        <LoadingButton
                            sx={{ height: '55px' }}
                            color='primary'
                            size='large'
                            variant='contained'
                            fullWidth
                            loading={submitting}
                            onClick={updateQuantity}
                        >
                            {item ? 'Update Quantity' : 'Add to Cart'}
                        </LoadingButton>
                    </Grid>
                </Grid>
            </Grid>
        </Grid>
    )
}