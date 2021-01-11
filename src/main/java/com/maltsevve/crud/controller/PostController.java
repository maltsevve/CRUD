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

    public Post save(Post Post) {
        return pr.save(Post);
    }

    public Post update(Post Post) {
        if (Post.getId() > 0 && pr.getById(Post.getId()) != null) {
            return pr.update(Post);
        } else {
            return Post;
        }
    }

    public void deleteById(Long id) {
        if (pr.getById(id) != null) {
            pr.deleteById(id);
        }
    }
}
