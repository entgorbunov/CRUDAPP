package com.gorbunov.crudapp.controller;

import com.gorbunov.crudapp.model.Post;
import com.gorbunov.crudapp.repository.gson.GsonPostStorageClass;
import com.gorbunov.crudapp.repository.PostStorage;

import java.util.List;

public class PostController {
    private static final PostStorage postStorage = new GsonPostStorageClass();

    public Post save(String content) {
        if (content.isEmpty() || content.isBlank()) {
            return null;
        }

        Post post = new Post();
        post.setContent(content);
        return postStorage.save(post);
    }

    public Post getById(Integer id) {
        if (id <= 0) {
            return null;
        }

        return postStorage.getById(id);
    }

    public static List<Post> getAll() {
        return postStorage.getAll();
    }

    public Post update(Post post) {
        if (post == null || post.getId() <= 0) {
            return null;
        }

        return postStorage.update(post);
    }

    public boolean deleteById(Integer id) {
        if (id <= 0) {
            return false;
        }

        return postStorage.deleteById(id);
    }
}
