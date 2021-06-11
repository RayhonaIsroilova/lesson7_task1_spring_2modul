package uz.pdp.appnewssite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewssite.aop.CheckPermission;
import uz.pdp.appnewssite.entity.Role;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.RoleDTO;
import uz.pdp.appnewssite.payload.UserDTO;
import uz.pdp.appnewssite.service.RoleService;
import uz.pdp.appnewssite.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @CheckPermission("VIEW_ROLES")
    @GetMapping
    public List<Role> getList(){
        return roleService.getList();
    }

    @CheckPermission("VIEW_ROLE")
    @GetMapping("/{id}")
    public HttpEntity<?> getId(@PathVariable Long id){
        ApiResponse id1 = roleService.getId(id);
        return ResponseEntity.status(id1.isSuccess()?200:409).body(id1);
    }

    @CheckPermission("ADD_ROLE")
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody RoleDTO roleDTO){
        ApiResponse apiResponse=roleService.addRole(roleDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @CheckPermission("EDIT_ROLE")
    @PutMapping("/{id}")
    public HttpEntity<?> editRole(@PathVariable Long id,
                                  @Valid @RequestBody RoleDTO roleDTO){
        ApiResponse apiResponse = roleService.editRole(id, roleDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @CheckPermission("DELETE_ROLE")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteRole(@PathVariable Long id) {
        ApiResponse apiResponse = roleService.deleteRole(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
