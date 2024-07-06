package com.imageweb.ImageWeb.picture.repository;

import com.imageweb.ImageWeb.picture.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    List<Picture> findByOwnerId(Long ownerId);
    @Query("SELECT p FROM Picture p ORDER BY p.created DESC")
    List<Picture> findAllOrderByCreatedDesc();
}
