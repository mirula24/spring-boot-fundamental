package simpleCRUD.unitTest.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import simpleCRUD.unitTest.service.TransactionService;
import simpleCRUD.unitTest.util.dto.ToDo;
import simpleCRUD.unitTest.util.dto.TransactionDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
@Validated
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionDto request){
        transactionService.create(request);
        return ResponseEntity.ok("Transaction Succes");
    }
//    @GetMapping
//    public ResponseEntity<?> getAll(@PageableDefault Pageable pageable,@PathVariable String name){
//
//    }
    @GetMapping("/todos")
    public List<?> getToDo(){
        return transactionService.getAllToDo();
    }
    @GetMapping("/todos/{id}")
    public ToDo getOneToDo(@PathVariable Integer id){
        return transactionService.getOneTodo(id);
    }
    @GetMapping("/alpha")
    public String getFromAlpha(){
        return transactionService.getFromAlphavantage();
    }

    @DeleteMapping("/{id}")
    void detele (@PathVariable Integer id){
        transactionService.delete(id);
    }
}
