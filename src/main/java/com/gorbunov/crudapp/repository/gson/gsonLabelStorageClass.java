package com.gorbunov.crudapp.repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gorbunov.crudapp.model.label;
import com.gorbunov.crudapp.model.status;
import com.gorbunov.crudapp.repository.labelStorage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class gsonLabelStorageClass implements labelStorage {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final String LABEL_FILE_PATH = "src/main/resources/labels.json";

    private List<label> getAllInternal() {
        List<label> allLabels;
        try (FileReader fileReader = new FileReader(LABEL_FILE_PATH)) {
            Type targetClassType = new TypeToken<ArrayList<label>>() {
            }.getType();
            allLabels = GSON.fromJson(fileReader, targetClassType);
            if (allLabels == null)
                allLabels = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allLabels;
    }

    private Integer generateNewId(List<label> labels) {
        return labels.stream()
                .mapToInt(label::getId).max().orElse(0) + 1;
    }

    private void writeToFile(List<label> allLabels) {
        try (FileWriter fileWriter = new FileWriter(LABEL_FILE_PATH)) {
            fileWriter.write(GSON.toJson(allLabels));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public label save(label label) {
        List<label> allLabels = getAllInternal();
        label.setId(generateNewId(allLabels));
        allLabels.add(label);
        writeToFile(allLabels);
        return label;
    }

    @Override
    public label update(label labelToUpdate) {
        List<label> allLabels = getAllInternal();
        List<label> updatedLabels = allLabels.stream()
                .peek(existingLabel -> {
                    if (existingLabel.getId().equals(labelToUpdate.getId()) &&
                            existingLabel.getStatus() == status.ACTIVE) {
                        existingLabel.setName(labelToUpdate.getName());
                    }
                })
                .collect(Collectors.toList());

        writeToFile(updatedLabels);
        return labelToUpdate;
    }


    @Override
    public List<label> getAll() {
        return getAllInternal();
    }

    @Override
    public label getById(Integer id) {
        return getAllInternal().stream()
                .filter(l -> l.getId().equals(id) && l.getStatus().equals(status.ACTIVE))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean deleteById(Integer id) {
        List<label> allLabels = getAllInternal();
        boolean labelResult = allLabels.stream()
                .filter(existingLabel -> existingLabel.getId().equals(id) &&
                        existingLabel.getStatus() == status.ACTIVE)
                .findFirst()
                .map(existingLabel -> {
                    existingLabel.setStatus(status.DELETED);
                    return true;
                })
                .orElse(false);

        writeToFile(allLabels);
        return labelResult;
    }
}

