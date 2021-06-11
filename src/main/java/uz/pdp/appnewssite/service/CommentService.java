package uz.pdp.appnewssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.Comment;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.CommentDto;
import uz.pdp.appnewssite.repository.CommentRepository;
import uz.pdp.appnewssite.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    public List<Comment> getList(){
        return commentRepository.findAll();
    }

    public ApiResponse getId(Long id){
        Optional<Comment> byId = commentRepository.findById(id);
        return byId.map(comment -> new ApiResponse("Mana ol", true, comment)).orElseGet(() -> new ApiResponse("This id not found", false));
    }

    public ApiResponse addComment(CommentDto commentDto){
        Optional<Comment> byPost_id = commentRepository.findByPost_Id(commentDto.getPostId());
        if (byPost_id.isPresent())
            return new ApiResponse("This post id already exist",false);
        Comment comment = new Comment(commentDto.getText(),postRepository.getOne(commentDto.getPostId()));
        commentRepository.save(comment);
        return new ApiResponse("Saved success",true,comment);
    }

    public ApiResponse editComment(Long id,CommentDto commentDto) {
        Optional<Comment> byId = commentRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found",false);
        Optional<Comment> byPost_id = commentRepository.findByPost_Id(commentDto.getPostId());
        if (byPost_id.isPresent())
            return new ApiResponse("This post id already exist", false);
        Comment comment1 = byId.get();
        comment1.setText(commentDto.getText());
        comment1.setPost(postRepository.getOne(commentDto.getPostId()));
        commentRepository.save(comment1);
        return new ApiResponse("Edited success", true, comment1);
    }

    public ApiResponse deleteComment(Comment commentDto){
        Optional<Comment> byPost_id = commentRepository.findByPost_Id(commentDto.getPost().getId());
        if (!byPost_id.isPresent())
            return new ApiResponse("This post id not found",false);
        commentRepository.delete(commentDto);
         return new ApiResponse("Delete comment",true);
    }

    public ApiResponse deleteMyComment(Long id){
        Optional<Comment> byId = commentRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found",false);
        commentRepository.deleteById(id);
        return new ApiResponse("Delete success",true);
    }


}
