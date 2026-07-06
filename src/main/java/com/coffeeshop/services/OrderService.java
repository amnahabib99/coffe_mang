package com.coffeeshop.services;

import com.coffeeshop.enums.ProductStatus;
import com.coffeeshop.exceptions.OrderProcessingException;
import com.coffeeshop.interfaces.Payable;
import com.coffeeshop.models.Customer;
import com.coffeeshop.models.Order;
import com.coffeeshop.models.Product;
import com.coffeeshop.models.User;
import com.coffeeshop.models.VIPCustomer;
import com.coffeeshop.repositories.OrderRepository;
import com.coffeeshop.utils.SessionManager;

import java.util.List;

/**
 * Handles order creation and completion.
 */
public class OrderService {
    private final OrderRepository repository = new OrderRepository();

    /**
     * Starts a new order for the logged-in cashier.
     *
     * @return new order
     * @throws OrderProcessingException when no user is logged in
     */
    public Order startOrder() throws OrderProcessingException {
        User user = SessionManager.getCurrentUser();
        if (user == null) {
            throw new OrderProcessingException("You must log in before creating an order.");
        }
        return new Order(user);
    }

    /**
     * Adds product to order with validation.
     *
     * @throws OrderProcessingException when product or quantity is invalid
     */
    public void addProduct(Order order, Product product, int quantity) throws OrderProcessingException {
        if (order == null) {
            throw new OrderProcessingException("Start an order first.");
        }
        if (product == null || product.getStatus() != ProductStatus.AVAILABLE) {
            throw new OrderProcessingException("Selected product is not available.");
        }
        if (quantity <= 0) {
            throw new OrderProcessingException("Quantity must be greater than zero.");
        }
        order.addItem(product, quantity);
    }

    /**
     * Completes and saves an order.
     *
     * @param order order
     * @param customer optional customer
     * @return saved order
     * @throws Exception when order is invalid or save fails
     */
    public Order completeOrder(Order order, Customer customer) throws Exception {
        if (order == null || order.getItems().isEmpty()) {
            throw new OrderProcessingException("Order must contain at least one item.");
        }
        double discount = customer instanceof VIPCustomer vip ? vip.calculateDiscount(order.getSubtotal()) : 0;
        order.setCustomer(customer);
        order.recalculate(discount);
        Payable payable = order;
        payable.calculateTotal();
        return repository.save(order);
    }

    /**
     * @return all completed orders
     * @throws Exception when loading fails
     */
    public List<Order> getAllOrders() throws Exception {
        return repository.findAll();
    }
}
