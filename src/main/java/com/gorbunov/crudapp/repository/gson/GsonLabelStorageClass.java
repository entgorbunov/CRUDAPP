package com.gorbunov.crudapp.repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gorbunov.crudapp.model.Label;
import com.gorbunov.crudapp.model.Status;
import com.gorbunov.crudapp.repository.LabelStorage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonLabelStorageClass implements LabelStorage {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final String LABEL_FILE_PATH = "src/main/resources/labels.json";

    private List<Label> getAllInternal() {
        List<Label> allLabels;
        try (FileReader fileReader = new FileReader(LABEL_FILE_PATH)) {
            Type targetClassType = new TypeToken<ArrayList<Label>>() {
            }.getType();
            allLabels = GSON.fromJson(fileReader, targetClassType);
            if (allLabels == null)
                allLabels = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allLabels;
    }

    private Integer generateNewId(List<Label> Labels) {
        return Labels.stream()
                .mapToInt(Label::getId).max().orElse(0) + 1;
    }

    private void writeToFile(List<Label> allLabels) {
        try (FileWriter fileWriter = new FileWriter(LABEL_FILE_PATH)) {
            fileWriter.write(GSON.toJson(allLabels));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Label save(Label label) {
        List<Label> allLabels = getAllInternal();
        label.setId(generateNewId(allLabels));
        allLabels.add(label);
        writeToFile(allLabels);
        return label;
    }

    @Override
    public Label update(Label labelToUpdate) {
        List<Label> allLabels = getAllInternal();
        List<Label> updatedLabels = allLabels.stream()
                .map(existingLabel -> {
                    if (existingLabel.getId().equals(labelToUpdate.getId()) &&
                            existingLabel.getStatus() == Status.ACTIVE) {
                        return labelToUpdate;
                    }
                    return existingLabel;
                })
                .collect(Collectors.toList());

        writeToFile(updatedLabels);
        return labelToUpdate;
    }


    @Override
    public List<Label> getAll() {
        return getAllInternal();
    }

    @Override
    public Label getById(Integer id) {
        return getAllInternal().stream()
                .filter(l -> l.getId().equals(id) && l.getStatus().equals(Status.ACTIVE))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean deleteById(Integer id) {
        List<Label> allLabels = getAllInternal();
        boolean labelResult = allLabels.stream()
                .filter(existingLabel -> existingLabel.getId().equals(id) &&
                        existingLabel.getStatus() == Status.ACTIVE)
                .findFirst()
                .map(existingLabel -> {
                    existingLabel.setStatus(Status.DELETED);
                    return true;
                })
                .orElse(false);

        writeToFile(allLabels);
        return labelResult;
    }
}

