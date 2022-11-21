package com.example.cyclean.repository;

import com.example.cyclean.vo.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
    Optional<Board> findByPostid (Long postid);

}
