import { Divider, Grid, Table, TableBody, TableCell, TableContainer, TableRow, Typography } from "@mui/material";

import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import type { Proizvod } from "../../app/models/proizvod";
import agent from "../../app/api/agent";
import NotFound from "../../app/errors/NotFoundError";
import Spinner from "../../app/layout/Spinner";

export default function ProductDetails(){
    const {id} = useParams<{id:string}>();
    const [proizvod, setProizvod] = useState<Proizvod | null>();
    const [loading, setLoading] = useState(true);
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
  }
    useEffect(()=>{
        if(!id) return;
        agent.Store.details(parseInt(id))
        .then(response=>setProizvod(response))
        .catch(error=>console.error(error))
        .finally(()=> setLoading(false));
    }, [id])
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
            </Grid>
        </Grid>
    )
}