package shop.project.vendiverse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @GetMapping("/get-order")
    public ResponseEntity index() {

        return ResponseEntity.status(HttpStatus.OK).body("Hello World");
    }
}
