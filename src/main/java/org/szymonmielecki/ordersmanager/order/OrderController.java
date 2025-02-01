package org.szymonmielecki.ordersmanager.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.szymonmielecki.ordersmanager.productOrder.ProductOrderService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final ProductOrderService productOrderService;

    public OrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }


    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order) {
        try {
            return ResponseEntity.ok(productOrderService.createNewOrderWithProducts(order));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderDetails(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(productOrderService.getOrderDetails(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrderDetails(@PathVariable("id") String id, @RequestBody OrderDTO orderDTO) {
        try {
            return ResponseEntity.ok(productOrderService.updateOrder(id, orderDTO));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable("id") String id) {
        try {
            productOrderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<OrderDTO> getAll(@RequestParam Map<String, Object> allRequestParams) {
        return productOrderService.getOrders(allRequestParams);
    }
}
