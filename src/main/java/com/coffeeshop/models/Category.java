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
     * @param name category name
     * @param description category description
     * @throws InvalidInputException when name is blank
     */
    public Category(String name, String description) throws InvalidInputException {
        this(0, name, description);
    }

    /**
     * Creates a category with id.
     *
     * @param id category id
     * @param name category name
     * @param description category description
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

    /**
     * Returns the category id.
     *
     * @return category id
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the category id.
     *
     * @param id category id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the category name.
     *
     * @return category name
     */
    public String getName() {
        return name;
    }

    /**
     * Validates and stores the category name.
     *
     * @param name category name
     * @throws InvalidInputException when the name is blank
     */
    public final void setName(String name) throws InvalidInputException {
        ValidationUtils.requireText(name, "اسم التصنيف");
        this.name = name.trim();
    }

    /**
     * Returns the category description.
     *
     * @return category description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Stores the category description.
     *
     * @param description category description
     */
    public void setDescription(String description) {
        this.description = description == null ? "" : description.trim();
    }
}
