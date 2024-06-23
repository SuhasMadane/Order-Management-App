package com.project.repository;

import com.project.entity.Catalog;
import org.assertj.core.api.Assertions;
import org.hibernate.annotations.processing.SQL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CatalogRepoTest {

    @Autowired
    private CatalogRepo catalogRepo;

    @Test
    void shouldSaveBook(){
        Catalog book = new Catalog();
        book.setTitle("OOPS");
        book.setPrice(299);
        book.setQuantity(9);
        book.setAuthor("Alen Key");
        Catalog savedBook = catalogRepo.save(book);
        Assertions.assertThat(savedBook).usingRecursiveComparison().ignoringFields("book_Id").isEqualTo(book);
    }

    @Test
    @Sql("classpath:test-data.sql")
    void findCatalogByTitle() {
//        Catalog newbook = new Catalog();
//        newbook.setTitle("OOPS");
//        newbook.setPrice(299);
//        newbook.setQuantity(9);
//        newbook.setAuthor("Alen Key");
//        catalogRepo.save(newbook);
        Catalog book = catalogRepo.findCatalogByTitle("new_book");
        Assertions.assertThat(book.getTitle()).isEqualTo("new_book");
    }
}