package com.gorbunov.crudapp.controller;

import com.gorbunov.crudapp.model.label;
import com.gorbunov.crudapp.repository.gson.gsonLabelStorageClass;
import com.gorbunov.crudapp.repository.labelStorage;


import java.util.List;

public class labelController {
    private final labelStorage labelStorage = new gsonLabelStorageClass();
    public label save(String name) {
        String checkedName = name.replaceAll("[^\\p{L}]", "");
        if(checkedName.isEmpty() || checkedName.isBlank() || checkedName.length() > 20)
            return null;

        label label = new label();
        label.setName(checkedName);

        List<label> allLabels = getAll();
        if(allLabels.size() == 0)
            label.setId(1);
        else {
            int id = allLabels.get(allLabels.size() - 1).getId() + 1;
            label.setId(id);
        }

        labelStorage.save(label);

        return label;
    }

    public List<label> getAll(){
        return labelStorage.getAll();
    }

    public label getById(Integer id){
        if(id <= 0)
            return null;

        return labelStorage.getById(id);
    }

    public label update(label label){
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

