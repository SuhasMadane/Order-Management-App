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
import {  useLocation, useNavigate } from "react-router-dom";
import { MenuItem } from "@mui/material";

function Copyright(props) {
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
}

const defaultTheme = createTheme();

const UpdateOrder = () => {
  // e.preventDefault();
  debugger
  const navigate = useNavigate();
  const [books, setBooks] = useState([]);
  const location = useLocation();
  // { location.state && navigate("/myorders")}
  const { bookData } = location.state;
  const [orderRequest, setOrderRequest] = useState({
    bookTitle: bookData.bookTitle,
    bookQuantity: bookData.bookQuantity,
    studentEmail: "",
  });

  React.useEffect(() => {
    
    const getAllBooks = async () => {
      try {
        let uri = `http://localhost:8181/books/getbooks`;

        const response = await axios.get(uri);
        setBooks(response.data);
      } catch (error) {
        alert(error);
      }
    };
    getAllBooks();
  
  }, []);

  const handleBookTitle = async (e) => {
    console.log(`Selected book: ${e.target.value}`);
    try{
      let uri = `http://localhost:8181/books/getSingleBook?title=${e.target.value}`
      const response = await axios.get(uri);
      bookData.bookPrice = response.data.price;
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
     debugger
      const email = localStorage.getItem("Student email");
      console.log(`Email received from localStorage ${email}`)
      const newOrderRequest = { ...orderRequest, studentEmail: email };
      setOrderRequest(newOrderRequest);
     // event.preventDefault();
      //   const data = new FormData(event.currentTarget);
      // console.log({
      //   email: localStorage.getItem("student email"),
      //   title: orderRequest.bookTitle,
      //   quantity: orderRequest.bookQuantity,
      // });

      let uri = `http://localhost:8181/orders/update`;
      const response = await axios.post(uri, {
        existingOrderId: bookData.bookOrderId,
        bookTitle: orderRequest.bookTitle,
        bookQuantity: orderRequest.bookQuantity,
        studentEmail: localStorage.getItem("Student email"),
      });

      if (response.status === 201) {
        alert(response.data);
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
            Update Order
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
                  required
                  label="Book"
                  sx={{ width: 450 }}
                  select
                  value={orderRequest.bookTitle}
                  onChange={handleBookTitle}
                  variant="filled"
                >
                  {books.map((book) => (
                    <MenuItem key={book.book_Id} value={book.title}>
                      {book.title}
                    </MenuItem>
                  ))}
                </TextField>
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
                  Price: Rs.{bookData.bookPrice}
                </Typography>
              </Grid>
              <Grid item xs={12} sm={12}>
                <Typography variant="h6" color="textPrimary">
                  Total: Rs.{bookData.bookPrice * orderRequest.bookQuantity}
                </Typography>
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Update Order
            </Button>
          </Box>
        </Box>
        <Copyright sx={{ mt: 5 }} />
      </Container>
    </ThemeProvider>
  );
};

export default UpdateOrder;
