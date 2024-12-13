package com.imageweb.ImageWeb.picture.controller;

import com.imageweb.ImageWeb.picture.dto.PictureDto;
import com.imageweb.ImageWeb.picture.service.PictureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pictures")
@Validated
public class PictureController {
    private final PictureService service;

    @PostMapping
    public PictureDto createPicture(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid PictureDto pictureDto) {
        return service.create(userId, pictureDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@RequestHeader("X-User-Id") Long userId, @PathVariable Long id) {
        service.deleteById(userId, id);
    }

    @GetMapping("/all")
    public Collection<PictureDto> getAllPictures() {
        return service.getAllPictures();
    }

    @GetMapping
    public Collection<PictureDto> getPicturesByOwner(@RequestHeader("X-User-Id") Long userId) {
        return service.getPicturesByOwner(userId);
    }
}
