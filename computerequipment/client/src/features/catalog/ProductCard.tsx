import {
  Avatar,
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  CardMedia,
  CircularProgress,
  Typography,
} from "@mui/material";
import type { Proizvod } from "../../app/models/proizvod";
import { Link } from "react-router-dom";
import { useState } from "react";
import { useAppDispatch } from "../../app/store/configureStore";
import agent from "../../app/api/agent";
import { LoadingButton } from "@mui/lab";
import { setBasket } from "../basket/basketSlice";

interface Props {
  proizvod: Proizvod;
}
export default function ProductCard({ proizvod }: Props) {
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

  const [loading, setLoading] = useState(false);
  const dispatch = useAppDispatch();
  function addItem(){
    setLoading(true);
    agent.Basket.addItem(proizvod, 1, dispatch)
    .then(()=>{
    })
    .catch(error=>console.log(error))
    .finally(()=>setLoading(false));
  }
  return (
    <Card>
      <CardHeader
        avatar={
          <Avatar sx={{ bgcolor: "secondary.main" }}>
            {proizvod.naziv.charAt(0).toUpperCase()}
          </Avatar>
        }
        title={proizvod.naziv}
        titleTypographyProps={{
          sx: { fontWeight: "bold", color: "primary.main" },
        }}
      />
      <CardMedia
        sx={{ height: 140, backgroundSize: "contain" }}
        image={"/images/products/" + extractImageName(proizvod)}
        title={proizvod.naziv}
      />
      <CardContent>
        <Typography gutterBottom color="secondary" variant="h5">
          {formatPrice(proizvod.cena)}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          {proizvod.nazivTipa} / {proizvod.nazivMarke}
        </Typography>
      </CardContent>
      <CardActions>
        <LoadingButton
          loading={loading}
          onClick={addItem}
          size="small"
          startIcon={loading ? <CircularProgress size={20} color="inherit" /> : null}
        >
          Add to cart
        </LoadingButton>
        <Button component={Link} to={`/store/${proizvod.id}`} size="small">Pregled</Button>
      </CardActions>
    </Card>
  )
}
