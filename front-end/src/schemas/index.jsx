import { Password } from "@mui/icons-material";
import * as Yup from "yup";
import YupPassword from "yup-password";
YupPassword(Yup);

export const signUpSchema = Yup.object({
  FirstName: Yup.string()
    .min(2)
    .max(25)
    .required("Please enter your First Name"),
  LastName: Yup.string()
    .min(3)
    .max(25)
    .required("Please enter your Last Name"),
  Email: Yup.string().email().required("Please enter your email"),
  Password: Yup.string()
    .min(
      8,
      "'password must contain 8 or more characters with at least one of each: uppercase, lowercase, number and special"
    )
    .required("Please enter your password")
    .minLowercase(1, "password must contain at least 1 lower case letter")
    .minUppercase(1, "password must contain at least 1 upper case letter")
    .minNumbers(1, "password must contain at least 1 number")
    .minSymbols(1, "password must contain at least 1 special character"),
  ConfirmPassword: Yup.string()
    .required()
    .oneOf([Yup.ref("Password"), null], "Password must match"),
});

export const signInSchema = Yup.object({
  Email:Yup.string().email().required("Please enter your email"),
  Password:Yup.string().required("Please enter your password")
})

export const addBookSchema = Yup.object({
  BookTitle:Yup.string().required("Please enter book title").min(2),
  Author:Yup.string().required("Please enter name of the author").min(2),
  Price:Yup.number().required("Please enter price of book").moreThan(0,"Price of book should be greater than 0"),
  Quantity:Yup.number().required().moreThan(0,"Quantity should be greater than 0")
})

export const createOrderSchema = Yup.object({
  BookTitle:Yup.string().required("Please enter book title").min(2),
  Quantity:Yup.number().required().moreThan(0,"Quantity should be greater than 0")
})
