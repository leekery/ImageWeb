package com.imageweb.ImageWeb.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private Integer balance;
    @NotBlank
    @NonNull
    private String login;
    @Email
    @NonNull
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordConfirm;
}
