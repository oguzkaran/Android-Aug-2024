package org.csystem.android.data.objectbox.repository;

import org.csystem.android.data.objectbox.entity.EntityBase;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import io.objectbox.Box;

public class ObjectBoxCrudRepository<E extends EntityBase> implements IObjectBoxCrudRepository<E> {
    protected final Box<E> box;

    public ObjectBoxCrudRepository(Box<E> box)
    {
        this.box = box;
    }

    @Override
    public long count()
    {
        return box.count();
    }

    @Override
    public void delete(E entity)
    {
        deleteById(entity.id);
    }

    @Override
    public void deleteAll()
    {
        box.removeAll();
    }

    @Override
    public void deleteAll(Iterable<? extends E> entities)
    {
        box.remove(StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toList()));
    }

    @Override
    public void deleteById(Long id)
    {
        box.remove(id);
    }

    @Override
    public boolean exitsById(Long id)
    {
        return findById(id).isPresent();
    }

    @Override
    public Iterable<E> findAll()
    {
        return box.query().build().find();
    }

    @Override
    public Iterable<E> findAllById(Iterable<Long> ids)
    {
        return StreamSupport.stream(ids.spliterator(), false)
                .map(i -> box.query().filter(e -> e.id == i).build().findFirst())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<E> findById(Long id)
    {
        return Optional.ofNullable(box.query()
                .filter(e -> e.id == id)
                .build().findFirst());
    }

    @Override
    public <S extends E> S save(S entity)
    {
        entity.id = box.put(entity);

        return entity;
    }

    @Override
    public <S extends E> Iterable<S> save(Iterable<S> entities)
    {
        return StreamSupport.stream(entities.spliterator(), false)
                .collect(Collectors.toList());
    }
}
