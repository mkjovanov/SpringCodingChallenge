package access.rights.rest.api.repository;

import java.util.List;

public abstract class IRepository <T extends IEntity>{
    public abstract List<T> getAll();
    public abstract T get(Integer id);
    public abstract void add(T newEntity);
    public abstract void update(T updatedEntity);
    public abstract void delete(Integer id);
}
