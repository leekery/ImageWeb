package com.imageweb.ImageWeb.picture.service;

import com.imageweb.ImageWeb.picture.exception.PictureConflictException;
import com.imageweb.ImageWeb.picture.exception.PictureNotFoundException;
import com.imageweb.ImageWeb.picture.dto.PictureDto;
import com.imageweb.ImageWeb.picture.dto.PictureMapper;
import com.imageweb.ImageWeb.picture.model.Picture;
import com.imageweb.ImageWeb.picture.repository.PictureRepository;
import com.imageweb.ImageWeb.user.exception.UserNotFoundException;
import com.imageweb.ImageWeb.user.repository.UserRepository;
import com.imageweb.ImageWeb.user.model.User;
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
public class PictureService {
    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;
    private final PictureMapper mapper;

    @Transactional
    public PictureDto create(Long userId, PictureDto pictureDto) {
        Picture picture = mapper.toEntity(pictureDto);
        User user = userValidation(userId);
        picture.setOwner(user);
        log.info("Создание картинки = {}", pictureDto);
        return mapper.toDto(pictureRepository.save(picture));
    }

    @Transactional
    public void deleteById(Long userId, Long pictureId) {
        User user = userValidation(userId);
        Optional<Picture> picture = pictureRepository.findById(pictureId);

        if (picture.isEmpty()) {
            log.error("Картинка с id = {} не найдена", pictureId);
            throw new PictureNotFoundException("Картинка не найдена");
        }

        if (picture.get().getOwner() != user) {
            log.error("Пользователь с id = {} не является владельцем картинки с id = {}", userId, pictureId);
            throw new PictureConflictException("Пользователь не владелец картинки");
        }

        log.info("Удаление картинки с id = {}", pictureId);
        pictureRepository.deleteById(pictureId);
    }

    @Transactional(readOnly = true)
    public List<PictureDto> getAllPictures() {
        List<Picture> pictures = pictureRepository.findAllOrderByCreatedDesc();
        List<PictureDto> dtos = new ArrayList<>();

        for (Picture picture : pictures) {
            dtos.add(mapper.toDto(picture));
        }

        log.info("Получение всех картинок");
        return dtos;
    }

    @Transactional(readOnly = true)
    public List<PictureDto> getPicturesByOwner(Long userId) {
        userValidation(userId);
        List<Picture> pictures = pictureRepository.findByOwnerId(userId);
        List<PictureDto> dtos = new ArrayList<>();

        for (Picture picture : pictures) {
            dtos.add(mapper.toDto(picture));
        }

        log.info("Вывод картинок для пользователя c id = {}", userId);
        return dtos;
    }

    private User userValidation(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            log.error("Пользователь с id = {} не найден",  userId);
            throw new UserNotFoundException("Пользователь не найден");
        }

        return user.get();
    }
}
