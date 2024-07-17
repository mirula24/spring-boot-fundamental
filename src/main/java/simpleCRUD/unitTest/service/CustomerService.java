package simpleCRUD.unitTest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import simpleCRUD.unitTest.model.Customer;
import simpleCRUD.unitTest.util.dto.CustomerDto;


public interface CustomerService {
    Customer create(CustomerDto request);
    Customer update(Integer id,CustomerDto request);
    Page<Customer> getAll(Pageable pageable, String name);
    Customer getOne(Integer id);
    void delete(Integer id);

}
