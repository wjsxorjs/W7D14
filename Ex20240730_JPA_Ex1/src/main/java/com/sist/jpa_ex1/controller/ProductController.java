package com.sist.jpa_ex1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.jpa_ex1.repository.ProductRepository;
import com.sist.jpa_ex1.store.ProductJPO;
import org.springframework.web.bind.annotation.RequestParam;


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

    @GetMapping("list")
    public String getList() {
        List<ProductJPO> list = productRepository.findAll();
        StringBuffer sb = new StringBuffer();
        for(ProductJPO pvo: list){
            sb.append(pvo.getPNum());
            sb.append(" || ");
            sb.append(pvo.getPName());
            sb.append(" || ");
            sb.append(pvo.getC1vo().getCName());
            sb.append(" || ");
            sb.append(pvo.getC1vo().getDesc());
            sb.append(" <br/> ");
        }


        return sb.toString();
    }
    
}
