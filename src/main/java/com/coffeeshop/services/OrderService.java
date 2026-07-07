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
            throw new OrderProcessingException("يجب تسجيل الدخول قبل إنشاء طلب.");
        }
        return new Order(user);
    }

    /**
     * Adds product to order with validation.
     *
     * @param order active order
     * @param product selected product
     * @param quantity requested quantity
     * @throws OrderProcessingException when product or quantity is invalid
     */
    public void addProduct(Order order, Product product, int quantity) throws OrderProcessingException {
        if (order == null) {
            throw new OrderProcessingException("ابدأ طلبًا جديدًا أولًا.");
        }
        if (product == null || product.getStatus() != ProductStatus.AVAILABLE) {
            throw new OrderProcessingException("المنتج المحدد غير متوفر.");
        }
        if (quantity <= 0) {
            throw new OrderProcessingException("الكمية يجب أن تكون أكبر من صفر.");
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
            throw new OrderProcessingException("يجب أن يحتوي الطلب على منتج واحد على الأقل.");
        }
        double discount = customer instanceof VIPCustomer vip ? vip.calculateDiscount(order.getSubtotal()) : 0;
        order.setCustomer(customer);
        order.recalculate(discount);
        Payable payable = order;
        payable.calculateTotal();
        return repository.save(order);
    }

    /**
     * Loads completed orders for invoice and report screens.
     *
     * @return all completed orders
     * @throws Exception when loading fails
     */
    public List<Order> getAllOrders() throws Exception {
        return repository.findAll();
    }
}
