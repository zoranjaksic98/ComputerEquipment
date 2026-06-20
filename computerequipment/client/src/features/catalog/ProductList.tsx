
import { Grid } from "@mui/material";
import type { Proizvod } from "../../app/models/proizvod";
import ProductCard from "./ProductCard";

interface Props {
  proizvodi: Proizvod[];
}
export default function ProductList({ proizvodi }: Props) {
  return (
    <Grid container spacing={4}>
  {proizvodi.map((proizvod) => (
    <Grid size={4} key={proizvod.id}>
      <ProductCard proizvod={proizvod} />
    </Grid>
  ))}
</Grid>
  );
}
