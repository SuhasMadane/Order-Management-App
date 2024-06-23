package com.project.controller;

import com.project.entity.Catalog;
import com.project.exception.CatalogException;
import com.project.logging.CustomLog;
import com.project.request.CatalogRequest;
import com.project.service.CatalogService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "http://localhost:3000")
public class CatalogController {

    private Logger logger= LoggerFactory.getLogger(CatalogController.class);
    @Autowired
    private CatalogService catalogService;

    @Autowired
    private CustomLog customLog;

    @PostMapping("/add")
    public ResponseEntity<String> createAndAddBook(@Valid  @RequestBody CatalogRequest book){
       String methodName = "CatalogController.createAndAddBook";
        Optional<Catalog> existingBook = Optional.ofNullable(catalogService.findBookByTitle(book.getTitle()));
        try{
            if( existingBook.isPresent())
                throw new CatalogException("Book Already Exists,Just Updating Existing Book");
            Catalog newBook = new Catalog();
            newBook.setTitle(book.getTitle());
            newBook.setAuthor(book.getAuthor());
            newBook.setPrice(book.getPrice());
            newBook.setQuantity(book.getQuantity());
            catalogService.addBook(newBook);
            logger.info("Book Created");
            customLog.generateLog(null,methodName,"Inside Method",null,"Book Created");
            return ResponseEntity.status(HttpStatus.CREATED).body("Book Added Succesfully");
        }
        catch (CatalogException c){
            Catalog oldBook = existingBook.get();
            oldBook.setQuantity(oldBook.getQuantity()+book.getQuantity());
            oldBook.setPrice(book.getPrice());
            catalogService.updateExistingBook(oldBook);
            logger.info("Book Already exists so just updating");
            customLog.generateLog(null,methodName,"Exception Occurred",c.getMessage(),"Book Already exists so just updating");
            return ResponseEntity.status(HttpStatus.OK).body("Message: "+c.getMessage());
        }
    }

    @GetMapping("/getbooks")
    public ResponseEntity<List<Catalog>> getAllBooks(){
        String methodName = "CatalogController.getAllBooks";
//        Optional<Catalog> existingBook = Optional.ofNullable(catalogService.findBookById(1));
//        if(existingBook.isPresent()){

            List<Catalog> existingBooks = catalogService.getAllBooks();
            logger.info("Loaded all books");
            customLog.generateLog(null,methodName,"Inside Method: Fetching Books from Database",null,"Loaded All Books");
            return ResponseEntity.status(HttpStatus.OK).body(existingBooks);

//        else{
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
    }

    @GetMapping("/getSingleBook")
    public ResponseEntity<Catalog> getBookByTitle(@RequestParam String title){
        Optional<Catalog> existingBook = Optional.ofNullable(catalogService.findBookByTitle(title));
        if(existingBook.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(existingBook.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
