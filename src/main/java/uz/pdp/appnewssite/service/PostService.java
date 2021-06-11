package uz.pdp.appnewssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.Post;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.PostDto;
import uz.pdp.appnewssite.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public List<Post> getList(){
        return postRepository.findAll();
    }

    public ApiResponse getId(Long id){
        Optional<Post> byId = postRepository.findById(id);
        return byId.map(post -> new ApiResponse("Mana ol", true, post)).orElseGet(() -> new ApiResponse("This id not found", false));
    }

    public ApiResponse addPost(PostDto postDto){
        Optional<Post> byTitle = postRepository.findByTitle(postDto.getTitle());
        if (byTitle.isPresent())
            return new ApiResponse("Bunaqa title bor, boshqa title qo'y",false);
        Post post = new Post();
        post.setText(postDto.getText());
        post.setTitle(postDto.getTitle());
        post.setUrl(postDto.getUrl());
        postRepository.save(post);
        return new ApiResponse("Saved success",true,post);
    }

    public ApiResponse editPost(Long id,PostDto postDto){
        Optional<Post> byId = postRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found",false);
        Optional<Post> byTitle = postRepository.findByTitle(postDto.getTitle());
        if (byTitle.isPresent())
            return new ApiResponse("Bunaqa title bor, boshqa title qo'y",false);
        Post post = byId.get();
        post.setText(postDto.getText());
        post.setTitle(postDto.getTitle());
        post.setUrl(postDto.getUrl());
        postRepository.save(post);
        return new ApiResponse("Edited success",true,post);
    }

    public ApiResponse deletePost(Long id){
        Optional<Post> byId = postRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found",false);
        postRepository.deleteById(id);
        return new ApiResponse("delete success",true);
    }
}
