package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@RequestMapping("/admin")
@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model, @AuthenticationPrincipal UserDetails admin, User addUser) {
        User thisUser = userService.findUserByUsername(admin.getUsername());
        model.addAttribute("usersList", userService.allUsers());
        model.addAttribute("thisUser", thisUser);
        model.addAttribute("role_admin", roleService.findRoleByName("ROLE_ADMIN"));
        model.addAttribute("role_user", roleService.findRoleByName("ROLE_USER"));
        model.addAttribute("addUser", addUser);
        return "users";
    }

    @PostMapping(value = "/addUser")
    public String addNewUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin/users";
    }

    @PostMapping("edit")
    public String updateUserById(@ModelAttribute("user") User user, @RequestParam("id") long id) {
        userService.edit(user, id);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete")
    public String deleteUserById(@RequestParam(value = "id") long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
