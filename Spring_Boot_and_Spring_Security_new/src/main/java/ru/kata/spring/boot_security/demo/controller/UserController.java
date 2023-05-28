package ru.kata.spring.boot_security.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.configs.WebSecurityConfig;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("/user")
    public String infoUser(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "userinfo";
    }

    @GetMapping("/admin/users")
    public String allUsers(Model model, @AuthenticationPrincipal UserDetails admin, User addUser) {
        User thisUser = userService.findUserByUsername(admin.getUsername());
        model.addAttribute("usersList", userService.allUsers());
        model.addAttribute("thisUser", thisUser);
        model.addAttribute("role_admin", roleService.findRoleByName("ROLE_ADMIN"));
        model.addAttribute("role_user", roleService.findRoleByName("ROLE_USER"));
        model.addAttribute("addUser", addUser);
        return "users";
    }

    @PostMapping(value = "/admin/addUser")
    public String create(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/edit")
    public String update(@ModelAttribute("user") User user, @RequestParam("id") long id) {
        userService.edit(user, id);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/delete")
    public String delete(@RequestParam(value = "id") long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
