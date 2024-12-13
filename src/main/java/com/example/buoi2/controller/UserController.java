    package com.example.buoi2.controller;

    import com.example.buoi2.dto.RegisterDto;
    import com.example.buoi2.models.Company;
    import com.example.buoi2.models.Role;
    import com.example.buoi2.models.User;
    import com.example.buoi2.repositories.RoleRepository;
    import com.example.buoi2.repositories.UserRepository;
    import com.example.buoi2.services.CompanyService;
    import com.example.buoi2.services.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import java.util.Collections;
    import java.util.List;

    @Controller
    @RequestMapping("/user")
    public class UserController {

        @Autowired
        private UserService userService;
        @Autowired
        private CompanyService companyService;

        @GetMapping("/list")
        public String showUserList(Model model) {
            List<User> users = userService.getAllUsers();

            model.addAttribute("users", users);
            return "userList";
        }

        @GetMapping("/add")
        public String showAddUserForm(Model model) {
            model.addAttribute("user", new User());
            List<Company> companies = companyService.getAllCompany();
            model.addAttribute("companies", companies);
            return "addUser";
        }

        @PostMapping("/add")
        public String saveUser(@ModelAttribute User user, @RequestParam("companyId") Long companyId) {
            Company company = companyService.getCompanyById(companyId);
            user.setCompany(company); // Gán Company cho User
            userService.saveOrUpdate(user);
            return "redirect:/user";
        }
    }
