package com.coffeeshop.models;

import com.coffeeshop.interfaces.Payable;
import com.coffeeshop.interfaces.Printable;
import com.coffeeshop.utils.AppConstants;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Completed cafe order. Its inner {@link OrderItem} class models each line item.
 */
public class Order implements Payable, Printable {
    private int id;
    private Customer customer;
    private User cashier;
    private final List<OrderItem> items;
    private double subtotal;
    private double tax;
    private double discount;
    private double total;
    private String status;
    private LocalDateTime createdAt;

    /**
     * Creates an empty order for a cashier.
     *
     * @param cashier cashier
     */
    public Order(User cashier) {
        this(0, null, cashier, LocalDateTime.now());
    }

    /**
     * Creates an order with database information.
     *
     * @param id id
     * @param customer customer
     * @param cashier cashier
     * @param createdAt date
     */
    public Order(int id, Customer customer, User cashier, LocalDateTime createdAt) {
        this.id = id;
        this.customer = customer;
        this.cashier = cashier;
        this.items = new ArrayList<>();
        this.status = "COMPLETED";
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    /**
     * Adds a product to the order.
     *
     * @param product product
     * @param quantity quantity
     */
    public void addItem(Product product, int quantity) {
        items.add(new OrderItem(product.getId(), product.getName(), quantity, product.getPrice()));
        recalculate(0);
    }

    /**
     * Adds a database-loaded item without changing stored totals.
     *
     * @param item loaded item
     */
    public void addLoadedItem(OrderItem item) {
        items.add(item);
    }

    /**
     * Removes a product line by table index.
     *
     * @param index item index
     */
    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            recalculate(discount);
        }
    }

    /**
     * Recalculates totals with a discount.
     *
     * @param discount discount amount
     */
    public void recalculate(double discount) {
        subtotal = items.stream().mapToDouble(OrderItem::getLineTotal).sum();
        this.discount = Math.max(0, discount);
        tax = subtotal * AppConstants.TAX_RATE;
        total = calculateTotal();
    }

    @Override
    public double calculateTotal() {
        return Math.max(0, subtotal + tax - discount);
    }

    @Override
    public String print() {
        StringBuilder builder = new StringBuilder();
        builder.append("فاتورة رقم ").append(id == 0 ? "جديدة" : id).append(System.lineSeparator());
        builder.append("الموظف: ").append(cashier == null ? "غير محدد" : cashier.getName()).append(System.lineSeparator());
        for (OrderItem item : items) {
            builder.append(item.getProductName()).append(" × ").append(item.getQuantity())
                    .append(" = ").append(String.format("%.2f", item.getLineTotal())).append(System.lineSeparator());
        }
        builder.append("المجموع الفرعي: ").append(String.format("%.2f", subtotal)).append(System.lineSeparator());
        builder.append("الضريبة: ").append(String.format("%.2f", tax)).append(System.lineSeparator());
        builder.append("الخصم: ").append(String.format("%.2f", discount)).append(System.lineSeparator());
        builder.append("الإجمالي النهائي: ").append(String.format("%.2f", total));
        return builder.toString();
    }

    /**
     * Inner class representing a product line inside an order.
     */
    public class OrderItem {
        private int id;
        private int productId;
        private String productName;
        private int quantity;
        private double unitPrice;
        private double lineTotal;

        /**
         * Creates an order item.
         *
         * @param productId product id
         * @param productName product name snapshot
         * @param quantity quantity
         * @param unitPrice unit price snapshot
         */
        public OrderItem(int productId, String productName, int quantity, double unitPrice) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.lineTotal = quantity * unitPrice;
        }

        /**
         * Returns the database id of this order item.
         *
         * @return order item id
         */
        public int getId() {
            return id;
        }

        /**
         * Updates the database id after saving or loading the item.
         *
         * @param id order item id
         */
        public void setId(int id) {
            this.id = id;
        }

        /**
         * Returns the referenced product id.
         *
         * @return product id
         */
        public int getProductId() {
            return productId;
        }

        /**
         * Returns the product name snapshot stored on the invoice.
         *
         * @return product name
         */
        public String getProductName() {
            return productName;
        }

        /**
         * Returns the quantity sold.
         *
         * @return quantity
         */
        public int getQuantity() {
            return quantity;
        }

        /**
         * Returns the product unit price at sale time.
         *
         * @return unit price
         */
        public double getUnitPrice() {
            return unitPrice;
        }

        /**
         * Returns quantity multiplied by unit price.
         *
         * @return line total
         */
        public double getLineTotal() {
            return lineTotal;
        }
    }

    /**
     * Returns the order id.
     *
     * @return order id
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the order id after saving or loading.
     *
     * @param id order id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the customer linked to this order.
     *
     * @return customer, or null for walk-in orders
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Assigns an optional customer to the order.
     *
     * @param customer customer, or null for walk-in orders
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Returns the user who completed the order.
     *
     * @return cashier user
     */
    public User getCashier() {
        return cashier;
    }

    /**
     * Returns the order items as a read-only list.
     *
     * @return order items
     */
    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Returns the subtotal before tax and discount.
     *
     * @return subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Updates the subtotal loaded from the database.
     *
     * @param subtotal subtotal amount
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Returns the calculated tax amount.
     *
     * @return tax amount
     */
    public double getTax() {
        return tax;
    }

    /**
     * Updates the tax amount loaded from the database.
     *
     * @param tax tax amount
     */
    public void setTax(double tax) {
        this.tax = tax;
    }

    /**
     * Returns the discount applied to the order.
     *
     * @return discount amount
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Updates the discount amount.
     *
     * @param discount discount amount
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * Returns the final order total.
     *
     * @return final total
     */
    public double getTotal() {
        return total;
    }

    /**
     * Updates the final total loaded from the database.
     *
     * @param total final total
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Returns the order status.
     *
     * @return order status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns the order creation date and time.
     *
     * @return creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
