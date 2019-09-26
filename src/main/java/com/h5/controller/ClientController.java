package com.h5.controller;

import com.h5.entity.Room;
import com.h5.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@Slf4j
@Api("测试")
@RequestMapping("demo")
@Transactional(readOnly = false)
public class ClientController {

    List<Room> rs = new ArrayList<>();

    @PostMapping(value = "/Client")
    @ApiOperation("客户端")
    public Room Client(String roomNumber , User user) {
//        String roomNumber ,User user
        int rIndex = -1;
        for (int i = 0 ; i < rs.size() ; i++){
            if (rs.get(i).getRoomNumber().equals(roomNumber)){
                rIndex = i;
            }
        }
        if( rIndex == -1 ) {
            rs.add(new Room());
            rIndex = rs.size()-1;
        }
        rs.get(rIndex).setRoomNumber(roomNumber);
        rs.get(rIndex).getUList().add(user);
        System.out.println(user.getId());
        System.out.println(rs.size() + ", " + rIndex);
        System.out.println(rs.get(rIndex));
        return rs.get(rIndex);
//        System.out.print(snake);
//        return null;
    }

    @PostMapping(value = "Client2")
    @ApiOperation(value = "测试")
    public User Client(User user){
        System.out.println(user.getId());
        System.out.println(user);
//        user.setUId("1234566");
//        user.setScore(15);
        return user;
    }
}
