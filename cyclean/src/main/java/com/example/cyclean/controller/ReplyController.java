package com.example.cyclean.controller;

import com.example.cyclean.repository.BoardRepository;
import com.example.cyclean.repository.ReplyRepository;
import com.example.cyclean.vo.Board;
import com.example.cyclean.vo.Reply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reply/*")
public class ReplyController {
    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    BoardRepository boardRepository;

    //댓글등록
    @PostMapping("/register/{postid}")
    public void replyRegist(@PathVariable Long postid, @RequestBody Reply reply){
        Board board = boardRepository.findByPostid(postid);
        board.addList(reply);
        reply.setBoard(board);
        replyRepository.save(reply);
    }


    //댓글삭제
    @PostMapping("delete/{postid}/{replyno}/{userid}")
    public void deleteReply(@PathVariable Long postid, @PathVariable int replyno, @PathVariable String userid){
        Board board = boardRepository.findByPostid(postid);
        List<Reply> replys = board.getReplys();
        int number= replyno-1;
        Reply deletereply = replys.get(number);
        if(deletereply.getUserid().equals(userid)){
            replyRepository.delete(deletereply);
        }
        return;
    }
}
