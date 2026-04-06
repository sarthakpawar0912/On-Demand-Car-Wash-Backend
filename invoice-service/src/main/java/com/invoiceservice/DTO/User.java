package com.invoiceservice.DTO;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String name;     // optional but useful
    private String email;
    private String phone;    // 🔥 ADD THIS
}