package com.example.cyclean.controller;


import com.example.cyclean.repository.BoardRepository;
import com.example.cyclean.vo.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@Slf4j
@RestController
@RequestMapping("/board/*")
public class BoardController {
    @Autowired
    BoardRepository boardRepository;

    //게시글 조회
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Board> getList(){
        return boardRepository.findAll();
    }

    //게시글 한 개 조회
    @GetMapping(value ="/list/{postid}")
    public Board getone(@PathVariable Long postid){
        return boardRepository.findByPostid(postid) .orElseThrow(() ->
                new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }

    //게시글 작성
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public int register(@RequestBody Board board){
        try{
            boardRepository.save(board);
        }
        catch (Exception e){
            return 0;
        }
        return 1;
    }

    //게시글 수정
    @PostMapping(value ="/modify/{postid}/{userid}")
    public int modify(@PathVariable Long postid, @PathVariable String userid, @RequestBody Board board){
            Board updateBoard = boardRepository.findByPostid(postid).orElseThrow(() ->
                    new IllegalArgumentException("게시글이 존재하지 않습니다."));;
            String checkUser = updateBoard.getUserid().toString();
            if(checkUser.equals(updateBoard.getUserid())){
                updateBoard.setTitle(board.getTitle());
                updateBoard.setContent(board.getContent());
                try {
                    boardRepository.save(updateBoard);
                }
                catch (Exception e){
                    return 0;
                }
            }
            else{
                return 0;
            }
        return 1;
    }

    //게시글 삭제
    @PostMapping(value="/delete/{postid}/{userid}")
    public int delete(@PathVariable Long postid, @PathVariable String userid){
        Board checkbd = boardRepository.findByPostid(postid).orElseThrow(() ->
                new IllegalArgumentException("게시글이 존재하지 않습니다."));;
        String checkUser = checkbd.getUserid().toString();
        log.info("checkUser: " + checkUser);
        log.info("userid: " + userid);
        if(checkUser.equals(userid)){
            boardRepository.deleteById(postid);
            return 1;
        }
        else {
            new IllegalArgumentException("글쓴이가 일치하지 않습니다.");
            return 0;
        }
    }



}
