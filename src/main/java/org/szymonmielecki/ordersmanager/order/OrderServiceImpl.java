package org.szymonmielecki.ordersmanager.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.szymonmielecki.ordersmanager.product.ProductModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    public OrderServiceImpl() {
    }

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderModel createNewOrder(String client_id, List<ProductModel> products) throws IllegalArgumentException {
        if (client_id == null || products.isEmpty()) {
            throw new IllegalArgumentException();
        }
        OrderModel order = new OrderModel(OrderStatus.NEW, client_id, products, LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Override
    public OrderModel getOrderDetails(String id) throws NoSuchElementException {
        Optional<OrderModel> opt = orderRepository.findById(id);
        if (opt.isEmpty()) {
            throw new NoSuchElementException();
        }
        return opt.get();
    }

    @Override
    public void deleteOrder(String id) throws NoSuchElementException {
        if (!orderRepository.existsById(id)) {
            throw new NoSuchElementException();
        }
        orderRepository.deleteOrderModelById(id);
    }

    @Override
    public List<OrderModel> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderModel updateOrder(String id, OrderModel update) throws NoSuchElementException {
        if (!orderRepository.existsById(id)) {
            throw new NoSuchElementException();
        }
        update.setId(id);
        return orderRepository.save(update);
    }
}
