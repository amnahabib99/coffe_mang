package com.coffeeshop.services;

import com.coffeeshop.abstracts.Person;
import com.coffeeshop.enums.UserStatus;
import com.coffeeshop.models.Employee;
import com.coffeeshop.models.Manager;
import com.coffeeshop.models.VIPCustomer;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds reports, including a visible OOP polymorphism report.
 */
public class ReportService {
    /**
     * Demonstrates polymorphism by storing subclasses as Person references.
     *
     * @return report text
     * @throws Exception when model creation fails
     */
    public String buildOopDemoReport() throws Exception {
        List<Person> people = new ArrayList<>();
        people.add(new Manager(1, "Sara Manager", "sara", "x", "111", "Q", "A", UserStatus.ACTIVE));
        people.add(new Employee(2, "Omar Employee", "omar", "x", "222", "Q", "A", UserStatus.ACTIVE));
        people.add(new VIPCustomer(3, "Lina VIP", "333"));
        StringBuilder builder = new StringBuilder("OOP Polymorphism Demo\n");
        for (Person person : people) {
            builder.append(person.getName()).append(" -> ").append(person.getRoleDescription()).append('\n');
        }
        return builder.toString();
    }
}
