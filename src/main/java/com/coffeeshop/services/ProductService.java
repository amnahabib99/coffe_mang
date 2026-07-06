package com.coffeeshop.services;

import com.coffeeshop.enums.ProductStatus;
import com.coffeeshop.exceptions.EntityNotFoundException;
import com.coffeeshop.interfaces.Manageable;
import com.coffeeshop.models.Category;
import com.coffeeshop.models.Product;
import com.coffeeshop.repositories.ProductRepository;

import java.util.List;

/**
 * Manager service for product operations.
 */
public class ProductService implements Manageable<Product> {
    private final ProductRepository repository = new ProductRepository();

    @Override
    public void add(Product item) throws Exception {
        repository.save(item);
    }

    /**
     * Overloaded product creation method.
     *
     * @throws Exception when validation or saving fails
     */
    public void addProduct(String name, Category category, double price, String size) throws Exception {
        add(new Product(0, name, category, price, size, ProductStatus.AVAILABLE));
    }

    /**
     * Overloaded product creation method.
     *
     * @throws Exception when saving fails
     */
    public void addProduct(Product product) throws Exception {
        add(product);
    }

    @Override
    public void update(Product item) throws Exception {
        repository.update(item);
    }

    @Override
    public void delete(int id) throws Exception {
        repository.delete(id);
    }

    @Override
    public List<Product> getAll() throws Exception {
        return repository.findAll();
    }

    /**
     * Loads one product.
     *
     * @param id id
     * @return product
     * @throws Exception when not found
     */
    public Product findById(int id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found."));
    }
}
