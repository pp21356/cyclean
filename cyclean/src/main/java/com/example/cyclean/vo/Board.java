package com.example.cyclean.vo;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postid", nullable = false)
    private Long postid;
    private String userid;
    private String title;
    private String content;
    private String regdate;
    private String updatedate;
}
