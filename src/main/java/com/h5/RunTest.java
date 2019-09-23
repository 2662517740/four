package com.h5;

import com.h5.entity.ServerThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class RunTest {
    public static void main(String args[]){
        SpringApplication.run(RunTest.class ,args);
    }
}
