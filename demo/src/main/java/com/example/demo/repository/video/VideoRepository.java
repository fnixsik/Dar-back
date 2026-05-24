package com.example.demo.repository.video;

import com.example.demo.entity.video.Videos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Videos, Long> {

}
