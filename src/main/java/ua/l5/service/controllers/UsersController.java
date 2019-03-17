package ua.l5.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.l5.service.forms.UserForm;
import ua.l5.service.models.User;
import ua.l5.service.repositories.UsersRepository;
import ua.l5.service.services.UserService;

import javax.xml.ws.Response;
import java.util.List;

@RestController
public class UsersController {

    private final
    UserService service;

    @Autowired
    public UsersController(UserService service) {
        this.service = service;
    }


    @GetMapping("/users")
    public List<User> getUsers(){
        return service.findAll();
    }

    @GetMapping("/users/{user-id}")
    public User getUser(@PathVariable("user-id") Long userId){
        return service.findOne(userId);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@RequestBody UserForm userForm){
        service.signUp(userForm);
        return ResponseEntity.ok().build();
    }
}
