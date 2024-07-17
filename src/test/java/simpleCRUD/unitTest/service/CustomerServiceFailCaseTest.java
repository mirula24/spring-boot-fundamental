package simpleCRUD.unitTest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import simpleCRUD.unitTest.model.Customer;
import simpleCRUD.unitTest.repository.CustomerRepository;
import simpleCRUD.unitTest.service.implementation.CustomerImpl;
import simpleCRUD.unitTest.util.dto.CustomerDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceFailCaseTest{

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerImpl customerImpl;

    private Customer customer;
    private CustomerDto customerDto;

    @BeforeEach
    public void setup() {
        customer = new Customer();
        customer.setId(2);
        customer.setName(null);
        customer.setBirthDate(new Date(2000, 10, 24));

        customerDto = new CustomerDto(customer.getName(), customer.getBirthDate());
    }

    @Test
    public void NameIsNull() {
        // given

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            customerImpl.create(customerDto);
        });
    }

    @Test
    public void nameIsEmpty() {
        // given
        customer.setName("");
        customerDto.setName("");

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            customerImpl.create(customerDto);
        });
    }
    @Test
    public void wrongInputDate() {
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
