
import { Typography, TableContainer, Table, TableHead, TableRow, TableCell, TableBody } from "@mui/material";
import type { BasketItem } from "../../app/models/basket";
import { useAppSelector } from "../../app/store/configureStore";
import BasketSummary from "../basket/BasketSummary";

export default function Review() {
  const { basket } = useAppSelector((state) => state.basket);
  // Define the extractImageName function
  const extractImageName = (item: BasketItem): string | null => {
    if (item && item.urlSlike) {
      const parts = item.urlSlike.split("/");
      if (parts.length > 0) {
        return parts[parts.length - 1];
      }
    }
    return null;
  };

  // Function to format the price with INR currency symbol
  const formatPrice = (price: number): string => {
    return new Intl.NumberFormat("sr-RS", {
      style: "currency",
      currency: "RSD",
      minimumFractionDigits: 2,
    }).format(price);
  };
  return (
    <>
      <Typography variant="h6" gutterBottom>
        Order summary
      </Typography>
      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Product Image</TableCell>
              <TableCell>Product</TableCell>
              <TableCell>Price</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {basket?.items.map((product) => (
              <TableRow key={product.id}>
                <TableCell>
                  {product.urlSlike && (
                    <img
                      src={"/images/products/" + extractImageName(product)}
                      alt="Product"
                      width="50"
                      height="50"
                    />
                  )}
                </TableCell>
                <TableCell>{product.naziv}</TableCell>
                <TableCell>{formatPrice(product.cena)}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <BasketSummary />
    </>
  );
}
