package uz.pdp.appnewssite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewssite.aop.CheckPermission;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.RegisterDTO;
import uz.pdp.appnewssite.payload.UserDTO;
import uz.pdp.appnewssite.service.AuthService;
import uz.pdp.appnewssite.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @CheckPermission("VIEW_USERS")
    @GetMapping
    public List<User> getList(){
        return userService.getList();
    }

    @CheckPermission("VIEW_USER")
    @GetMapping("/{id}")
    public HttpEntity<?> getId(@PathVariable Long id){
        ApiResponse id1 = userService.getId(id);
        return ResponseEntity.status(id1.isSuccess()?200:409).body(id1);
    }

    @CheckPermission("ADD_USER")
    @PostMapping
    public HttpEntity<?> addUser(@Valid @RequestBody UserDTO userDTO){
        ApiResponse apiResponse=userService.addUser(userDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @CheckPermission("EDIT_USER")
    @PutMapping("/{id}")
    public HttpEntity<?> editUser(@PathVariable Long id,
                                  @Valid @RequestBody UserDTO userDTO){
        ApiResponse apiResponse = userService.editUser(id, userDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @CheckPermission("DELETE_USER")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUser(@PathVariable Long id){
        ApiResponse apiResponse = userService.deleteUser(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
