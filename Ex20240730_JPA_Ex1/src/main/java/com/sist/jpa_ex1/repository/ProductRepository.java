package com.sist.jpa_ex1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.jpa_ex1.store.ProductJPO;

public interface ProductRepository extends JpaRepository<ProductJPO, Long>{
    
}
