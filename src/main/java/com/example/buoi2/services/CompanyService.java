package com.example.buoi2.services;

import com.example.buoi2.models.Company;
import com.example.buoi2.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public void saveOrUpdate(Company company) {
        companyRepository.save(company);
    }

    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new HttpClientErrorException( HttpStatusCode.valueOf(404), "Company not found with id: " + id));
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, Company company) {
        Company existingCompany = getCompanyById(id);
        existingCompany.setName(company.getName());
        return companyRepository.save(existingCompany);
    }

    public void deleteCompany(Long id) {
        Company existingCompany = getCompanyById(id);
        companyRepository.delete(existingCompany);
    }
}
