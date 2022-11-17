package com.example.cyclean.vo;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String password;

    private int point;

    @Column(length = 100)
    private String role;

    @Column(length = 200, unique = true)
    private String username;

//    public List<String> getRoles(){
//        if(this.role.length() > 0){
//            return Arrays.asList(this.role.split(","));
//        }
//        return new ArrayList<>();
//    }
}
