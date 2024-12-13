package com.example.buoi2.repositories;

import com.example.buoi2.models.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyRepository extends CrudRepository<Company, Integer>  {

    List<Company> findAll();
}
