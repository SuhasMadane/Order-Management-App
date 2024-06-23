import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useFormik } from 'formik';
import { addBookSchema } from '../../schemas';


function Copyright(props) {
  return (
    <Typography variant="body2" color="text.secondary" align="center" {...props}>
      {'Copyright © '}
      <Link color="inherit" href="https://mui.com/">
        Your Website
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

const defaultTheme = createTheme();

const initialValues={
  BookTitle:"",
  Author:"",
  Price:0,
  Quantity:0
}

const AddBook = ()=> {
  const navigate = useNavigate();
 const {values,errors,touched,handleBlur,handleChange,handleSubmit} = useFormik({
    initialValues:initialValues,
    validationSchema:addBookSchema,
    onSubmit:async (values,action)=>{
      console.log(values);
      try {
    
          let uri = `http://localhost:8181/books/add`;
          const response = await axios.post(uri,{
              title: values.BookTitle,
              author: values.Author,
              price : values.Price,
              quantity: values.Quantity,
          });
      
          if(response.status === 201){
            alert(response.data)
            navigate("/");
          }
          else
           throw new Error(response.data);
          } catch (error) {
            alert(error.message);
            navigate("/")
          }
      action.resetForm();
    }
  })
  
  // const handleSubmit = async (event) => {
  //   
  // };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Add Book
          </Typography>
          <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  type='text'
                  name="BookTitle"
                  required
                  fullWidth
                  label="Book Title"
                  onChange={handleChange}
                  onBlur={handleBlur}
                />
                {errors.BookTitle && touched.BookTitle ? <p style={{color:"red",fontSize:13 }}>{errors.BookTitle}</p>:null}
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  type='text'
                  required
                  fullWidth
                  label="Author"
                  name="Author"
                  onChange={handleChange}
                  onBlur={handleBlur}
                //   autoComplete="family-name"
                />
                {errors.Author && touched.Author ? <p style={{color:"red",fontSize:13 }}>{errors.Author}</p>:null}
              </Grid>
              <Grid item xs={12}>
                <TextField
                  id='number'
                  type='number'
                  required
                  fullWidth
                  label="Price Of Book"
                  name="Price"
                  onChange={handleChange}
                  onBlur={handleBlur}
                //   autoComplete="email"
                />
                {errors.Price && touched.Price ? <p style={{color:"red",fontSize:13 }}>{errors.Price}</p>:null}
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  id='number'
                  type='number'
                  name="Quantity"
                  label="Book Quantity"
                  onChange={handleChange}
                  onBlur={handleBlur}
                />
              {errors.Quantity && touched.Quantity ? <p style={{color:"red",fontSize:13 }}>{errors.Quantity}</p>:null}

              </Grid>
              
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Add
            </Button>
            {/* <Grid container justifyContent="flex-end">
              <Grid item>
                <Link href="#" variant="body2">
                  Already have an account? Sign in
                </Link>
              </Grid>
            </Grid> */}
          </Box>
        </Box>
        <Copyright sx={{ mt: 5 }} />
      </Container>
    </ThemeProvider>
  );
}

export default AddBook;
