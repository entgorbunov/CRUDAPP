package com.gorbunov.crudapp.model;

import java.util.List;
import java.util.Objects;

public class Writer {
    private Integer id;
    private String firstName;
    private String lastName;
    private List<Post> Posts;
    private Status status = Status.ACTIVE;


    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", posts=" + Posts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Writer writer)) return false;
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

    public List<Post> getPosts() {
        return Posts;
    }

    public void setPosts(List<Post> Posts) {
        this.Posts = Posts;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

