package org.project.ecommerce.service;

import org.project.ecommerce.entity.Product;
import org.project.ecommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepo prepo ;

    public void saveProduct(Product p){
        prepo.save(p);
    }
    public List<Product> findAll(){
        return prepo.findAll();
    }
    public void deleteById(Integer id){
        prepo.deleteById(id);
    }
    public Optional<Product> fetchById(Integer id){
        return prepo.findById(id) ;
    }
    public List<Product> getProductByCategoryId(Integer id){
        return prepo.findAllByCategoryId(id) ;
    }
}
