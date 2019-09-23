package com.h5.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Room {
    private List<User> uList = new ArrayList<>();
    private String roomNumber;
//    public Room(String roomNumber){
//        this.roomNumber = roomNumber;
//    }
}