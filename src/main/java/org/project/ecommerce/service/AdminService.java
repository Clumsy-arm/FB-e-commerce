package org.project.ecommerce.service;

import org.project.ecommerce.entity.Admin;
import org.project.ecommerce.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
   private AdminRepo aRepo;
   public void save(Admin a){
       aRepo.save(a);
   }
   public List<Admin> fetchAll(){
       return aRepo.findAll();
   }
}
