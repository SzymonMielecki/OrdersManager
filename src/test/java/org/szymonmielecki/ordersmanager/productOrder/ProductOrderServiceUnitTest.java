package org.szymonmielecki.ordersmanager.productOrder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.szymonmielecki.ordersmanager.order.OrderMapper;
import org.szymonmielecki.ordersmanager.order.OrderMapperImpl;
import org.szymonmielecki.ordersmanager.order.OrderServiceImpl;
import org.szymonmielecki.ordersmanager.order.OrderStatus;
import org.szymonmielecki.ordersmanager.product.ProductService;

import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
class ProductOrderServiceUnitTest {
    private ProductOrderService productOrderService;
    @Captor
    private ArgumentCaptor<String> idCaptor;
    @Captor
    private ArgumentCaptor<String> nameCaptor;
    @Mock
    private ProductService productService;
    @Mock
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        OrderMapper orderMapper = new OrderMapperImpl();
        productOrderService = new ProductOrderService(orderMapper, productService, orderService);
    }

    @Test
    void createNewOrderWithProducts() {

    }

    @Test
    void getOrderDetails() {
        productOrderService.getOrderDetails("0000-0000-0000-0000");
        Mockito.verify(orderService).getOrderDetails(idCaptor.capture());
        Assertions.assertEquals("0000-0000-0000-0000", idCaptor.getValue());
    }

    @Test
    void createOrFindProductByName() {
        productOrderService.createOrFindProductByName("test");
        Mockito.verify(productService).createOrFindProductByName(nameCaptor.capture());
        Assertions.assertEquals("test", nameCaptor.getValue());
    }

    @Test
    void deleteOrder() {
        orderService.deleteOrder("0000-0000-0000-0000");
        Mockito.verify(orderService).deleteOrder(idCaptor.capture());
        Assertions.assertEquals("0000-0000-0000-0000", idCaptor.getValue());
    }

    @Test
    void testGetOrdersByStatus() {
        HashMap<String, Object> filters = new HashMap<>();
        filters.put("status", OrderStatus.NEW);
        productOrderService.getOrders(filters);
        Mockito.verify(orderService).getOrders(filters);
    }
}