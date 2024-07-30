package com.sist.jpa_ex1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.jpa_ex1.repository.ProductRepository;
import com.sist.jpa_ex1.store.ProductJPO;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("test")
    public String test(){

        ProductJPO p1 = ProductJPO.builder()
                                    .pName("test")
                                    .pCompany("Tester")
                                    .build();
        
        productRepository.save(p1); // 저장

        return "TEST";
    }
}
