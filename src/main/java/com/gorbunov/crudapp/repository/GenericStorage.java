package com.gorbunov.crudapp.repository;

import java.util.List;

public interface GenericStorage<T, ID> {
    T save(T t);
    T update(T t);
    List<T> getAll();
    T getById(ID id);
    boolean deleteById(ID id);
}
