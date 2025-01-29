package org.szymonmielecki.ordersmanager.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.szymonmielecki.ordersmanager.OrderManagerApplication;
import org.szymonmielecki.ordersmanager.product.ProductModel;
import org.szymonmielecki.ordersmanager.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = OrderManagerApplication.class)
@AutoConfigureMockMvc
class OrderControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void cleanUp() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Transactional
    @Test
    void testCreateOrder() throws Exception {
        List<String> productNames = new ArrayList<>();
        productNames.add("product1");
        OrderDTO orderDTO = new OrderDTO(null, "NEW", "0000-0000-0000-0000", LocalDateTime.now().toString(), productNames);
        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON).content(
                mapper.writeValueAsBytes(orderDTO)
        )).andExpect(
                status().isOk()
        ).andExpect(jsonPath("$.clientId").value(orderDTO.getClientId()));
        Assertions.assertEquals(1, orderRepository.findAll().size());
    }

    @Test
    void testGetOrderDetailsExists() throws Exception {
        List<ProductModel> products = new ArrayList<ProductModel>();
        products.add(productRepository.save(new ProductModel("test")));
        OrderModel orderModel = orderRepository.save(
                new OrderModel(
                        OrderStatus.NEW,
                        "1111-1111-1111-1111",
                        products,
                        LocalDateTime.now()
                )
        );

        mvc.perform(get("/order/{id}", orderModel.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(
                        jsonPath("$.id").value(orderModel.getId())).andExpect(
                        jsonPath("$.clientId").value(orderModel.getClientId()));
    }

    @Test
    void testGetOrderDetailsNotExists() throws Exception {
        List<ProductModel> products = new ArrayList<ProductModel>();
        products.add(productRepository.save(new ProductModel("test")));
        OrderModel orderModel = orderRepository.save(
                new OrderModel(
                        OrderStatus.NEW,
                        "1111-1111-1111-1111",
                        products,
                        LocalDateTime.now()
                )
        );
        String differentUuid = UUID.randomUUID().toString();
        while (Objects.equals(differentUuid, orderModel.getId())) {
            differentUuid = UUID.randomUUID().toString();
        }


        mvc.perform(get("/order/{id}", differentUuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testDeleteOrderNotExists() throws Exception {
        List<ProductModel> products = new ArrayList<ProductModel>();
        products.add(productRepository.save(new ProductModel("test")));
        OrderModel orderModel = orderRepository.save(
                new OrderModel(
                        OrderStatus.NEW,
                        "1111-1111-1111-1111",
                        products,
                        LocalDateTime.now()
                )
        );
        String differentUuid = UUID.randomUUID().toString();
        while (Objects.equals(differentUuid, orderModel.getId())) {
            differentUuid = UUID.randomUUID().toString();
        }


        mvc.perform(delete("/order/{id}", differentUuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        Assertions.assertEquals(1, orderRepository.findAll().size());
    }

    @Transactional
    @Test
    void testUpdateOrderDetails() throws Exception {
        List<ProductModel> products = new ArrayList<ProductModel>();
        products.add(productRepository.save(new ProductModel("test")));
        OrderModel orderModel = orderRepository.save(
                new OrderModel(
                        OrderStatus.NEW,
                        "1111-1111-1111-1111",
                        products,
                        LocalDateTime.now()
                )
        );

        OrderDTO update = new OrderDTO(null, "IN_PROCESSING", null, null, null);
        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(put("/order/{id}", orderModel.getId())
                        .contentType(MediaType.APPLICATION_JSON).content(
                                mapper.writeValueAsBytes(update)
                        ))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(
                        jsonPath("$.status").value("IN_PROCESSING")
                );

        Assertions.assertEquals(
                OrderStatus.IN_PROCESSING, orderModel.getStatus()
        );
    }

    @Test
    @Transactional
    void testDeleteOrderExists() throws Exception {
        List<ProductModel> products = new ArrayList<ProductModel>();
        products.add(productRepository.save(new ProductModel("test")));
        OrderModel orderModel = orderRepository.save(
                new OrderModel(
                        OrderStatus.NEW,
                        "1111-1111-1111-1111",
                        products,
                        LocalDateTime.now()

                )
        );
        mvc.perform(delete("/order/{id}", orderModel.getId())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        Assertions.assertEquals(0, orderRepository.findAll().size());

    }

    @Transactional
    @Test
    void testGetAll() throws Exception {
        List<ProductModel> products = new ArrayList<ProductModel>();
        products.add(productRepository.save(new ProductModel("test")));
        OrderModel orderModel = orderRepository.save(
                new OrderModel(
                        OrderStatus.NEW,
                        "1111-1111-1111-1111",
                        products,
                        LocalDateTime.now()

                )
        );
        mvc.perform(get("/order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(
                        jsonPath("$[0].id").value(orderModel.getId()));
    }
}