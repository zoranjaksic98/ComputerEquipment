
import { useEffect, useState } from "react";
import type { Proizvod } from "../../app/models/proizvod";
import ProductList from "./ProductList";
import agent from "../../app/api/agent";
import Spinner from "../../app/layout/Spinner";
import { Box, FormControl, FormControlLabel, FormLabel, Grid, Pagination, Paper, Radio, RadioGroup, TextField, Typography, type SelectChangeEvent } from "@mui/material";
import type { Brand } from "../../app/models/Brand";
import type { Type } from "../../app/models/type";

const sortOptions = [
  {value: "asc", label:"Ascending"},
  {value: "desc", label:"Descending"}
]
export default function Catalog() {
  const [proizvodi, setProizvode] = useState<Proizvod[]>([]);
  const [loading, setLoading] = useState(true);
  const [brands, setBrands] = useState<Brand[]>([]);
  const [types, setTypes] = useState<Type[]>([]);
  const [selectedSort, setSelectedSort] = useState("asc");
  const [selectedBrand, setSelectedBrand] = useState("All");
  const [selectedType, setSelectedType] = useState("All");
  const [selectedBrandId, setSelectedBrandId] = useState(0);
  const [selectedTypeId, setSelectedTypeId] = useState(0);
  const [searchTerm, setSearchTerm] = useState('');
  const [totalItems, setTotalItems] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const pageSize = 10;
/*
 useEffect(() => {
  agent.Store.list()
  .then((proizvodi)=> setProizvode(proizvodi.content))
  .catch(error=>console.log(error))
  .finally(()=> setLoading(false));
 }, []);
 */
useEffect(() => {
    Promise.all([
        agent.Store.brands(),
        agent.Store.types()
    ]).then(([brandsResp, typesResp]) => {
        setBrands(brandsResp);
        setTypes(typesResp);
    }).catch((error) => console.error(error));
}, []);

const loadProducts = (selectedSort: string, searchKeyword='', brandId = selectedBrandId, typeId = selectedTypeId)=> {
  setLoading(true);
  const page = currentPage - 1;
  const size = pageSize;
  const activeBrandId = brandId !== 0 ? brandId : undefined;
  const activeTypeId = typeId !== 0 ? typeId : undefined;
  const sort = "naziv";
  const order = selectedSort === "desc" ? "desc" : "asc";
  let url = `${agent.Store.apiUrl}?page=${page}&size=${size}&sort=${sort}&order=${order}`;
  if(activeBrandId !== undefined || activeTypeId !== undefined){
    url+='&';
    if(activeBrandId!== undefined) url += `markaId=${activeBrandId}&`;
    if(activeTypeId!== undefined) url += `tipId=${activeTypeId}&`;
    url = url.replace(/&$/, "");
  }

  if(searchKeyword){
    console.log(searchKeyword);
    agent.Store.search(searchKeyword)
    .then((productsRes)=>{
      setProizvode(productsRes.content);
      setTotalItems(productsRes.totalElements);
    })
    .catch((error)=>console.error(error))
    .finally(()=> setLoading(false));
  }else {
    agent.Store.list(undefined, undefined,undefined, undefined, url)
    .then((productRes) => {
      setProizvode(productRes.content)
      setTotalItems(productRes.totalElements);
    })
    .catch((error)=>console.log(error))
    .finally(()=> setLoading(false));
  }
}
 
useEffect(()=> {
  loadProducts(selectedSort, '', selectedBrandId, selectedTypeId);
}, [currentPage, selectedBrandId, selectedTypeId, selectedSort]);

const handleSortChange = (event: SelectChangeEvent<string>) => {
  const selectedSort = event.target.value;
  setSelectedSort(selectedSort);
  loadProducts(selectedSort);
};

const handleBrandChange = (event: SelectChangeEvent<string>) => {
  const selectedBrand = event.target.value;
  const brand = brands.find((b)=>b.naziv === selectedBrand);
  setSelectedBrand(selectedBrand);
  if(brand){
    setSelectedBrandId(brand.id);
  } else {
    setSelectedBrandId(0);
  }
};

const handleTypeChange = (event: SelectChangeEvent<string>) => {
  const selectedType = event.target.value;
  const type = types.find((t)=>t.naziv === selectedType);
  setSelectedType(selectedType);
  if(type){
    setSelectedTypeId(type.id);
  } else {
    setSelectedTypeId(0);
  }
};

const handlePageChange = (_event: React.ChangeEvent<unknown>, page: number) => {
  setCurrentPage(page);
}

 if(!proizvodi) return <h3>Nemoguće je dobaviti proizvod.</h3>
 if(loading) return <Spinner message='Loading Products...'/>
  return (
    <Grid container spacing={4}>
      <Grid size={12}>
        <Box sx={{mb: 2, textAlign: "center"}}>
          <Typography variant="subtitle1">
            Displaying {(currentPage - 1) * pageSize + 1}-{Math.min(currentPage * pageSize, totalItems)} of {totalItems} items
          </Typography>
        </Box>
        <Box sx={{mt: 4, display: "flex", justifyContent: "center"}}>
          <Pagination count={Math.ceil(totalItems / pageSize)} color="primary" onChange={handlePageChange} page={currentPage} />
        </Box>
      </Grid>
      <Grid size={3}>
        <Paper sx={{mb:2}}>
            <TextField 
          label="Search products" 
          variant="outlined" 
          fullWidth 
          value={searchTerm} 
          onChange={(e) => setSearchTerm(e.target.value)}
          onKeyDown={(e) => {
            if (e.key === 'Enter') {
             loadProducts(selectedSort, searchTerm);
            }
          }}
        />
        </Paper>
        <Paper sx={{ mb: 2, p: 2 }}>
          <FormControl>
            <FormLabel id="sort-by-name-label">Sort by Name</FormLabel>
            <RadioGroup
              aria-label="sort-by-name"
              name="sort-by-name"
              value={selectedSort}
              onChange={handleSortChange}
            >
              {sortOptions.map(({ value, label }) => (
                <FormControlLabel
                  key={value}
                  value={value}
                  control={<Radio />}
                  label={label}
                />
              ))}
            </RadioGroup>
          </FormControl>
        </Paper>
        <Paper sx={{ mb: 2, p: 2 }}>
          <FormControl>
            <FormLabel id="brands-label">Brands</FormLabel>
            <RadioGroup
              aria-label="brands"
              name="brands"
              value={selectedBrand}
              onChange={handleBrandChange}
            >
              {brands.map((brand) => (
                <FormControlLabel
                  key={brand.id}
                  value={brand.naziv}
                  control={<Radio />}
                  label={brand.naziv}
                />
              ))}
            </RadioGroup>
          </FormControl>
        </Paper>
        <Paper sx={{ mb: 2, p: 2 }}>
          <FormControl>
            <FormLabel id="types-label">Types</FormLabel>
            <RadioGroup
              aria-label="types"
              name="types"
              value={selectedType}
              onChange={handleTypeChange}
            >
              {types.map((type) => (
                <FormControlLabel
                  key={type.id}
                  value={type.naziv}
                  control={<Radio />}
                  label={type.naziv}
                />
              ))}
            </RadioGroup>
          </FormControl>
        </Paper>
      </Grid>
      <Grid size={9}>
          <ProductList proizvodi={proizvodi}/>
      </Grid>
      <Grid size={12}>
        <Box sx={{mt: 4, display: "flex", justifyContent: "center"}}>
          <Pagination count={Math.ceil(totalItems / pageSize)} color="primary" onChange={handlePageChange} page={currentPage} />
        </Box>
      </Grid>
    </Grid>
  );
}
