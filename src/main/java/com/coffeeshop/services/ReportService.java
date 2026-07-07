package com.coffeeshop.services;

import com.coffeeshop.models.Order;

import java.util.List;

/**
 * Builds operational reports from saved coffee shop orders.
 */
public class ReportService {
    /**
     * Builds a sales summary from completed orders saved in the database.
     *
     * @param orders completed orders
     * @return formatted sales report
     */
    public String buildSalesReport(List<Order> orders) {
        int count = 0;
        double subtotal = 0;
        double tax = 0;
        double discount = 0;
        double total = 0;

        for (Order order : orders) {
            count++;
            subtotal += order.getSubtotal();
            tax += order.getTax();
            discount += order.getDiscount();
            total += order.getTotal();
        }

        double average = count == 0 ? 0 : total / count;
        StringBuilder builder = new StringBuilder();
        builder.append("تقرير المبيعات").append(System.lineSeparator());
        builder.append("----------------").append(System.lineSeparator());
        builder.append("عدد الطلبات المكتملة: ").append(count).append(System.lineSeparator());
        builder.append("إجمالي المبيعات قبل الضريبة: ").append(String.format("%.2f", subtotal)).append(System.lineSeparator());
        builder.append("إجمالي الضريبة: ").append(String.format("%.2f", tax)).append(System.lineSeparator());
        builder.append("إجمالي الخصومات: ").append(String.format("%.2f", discount)).append(System.lineSeparator());
        builder.append("إجمالي المبيعات النهائي: ").append(String.format("%.2f", total)).append(System.lineSeparator());
        builder.append("متوسط قيمة الطلب: ").append(String.format("%.2f", average)).append(System.lineSeparator());

        if (!orders.isEmpty()) {
            builder.append(System.lineSeparator()).append("تفاصيل الطلبات").append(System.lineSeparator());
            builder.append("----------------").append(System.lineSeparator());
            for (Order order : orders) {
                builder.append("طلب رقم ").append(order.getId())
                        .append(" | التاريخ: ").append(order.getCreatedAt())
                        .append(" | الموظف: ").append(order.getCashier() == null ? "غير محدد" : order.getCashier().getName())
                        .append(" | الإجمالي: ").append(String.format("%.2f", order.getTotal()))
                        .append(System.lineSeparator());
            }
        }

        return builder.toString();
    }
}
