package br.csi.politecnico.financecontrol.controller;

import br.csi.politecnico.financecontrol.dto.ExpenseDTO;
import br.csi.politecnico.financecontrol.dto.ResponseDTO;
import br.csi.politecnico.financecontrol.dto.RevenueDTO;
import br.csi.politecnico.financecontrol.dto.TransactionDTO;
import br.csi.politecnico.financecontrol.exception.BadRequestException;
import br.csi.politecnico.financecontrol.model.Revenue;
import br.csi.politecnico.financecontrol.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/find-all/revenues")
    public ResponseEntity<ResponseDTO<List<RevenueDTO>>> findAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok(transactionService.findAll()));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @PostMapping("/create/revenue")
    public ResponseEntity<ResponseDTO<RevenueDTO>> createRevenue(@RequestBody RevenueDTO revenue) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.ok("Transação criada com sucesso!", transactionService.createRevenue(revenue)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @GetMapping("/find-revenue/by/{id}")
    public ResponseEntity<ResponseDTO<RevenueDTO>> findRevenueById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok(transactionService.findRevenueById(id)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @PutMapping("/update/revenue/{id}")
    public ResponseEntity<ResponseDTO<RevenueDTO>> updateRevenue(@PathVariable Long id, @RequestBody RevenueDTO revenue) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok("Transação atualizada com sucesso!", transactionService.updateRevenueById(id, revenue)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/revenue/by/{id}")
    public ResponseEntity<ResponseDTO<Boolean>> deleteRevenueById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok("Transação deletada com sucesso!", transactionService.deleteRevenueById(id)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @GetMapping("/find-all/revenue/by/user/{uuid}")
    public ResponseEntity<ResponseDTO<List<RevenueDTO>>> findAllRevenueByUser(@PathVariable String uuid) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok(transactionService.findAllRevenueByUserUuid(uuid)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @GetMapping("/find-all/expenses")
    public ResponseEntity<ResponseDTO<List<ExpenseDTO>>> findAllExpenses() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok(transactionService.findAllExpenses()));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @PostMapping("/create/expense")
    public ResponseEntity<ResponseDTO<ExpenseDTO>> createExpense(@RequestBody ExpenseDTO expense) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.ok("Transação criada com sucesso!", transactionService.createExpense(expense)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @PatchMapping("/update/expense/{id}")
    public ResponseEntity<ResponseDTO<ExpenseDTO>> updateExpense(@PathVariable Long id, @RequestBody ExpenseDTO expense) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok("Transação atualizada com sucesso!", transactionService.updateExpenseById(id, expense)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/expense/by/{id}")
    public ResponseEntity<ResponseDTO<Boolean>> deleteExpenseById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok("Transação deletada com sucesso!", transactionService.deleteExpenseById(id)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

}
