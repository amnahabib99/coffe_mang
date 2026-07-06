package com.coffeeshop.abstracts;

import com.coffeeshop.exceptions.InvalidInputException;
import com.coffeeshop.utils.ValidationUtils;

/**
 * Abstract base class for people in the cafe domain.
 */
public abstract class Person {
    private int id;
    private String name;
    private String phone;

    /**
     * Creates a person without id.
     *
     * @param name person name
     * @param phone phone number
     * @throws InvalidInputException when name is blank
     */
    protected Person(String name, String phone) throws InvalidInputException {
        setName(name);
        setPhone(phone);
    }

    /**
     * Creates a person with id.
     *
     * @param id id
     * @param name person name
     * @param phone phone number
     * @throws InvalidInputException when name is blank
     */
    protected Person(int id, String name, String phone) throws InvalidInputException {
        this(name, phone);
        this.id = id;
    }

    /**
     * @return role description used for polymorphism demonstration
     */
    public abstract String getRoleDescription();

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
        ValidationUtils.requireText(name, "Name");
        this.name = name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public final void setPhone(String phone) {
        this.phone = phone == null ? "" : phone.trim();
    }
}
