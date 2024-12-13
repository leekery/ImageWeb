package com.imageweb.ImageWeb.user.service;

import com.imageweb.ImageWeb.user.dto.UserDto;
import com.imageweb.ImageWeb.user.exception.UserConflictException;
import com.imageweb.ImageWeb.user.model.User;
import com.imageweb.ImageWeb.user.dto.UserMapper;
import com.imageweb.ImageWeb.user.exception.UserNotFoundException;
import com.imageweb.ImageWeb.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);

        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserConflictException("Пользователь с таким email уже существует");
        }

        log.info("Создание пользователя");
        repository.save(user);
        return mapper.userToUserDto(repository.save(user));
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
    public List<UserDto> getAllUsers() {
        log.info("Вывод всех пользователей");
        List<User> users = repository.findAll();
        List<UserDto> listDto = new ArrayList<>();

        for (User user : users) {
            listDto.add(mapper.userToUserDto(user));
        }

        return listDto;
    }
}
