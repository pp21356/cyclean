package com.example.cyclean.controller;


import com.example.cyclean.repository.BoardRepository;
import com.example.cyclean.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/board/*")
public class BoardController {
    @Autowired
    BoardRepository boardRepository;

    //게시글 조회
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Board> getList(@RequestParam(required = false) Long postid ){
        if (postid != null){
            return boardRepository.findByPostid(postid);
        }
        return boardRepository.findAll();
    }

    //게시글 작성
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void register(Model model, @RequestBody Board board){

    }




}