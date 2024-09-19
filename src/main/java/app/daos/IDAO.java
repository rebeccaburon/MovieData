package app.daos;

import java.util.Set;


public interface IDAO  <T>{
    T getId(Integer id);

    Set<T> getAll();

    void create(T t);

    void update(T t);

    void delete(T t);
}
