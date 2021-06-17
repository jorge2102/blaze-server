package com.blaze.server.services.interfaces;

import java.util.List;

public interface IService<T> {
    
    public List<T> getAll();
    public T get(String id);
    public T save(T item);
    public T update(T item);
    public void delete(String id);

}
