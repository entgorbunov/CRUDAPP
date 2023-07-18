package com.gorbunov.crudapp.repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gorbunov.crudapp.model.Status;
import com.gorbunov.crudapp.model.Writer;
import com.gorbunov.crudapp.repository.WriterStorage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonWriterStorageClass implements WriterStorage {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    private final String WRITERS_FILE_PATH = "src/main/resources/writers.json";


    private Integer generateNewId(List<Writer> Writers) {
        return Writers.stream()
                .mapToInt(Writer::getId).max().orElse(0) + 1;
    }

    @Override
    public Writer save(Writer writer) {
        List<Writer> allWriters = getAllInternal();
        writer.setId(generateNewId(allWriters));
        allWriters.add(writer);
        writeToFile(allWriters);
        return writer;
    }


    @Override
    public Writer update(Writer writerToUpdate) {
        List<Writer> allWriters = getAllInternal();
        List<Writer> updateWriters = allWriters.stream()
                .map(existingWriter -> {
                    if (existingWriter.getId().equals(writerToUpdate.getId()) &&
                            existingWriter.getStatus() == Status.ACTIVE) {
                        return writerToUpdate;
                    }
                    return existingWriter;
                })
                .collect(Collectors.toList());
        writeToFile(updateWriters);
        return writerToUpdate;

    }


    @Override
    public List<Writer> getAll() {
        List<Writer> allWriters = getAllInternal();
        return allWriters.stream()
                .filter(writer -> writer.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
    }

    @Override
    public Writer getById(Integer id) {
        List<Writer> allWriters = getAllInternal();
        return allWriters.stream()
                .filter(writer -> writer.getId().equals(id) && writer.getStatus() == Status.ACTIVE)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean deleteById(Integer id) {
        List<Writer> allWriters = getAllInternal();
        boolean writerResult = allWriters.stream()
                .filter(existingWriter -> existingWriter.getId().equals(id))
                .findFirst()
                .map(existingWriter -> {
                    existingWriter.setStatus(Status.DELETED);
                    return true;
                })
                .orElse(false);
        writeToFile(allWriters);
        return writerResult;
    }


    private List<Writer> getAllInternal() {
        List<Writer> allWriters;
        try (FileReader reader = new FileReader(WRITERS_FILE_PATH)) {
            Type targetClassType = new TypeToken<ArrayList<Writer>>() {
            }.getType();
            allWriters = GSON.fromJson(reader, targetClassType);
            if (allWriters == null)
                allWriters = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allWriters;
    }

    private void writeToFile(List<Writer> allWriters) {
        try (FileWriter fileWriter = new FileWriter(WRITERS_FILE_PATH)) {
            fileWriter.write(GSON.toJson(allWriters));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

