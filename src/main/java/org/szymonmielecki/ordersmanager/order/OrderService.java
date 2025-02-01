package org.szymonmielecki.ordersmanager.order;

import org.szymonmielecki.ordersmanager.product.ProductModel;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public interface OrderService {
    OrderModel createNewOrder(String client_id, List<ProductModel> products) throws IllegalArgumentException;

    OrderModel getOrderDetails(String id) throws NoSuchElementException;

    void deleteOrder(String id) throws NoSuchElementException;

    List<OrderModel> getOrders(Map<String, Object> filters);

    OrderModel updateOrder(String id, OrderModel update) throws NoSuchElementException;
}
