package simpleCRUD.unitTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import simpleCRUD.unitTest.model.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> , JpaSpecificationExecutor<Customer> {
    Optional<Customer> findById(Integer id);
}
