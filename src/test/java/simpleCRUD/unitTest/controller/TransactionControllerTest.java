package simpleCRUD.unitTest.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import simpleCRUD.unitTest.model.Customer;
import simpleCRUD.unitTest.model.Transaction;
import simpleCRUD.unitTest.service.TransactionService;
import simpleCRUD.unitTest.util.dto.TransactionDto;

import java.awt.*;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Transaction transaction;
    private TransactionDto transactionDto;
    private Customer customer;
    @BeforeEach
    void setUp(){
        customer = Customer.builder()
                .id(1)
                .birthDate(new Date(2000,10,24))
                .name("mirul")
                .build();
        transaction = Transaction.builder()
                .customer_id(customer)
                .product_name("pc")
                .Id(1)
                .price(1000)
                .quantity(500)
                .build();
        transactionDto = TransactionDto.builder()
                .product_name("pc")
                .customer_id(1)
                .price(1000)
                .quantity(500)
                .build();

    }

//
//    @Test
//    void createTransaction() throws Exception {
//        when(transactionService.create(any(TransactionDto.class))).thenReturn(transaction);
//
//        mockMvc.perform(post("/api/v1/transaction")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(transactionDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.price").value(transaction.getPrice()))
//                .andExpect(jsonPath("$.quantity").value(transaction.getQuantity()));
//
//        verify(transactionService).create(any(TransactionDto.class));
//    }

    @Test
    void deleteTransaction() throws Exception{
        doNothing().when(transactionService).delete(transaction.getId());
        mockMvc.perform(delete("/api/v1/transaction/"+transaction.getId()))
                .andExpect(status().isOk());

        verify(transactionService).delete(1);

    }





}
