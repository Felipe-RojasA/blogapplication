package com.itp.blogapp.controller;

import java.time.LocalDate;
import java.util.List;

import com.itp.blogapp.aid.ReadPosts;
import com.itp.blogapp.aid.WritePost;
import com.itp.blogapp.model.BlogPost;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BlogPostController {

	@GetMapping("/blogpost/{id}")
	public String getblogpost(@PathVariable int id, Model model) {
        String view = "bpost";
        List<BlogPost> posts = ReadPosts.readJson();
        if(posts.size()>=id){
            model.addAttribute("post", posts.get(id-1));
        }else{
            view = "error";
        }
        return view;
    }   
    
    @GetMapping("/newPost")
    public String newPost(Model model){
        model.addAttribute("blogPost", new BlogPost());
        return "postForm";
    }

    @PostMapping("/postAdded")
    public String newPost(@ModelAttribute BlogPost post){
        List<BlogPost> posts = ReadPosts.readJson();
        int sizeNum = posts.size() + 1;

        post.setId(sizeNum);
        post.setUsername("Anonymus");
        LocalDate date = LocalDate.now();
        post.setDate(""+date);

        WritePost.writeJson(post);
        return "redirect:/blogpost/" + post.getId();
    }


}
