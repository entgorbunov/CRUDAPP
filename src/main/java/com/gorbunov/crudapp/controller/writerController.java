package com.gorbunov.crudapp.controller;


import com.gorbunov.crudapp.model.writer;
import com.gorbunov.crudapp.repository.gson.gsonWriterStorageClass;
import com.gorbunov.crudapp.repository.writerStorage;

import java.util.List;

public class writerController {
    private final writerStorage writerStorage = new gsonWriterStorageClass();

    public writer save(String firstName, String lastName) {
        String checkedFirstName = firstName.replaceAll("[^\\p{L}]", "");
        String checkedLastName = lastName.replaceAll("[^\\p{L}]", "");
        if (checkedFirstName.isEmpty() || checkedLastName.isEmpty()) return null;
        if (checkedFirstName.length() > 10 || checkedLastName.length() > 10) return null;
        writer writer = new writer();
        writer.setFirstName(checkedFirstName);
        writer.setLastName(checkedLastName);
        List<writer> allWriters = writerStorage.getAll();
        if (allWriters.size() == 0) {
            writer.setId(1);
        } else {
            int id = allWriters.get(allWriters.size() - 1).getId() + 1;
            writer.setId(id);
        }
        writerStorage.save(writer);
        return writer;
    }

    public writer getById(Integer id) {
        if (id <= 0) {
            return null;
        }
        return writerStorage.getById(id);
    }

    public List<writer> getAll() {
        return writerStorage.getAll();
    }

    public writer update(writer writer) {
        writer outdateWriter = getById(writer.getId());
        if (!writer.getFirstName().equals(outdateWriter.getFirstName())) {
            String checkedFirstName = writer.getFirstName().replaceAll("[^\\p{L}]", "");
            writer.setFirstName(checkedFirstName);
            if (writer.getFirstName().isEmpty()) {
                return null;
            }
            if (writer.getFirstName().length() > 10) {
                return null;
            }
        }
        if (!writer.getLastName().equals(outdateWriter.getLastName())) {
            String checkedFirstName = writer.getLastName().replaceAll("[^\\p{L}]", "");
            writer.setLastName(checkedFirstName);
            if (writer.getLastName().isEmpty()) {
                return null;
            }
            if (writer.getLastName().length() > 10) {
                return null;
            }
        }
        return writerStorage.update(writer);
    }

    public boolean deleteById(Integer id) {
        if (id <= 0) {
            return false;
        }
        return writerStorage.deleteById(id);
    }
}

