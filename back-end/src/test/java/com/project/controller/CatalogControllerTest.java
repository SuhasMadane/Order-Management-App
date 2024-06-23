package com.project.controller;

import com.project.entity.Catalog;
import com.project.logging.CustomLog;
import com.project.request.CatalogRequest;
import com.project.service.CatalogService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = CatalogController.class)
class CatalogControllerTest {

    @MockBean
    private CatalogService catalogService;


    @MockBean
    private CustomLog customLog;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAndAddBook() throws Exception{
        CatalogRequest catalogRequest = new CatalogRequest();
        catalogRequest.setTitle("New Book");
        catalogRequest.setAuthor("New Author");
        catalogRequest.setQuantity(11);
        catalogRequest.setPrice(299);

//        Mockito.when(catalogService.addBook(catalogRequest)).thenReturn()

        mockMvc.perform(MockMvcRequestBuilders
                .post("/books/add")
                .content(new ObjectMapper().writeValueAsString(catalogRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(content().string("Book Added Succesfully"));
    }

    @Test
    void getAllBooks() throws Exception{

        Catalog book1 = new Catalog();
        book1.setTitle("Book1");
        book1.setAuthor("Author1");
        book1.setPrice(199);
        book1.setQuantity(8);

        Catalog book2 = new Catalog();
        book2.setTitle("Book2");
        book2.setAuthor("Author2");
        book2.setPrice(149);
        book2.setQuantity(13);
        List<Catalog> books = Arrays.asList(book1,book2);

        Mockito.when(catalogService.getAllBooks()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/getbooks")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Book1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Book2"));
    }

    @Test
    void getBookByTitle() throws Exception{
        Catalog book1 = new Catalog();
        book1.setTitle("New Book");
        book1.setAuthor("Author");
        book1.setPrice(229);
        book1.setQuantity(2);

           Mockito.when(catalogService.findBookByTitle("New Book")).thenReturn(book1);

           mockMvc.perform(MockMvcRequestBuilders.get("/books/getSingleBook")
                   .accept(MediaType.APPLICATION_JSON)
                   .param("title","New Book"))
                   .andExpect(status().isOk())
                   .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(229));
    }
}