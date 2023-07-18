package com.gorbunov.crudapp.repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gorbunov.crudapp.model.Post;
import com.gorbunov.crudapp.model.Status;
import com.gorbunov.crudapp.repository.PostStorage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonPostStorageClass implements PostStorage {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final String POSTS_FILE_PATH = "/Users/entgorbunov/Documents/GitHub/CRUDAPP/src/main/resources/posts.json";


    private List<Post> getAllInternal() {
        List<Post> allPosts;
        try (FileReader reader = new FileReader(POSTS_FILE_PATH)) {
            Type targetClassType = new TypeToken<ArrayList<Post>>() {
            }.getType();
            allPosts = GSON.fromJson(reader, targetClassType);
            if (allPosts == null)
                allPosts = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allPosts;
    }

    private void writeToFile(List<Post> allPosts) {
        try (FileWriter writer = new FileWriter(POSTS_FILE_PATH)) {
            writer.write(GSON.toJson(allPosts));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer generateNewId(List<Post> Posts) {
        return Posts.stream()
                .mapToInt(Post::getId).max().orElse(0) + 1;
    }

    @Override
    public Post save(Post post) {
        List<Post> allPosts = getAllInternal();
        post.setId(generateNewId(allPosts));
        allPosts.add(post);
        writeToFile(allPosts);
        return post;
    }


    @Override
    public Post update(Post postToUpdate) {
        List<Post> allPosts = getAllInternal();
        List<Post> updatedPosts = allPosts.stream()
                .map(existingPost -> {
                    if (existingPost.getId().equals(postToUpdate.getId()) &&
                            existingPost.getStatus() == Status.ACTIVE) {
                        return postToUpdate;
                    }
                    return existingPost;
                })
                .collect(Collectors.toList());
        writeToFile(updatedPosts);
        return postToUpdate;

    }

    @Override
    public List<Post> getAll() {
        return getAllInternal();
    }

    @Override
    public Post getById(Integer id) {
        List<Post> allPosts = getAllInternal();
        return allPosts.stream()
                .filter(p -> p.getId().equals(id) && p.getStatus() == Status.ACTIVE)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean deleteById(Integer id) {
        List<Post> allPosts = getAllInternal();
        boolean postResult = allPosts.stream()
                .filter(existingPost -> existingPost.getId().equals(id) &&
                        existingPost.getStatus() == Status.ACTIVE)
                .findFirst()
                .map(exisitingPost -> {
                    exisitingPost.setStatus(Status.DELETED);
                    return true;
                })
                .orElse(false);
        writeToFile(allPosts);
        return postResult;
    }


}
