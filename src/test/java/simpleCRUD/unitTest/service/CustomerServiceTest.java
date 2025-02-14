package simpleCRUD.unitTest.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import simpleCRUD.unitTest.model.Customer;
import simpleCRUD.unitTest.model.Transaction;
import simpleCRUD.unitTest.repository.CustomerRepository;
import simpleCRUD.unitTest.service.implementation.CustomerImpl;
import simpleCRUD.unitTest.util.dto.CustomerDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Slf4j
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerImpl customerImpl;

    private Customer customer;
    private CustomerDto customerDto;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setId(2);
        customer.setName("Amirul");
        customer.setBirthDate(new Date(2000,10,24));
    }

    @DisplayName("JUnit test for save customer")
    @Test
    public void createCustomer() {
        //given
        CustomerDto customerDto = new CustomerDto(customer.getName(), customer.getBirthDate());
        given(customerRepository.save(Mockito.any(Customer.class))).willReturn(customer);

        //when
        Customer createCustomer = customerImpl.create(customerDto);
        log.info("saved customer {}", createCustomer.toString());

        //then
        assertNotNull(createCustomer);
        assertEquals(createCustomer.getName(), customer.getName());
        assertEquals(createCustomer.getBirthDate(), customer.getBirthDate());
    }

    @DisplayName("JUnit test for get all Customers")
    @Test
    public void getAllCustomers() {
        //given
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        customerList.add(customer);
        customerList.add(customer);

        Page<Customer> customers = new PageImpl<>(customerList);

        // Mocking repository method
        given(customerRepository.findAll(any(Specification.class), any(Pageable.class))).willReturn(customers);

        // Testing the service method
        Page<Customer> result = customerImpl.getAll(Pageable.unpaged(), new CustomerDto().getName());

        // Verifying that the repository method was called with the expected parameters
        verify(customerRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));

        // Verifying that the result matches the mock data
        assertEquals(customers.getTotalElements(), result.getTotalElements());
        assertEquals(customers, result);
    }

    @DisplayName("JUnit test for get Customer by id ")
    @Test
    public void getOneCustomer() {
        //given
        given(customerRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.of(customer));

        //when
        Customer oneCustomer = customerImpl.getOne(customer.getId());
        log.info("this is customer service by id {}", oneCustomer);

        //then
        assertNotNull(oneCustomer);
        assertEquals(oneCustomer.getId(), customer.getId());
        assertEquals(oneCustomer.getName(), customer.getName());
        assertEquals(oneCustomer.getBirthDate(), customer.getBirthDate());
    }

    @DisplayName("JUnit test for update customer")
    @Test
    public void updateCustomer() {
        Integer customerId = 2;
        CustomerDto customerDto = new CustomerDto("muhammad", new Date(2000,1,1));

        when(customerRepository.findById(any(Integer.class))).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Act
        Customer result = customerImpl.update(customerId, customerDto);

        // Assert
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).save(customer);

        assertEquals(result.getName(), customerDto.getName());
        assertEquals(result.getBirthDate(), customerDto.getBirthDate());
    }
    @Test
    void deleteCuctumer_succes(){
        when(customerRepository.findById(customer.getId()))
                .thenReturn(Optional.of(customer))
                .thenReturn(Optional.empty());
        doNothing().when(customerRepository).delete(customer);

        customerImpl.delete(customer.getId());

        verify(customerRepository).findById(customer.getId());
        verify(customerRepository).delete(customer);

        Optional<Customer> deleteTransaction = customerRepository.findById(customer.getId());
        assertThat(deleteTransaction).isEmpty();
    }

    @Test
    public void NameIsNull() {
        // given
        customer = new Customer();
        customer.setId(2);
        customer.setName(null);
        customer.setBirthDate(new Date(2000, 10, 24));

        customerDto = new CustomerDto(customer.getName(), customer.getBirthDate());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            customerImpl.create(customerDto);
        });
    }

    @Test
    public void nameIsEmpty() {
        // given
        customer = new Customer();
        customer.setId(2);
        customer.setName(null);
        customer.setBirthDate(new Date(2000, 10, 24));

        customerDto = new CustomerDto(customer.getName(), customer.getBirthDate());

        customer.setName("");
        customerDto.setName("");

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            customerImpl.create(customerDto);
        });
    }
    @Test
    public void wrongInputDate() {
        customer = new Customer();
        customer.setId(2);
        customer.setName(null);
        customer.setBirthDate(new Date(2000, 10, 24));

        customerDto = new CustomerDto(customer.getName(), customer.getBirthDate());

        // given
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date invalidDate;
        try {
            invalidDate = sdf.parse("24-10-2000");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        customerDto.setBirthDate(invalidDate);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            customerImpl.create(customerDto);
        });
    }

    @Test
    public void customerIdNotFound(){
        assertThrows(RuntimeException.class, () -> {
            customerImpl.getOne(4);
        });
    }
    @Test
    public void updateCustomerWronginputdate(){
        assertThrows(RuntimeException.class, () -> {
            CustomerDto update = CustomerDto.builder()
                    .name(customer.getName())
                    .birthDate(new Date())
                    .build();
            customerImpl.update(2,update);
        });
    }
    @Test
    public void deleteCustomerIdNotFound(){
        assertThrows(RuntimeException.class, () -> {
            customerImpl.delete(4);
        });
    }
}