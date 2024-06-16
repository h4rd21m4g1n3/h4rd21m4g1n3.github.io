package cz.cvut.fel.nss.sem.security;

public class Optional<T> {
    private T obj = null;
    public Optional() {}

    public T get(){return obj;}
    public void set(T obj){this.obj = obj;}

    public boolean isPresent(){return obj != null;}
}
