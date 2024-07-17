package simpleCRUD.unitTest.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import simpleCRUD.unitTest.model.Customer;
import simpleCRUD.unitTest.repository.CustomerRepository;
import simpleCRUD.unitTest.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simpleCRUD.unitTest.util.dto.CustomerDto;
import simpleCRUD.unitTest.util.specification.CustomerSpecification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class CustomerImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer create(CustomerDto request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }

        if (!isValidDate(request.getBirthDate())) {
            throw new IllegalArgumentException("Invalid date format");
        }

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setBirthDate(request.getBirthDate());

        return customerRepository.save(customer);
    }

    private boolean isValidDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public Customer update(Integer id, CustomerDto request) {
        Customer customer = getOne(id);
        customer.setName(request.getName());
        if (!isValidDate(request.getBirthDate())) {
            throw new IllegalArgumentException("Invalid date format");
        }
        customer.setBirthDate(request.getBirthDate());
        return customerRepository.save(customer);
    }

    @Override
    public Page<Customer> getAll(Pageable pageable, String name) {
        Specification<Customer> spec = CustomerSpecification.getSpecification(name);
        return customerRepository.findAll(spec, pageable);
    }

    @Override
    public Customer getOne(Integer id) {
        return customerRepository.findById(id).orElseThrow(()-> new RuntimeException("User not Found"));
    }

    @Override
    public void delete(Integer id) {
        Customer delete = getOne(id);
        customerRepository.delete(delete);

    }
}
