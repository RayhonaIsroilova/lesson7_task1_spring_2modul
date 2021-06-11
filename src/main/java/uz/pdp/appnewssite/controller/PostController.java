package uz.pdp.appnewssite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewssite.aop.CheckPermission;
import uz.pdp.appnewssite.entity.Post;
import uz.pdp.appnewssite.entity.Role;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.PostDto;
import uz.pdp.appnewssite.payload.RoleDTO;
import uz.pdp.appnewssite.service.PostService;
import uz.pdp.appnewssite.service.RoleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    PostService postService;

    @CheckPermission("VIEW_POSTS")
    @GetMapping
    public List<Post> getList(){
        return postService.getList();
    }

    @CheckPermission("VIEW_POST")
    @GetMapping("/{id}")
    public HttpEntity<?> getId(@PathVariable Long id){
        ApiResponse id1 = postService.getId(id);
        return ResponseEntity.status(id1.isSuccess()?200:409).body(id1);
    }

    @CheckPermission("ADD_POST")
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody PostDto postDto){
        ApiResponse apiResponse=postService.addPost(postDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @CheckPermission("EDIT_POST")
    @PutMapping("/{id}")
    public HttpEntity<?> editPost(@PathVariable Long id,
                                  @Valid @RequestBody PostDto postDto){
        ApiResponse apiResponse = postService.editPost(id, postDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @CheckPermission("DELETE_POST")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deletePost(@PathVariable Long id) {
        ApiResponse apiResponse = postService.deletePost(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
