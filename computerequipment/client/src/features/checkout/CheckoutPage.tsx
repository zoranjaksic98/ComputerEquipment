import { useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useAppDispatch } from "../../app/store/configureStore";
import AddressForm from "./AddressForm";
import PaymentForm from "./PaymentForm";
import Review from "./Review";
import { ValidationRute } from "./validationRute";
import {yupResolver} from "@hookform/resolvers/yup"
import agent from "../../app/api/agent";
import { setBasket } from "../basket/basketSlice";
import { toast } from "react-toastify";
import type { BasketItem } from "../../app/models/basket";
import { Paper, Typography, Stepper, Step, StepLabel, Box, Button } from "@mui/material";

const steps = ["Shipping Address", "Review your oder", "Payment Details"]
    function getStepContent(step:number){
        switch(step){
            case 0:
                return <AddressForm/>
            case 1:
                return <Review/>
            case 2:
                return <PaymentForm/>
            default:
                throw new Error("Unknown Step");
        }
    }
export default function CheckoutPage(){
    const [activeStep, setActiveStep] = useState(0);
    const [orderNumber, setOrderNumber] = useState(0);
    const [loading, setLoading] = useState(false);
    const currentVarlidationRule = ValidationRute[activeStep];
    const methods = useForm({
        mode: 'all',
        resolver: yupResolver(currentVarlidationRule)
    })
    const dispatch = useAppDispatch();

    const handleNext = async () =>{
        const isValid = await methods.trigger();
        if(isValid){
            console.log(methods.getValues());
            // eslint-disable-next-line @typescript-eslint/no-explicit-any
            const data: any = methods.getValues();
            if(activeStep == steps.length-1){
                // if its last step then submit the order
                console.log("localStorage basket:", localStorage.getItem('basket'));
                const basket = await agent.Basket.get();
                console.log("BASKET:", basket);
console.log("BASKET ID:", basket?.id);
                if(basket){
                    const subTotal = calculateSubTotal(basket.items);
                    // add logic to calculate delivery fee
                    const deliveryFee = 200;
                    try{
                        setLoading(true);
                        //constuct the order dto to send to backed
                        const orderDto = {
                            idKorpe: basket.id,
                            adresa:{
                                naziv: data.firstName+ " " + data.lastName,
                                adresa1: data.address1,
                                adresa2: data.address2,
                                grad: data.city,
                                opstina: data.state,
                                postanskiBroj: data.zip,
                                drzava: data.country
                            },
                            konacnaCena: subTotal,
                            naknadaZaDostavu: deliveryFee
                        };
                        console.log("basket.id:", basket.id);
                        console.log("orderDto:", orderDto);
                        // call the api to create the order
                        const orderId = await agent.Orders.create(orderDto);
                        //order created successfully
                        setOrderNumber(orderId);
                        setActiveStep(activeStep+1);
                        //now clear the basket
                        agent.Basket.deleteBasket(basket.id);
                        dispatch(setBasket(null));
                        // clear form local storage
                        localStorage.removeItem('basket_id');
                        localStorage.removeItem('basket');
                    } catch(error){
                        console.error("Error submitting order:", error);
                        toast.error("Error submitting order");
                    }finally{
                        setLoading(false);
                    }
                }else {
                    toast.error("Basket not found in local storage");
                }
            } else {
                // move to next step
                setActiveStep(activeStep+1);
            }
        }
    };
    const calculateSubTotal = (items: BasketItem[]): number =>{
        return items.reduce((total, item) => total + item.cena * item.kolicina, 0);
    };

    const handleBack = () => {
        setActiveStep(activeStep-1);
    }
    return(
        <FormProvider {...methods}>
        <Paper variant="outlined" sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}>
          <Typography component="h1" variant="h4" align="center">
            Checkout
          </Typography>
          <Stepper activeStep={activeStep} sx={{ pt: 3, pb: 5 }}>
            {steps.map((label) => (
              <Step key={label}>
                <StepLabel>{label}</StepLabel>
              </Step>
            ))}
          </Stepper>
          <>
            {activeStep === steps.length ? (
              <>
                <Typography variant="h5" gutterBottom>
                  Thank you for your order.
                </Typography>
                <Typography variant="subtitle1">
                  Your order number is #{orderNumber}. We have emailed your order confirmation, and will send you an update when your order has shipped.
                </Typography>
              </>
            ) : (
              <>
                {getStepContent(activeStep)}
                <Box sx={{ display: "flex", justifyContent: "flex-end" }}>
                  {activeStep !== 0 && (
                    <Button onClick={handleBack} sx={{ mt: 3, ml: 1 }}>
                      Back
                    </Button>
                  )}
                  <Button
                    variant="contained"
                    onClick={handleNext}
                    sx={{ mt: 3, ml: 1 }}
                  >
                    {activeStep === steps.length - 1 ? "Place order" : "Next"}
                  </Button>
                </Box>
              </>
            )}
          </>
        </Paper>
      </FormProvider>
    )
}