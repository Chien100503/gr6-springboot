package com.example.buoi2.services;

import com.example.buoi2.dto.UserRequest;
import com.example.buoi2.models.Company;
import com.example.buoi2.models.Role;
import com.example.buoi2.models.User;
import com.example.buoi2.repositories.CompanyRepository;
import com.example.buoi2.repositories.RoleRepository;
import com.example.buoi2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public void saveOrUpdate(User user) {
        userRepository.save(user);
    }

    // Lấy danh sách tất cả User
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Lấy User theo ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatusCode.valueOf(404), "User not found with id: " + id));
    }

    // Tạo mới User
    public User createUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setEmail(userRequest.getEmail());

        // Liên kết Role từ roleIds (dùng Set)
        Set<Role> roles = userRequest.getRoleIds().stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Role with ID " + roleId + " not found")))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        // Liên kết Company từ companyId
        if (userRequest.getCompanyId() != null) {
            Company company = companyRepository.findById(Math.toIntExact(userRequest.getCompanyId()))
                    .orElseThrow(() -> new RuntimeException("Company with ID " + userRequest.getCompanyId() + " not found"));
            user.setCompany(company);
        }

        return userRepository.save(user);
    }

    // Cập nhật User
    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setEmail(user.getEmail());
        existingUser.setCompany(user.getCompany());
        existingUser.setRoles(user.getRoles());
        return userRepository.save(existingUser);
    }

    // Xóa User
    public void deleteUser(Long id) {
        User existingUser = getUserById(id);
        userRepository.delete(existingUser);
    }
}
