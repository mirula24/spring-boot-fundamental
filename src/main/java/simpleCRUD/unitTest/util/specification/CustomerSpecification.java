package simpleCRUD.unitTest.util.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import simpleCRUD.unitTest.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification {
    public static Specification<Customer> getSpecification(String title) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (title != null && !title.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + title.toLowerCase() + "%")
                );
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
