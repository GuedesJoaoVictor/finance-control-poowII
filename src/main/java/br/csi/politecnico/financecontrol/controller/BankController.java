package br.csi.politecnico.financecontrol.controller;

import br.csi.politecnico.financecontrol.dto.BankDTO;
import br.csi.politecnico.financecontrol.dto.ResponseDTO;
import br.csi.politecnico.financecontrol.exception.BadRequestException;
import br.csi.politecnico.financecontrol.exception.NotFoundException;
import br.csi.politecnico.financecontrol.model.Bank;
import br.csi.politecnico.financecontrol.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/bank")
@RestController
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> createBank(@RequestBody BankDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.ok(bankService.create(dto)));
        } catch (BadRequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(ex.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @GetMapping("/find-all")
    public ResponseEntity<ResponseDTO<List<BankDTO>>> findAllBanks() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok(bankService.findAll()));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<BankDTO>> findBankById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok(bankService.findById(id)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

//    @PutMapping
//    public ResponseEntity<ResponseDTO<Bank>> updateBank(@RequestBody BankDTO dto) {
//        try {
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBankById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(bankService.deleteById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
