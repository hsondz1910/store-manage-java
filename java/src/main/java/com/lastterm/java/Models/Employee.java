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
public class Employee {
    @Id
    private String employeeId;
    private String employeeName;
    private String employeeAge;
    private String employeePhone;
    private String employeeAddress;
}
