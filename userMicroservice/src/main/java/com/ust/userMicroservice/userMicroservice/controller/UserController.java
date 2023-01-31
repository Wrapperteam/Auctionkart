package com.ust.userMicroservice.userMicroservice.controller;

import com.ust.userMicroservice.userMicroservice.Dto.UserDto;
import com.ust.userMicroservice.userMicroservice.exception.UserAlreadyExistException;
import com.ust.userMicroservice.userMicroservice.exception.UserNotFoundException;
import com.ust.userMicroservice.userMicroservice.model.User;
import com.ust.userMicroservice.userMicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findUser/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") int userId) {
        UserDto userDto = userService.getUser(userId);
        if(Objects.nonNull(userDto))
            return ResponseEntity.ok().body(userDto);
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/user")
    public ResponseEntity addUser(@RequestBody User user) throws UserAlreadyExistException{
        ResponseEntity responseEntity;
        try{
            responseEntity= userService.saveUser(user);
        }catch(UserAlreadyExistException ex){
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return  new ResponseEntity<List<User>>(userService.getAllUsers(),HttpStatus.OK);
    }
    @PutMapping("/editUser")
    public ResponseEntity<?> updateUsers(@RequestBody User user) throws UserNotFoundException {
        ResponseEntity responseEntity;
        try{
            userService.updateUser(user);
            responseEntity=new ResponseEntity<String>("Updated Successfully", HttpStatus.CREATED);
        }
        catch(UserNotFoundException exception){
            responseEntity=new ResponseEntity<String>(exception.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) throws UserNotFoundException {
        ResponseEntity responseEntity;
        try {
            userService.deleteUser(id);
            responseEntity = new ResponseEntity("Succesfully Deleted", HttpStatus.OK);
        }
        catch (UserNotFoundException e)
        {
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;

    }

    @GetMapping("/findUser/role/{id}")
    public String getRole(@PathVariable("id") int userId) {
        UserDto userDto = userService.getUser(userId);
        if(Objects.nonNull(userDto))
            return userDto.getRole();
        return "User not found";
    }

}
