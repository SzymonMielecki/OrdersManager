package org.szymonmielecki.ordersmanager.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplUnitTest {

    @Captor
    ArgumentCaptor<ProductModel> productModelCaptor;
    @Captor
    private ArgumentCaptor<String> nameCaptor;
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;

    @Test
    void testCreateOrFindProductByNameFind() {
        Mockito.when(productRepository.existsByName("test")).thenReturn(true);
        productService.createOrFindProductByName("test");
        Mockito.verify(productRepository).findByName(nameCaptor.capture());
        Assertions.assertEquals(
                "test", nameCaptor.getValue());
    }

    @Test
    void testCreateOrFindProductByNameCreate() {
        Mockito.when(productRepository.existsByName("test")).thenReturn(false);
        productService.createOrFindProductByName("test");
        Mockito.verify(productRepository).save(productModelCaptor.capture());
        Assertions.assertEquals(
                "test", productModelCaptor.getValue().getName());
    }
}