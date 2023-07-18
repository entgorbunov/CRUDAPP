package com.gorbunov.crudapp.controller;

import com.gorbunov.crudapp.model.Label;
import com.gorbunov.crudapp.repository.gson.GsonLabelStorageClass;
import com.gorbunov.crudapp.repository.LabelStorage;


import java.util.List;

public class LabelController {
    private final LabelStorage labelStorage = new GsonLabelStorageClass();
    public Label save(String name) {
        String checkedName = name.replaceAll("[^\\p{L}]", "");
        if(checkedName.isEmpty() || checkedName.isBlank() || checkedName.length() > 20)
            return null;

        Label label = new Label();
        label.setName(checkedName);

        return labelStorage.save(label);
    }

    public List<Label> getAll(){
        return labelStorage.getAll();
    }

    public Label getById(Integer id){
        if(id <= 0)
            return null;

        return labelStorage.getById(id);
    }

    public Label update(Label label){
        label.setName(label.getName().replaceAll("[^\\p{L}]", ""));
        if(label.getName().isEmpty() || label.getName().isBlank() || label.getName().length() > 20)
            return null;
        return labelStorage.update(label);
    }
    public boolean deleteById(Integer id){
        if(id <= 0)
            return false;

        return labelStorage.deleteById(id);
    }
}

