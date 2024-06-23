package com.project.service;

import com.project.entity.Catalog;
import com.project.repository.CatalogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    @Autowired
    private CatalogRepo catalogRepo;

    public void addBook(Catalog book){
        catalogRepo.save(book);
    }

    public Catalog findBookById(long id){
        return catalogRepo.findCatalogByBook_Id(id);
    }

    public List<Catalog> getAllBooks(){
        return catalogRepo.findAll();
    }

    public Catalog findBookByTitle(String title){
        return catalogRepo.findCatalogByTitle(title);
    }

    public void updateExistingBook(Catalog book){
        catalogRepo.save(book);
    }
}
