package org.szymonmielecki.ordersmanager.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, String> {
    ProductModel findByName(String name);

    Boolean existsByName(String name);
}
