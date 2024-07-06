package com.imageweb.ImageWeb.user.controller;


import com.imageweb.ImageWeb.user.service.UserService;
import com.imageweb.ImageWeb.user.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/registration")
@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService service;

    @PostMapping
    public String addUser(@ModelAttribute("userForm") @Valid UserDto userForm,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("loginError", "Пароль неверный");
            return "registration";
        }

        if (!service.saveUser(userForm)) {
            model.addAttribute("loginError", "пользователь с таким именем уже существует");
            return "registration";
        }

        return "redirect:/";
    }

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("userForm", new UserDto());

        return "registration";
    }
}
