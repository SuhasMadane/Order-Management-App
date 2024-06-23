import axios from "axios";
import React, { useEffect, useState } from "react";
import BookCard from "../Book/BookCard";
import NavBar from "../NavBar/NavBar";
import SlideBar from "../SlideBar/SlideBar";

const MainPage = () => {
  const [books, setBooks] = useState([]);

  useEffect(() => {
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

  // console.log(books)
  return (
    <>
      <div>
        <NavBar />
        <SlideBar />
        <div className="row ms-4" >
            {books.map((book) => (
              // console.log(book);
              
              <div className="col-3">
              <BookCard
                key={book.book_Id}
                title={book.title}
                author={book.author}
                price={book.price}
              />
              </div>
            ))}
          
        </div>
      </div>
    </>
  );
};

export default MainPage;
