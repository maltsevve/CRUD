package com.maltsevve.crud.controller;

import com.maltsevve.crud.model.Post;
import com.maltsevve.crud.repository.JavaIOPostRepositoryImpl;

import java.util.List;

public class PostController {
    JavaIOPostRepositoryImpl pr = new JavaIOPostRepositoryImpl();

    public PostController() {
        
    }

    public Post getByID(Long id) {
        if (id > 0 && pr.getById(id) != null) {
            return pr.getById(id);
        } else {
            return null;
        }
    }

    public List<Post> getAll() {
        return pr.getAll();
    }

    public Post save(Post post) {
        return pr.save(post);
    }

    public Post update(Post post) {
        if (post.getId() > 0 && pr.getById(post.getId()) != null) {
            return pr.update(post);
        } else {
            return post;
        }
    }

    public void deleteById(Long id) {
        if (pr.getById(id) != null) {
            pr.deleteById(id);
        }
    }
}
