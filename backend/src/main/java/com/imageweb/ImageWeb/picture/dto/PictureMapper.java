package com.imageweb.ImageWeb.picture.dto;

import com.imageweb.ImageWeb.picture.model.Picture;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PictureMapper {
    PictureDto toDto(Picture picture);

    Picture toEntity(PictureDto pictureDto);
}
