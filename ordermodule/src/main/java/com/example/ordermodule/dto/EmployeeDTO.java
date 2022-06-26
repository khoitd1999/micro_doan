package com.example.ordermodule.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class EmployeeDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String role;
    private Boolean status;
    private String email;
    private String phone;
}
