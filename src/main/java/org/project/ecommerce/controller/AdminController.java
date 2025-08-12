package org.project.ecommerce.controller;

import org.project.ecommerce.dto.ProductDtO;
import org.project.ecommerce.entity.Admin;
import org.project.ecommerce.entity.Product;
import org.project.ecommerce.service.AdminService;
import org.project.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.project.ecommerce.entity.Category;
import org.project.ecommerce.service.CategoryService;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class AdminController {

    private final CategoryService cservice;
    private final ProductService pservice;
    private final AdminService aService;
    @Value("${upload.path}")
    private String uploadDir;
    //public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    @Autowired
    public AdminController(CategoryService cservice, ProductService pservice, AdminService aService) {
        this.cservice = cservice;
        this.pservice = pservice;
        this.aService = aService;
    }


    @GetMapping("/admin")
    public String admin() {
        return "login";
    }
    @RequestMapping("/register")
    public String register(String email, String password) {
        if(!(email == null && password == null)) {
            Admin a = new Admin();
            a.setEmail(email);
            a.setPassword(password);
            aService.save(a);
        }
        return "register";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password,Model model) {
        List<Admin> list = aService.fetchAll() ;
        for(Admin a : list) {
            if(a.getEmail().equals(email) && a.getPassword().equals(password)) {
                model.addAttribute("userobject", a);
                return "admin" ;
            }
        }

            return "login" ;

    }
    @GetMapping("/admin/categories")
    public String categorypage(Model model) {
        List<Category> list = cservice.getAll();
        model.addAttribute("categories",list);
        return "categories" ;
    }
    @GetMapping("/admin/categories/add")
    public String Addcategory(Model model) {
        Category c =  new Category();
        model.addAttribute("category",c);
        return "categoriesAdd" ;
    }
    @PostMapping("admin/categories/add")
        public String postAddCategory(@ModelAttribute("category") Category c){
            cservice.SaveCategory(c) ;
            return "redirect:/admin/categories" ;
    }
    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id){
        cservice.DeleteCategory(id);
        return "redirect:/admin/categories" ;
    }
    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(@PathVariable("id") int id,Model model){
       Optional<Category>  category = cservice.fetchbyId(id) ;
       if(category.isPresent()){
           model.addAttribute("category",category.get());
           return "categoriesAdd" ;
       } else{
           return "error" ;
       }

    }
    @GetMapping("/admin/products")
    public String productPage(Model model){
        List<Product> list = pservice.findAll() ;
        model.addAttribute("products",list);
        return "products" ;
    }
    @GetMapping("/admin/products/add")
    public String addProductGet(Model model) {
        model.addAttribute("productDTO", new ProductDtO());
        model.addAttribute("categories", cservice.getAll());

        return "productsAdd";
    }
    @PostMapping("/admin/products/add")
    public String postAddProduct(@ModelAttribute("productDtO") ProductDtO p, @RequestParam("productImage") MultipartFile file,
                                 @RequestParam("imgName")String imgName) throws IOException {
        Product pro = new Product() ;
        pro.setId(p.getId());
        pro.setName(p.getName());
        pro.setPrice(p.getPrice());
        pro.setWeight(p.getWeight());
        pro.setDescription(p.getDescription());
        pro.setCategory(cservice.fetchbyId(p.getCategoryId()).orElseThrow()) ;
        String imageUUID;
        if(!file.isEmpty()) {
            imageUUID = UUID.randomUUID().toString();
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String secureFilename = imageUUID + "." + extension;
            Path path = Paths.get(uploadDir, secureFilename);
            Files.write(path, file.getBytes());
            pro.setImageName(secureFilename);
        }
        pservice.saveProduct(pro);
        return "redirect:/admin/products";
    }
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        pservice.deleteById(id);
        return "redirect:/admin/products";
    }
    @GetMapping("/admin/product/update/{id}")
    public String updateProductGet(@PathVariable("id") int id,Model model){
        Product pro = pservice.fetchById(id).get();
        ProductDtO p = new ProductDtO();
        p.setId(pro.getId());
        p.setName(pro.getName());
        p.setPrice(pro.getPrice());
        p.setWeight(pro.getWeight());
        p.setDescription(pro.getDescription());
        p.setCategoryId(pro.getCategory().getId());
        p.setImageName(pro.getImageName());

        model.addAttribute("categories" , cservice.getAll());
        model.addAttribute("productDTO",p);
        return "productsAdd";
    }
}

