package com.gima.gimastore.repository;

import java.io.IOException;
import java.util.List;

public interface CommonRepo<E> {

    public void add(E dto) throws IOException;

    public void update(E dto);

    public void delete(Long id);

    public E findById(Long id);

    public List<E> findAll();
}
