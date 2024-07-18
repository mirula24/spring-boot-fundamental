package simpleCRUD.unitTest.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import simpleCRUD.unitTest.model.Customer;
import simpleCRUD.unitTest.model.Transaction;
import simpleCRUD.unitTest.repository.CustomerRepository;
import simpleCRUD.unitTest.repository.TransactionRepository;
import simpleCRUD.unitTest.service.CustomerService;
import simpleCRUD.unitTest.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simpleCRUD.unitTest.util.dto.ToDo;
import simpleCRUD.unitTest.util.dto.TransactionDto;
import simpleCRUD.unitTest.util.specification.GeneralSpecification;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionImplementation implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CustomerService customerService;
    private final RestClient restClient;
    private final String placeHolder = "https://jsonplaceholder.typicode.com/todos";

    private final String alpha = "https://www.alphavantage.co/query";

    @Override
    public List<ToDo> getAllToDo() {
      return restClient.get()
              .uri(placeHolder)
              .retrieve()
              .body(new ParameterizedTypeReference<List<ToDo>>() {
              });
    }

    @Override
    public ToDo getOneTodo(Integer id) {
        return restClient.get()
                .uri(placeHolder+"/"+id)
                .retrieve()
                .body(new ParameterizedTypeReference<ToDo>() {});
    }

    @Override
    public String getFromAlphavantage() {
        String uri = UriComponentsBuilder.fromHttpUrl("https://www.alphavantage.co/query")
                .queryParam("function", "TIME_SERIES_DAILY")
                .queryParam("symbol", "IBM")
                .queryParam("apikey", "ZDTH89D9LOW3Z8DQ")
                .toUriString();
        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<String>() {});

    }

    @Override
    public Transaction create(TransactionDto request) {
        if (request.getProduct_name() == null || request.getProduct_name().isEmpty()) {
            throw new IllegalArgumentException("The Product_name cannot be null or empty");
        }
        Transaction newTransaction = Transaction.builder()
                .product_name(request.getProduct_name())
                .customer_id(customerService.getOne(request.getCustomer_id()))
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        return transactionRepository.save(newTransaction);

    }

    @Override
    public Page<Transaction> getAll(Pageable pageable, TransactionDto dto) {
        Specification<Transaction> specification = GeneralSpecification.getSpecification(dto);
        return transactionRepository .findAll(specification, pageable);
    }

    @Override
    public Transaction getOne(Integer id) {
        return transactionRepository.findById(id).orElseThrow(()-> new RuntimeException("Transaction not Found"));
    }

    @Override
    public Transaction update(Integer id, TransactionDto request) {
        Transaction update = getOne(id);
        update.setPrice(request.getPrice());
        update.setQuantity(request.getQuantity());
        update.setCustomer_id(customerService.getOne(request.getCustomer_id()));
        update.setProduct_name(request.getProduct_name());
        return transactionRepository.save(update);
    }

    @Override
    public void delete(Integer id) {
        Transaction deleteTrans = getOne(id);
        transactionRepository.delete(deleteTrans);

    }
}
