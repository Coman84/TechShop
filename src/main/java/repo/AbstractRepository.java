package repo;

import domain.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractRepository<T extends Entity>implements Iterable<T> {
    protected List<T> entities=new ArrayList<T>();

    public abstract void add(T elem) throws DuplicateIDException, RepositoryException;

    public abstract void remove(int id) throws RepositoryException;
    public abstract T find(int id);
    public int size(){return entities.size();}
    public Collection<T> getAll() throws RepositoryException {
        return new ArrayList<>(entities);
    }
    public Iterator<T>iterator(){return entities.iterator();}
    public abstract void update(T entity) throws RepositoryException;
}
