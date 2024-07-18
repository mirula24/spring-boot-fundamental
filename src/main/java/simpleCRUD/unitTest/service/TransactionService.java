package simpleCRUD.unitTest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import simpleCRUD.unitTest.model.Transaction;
import simpleCRUD.unitTest.util.dto.TransactionDto;

public interface TransactionService {
    Transaction create(TransactionDto request);
    Page<Transaction> getAll(Pageable pageable,TransactionDto dto);
    Transaction getOne(Integer id);
    Transaction update(Integer id,TransactionDto request);
    void delete(Integer id);
}
