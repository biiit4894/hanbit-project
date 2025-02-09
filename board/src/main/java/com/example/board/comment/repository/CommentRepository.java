package com.example.board.comment.repository;

import com.example.board.comment.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);

    @Query("select c from Comment c where c.article.id = :articleId and c.parent is null order by c.createdAt asc")
    List<Comment> findParentsByArticleId(Long articleId);

    @Query("select c from Comment c where c.parent.id = :parentId order by c.createdAt asc")
    List<Comment> findRepliesByParentId(Long parentId);
}
