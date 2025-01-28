package org.szymonmielecki.ordersmanager.product;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public ProductModel createOrFindProductByName(String name) {
        if (productRepository.existsByName(name)) {
            return productRepository.findByName(name);
        }
        return productRepository.save(new ProductModel(name));
    }

}
