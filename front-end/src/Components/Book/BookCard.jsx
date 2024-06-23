import * as React from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { useNavigate } from "react-router-dom";
import bookImage from "./bookImage.jpg"
import { replace } from "formik";

const BookCard = (props) => {
  const navigate = useNavigate();
  // console.log(props.title)

  const handleClick = () => {
    const email = localStorage.getItem("Student email");
    if (email) {
      const bookData = { bookTitle: props.title, bookPrice: props.price };
      console.log(bookData);
      navigate("/createOrder", { state: { bookData } ,replace:true});
    } else {
      alert("Sign In First");
      navigate("/signin");
    }
  };

  return (
    <Card className="m-4" sx={{ maxWidth: 200, minWidth: 200, maxHeight: 200, minHeight: 350 }}>
      <CardMedia component=""/>
      <img src={bookImage} alt="green iguana" height="140"/>
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {props.title}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          Author: {props.author}
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small">Rs.{props.price}</Button>
        <Button size="small" onClick={handleClick}>
          Buy Now
        </Button>
      </CardActions>
    </Card>
  );
};

export default BookCard;
