package org.csystem.android.data.objectbox.repository;

import org.csystem.android.data.objectbox.entity.EntityBase;

import java.util.Optional;

public interface IObjectBoxCrudRepository<E extends EntityBase>  {

    long count();

    void delete(E entity);

    void deleteAll();

    void deleteAll(Iterable<? extends E> entities);

    void deleteById(Long id);

    boolean exitsById(Long id);

    Iterable<E> findAll();


    Iterable<E> findAllById(Iterable<Long> ids);

    Optional<E> findById(Long id);


    <S extends E> S save(S entity);

    <S extends E> Iterable<S> save(Iterable<S> entities);
}
