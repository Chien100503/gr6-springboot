package com.example.buoi2.controller;

import com.example.buoi2.models.Company;
import com.example.buoi2.models.User;
import com.example.buoi2.services.CompanyService;
import com.example.buoi2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    // Display all companies
    @GetMapping("/all")
    public String getAllCompanies(Model model) {
        List<Company> companies = companyService.getAllCompany();
        System.out.println(companies);
        model.addAttribute("companies", companies);
        return "companyList";
    }

    // Show form to add a new company
    @GetMapping("/add")
    public String showAddCompanyForm(Model model) {
        model.addAttribute("company", new Company());
        List<User> users = userService.getAllUsers(); // Lấy danh sách user
        model.addAttribute("users", users);          // Thêm vào model
        return "addCompany";
    }

    // Save a new or existing company
    @PostMapping("/add")
    public String saveCompany(@ModelAttribute Company company) {
        companyService.saveOrUpdate(company);
        return "redirect:/company/all";
    }

    // Show company details along with its users
    @GetMapping("/edit/{id}")
    public String showEditCompanyForm(@PathVariable("id") int id, Model model) {
        Company company = companyService.getCompanyById((long) id);
        List<User> users = userService.getAllUsers();
        model.addAttribute("company", company);
        model.addAttribute("users", users);
        return "editCompany";
    }

    // Update company information
    @PostMapping("/update")
    public String updateCompany(@ModelAttribute Company company) {
        companyService.saveOrUpdate(company);
        return "redirect:/company/all";
    }

    // Delete a company
    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable("id") Long id) {
        companyService.deleteCompany(id);
        return "redirect:/company/all";
    }
}
