package com.example.productmodule.repository;

import com.example.productmodule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select * from Comment where idPro = ?1 order by date desc", nativeQuery = true)
    List<Comment> getAllComment(Long id);
}
