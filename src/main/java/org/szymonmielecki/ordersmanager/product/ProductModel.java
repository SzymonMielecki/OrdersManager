package org.szymonmielecki.ordersmanager.product;

import jakarta.persistence.*;
import org.szymonmielecki.ordersmanager.order.OrderModel;

@Entity
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderModel order;

    public ProductModel() {
    }

    public ProductModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }
}
