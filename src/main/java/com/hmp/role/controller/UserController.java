package com.hmp.role.controller;
import com.hmp.role.common.UserConstant;
import com.hmp.role.entity.User;
import com.hmp.role.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @PostMapping(value = "/join")
    public String joinGroup(@RequestBody User user){
        user.setRoles(UserConstant.DEFAULT_ROLE);
        String encryptedPassword = encoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        return "Hi "+user.getUserName()+" welcome to group.";
    }
    //if logged in user is admin he can give 2 access either admin or mooderator
    // if logged in user is moderator he can only give moderator access
    @GetMapping("/access/{userId}/{userRole}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    //@Secured("ROLE_ADMIN")
    public String giveAccessToUser(@PathVariable int userId, @PathVariable String userRole, Principal principal){
       User user =  userRepository.findById(userId).get();
       List<String> activeRoles = getRolesByLoggedinUsers(principal);
       if (activeRoles.contains(userRole)){
         String newRole =   user.getRoles()+","+userRole;
         user.setRoles(newRole);
       }
       userRepository.save(user);
       return "Hi "+user.getUserName()+" new role assigned to you by "+principal.getName();
    }
    ///get logged in user
    private User getLoggedInUser(Principal principal){
        return (User) userRepository.findByUserName(principal.getName());

    }
    private List<String>  getRolesByLoggedinUsers(Principal principal){
        String roles = getLoggedInUser(principal).getRoles();
        List<String> assgnRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());
        if (assgnRoles.contains("ROLE_ADMIN")){
            return Arrays.stream(UserConstant.ADMIN_ACCESS).collect(Collectors.toList());

        }
        if (assgnRoles.contains("ROLE_MODERATOR")){
            return Arrays.stream(UserConstant.MODERATOR_ACCESS).collect(Collectors.toList());

        }
     return Collections.emptyList();
    }
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Secured("ROLE_ADMIN")

    public List<User> loadUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/test")
    //@PreAuthorize("hasAuthority('ROLE_USER')")

    @Secured("ROLE_USER")
    public String testUserAccess(){
        return "user can only access it";
    }

}
