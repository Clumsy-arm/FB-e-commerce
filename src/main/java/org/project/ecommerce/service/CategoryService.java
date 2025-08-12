package org.project.ecommerce.service;

import org.project.ecommerce.entity.Category;
import org.project.ecommerce.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo crepo;
    public void SaveCategory(Category C) {
        crepo.save(C);
    }
    public List<Category> getAll(){
            return crepo.findAll();
    }
    public void DeleteCategory(int id) {
        crepo.deleteById(id);
    }
    public Optional<Category> fetchbyId(int id) {
        return crepo.findById(id);
    }

}
