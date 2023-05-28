package com.gima.gimastore.repository;

import java.util.List;

public interface CommonRepo<E> {

    public void add(E dto);

    public void update(E dto);

    public void delete(Long id);

    public E findById(Long id);

    public List<E> findAll();
}
