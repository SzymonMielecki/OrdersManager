package org.szymonmielecki.ordersmanager.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.szymonmielecki.ordersmanager.product.ProductService;


@Mapper(componentModel = "spring")
public abstract class OrderMapper {
    @Autowired
    protected ProductService productService;

    @Mapping(target = "productNames", expression = "java(orderModel.getProducts().stream().map(p-> p.getName()).collect(java.util.stream.Collectors.toList()))")
    public abstract OrderDTO orderModelToDTO(OrderModel orderModel);

    @Mapping(target = "products", expression = "java(orderDTO.getProductNames() != null ? orderDTO.getProductNames().stream().map(o -> productService.createOrFindProductByName(o)).collect(java.util.stream.Collectors.toList()) : new java.util.ArrayList<>())")
    public abstract OrderModel orderDTOToModel(OrderDTO orderDTO);

}
