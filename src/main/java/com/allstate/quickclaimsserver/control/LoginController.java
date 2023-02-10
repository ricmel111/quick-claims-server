package com.allstate.quickclaimsserver.control;

import com.allstate.quickclaimsserver.data.UserRepository;
import com.allstate.quickclaimsserver.domain.User;
import com.allstate.quickclaimsserver.domain.UserDTO;
import com.allstate.quickclaimsserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/login")
public class LoginController {

       @Autowired
       UserService userService;

       @PostMapping
       public User login(@RequestBody Map<String,String> loginData) {
              Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //returns currently logged in user
              UserDetails userDetails = (UserDetails)principal;
              System.out.println(userDetails.getUsername());
              User user = userService.findUser(userDetails.getUsername());
              return user;
       }

       @GetMapping
       public List<UserDTO> getUsers() {
              return userService.getAllUsers();
       }
}
