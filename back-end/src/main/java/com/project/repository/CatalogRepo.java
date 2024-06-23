package com.project.repository;

import com.project.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CatalogRepo extends JpaRepository<Catalog,Long> {

    @Query("Select c from Catalog c where c.book_Id = :id")
    public Catalog findCatalogByBook_Id(long id);

    public Catalog findCatalogByTitle(String title);

}
