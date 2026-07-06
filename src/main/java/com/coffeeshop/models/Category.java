package com.coffeeshop.models;

import com.coffeeshop.exceptions.InvalidInputException;
import com.coffeeshop.utils.ValidationUtils;

/**
 * Product category such as Coffee, Tea, or Dessert.
 */
public class Category {
    private int id;
    private String name;
    private String description;

    /**
     * Creates a category without id.
     *
     * @throws InvalidInputException when name is blank
     */
    public Category(String name, String description) throws InvalidInputException {
        this(0, name, description);
    }

    /**
     * Creates a category with id.
     *
     * @throws InvalidInputException when name is blank
     */
    public Category(int id, String name, String description) throws InvalidInputException {
        this.id = id;
        setName(name);
        this.description = description == null ? "" : description.trim();
    }

    @Override
    public String toString() {
        return name;
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
        ValidationUtils.requireText(name, "اسم التصنيف");
        this.name = name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description.trim();
    }
}
