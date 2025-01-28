package org.szymonmielecki.ordersmanager.order;

import jakarta.persistence.*;
import org.szymonmielecki.ordersmanager.product.ProductModel;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private OrderStatus status;

    private String clientId;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<ProductModel> products;
    private LocalDateTime creationDate;

    public OrderModel() {

    }

    public OrderModel(String id, OrderStatus status, String clientId, List<ProductModel> products, LocalDateTime creationDate) {
        this.id = id;
        this.status = status;
        this.clientId = clientId;
        this.products = products;
        this.creationDate = creationDate;
    }

    public OrderModel(OrderStatus status, String clientId, List<ProductModel> products, LocalDateTime creationDate) {
        this.status = status;
        this.clientId = clientId;
        this.products = products;
        this.creationDate = creationDate;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> product_ids) {
        this.products = product_ids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String client_id) {
        this.clientId = client_id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creation_date) {
        this.creationDate = creation_date;
    }

}
