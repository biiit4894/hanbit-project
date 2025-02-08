package com.example.board.like.repository;

import com.example.board.like.model.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {


}
