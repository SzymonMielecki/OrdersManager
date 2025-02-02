package org.szymonmielecki.ordersmanager.productOrder;

import org.springframework.stereotype.Service;
import org.szymonmielecki.ordersmanager.order.OrderDTO;
import org.szymonmielecki.ordersmanager.order.OrderMapper;
import org.szymonmielecki.ordersmanager.order.OrderService;
import org.szymonmielecki.ordersmanager.product.ProductModel;
import org.szymonmielecki.ordersmanager.product.ProductService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductOrderService {

    private final OrderMapper orderMapper;

    private final ProductService productService;
    private final OrderService orderService;

    public ProductOrderService(OrderMapper orderMapper, ProductService productService, OrderService orderService) {
        this.orderMapper = orderMapper;
        this.productService = productService;
        this.orderService = orderService;
    }

    public OrderDTO createNewOrderWithProducts(OrderDTO order) throws IllegalArgumentException {
        return orderMapper.orderModelToDTO(
                orderService.createNewOrder(
                        order.getClientId(), order.getProductNames().stream().map(
                                this::createOrFindProductByName
                        ).collect(Collectors.toList())
                ));
    }

    public OrderDTO getOrderDetails(String id) throws NoSuchElementException {
        return orderMapper.orderModelToDTO(orderService.getOrderDetails(id));
    }

    public ProductModel createOrFindProductByName(String name) {
        return productService.createOrFindProductByName(name);
    }

    public void deleteOrder(String id) throws NoSuchElementException {
        orderService.deleteOrder(id);
    }

    public List<OrderDTO> getOrders(Map<String, Object> filters) {
        return orderService.getOrders(filters).stream()
                .map(
                        orderMapper::orderModelToDTO
                ).toList();
    }

    public OrderDTO updateOrder(String orderId, OrderDTO orderDTO) throws NoSuchElementException {
        return orderMapper.orderModelToDTO(orderService.updateOrder(orderId, orderMapper.orderDTOToModel(orderDTO)));
    }
}
