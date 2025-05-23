
package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class app {

    public static void main(String[] args) {
        SpringApplication.run(app.class, args);
    }

}

@RestController
@RequestMapping("/api")
class HelloWorldController {

    @GetMapping("/hello")
    public String sayHello() {
    	
    	System.out.println("Hello we are back online");
    	System.out.println("Hello we are back online2");
    	
        return "Hello, World!";
    }
}
