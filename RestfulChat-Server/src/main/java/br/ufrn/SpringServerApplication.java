package br.ufrn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class SpringServerApplication {

    public static void main(String[] args){
        SpringApplication.run(SpringServerApplication.class,args);
    }

}
