package com.coffeeshop.services;

import com.coffeeshop.interfaces.Manageable;
import com.coffeeshop.models.Category;
import com.coffeeshop.repositories.CategoryRepository;

import java.util.List;

/**
 * Manager service for category operations.
 */
public class CategoryService implements Manageable<Category> {
    private final CategoryRepository repository = new CategoryRepository();

    @Override
    public void add(Category item) throws Exception {
        repository.save(item);
    }

    @Override
    public void update(Category item) throws Exception {
        repository.update(item);
    }

    @Override
    public void delete(int id) throws Exception {
        repository.delete(id);
    }

    @Override
    public List<Category> getAll() throws Exception {
        return repository.findAll();
    }
}
