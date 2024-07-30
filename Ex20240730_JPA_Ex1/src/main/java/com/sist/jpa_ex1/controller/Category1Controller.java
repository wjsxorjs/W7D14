package com.sist.jpa_ex1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.jpa_ex1.repository.Category1Repository;
import com.sist.jpa_ex1.store.Category1JPO;

@RestController
public class Category1Controller {
    @Autowired
    private Category1Repository category1Repository;

    @GetMapping("test")
    public String test(){

        Category1JPO c1 = Category1JPO.builder()
                                    .cName("test")
                                    .desc("Tester")
                                    .status(0)
                                    .build();
        
        // category1Repository.save(c1); // 저장

        return "TEST";
    }
}
