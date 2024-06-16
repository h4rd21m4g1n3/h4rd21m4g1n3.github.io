package cz.cvut.fel.nss.sem.model;

public interface DTO<Key, Record>{
    Record getRecord();
    Key getKey();
}
