package org.szymonmielecki.ordersmanager.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.szymonmielecki.ordersmanager.product.ProductModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplUnitTest {

    private OrderServiceImpl orderService;

    @Captor
    private ArgumentCaptor<String> idCaptor;

    @Mock
    private OrderRepository orderRepository;


    @BeforeEach
    public void setUp() {
        orderService = new OrderServiceImpl(orderRepository);
    }


    @Test
    void testCreateNewOrderThrowsClientId() {
        List<ProductModel> products = new ArrayList<ProductModel>();
        products.add(new ProductModel());
        Assertions.assertThrows(IllegalArgumentException.class, () -> orderService.createNewOrder(null, products));
    }

    @Test
    void testCreateNewOrderThrowsProducts() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> orderService.createNewOrder("0000-0000-0000-0000", new ArrayList<ProductModel>()));
    }

    @Test
    void testCreateNewOrder() {
        List<ProductModel> products = new ArrayList<ProductModel>();
        products.add(new ProductModel("test"));
        OrderModel expected = new OrderModel("1111-1111-1111-1111", OrderStatus.NEW, "0000-0000-0000-0000", products, LocalDateTime.now());
        Mockito.when(orderRepository.save(
                any(OrderModel.class)
        )).thenReturn(
                expected
        );
        Assertions.assertEquals(
                expected, orderService.createNewOrder("0000-0000-0000-0000", products));
    }

    @Test
    void testGetOrderDetails() {
        List<ProductModel> products = new ArrayList<ProductModel>();
        products.add(new ProductModel("test"));
        OrderModel expected = new OrderModel("1111-1111-1111-1111", OrderStatus.NEW, "0000-0000-0000-0000", products, LocalDateTime.now());
        Mockito.when(orderRepository.findById(any())).thenReturn(
                Optional.of(expected)
        );
        Assertions.assertEquals(
                expected,
                orderService.getOrderDetails("1111-1111-1111-1111")
        );
        Mockito.verify(orderRepository).findById(idCaptor.capture());
        Assertions.assertEquals(
                "1111-1111-1111-1111", idCaptor.getValue()
        );
    }

    @Test
    void testDeleteOrder() {
        List<ProductModel> products = new ArrayList<ProductModel>();
        products.add(new ProductModel("test"));
        OrderModel orderModel = new OrderModel("1111-1111-1111-1111", OrderStatus.NEW, "0000-0000-0000-0000", products, LocalDateTime.now());
        Mockito.when(orderRepository.existsById("1111-1111-1111-1111")).thenReturn(true);
        Mockito.doNothing().when(orderRepository).deleteOrderModelById(idCaptor.capture());
        orderService.deleteOrder(orderModel.getId());
        Assertions.assertEquals("1111-1111-1111-1111", idCaptor.getValue());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testGetOrdersByStatus() {
        HashMap<String, Object> filters = new HashMap<>();
        filters.put("status", OrderStatus.NEW);
        orderService.getOrders(filters);
        Mockito.verify(orderRepository).findAll(any(Specification.class));
    }
}