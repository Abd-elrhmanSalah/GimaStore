package com.gima.gimastore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface CommonRepo<E> {

    public void add(E dto) throws IOException;

    public void update(E dto);

    public void delete(Long id);

    public E findById(Long id);

    public Page<E> findAll(Pageable pageable);
}
