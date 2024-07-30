package com.sist.jpa_ex1.store;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "product_t")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductJPO {
    @Id
    @GeneratedValue
    private Long pNum;
    private String pName;
    private String pCompany;
    private LocalDate regDate;
    @Column(name = "category1")
    private int category1;

    private int category2;
    private int category3;


    @ManyToOne // 참조관계 명시
    @JoinColumn(name="category1",
                insertable = false, // 참조컬럼 명시
                updatable = false)
    private Category1JPO c1vo; // 참조객체 명시
}
