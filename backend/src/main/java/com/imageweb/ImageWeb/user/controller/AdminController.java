package com.imageweb.ImageWeb.user.controller;

import com.imageweb.ImageWeb.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {
    private UserService service;

    @PostMapping("/{userId}")
    public String deleteUser(@PathVariable(required = false) Long userId,
                             @RequestParam(required = false) String action,
                             Model model) {
        if (action.equals("delete")) {
            service.deleteUser(userId);
        }

        return "redirect:/admin";
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("allUsers", service.getAllUsers());
        return "admin";
    }

    @GetMapping("/get/{userId}")
    public String getUser(@PathVariable Long userId, Model model) {
        model.addAttribute("allUsers", service.getUserById(userId));
        return "admin";
    }
}
