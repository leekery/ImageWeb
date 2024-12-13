package com.imageweb.ImageWeb.picture.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureDto {
    private Long id;
    @URL
    private String url;
    private String prompt;
    private LocalDateTime created;
    private Integer price;
}
