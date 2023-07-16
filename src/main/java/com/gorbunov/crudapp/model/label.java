package com.gorbunov.crudapp.model;

import java.util.Objects;

public class label {
    private Integer id;
    private String name;
    private com.gorbunov.crudapp.model.status status = com.gorbunov.crudapp.model.status.ACTIVE;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public com.gorbunov.crudapp.model.status getStatus() {
        return status;
    }

    public void setStatus(com.gorbunov.crudapp.model.status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof label label)) return false;
        return Objects.equals(getId(), label.getId()) && Objects.equals(getName(), label.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
