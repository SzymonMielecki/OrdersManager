package org.szymonmielecki.ordersmanager.order;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, String> {
    void deleteOrderModelById(String id);
}
