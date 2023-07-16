package com.gorbunov.crudapp.model;

import java.util.List;
import java.util.Objects;

public class writer {
    private Integer id;
    private String firstName;
    private String lastName;
    private List<post> posts;
    private com.gorbunov.crudapp.model.status status = com.gorbunov.crudapp.model.status.ACTIVE;


    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", posts=" + posts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof writer writer)) return false;
        return getId() == writer.getId() && Objects.equals(getFirstName(), writer.getFirstName()) && Objects.equals(getLastName(), writer.getLastName()) && Objects.equals(getPosts(), writer.getPosts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPosts());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<post> getPosts() {
        return posts;
    }

    public void setPosts(List<post> posts) {
        this.posts = posts;
    }

    public com.gorbunov.crudapp.model.status getStatus() {
        return status;
    }

    public void setStatus(com.gorbunov.crudapp.model.status status) {
        this.status = status;
    }
}

