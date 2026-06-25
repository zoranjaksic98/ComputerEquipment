import { Box, Button, IconButton, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import { useAppDispatch, useAppSelector } from "../../app/store/configureStore";
import agent from "../../app/api/agent";
import { Add, Remove } from '@mui/icons-material';
import type { BasketItem } from "../../app/models/basket";
import BasketSummary from "./BasketSummary";
import { Link } from "react-router-dom";

export default function BasketPage(){
    const {basket} = useAppSelector(state=>state.basket);
    const dispatch = useAppDispatch();
    const {Basket: BasketActions} = agent;

    const removeItem = (productId: number)=> {
        BasketActions.removeItem(productId, dispatch);
    };

    const decrementItem = (productId: number, kolicina: number = 1)=>{
        BasketActions.decrementItemQuantity(productId, kolicina, dispatch);
    };

    const incrementItem = (productId: number, kolicina: number = 1)=>{
        BasketActions.incrementItemQuantity(productId, kolicina, dispatch);
    };
     // Define the extractImageName function
    const extractImageName = (item: BasketItem): string | null => {
        if (item && item.urlSlike) {
            const parts = item.urlSlike.split('/');
            if (parts.length > 0) {
                return parts[parts.length - 1];
            }
        }
        return null;
    };

    // Function to format the price with INR currency symbol
    const formatPrice = (price: number): string =>{
        return new Intl.NumberFormat('sr-RS', {
            style: 'currency',
            currency: 'RSD',
            minimumFractionDigits: 2
        }).format(price);
    };
    if(!basket || basket.items.length === 0) return <Typography variant="h3">Vasa korpa je prazna. Dodajte proizvode!</Typography>
    return (
        <>
        <TableContainer component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Product Image</TableCell>
                        <TableCell>Product</TableCell>
                        <TableCell>Price</TableCell>
                        <TableCell>Quantity</TableCell>
                        <TableCell>Subtotal</TableCell>
                        <TableCell>Remove</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {basket.items.map((item) => (
                        <TableRow key={item.id}>
                            <TableCell>
                                {item.urlSlike && (
                                    <img src={"/images/products/"+extractImageName(item)} alt="Proizvod" width="50" height="50" />
                                )}
                            </TableCell>
                            <TableCell>{item.naziv}</TableCell>
                            <TableCell>{formatPrice(item.cena)}</TableCell>
                            <TableCell>
                                <IconButton color='error' onClick={() => decrementItem(item.id)}>
                                    <Remove />
                                </IconButton>
                                {item.kolicina}
                                <IconButton color='error' onClick={() => incrementItem(item.id)}>
                                    <Add />
                                </IconButton>
                            </TableCell>
                            <TableCell>{formatPrice(item.cena * item.kolicina)}</TableCell>
                            <TableCell>
                                <IconButton onClick={() => removeItem(item.id)} aria-label="delete">
                                    <DeleteIcon />
                                </IconButton>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
        <Box sx={{ mx: 2, p: 2, bgcolor: "background.paper", borderRadius: 4 }}>
            <BasketSummary/>
            <Button
                component={Link}
                to='/checkout'
                variant='contained'
                size='large'
                fullWidth>
                    Checkout
            </Button>
        </Box>
        </>
    );
}