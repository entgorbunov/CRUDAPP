package com.gorbunov.crudapp.model;


import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Post {
    private Integer id;
    private String content;
    private Date created;
    private Date updated;
    private List<Label> Labels;
    private Status status = Status.ACTIVE;


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", labels=" + Labels +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return getId() == post.getId() && Objects.equals(getContent(), post.getContent()) && Objects.equals(getCreated(), post.getCreated()) && Objects.equals(getUpdated(), post.getUpdated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContent(), getCreated(), getUpdated());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public List<Label> getLabels() {
        return Labels;
    }

    public void setLabels(List<Label> Labels) {
        this.Labels = Labels;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

