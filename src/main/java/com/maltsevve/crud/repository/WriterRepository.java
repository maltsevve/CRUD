package com.maltsevve.crud.repository;

import com.maltsevve.crud.model.Writer;

import java.util.List;

public interface WriterRepository extends GenericRepository<Writer, Long>{
    @Override
    Writer save(Writer writer);

    @Override
    Writer update(Writer writer);

    @Override
    Writer getById(Long id);

    @Override
    List<Writer> getAll();

    @Override
    void deleteById(Long id);
}
