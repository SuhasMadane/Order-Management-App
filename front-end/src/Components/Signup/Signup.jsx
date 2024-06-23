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
import { signUpSchema } from '../../schemas';

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
const initialValues ={
  FirstName:"",
  LastName:"",
  Email:"",
  Password:"",
  ConfirmPassword:"",
}
const SignUp = ()=> {
  const navigate = useNavigate();
 const {values,errors,touched,handleBlur,handleChange,handleSubmit} =   useFormik({
     initialValues:initialValues,
     validationSchema:signUpSchema,
     onSubmit:async (values,action)=>{
       console.log(values);
       try {
  
      let uri = `http://localhost:8181/student/signup`;
      const response = await axios.post(uri,{
        
        firstName: values.FirstName,
        lastName: values.LastName,
        emailId : values.Email,
        passWord: values.Password,
      });
  
      if(response.status === 201){
        alert(response.data)
        navigate("/signin");
      }
      else
       throw new Error(response.data);
      } catch (error) {
        alert(error.message);
      }
       action.resetForm();
     }
   })

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
            Sign up
          </Typography>
          <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  name="FirstName"
                  required
                  fullWidth
                  id="FirstName"
                  label="FirstName"
                  value={values.FirstName}
                  onChange={handleChange}
                  onBlur={handleBlur}
                />
                {errors.FirstName && touched.FirstName ?<p style={{color:"red" , fontSize:13}}>{errors.FirstName}</p>: null}
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  required
                  fullWidth
                  id="LastName"
                  label="LastName"
                  name="LastName"
                  value={values.LastName}
                  onChange={handleChange}
                  onBlur={handleBlur}
                />
                {errors.LastName && touched.LastName ?<p style={{color:"red", fontSize:13}}>{errors.LastName}</p>: null}
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  id="Email"
                  label="Email Address"
                  name="Email"
                  value={values.Email}
                  onChange={handleChange}
                  onBlur={handleBlur}
                />
                {errors.Email && touched.Email ?<p style={{color:"red" , fontSize:13}}>{errors.Email}</p>: null}
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="Password"
                  label="Password"
                  type="Password"
                  id="Password"
                  value={values.Password}
                  onChange={handleChange}
                  onBlur={handleBlur}
                />
                {errors.Password && touched.Password ?<p style={{color:"red" , fontSize:13 }}>{errors.Password}</p>: null}
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="ConfirmPassword"
                  label="ConfirmPassword"
                  type="Password"
                  id="Password"
                  value={values.ConfirmPassword}
                  onChange={handleChange}
                  onBlur={handleBlur}
                />
                {errors.ConfirmPassword && touched.ConfirmPassword ?<p style={{color:"red", fontSize:13}}>{errors.ConfirmPassword}</p>: null}
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign Up
            </Button>
            <Grid container justifyContent="flex-end">
              <Grid item>
                <Link href="/signin" variant="body2">
                  Already have an account? Sign in
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Copyright sx={{ mt: 5 }} />
      </Container>
    </ThemeProvider>
  );
}

export default SignUp;
