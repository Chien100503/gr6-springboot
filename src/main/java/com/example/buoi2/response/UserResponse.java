package com.example.buoi2.response;

import com.example.buoi2.models.Company;

import java.util.List;

public class UserResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Long companyId;
    private List<String> roles;

    public UserResponse(Long id, String firstname, String lastname, String email, Long company, List<String> roles) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.companyId = company;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCompany() {
        return companyId;
    }

    public void setCompany(Long company) {
        this.companyId = company;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
