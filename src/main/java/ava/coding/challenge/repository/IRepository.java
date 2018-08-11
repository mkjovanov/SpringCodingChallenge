package ava.coding.challenge.repository;

import java.util.List;

public abstract class IRepository <T extends IEntity>{
    public abstract List<T> getAll();
    public abstract T get(String id);
    public abstract void add(T newEntity);
    public abstract void update(String id, T updatedEntity);
    public abstract void delete(String id);
}
