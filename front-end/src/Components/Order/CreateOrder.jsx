import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import axios from "axios";
import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { MenuItem } from "@mui/material";
import { memo } from "react";
import { replace } from "formik";
import { MDBBtn, MDBCheckbox, MDBCol, MDBInput, MDBRow } from "mdb-react-ui-kit";

const Copyright = memo((props)=> {
  return (
    <Typography
      variant="body2"
      color="text.secondary"
      align="center"
      {...props}
    >
      {"Copyright © "}
      <Link color="inherit" href="https://mui.com/">
        Your Website
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
})

const defaultTheme = createTheme();



const CreateOrder = () => {
  const navigate = useNavigate();
  const location = useLocation();
  // if(!location.state?.bookData){
  //   navigate("/");
  // }
  const {bookTitle, bookPrice: initialBookPrice } = location.state.bookData;
  const [bookPrice,setBookPrice] = useState(initialBookPrice || 0);
  const [orderRequest, setOrderRequest] = useState({
    bookTitle:'',
    bookQuantity: 0,
    studentEmail: "",
  });
 
 
  React.useEffect(()=>{

   // const dd  = location.state?.bookData ;
    // setBookData(dd);
    setOrderRequest({
      bookTitle,
    bookQuantity: 0,
    studentEmail: "",
    
    })
    setBookPrice(initialBookPrice);
    
    console.log(orderRequest)
  },[]);

  const handleBookTitle = async (e) => {
    console.log(`Selected book: ${e.target.value}`);
    try{
      let uri = `http://localhost:8181/books/getSingleBook?title=${e.target.value}`
      const response = await axios.get(uri);
      // setBookData(...bookData,bookPrice: response.data.price)
      setBookPrice(response.data.price)
     
    }
    catch(error){
      console.log(error);
    }
    const newOrderRequest = { ...orderRequest, bookTitle: e.target.value };
    setOrderRequest(newOrderRequest);
  };

  const handleBookQuantity = (e) => {
    const newOrderRequest = { ...orderRequest, bookQuantity: e.target.value };
    setOrderRequest(newOrderRequest);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const email = localStorage.getItem("Student email");
      console.log(`Email received from localStorage ${email}`)
      const newOrderRequest = { ...orderRequest, studentEmail: email };
      setOrderRequest(newOrderRequest);
      console.log(orderRequest);
      let uri = `http://localhost:8181/orders/create`;
      const response = await axios.post(uri, {
        bookTitle: orderRequest.bookTitle,
        bookQuantity: orderRequest.bookQuantity,
        studentEmail: localStorage.getItem("Student email"),
      });
      if (response.status === 201) {
        console.log("navigating to main page")
          navigate("/");
      } else throw new Error(response.data);
    } catch (error) {
      alert(error.response.data);
    }
    
    
  };

  return (

    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Create Order
          </Typography>
          <Box
            component="form"
            noValidate
            onSubmit={(e)=>handleSubmit(e)}
            sx={{ mt: 3 }}
          >
            <Grid container spacing={2}>
              <Grid item xs={12} sm={12}>
                <TextField 
                label="Book"
                  sx={{ width: 450 }}
                   id="filled-basic" variant="filled" 
                   onChange={handleBookTitle}
                   value={orderRequest.bookTitle} 
                   />
              </Grid>
              <Grid item xs={12} sm={12}>
                <TextField
                required
                  id="number"
                  label="Book Quantity"
                  type="number"
                  sx={{ width: 450 }}
                  InputLabelProps={{
                    shrink: true,
                  }}
                  variant="filled"
                  value={orderRequest.bookQuantity}
                  onChange={handleBookQuantity}
                />
              </Grid>
              <Grid item xs={12} sm={12}>
                <Typography variant="h6" color="textPrimary">
                  Price: Rs.{bookPrice}
                </Typography>
              </Grid>
              <Grid item xs={12} sm={12}>
                <Typography variant="h6" color="textPrimary">
                  Total Amount: Rs.{bookPrice * orderRequest.bookQuantity}
                </Typography>
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Create Order
            </Button>
          </Box>
        </Box>
        {/* <Copyright sx={{ mt: 5 }} /> */}
      </Container>
    </ThemeProvider>
  );
};

export default CreateOrder;
