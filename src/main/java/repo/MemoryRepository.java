package repo;

import domain.Entity;

public class MemoryRepository<T extends Entity>extends AbstractRepository<T>{
    public void add(T elem) throws DuplicateIDException, RepositoryException {
        if(find(elem.getId())!=null)
            throw new DuplicateIDException("Exista deja un obiect cu acest id");
        entities.add(elem);
    }
    public void remove(int id)throws RepositoryException {
        if(find(id)==null)
            throw new RepositoryException("Nu exista acest element");
        entities.remove(find(id));
    }
    public T find(int id){
        for(T e:entities){
            if(id==e.getId())
                return e;
        }
        return null;
    }
    @Override
    public void update(T entity) throws RepositoryException {
        T entityToDelete = find(entity.getId());
        if(entityToDelete==null)
            throw new RepositoryException("Nu exista acest element");
        else
        {
            entities.remove(entityToDelete);
            entities.add(entity);
        }
    }
}
