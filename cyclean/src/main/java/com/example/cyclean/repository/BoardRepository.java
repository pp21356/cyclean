package com.example.cyclean.repository;

import com.example.cyclean.vo.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findByPostid (Long postid);
}
