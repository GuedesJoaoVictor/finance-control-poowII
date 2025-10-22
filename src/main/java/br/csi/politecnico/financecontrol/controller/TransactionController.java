package br.csi.politecnico.financecontrol.controller;

import br.csi.politecnico.financecontrol.dto.ResponseDTO;
import br.csi.politecnico.financecontrol.dto.RevenueDTO;
import br.csi.politecnico.financecontrol.dto.TransactionDTO;
import br.csi.politecnico.financecontrol.exception.BadRequestException;
import br.csi.politecnico.financecontrol.model.Revenue;
import br.csi.politecnico.financecontrol.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
