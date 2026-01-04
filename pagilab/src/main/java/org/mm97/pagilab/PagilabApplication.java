package org.mm97.pagilab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@RestController
public class PagilabApplication {

//    @GetMapping("/test")
//    public String home() {
//        return "Hello Docker World";
//    }

    public static void main(String[] args) {
        SpringApplication.run(PagilabApplication.class, args);
    }
}