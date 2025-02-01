package org.szymonmielecki.ordersmanager.order;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OrderUtil {
    public static Specification<OrderModel> withFilters(Map<String, Object> filters) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();
            filters.forEach(
                    (key, value) -> {
                        if (Arrays.stream(OrderModel.class.getFields()).map(Field::getName).anyMatch(field -> field.equals(key))) {
                            predicates.add(cb.equal(root.get(key), value));
                        }
                    }
            );
            return query.where(cb.and(predicates.toArray(new Predicate[0])))
                    .distinct(true).getRestriction();
        };
    }
}