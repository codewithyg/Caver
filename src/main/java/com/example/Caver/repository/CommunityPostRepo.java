package com.example.Caver.repository;

import com.example.Caver.model.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityPostRepo extends JpaRepository<CommunityPost, Long> {
}
