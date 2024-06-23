package com.project.service;

import com.project.entity.Catalog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CatalogServiceTest {

    @Autowired
    CatalogService catalogService;

    @Test
    void findBookByTitle() {
        Assertions.assertEquals("Java",catalogService.findBookByTitle("Java").getTitle());
    }

    @Test
    void getAllBooks() {
        //List<String> bookTitles = new ArrayList<>();
        String[] bookTitles = new String[6];
        bookTitles[0] = "Rich Dad Poor Dad";
        bookTitles[1] = "The Great Gatsby";
        bookTitles[2] = "To Kill a Mockingbird";
        bookTitles[3] = "All About Love";
        bookTitles[4] = "Java";
        bookTitles[5] = "12 Rules Of Life";

        List<Catalog> books = catalogService.getAllBooks();
        String[] titlesOfBooksFetchedFromDatabase = new String[6];
        int counter = 0;
        for (Catalog book:books
             ) {
            titlesOfBooksFetchedFromDatabase[counter] = book.getTitle();
            counter++;
        }

        Assertions.assertArrayEquals(bookTitles, titlesOfBooksFetchedFromDatabase);
    }

    @Test
    void updateExistingBook(){

        Catalog existingBook = catalogService.findBookByTitle("To Kill a Mockingbird");
        existingBook.setQuantity(10);
        catalogService.updateExistingBook(existingBook);
        Catalog updatedBook = catalogService.findBookByTitle("To Kill a Mockingbird");
        Assertions.assertEquals(10,updatedBook.getQuantity());
    }
}