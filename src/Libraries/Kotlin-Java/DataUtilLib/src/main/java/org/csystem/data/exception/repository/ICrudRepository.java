/*----------------------------------------------------------------
	FILE		: ICrudRepository.java
	AUTHOR		: JavaApp1-Nov-2023 Group
	LAST UPDATE	: 22nd June 2024

	ICrudRepository interface
	Copyleft (c) 1993 C and System Programmers Association
	All Rights Free
----------------------------------------------------------------*/
package org.csystem.data.exception.repository;

import java.util.Optional;

public interface ICrudRepository<T, ID> {
    long count();
    void delete(T entity);
    void deleteAll();
    void deleteAll(Iterable<? extends T> entities);
    void deleteAllById(Iterable<? extends ID> ids);
    void deleteById(ID id);
    boolean existsById(ID id);
    Iterable<T> findAll();
    Iterable<T> findAllById(Iterable<ID> ids);
    Optional<T> findById(ID id);
    <S extends T> S save(S entity);
    <S extends T> Iterable<S> saveAll(Iterable<S> entities);
}
