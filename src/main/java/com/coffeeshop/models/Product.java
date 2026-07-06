package com.coffeeshop.models;

import com.coffeeshop.enums.ProductStatus;
import com.coffeeshop.exceptions.InvalidInputException;
import com.coffeeshop.utils.ValidationUtils;

/**
 * Sellable cafe product.
 */
public class Product {
    private int id;
    private String name;
    private Category category;
    private double price;
    private String size;
    private ProductStatus status;

    /**
     * Creates an available product.
     *
     * @throws InvalidInputException when data is invalid
     */
    public Product(String name, Category category, double price) throws InvalidInputException {
        this(0, name, category, price, "Regular", ProductStatus.AVAILABLE);
    }

    /**
     * Creates a product with all fields.
     *
     * @throws InvalidInputException when data is invalid
     */
    public Product(int id, String name, Category category, double price, String size, ProductStatus status)
            throws InvalidInputException {
        this.id = id;
        setName(name);
        setCategory(category);
        setPrice(price);
        this.size = size == null ? "" : size.trim();
        this.status = status == null ? ProductStatus.AVAILABLE : status;
    }

    @Override
    public String toString() {
        return name + " - " + size + " ($" + String.format("%.2f", price) + ")";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) throws InvalidInputException {
        ValidationUtils.requireText(name, "Product name");
        this.name = name.trim();
    }

    public Category getCategory() {
        return category;
    }

    public final void setCategory(Category category) throws InvalidInputException {
        if (category == null || category.getId() <= 0) {
            throw new InvalidInputException("Product must belong to a saved category.");
        }
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public final void setPrice(double price) throws InvalidInputException {
        ValidationUtils.requirePositive(price, "Price");
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? "" : size.trim();
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }
}
