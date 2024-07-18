package simpleCRUD.unitTest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import simpleCRUD.unitTest.model.Customer;
import simpleCRUD.unitTest.model.Transaction;
import simpleCRUD.unitTest.repository.TransactionRepository;
import simpleCRUD.unitTest.service.implementation.TransactionImplementation;
import simpleCRUD.unitTest.util.dto.CustomerDto;
import simpleCRUD.unitTest.util.dto.TransactionDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionImplementation transactionImplementation;

    @Mock
    private CustomerService customerService;

    private Customer customer;
    private Transaction transaction;
    private TransactionDto transactionDto;

    @BeforeEach
    void setUp(){
        customer = Customer.builder()
                .name("amirul")
                .birthDate(new Date(2000,10,24))
                .id(1)
                .build();
        transaction =Transaction.builder()
                .Id(1)
                .customer_id(customer)
                .price(20000000)
                .product_name("laptop")
                .quantity(5)
                .build();


        transactionDto = TransactionDto.builder()
                .customer_id(1)
                .price(20000000)
                .product_name("laptop")
                .quantity(5)
                .build();
        MockitoAnnotations.openMocks(this);

    }
    @Test
    void create_transaction_success_test(){
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionImplementation.create(transactionDto);

        assertEquals(transaction, result);
    }


    @DisplayName("input the list of transactions")
    @Test
    void getAlltransaction_success(){
        List<Transaction> trasactions = new ArrayList<>();
        trasactions.add(transaction);
        trasactions.add(transaction);
        trasactions.add(transaction);

        Page<Transaction> customers = new PageImpl<>(trasactions);

        // Mocking repository method
        given(transactionRepository.findAll(any(Specification.class), any(Pageable.class))).willReturn(customers);

        // Testing the service method
        Page<Transaction> result = transactionImplementation.getAll(Pageable.unpaged(), new TransactionDto());

        // Verifying that the repository method was called with the expected parameters
        verify(transactionRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));

        // Verifying that the result matches the mock data
        assertEquals(customers.getTotalElements(), result.getTotalElements());
        assertEquals(customers, result);
    }

    @Test
    void updateTransaction_success(){
        int id = 1;
        TransactionDto updateRequest = TransactionDto.builder()
                .product_name("pc")
                .customer_id(id)
                .price(1000)
                .quantity(1)
                .build();
    }

    @Test
    void deleteTransaction_succes(){
        when(transactionRepository.findById(transaction.getId()))
                .thenReturn(Optional.of(transaction))
                .thenReturn(Optional.empty());
        doNothing().when(transactionRepository).delete(transaction);

        transactionImplementation.delete(transaction.getId());

        verify(transactionRepository).findById(transaction.getId());
        verify(transactionRepository).delete(transaction);

        Optional<Transaction> deleteTransaction = transactionRepository.findById(transaction.getId());
        assertThat(deleteTransaction).isEmpty();
    }


}
