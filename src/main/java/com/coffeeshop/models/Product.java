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
     * @param name product name
     * @param category saved product category
     * @param price product price
     * @throws InvalidInputException when data is invalid
     */
    public Product(String name, Category category, double price) throws InvalidInputException {
        this(0, name, category, price, "عادي", ProductStatus.AVAILABLE);
    }

    /**
     * Creates a product with all fields.
     *
     * @param id product id
     * @param name product name
     * @param category saved product category
     * @param price product price
     * @param size product size label
     * @param status product availability status
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
        return name + " - " + size + " (" + String.format("%.2f", price) + ")";
    }

    /**
     * Returns the product id.
     *
     * @return product id
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the product id.
     *
     * @param id product id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the product name.
     *
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * Validates and stores the product name.
     *
     * @param name product name
     * @throws InvalidInputException when the name is blank
     */
    public final void setName(String name) throws InvalidInputException {
        ValidationUtils.requireText(name, "اسم المنتج");
        this.name = name.trim();
    }

    /**
     * Returns the saved category assigned to the product.
     *
     * @return product category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Assigns a saved category to the product.
     *
     * @param category saved category
     * @throws InvalidInputException when the category is missing or unsaved
     */
    public final void setCategory(Category category) throws InvalidInputException {
        if (category == null || category.getId() <= 0) {
            throw new InvalidInputException("يجب أن يرتبط المنتج بتصنيف محفوظ.");
        }
        this.category = category;
    }

    /**
     * Returns the product price.
     *
     * @return product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Validates and stores the product price.
     *
     * @param price product price
     * @throws InvalidInputException when the price is not positive
     */
    public final void setPrice(double price) throws InvalidInputException {
        ValidationUtils.requirePositive(price, "السعر");
        this.price = price;
    }

    /**
     * Returns the size label.
     *
     * @return size label
     */
    public String getSize() {
        return size;
    }

    /**
     * Updates the size label.
     *
     * @param size size label
     */
    public void setSize(String size) {
        this.size = size == null ? "" : size.trim();
    }

    /**
     * Returns the availability status.
     *
     * @return product status
     */
    public ProductStatus getStatus() {
        return status;
    }

    /**
     * Updates the availability status.
     *
     * @param status product status
     */
    public void setStatus(ProductStatus status) {
        this.status = status;
    }
}
