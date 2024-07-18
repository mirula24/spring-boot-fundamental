package simpleCRUD.unitTest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import simpleCRUD.unitTest.model.Transaction;
import simpleCRUD.unitTest.util.dto.ToDo;
import simpleCRUD.unitTest.util.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    List<ToDo> getAllToDo();
    ToDo getOneTodo(Integer id);
    String getFromAlphavantage();
    Transaction create(TransactionDto request);
    Page<Transaction> getAll(Pageable pageable,TransactionDto dto);
    Transaction getOne(Integer id);
    Transaction update(Integer id,TransactionDto request);
    void delete(Integer id);
}
