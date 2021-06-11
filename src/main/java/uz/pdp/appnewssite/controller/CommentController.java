package uz.pdp.appnewssite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewssite.aop.CheckPermission;
import uz.pdp.appnewssite.entity.Comment;
import uz.pdp.appnewssite.entity.Post;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.CommentDto;
import uz.pdp.appnewssite.payload.PostDto;
import uz.pdp.appnewssite.service.CommentService;
import uz.pdp.appnewssite.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @CheckPermission("GET_COMMENTS")
    @GetMapping
    public List<Comment> getList(){
        return commentService.getList();
    }

    @CheckPermission("GET_COMMENT")
    @GetMapping("/{id}")
    public HttpEntity<?> getId(@PathVariable Long id){
        ApiResponse id1 = commentService.getId(id);
        return ResponseEntity.status(id1.isSuccess()?200:409).body(id1);
    }

    @CheckPermission("ADD_COMMENT")
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody CommentDto commentDto){
        ApiResponse apiResponse=commentService.addComment(commentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @CheckPermission("EDIT_COMMENT")
    @PutMapping("/{id}")
    public HttpEntity<?> editComment(@PathVariable Long id,
                                  @Valid @RequestBody CommentDto commentDto){
        ApiResponse apiResponse = commentService.editComment(id, commentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @CheckPermission("DELETE_COMMENT")
    @DeleteMapping
    public HttpEntity<?> deleteComment(Comment comment){
        ApiResponse apiResponse = commentService.deleteComment(comment);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @CheckPermission("DELETE_MY_COMMENT")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteMyComment(@PathVariable Long id) {
        ApiResponse apiResponse = commentService.deleteMyComment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
