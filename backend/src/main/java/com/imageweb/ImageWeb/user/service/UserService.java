package com.imageweb.ImageWeb.user.service;

import com.imageweb.ImageWeb.role.Role;
import com.imageweb.ImageWeb.user.dto.UserDto;
import com.imageweb.ImageWeb.user.model.User;
import com.imageweb.ImageWeb.user.dto.UserMapper;
import com.imageweb.ImageWeb.user.exception.UserNotFoundException;
import com.imageweb.ImageWeb.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = repository.findByLogin(login);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        return user.get();
    }

    @Transactional
    public boolean saveUser(UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);

        if (repository.findByLogin(user.getLogin()).isPresent()
                || repository.findByEmail(user.getEmail()).isPresent()) {
            return false;
        }

        user.setRole(new Role(1, "ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Создание пользователя");
        repository.save(user);
        return true;
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long userId) {
        Optional<User> user = repository.findById(userId);

        if (user.isEmpty()) {
            log.error("Пользователь с id = {} не найден", userId);
            throw new UserNotFoundException("Пользователь не найден");
        }

        log.info("Вывод пользователя с id = {}", userId);
        return mapper.userToUserDto(user.get());
    }

    @Transactional
    public void deleteUser(Long userId) {
        Optional<User> user = repository.findById(userId);

        if (user.isEmpty()) {
            log.error("Пользователь с id = {} не найден", userId);
            throw new UserNotFoundException("Пользователь не найден");
        }

        log.info("Удаление пользователя с id = {}", userId);
        repository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        log.info("Вывод всех пользователей");
        return repository.findAll();
    }
}
