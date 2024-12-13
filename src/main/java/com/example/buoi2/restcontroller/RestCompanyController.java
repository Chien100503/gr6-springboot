package com.example.buoi2.restcontroller;

import com.example.buoi2.models.Company;
import com.example.buoi2.response.CompanyResponse;
import com.example.buoi2.services.CompanyService;
import com.example.buoi2.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
public class RestCompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ApiResponse<List<CompanyResponse>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompany();

        List<CompanyResponse> companyResponses = companies.stream().map(company ->
                new CompanyResponse(
                        company.getId(),
                        company.getName(),
                        company.getUsers().stream().map(user -> user.getId()).collect(Collectors.toList())
                )
        ).collect(Collectors.toList());

        return new ApiResponse<>("Success", 200, companyResponses);
    }


    @GetMapping("/{id}")
    public ApiResponse<CompanyResponse> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);

        if (company == null) {
            return new ApiResponse("Company not found", 404);
        }

        CompanyResponse companyResponse = new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getUsers().stream().map(user -> user.getId()).collect(Collectors.toList())
        );

        return new ApiResponse<>("Success", 200, companyResponse);
    }


    @PostMapping("/create")
    public ApiResponse<CompanyResponse> createCompany(@RequestBody Company company) {
        Company createdCompany = companyService.createCompany(company);

        CompanyResponse companyResponse = new CompanyResponse(
                createdCompany.getId(),
                createdCompany.getName(),
                createdCompany.getUsers().stream().map(user -> user.getId()).collect(Collectors.toList())
        );

        return new ApiResponse<>("Company created successfully", 201, companyResponse);
    }


    @PutMapping("/update/{id}")
    public ApiResponse<CompanyResponse> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        Company updatedCompany = companyService.updateCompany(id, company);

        CompanyResponse companyResponse = new CompanyResponse(
                updatedCompany.getId(),
                updatedCompany.getName(),
                updatedCompany.getUsers().stream().map(user -> user.getId()).collect(Collectors.toList())
        );

        return new ApiResponse<>("Company updated successfully", 200, companyResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return new ApiResponse<>("Company deleted successfully", 200);
    }
}
