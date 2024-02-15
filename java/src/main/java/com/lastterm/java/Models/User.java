package com.lastterm.java.Models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {
    private String userId;
    private String email;
    private String username;
    private String password;
    private int age;
    private String phone;
    private String role; // ví dụ: "admin", "employee"
}
