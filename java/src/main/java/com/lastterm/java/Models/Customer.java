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
public class Customer {
    @Id
    private String customerId;
    private String customerName;
    private String customerAge;
    private String customerPhone;
    private Product product;
}
