package com.mantisa.inventory.controller.seeder;


import com.mantisa.inventory.util.ResponseObject;
import com.mantisa.inventory.util.SeederUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/seeder")
public class SeederController {

    @Autowired
    private SeederUsers seederUsers;

    @PostMapping("/users")
    public ResponseEntity<ResponseObject> seedUsers() {
        try {
            seederUsers.seed();
            return ResponseObject.build(true, HttpStatus.OK, "Users seeded successfully", null);
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurrió un error", e.getMessage());
        }
    }
}