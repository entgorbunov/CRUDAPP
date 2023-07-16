package com.gorbunov.crudapp.controller;

import com.gorbunov.crudapp.model.post;
import com.gorbunov.crudapp.repository.gson.gsonPostStorageClass;
import com.gorbunov.crudapp.repository.postStorage;

import java.util.List;

public class postController {
    private static final postStorage postStorage = new gsonPostStorageClass();

    public post save(String content) {
        if (content.isEmpty() || content.isBlank()) {
            return null;
        }

        post post = new post();
        post.setContent(content);
        return postStorage.save(post);
    }

    public post getById(Integer id) {
        if (id <= 0) {
            return null;
        }

        return postStorage.getById(id);
    }

    public static List<post> getAll() {
        return postStorage.getAll();
    }

    public post update(post post) {
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
