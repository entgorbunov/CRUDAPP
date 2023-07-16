package com.gorbunov.crudapp.repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gorbunov.crudapp.model.status;
import com.gorbunov.crudapp.model.writer;
import com.gorbunov.crudapp.repository.writerStorage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class gsonWriterStorageClass implements writerStorage {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    private final String WRITERS_FILE_PATH = "src/main/resources/writers.json";


    private Integer generateNewId(List<writer> writers) {
        return writers.stream()
                .mapToInt(writer::getId).max().orElse(0) + 1;
    }

    @Override
    public writer save(writer writer) {
        List<writer> allWriters = getAllInternal();
        writer.setId(generateNewId(allWriters));
        allWriters.add(writer);
        writeToFile(allWriters);
        return writer;
    }


    @Override
    public writer update(writer writerToUpdate) {
        List<writer> allWriters = getAllInternal();
        List<writer> updateWriters = allWriters.stream()
                .peek(existingWriter -> {
                    if (existingWriter.getId().equals(writerToUpdate.getId()) &&
                            existingWriter.getStatus() == status.ACTIVE) {
                        existingWriter.setFirstName(writerToUpdate.getFirstName());
                        existingWriter.setLastName(writerToUpdate.getLastName());
                        existingWriter.setPosts(writerToUpdate.getPosts());
                    }
                })
                .collect(Collectors.toList());
        writeToFile(updateWriters);
        return writerToUpdate;

    }


    @Override
    public List<writer> getAll() {
        List<writer> allWriters = getAllInternal();
        return allWriters.stream()
                .filter(writer -> writer.getStatus() == status.ACTIVE)
                .collect(Collectors.toList());
    }

    @Override
    public writer getById(Integer id) {
        List<writer> allWriters = getAllInternal();
        return allWriters.stream()
                .filter(writer -> writer.getId().equals(id) && writer.getStatus() == status.ACTIVE)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean deleteById(Integer id) {
        List<writer> allWriters = getAllInternal();
        boolean writerResult = allWriters.stream()
                .filter(existingWriter -> existingWriter.getId().equals(id) &&
                        existingWriter.getStatus() == status.ACTIVE)
                .findFirst()
                .map(existingWriter -> {
                    existingWriter.setStatus(status.DELETED);
                    return true;
                })
                .orElse(false);
        writeToFile(allWriters);
        return writerResult;
    }


    private List<writer> getAllInternal() {
        List<writer> allWriters;
        try (FileReader reader = new FileReader(WRITERS_FILE_PATH)) {
            Type targetClassType = new TypeToken<ArrayList<writer>>() {
            }.getType();
            allWriters = GSON.fromJson(reader, targetClassType);
            if (allWriters == null)
                allWriters = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allWriters;
    }

    private void writeToFile(List<writer> allWriters) {
        try (FileWriter fileWriter = new FileWriter(WRITERS_FILE_PATH)) {
            fileWriter.write(GSON.toJson(allWriters));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

