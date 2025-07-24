package domain;

public abstract class Entity {
    public int id;
    public Entity(int id)
    {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
