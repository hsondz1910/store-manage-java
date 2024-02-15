package com.lastterm.java.Models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document
public class Product {
    @Id
    private String productId;
    private String barCode;
    private String productName;
    private String importPrice;
    private String price;
    private String category;
    private String dateCreated;
}

