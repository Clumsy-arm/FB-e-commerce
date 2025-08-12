package org.project.ecommerce.controller;

import org.project.ecommerce.entity.Product;
import org.project.ecommerce.service.CategoryService;
import org.project.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class PageController {
    @Autowired
    private CategoryService cService;
    @Autowired
    private ProductService pService;
    @GetMapping("/shop")
    public String shop(Model model){
       model.addAttribute("categories", cService.getAll());
       model.addAttribute("products", pService.findAll());
       return "shop";
    }
    @GetMapping("/shop/category/{id}")
    public String category(Model model, @PathVariable("id") int id){
        model.addAttribute("categories", cService.getAll());
        model.addAttribute("products", pService.getProductByCategoryId(id));
        return "shop" ;
    }
    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable("id") int id) {
        model.addAttribute("product",pService.fetchById(id).get() );
        return "viewProduct" ;
    }
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id, Model model) {
        // 1. Fetch the product using the ID from the URL
        Product product = null;
        try {
            product = pService.fetchById(id)
                    .orElseThrow(() -> new IOException("Product not found for ID: " + id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 2. Add the fetched product to the model so the form can display it
        model.addAttribute("product", product);

        // 3. Return the name of your customer information form template
        return "userform";
    }
    @GetMapping("/userform")
    public String userForm(Model model){
        return "userform";
    }
}
