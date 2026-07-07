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
     * Returns the readable role text used in reports and polymorphism demonstrations.
     *
     * @return role description used for polymorphism demonstration
     */
    public abstract String getRoleDescription();

    /**
     * Returns the database identifier.
     *
     * @return person id
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the database identifier after loading or saving a record.
     *
     * @param id person id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the person display name.
     *
     * @return person name
     */
    public String getName() {
        return name;
    }

    /**
     * Validates and stores the person display name.
     *
     * @param name person name
     * @throws InvalidInputException when the name is blank
     */
    public final void setName(String name) throws InvalidInputException {
        ValidationUtils.requireText(name, "Ø§Ù„Ø§Ø³Ù…");
        this.name = name.trim();
    }

    /**
     * Returns the stored phone number.
     *
     * @return phone number, or an empty string when not provided
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Stores the phone number as trimmed text.
     *
     * @param phone phone number
     */
    public final void setPhone(String phone) {
        this.phone = phone == null ? "" : phone.trim();
    }
}


