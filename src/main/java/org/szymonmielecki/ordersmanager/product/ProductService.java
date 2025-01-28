package org.szymonmielecki.ordersmanager.product;

public interface ProductService {
    ProductModel createOrFindProductByName(String name);
}
