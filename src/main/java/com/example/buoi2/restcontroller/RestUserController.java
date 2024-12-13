package com.example.buoi2.restcontroller;

import com.example.buoi2.dto.UserRequest;
import com.example.buoi2.models.Company;
import com.example.buoi2.models.Role;
import com.example.buoi2.models.User;
import com.example.buoi2.response.UserResponse;
import com.example.buoi2.services.UserService;
import com.example.buoi2.utils.ApiResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestUserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        List<User> users = userService.getAllUsers();

        List<UserResponse> userResponses = users.stream().map(user -> {
            Long companyId = (user.getCompany() != null) ? user.getCompany().getId() : null;
            return new UserResponse(
                    user.getId(),
                    user.getFirstname(),
                    user.getLastname(),
                    user.getEmail(),
                    companyId,
                    user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())
            );
        }).collect(Collectors.toList());

        return new ApiResponse<>("Success", 200, userResponses);
    }


    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user == null) {
            return new ApiResponse("Not found", 404);
        }
        Long companyId = (user.getCompany() != null) ? user.getCompany().getId() : null;
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                companyId,
                user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())
        );

        return new ApiResponse<>("Success", 200, userResponse);
    }


    @PostMapping("/create")
    public ApiResponse<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        User createdUser = userService.createUser(userRequest);

        Long companyId = (createdUser.getCompany() != null) ? createdUser.getCompany().getId() : null;
        UserResponse userResponse = new UserResponse(
                createdUser.getId(),
                createdUser.getFirstname(),
                createdUser.getLastname(),
                createdUser.getEmail(),
                companyId,
                createdUser.getRoles().stream().map(Role::getName).collect(Collectors.toList())
        );

        return new ApiResponse<>("User created successfully", 201, userResponse);
    }



    @PutMapping("/update/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {

        User userToUpdate = new User();
        userToUpdate.setFirstname(userRequest.getFirstname());
        userToUpdate.setLastname(userRequest.getLastname());
        userToUpdate.setEmail(userRequest.getEmail());

        if (userRequest.getCompanyId() != null) {
            Company company = new Company();
            company.setId(userRequest.getCompanyId());
            userToUpdate.setCompany(company);
        }

        if (userRequest.getRoleIds() != null) {
            Set<Role> roles = userRequest.getRoleIds().stream().map(roleId -> {
                Role role = new Role();
                role.setId(roleId);
                return role;
            }).collect(Collectors.toSet());
            userToUpdate.setRoles(roles);
        }

        User updatedUser = userService.updateUser(id, userToUpdate);


        Long companyId = (updatedUser.getCompany() != null) ? updatedUser.getCompany().getId() : null;
        UserResponse userResponse = new UserResponse(
                updatedUser.getId(),
                updatedUser.getFirstname(),
                updatedUser.getLastname(),
                updatedUser.getEmail(),
                companyId,
                updatedUser.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())
        );

        return new ApiResponse<>("User updated successfully", 200, userResponse);
    }


    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ApiResponse<>("User deleted successfully", 200);
    }
}
