package com.gorbunov.crudapp.repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gorbunov.crudapp.model.post;
import com.gorbunov.crudapp.model.status;
import com.gorbunov.crudapp.repository.postStorage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class gsonPostStorageClass implements postStorage {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final String POSTS_FILE_PATH = "/Users/entgorbunov/Documents/GitHub/CRUDAPP/src/main/resources/posts.json";


    private List<post> getAllInternal() {
        List<post> allPosts;
        try (FileReader reader = new FileReader(POSTS_FILE_PATH)) {
            Type targetClassType = new TypeToken<ArrayList<post>>() {
            }.getType();
            allPosts = GSON.fromJson(reader, targetClassType);
            if (allPosts == null)
                allPosts = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allPosts;
    }

    private void writeToFile(List<post> allPosts) {
        try (FileWriter writer = new FileWriter(POSTS_FILE_PATH)) {
            writer.write(GSON.toJson(allPosts));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer generateNewId(List<post> posts) {
        return posts.stream()
                .mapToInt(post::getId).max().orElse(0) + 1;
    }

    @Override
    public post save(post post) {
        List<post> allPosts = getAllInternal();
        post.setId(generateNewId(allPosts));
        allPosts.add(post);
        writeToFile(allPosts);
        return post;
    }


    @Override
    public post update(post postToUpdate) {
        List<post> allPosts = getAllInternal();
        List<post> updatedPosts = allPosts.stream()
                .peek(existingPost -> {
                    if (existingPost.getId().equals(postToUpdate.getId()) &&
                            existingPost.getStatus() == status.ACTIVE) {
                        existingPost.setContent(postToUpdate.getContent());
                        existingPost.setCreated(postToUpdate.getCreated());
                        existingPost.setUpdated(postToUpdate.getUpdated());
                        existingPost.setLabels(postToUpdate.getLabels());
                    }
                })
                .collect(Collectors.toList());
        writeToFile(updatedPosts);
        return postToUpdate;
    }

    @Override
    public List<post> getAll() {
        return getAllInternal();
    }

    @Override
    public post getById(Integer id) {
        List<post> allPosts = getAllInternal();
        return allPosts.stream()
                .filter(p -> p.getId().equals(id) && p.getStatus() == status.ACTIVE)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean deleteById(Integer id) {
        List<post> allPosts = getAllInternal();
        boolean postResult = allPosts.stream()
                .filter(existingPost -> existingPost.getId().equals(id) &&
                        existingPost.getStatus() == status.ACTIVE)
                .findFirst()
                .map(exisitingPost -> {
                    exisitingPost.setStatus(status.DELETED);
                    return true;
                })
                .orElse(false);
        writeToFile(allPosts);
        return postResult;
    }


}
