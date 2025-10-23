package br.csi.politecnico.financecontrol.controller;

import br.csi.politecnico.financecontrol.dto.ExpenseDTO;
import br.csi.politecnico.financecontrol.dto.ResponseDTO;
import br.csi.politecnico.financecontrol.dto.RevenueDTO;
import br.csi.politecnico.financecontrol.exception.BadRequestException;
import br.csi.politecnico.financecontrol.model.Bank;
import br.csi.politecnico.financecontrol.model.Revenue;
import br.csi.politecnico.financecontrol.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Busca todas as transações de receita", description = "Retorna uma lista de todas as transações de receita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de receitas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Revenue.class))),
            @ApiResponse(responseCode = "400", description = "Nenhuma receita encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
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
    @Operation(summary = "Registra uma nova transação de receita", description = "Registra uma nova transação de receita")
    @Parameter(name = "revenue", description = "DTO de receita com valor e data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Receita criada com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RevenueDTO.class))),
            @ApiResponse(responseCode = "400", description = "A entidade já existe ou os campos não foram informados.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<ResponseDTO<RevenueDTO>> createRevenue(@RequestBody RevenueDTO revenue) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.ok("Receita criada com sucesso!", transactionService.createRevenue(revenue)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @GetMapping("/find-revenue/by/{id}")
    @Operation(summary = "Busca uma transação de receita pelo id", description = "Retorna uma transação de receita pelo id")
    @Parameter(name = "id", description = "ID único da transação de receita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação de receita encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RevenueDTO.class))),
            @ApiResponse(responseCode = "400", description = "Transação de receita não encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
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
    @Operation(summary = "Atualiza uma transação de receita pelo id", description = "Atualiza uma transação de receita pelo id")
    @Parameters(value = {
            @Parameter(name = "revenue", description = "DTO de receita com valor e data"),
            @Parameter(name = "id", description = "ID único da transação de receita")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação de receita atualizada com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RevenueDTO.class))),
            @ApiResponse(responseCode = "400", description = "A entidade já existe ou os campos não foram informados.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<ResponseDTO<RevenueDTO>> updateRevenue(@PathVariable Long id, @RequestBody RevenueDTO revenue) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok("Receita atualizada com sucesso!", transactionService.updateRevenueById(id, revenue)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/revenue/by/{id}")
    @Operation(summary = "Deleta uma transação de receita pelo id", description = "Deleta uma transação de receita pelo id")
    @Parameter(name = "id", description = "ID único da transação de receita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação de receita deletada com sucesso!", content = @Content),
            @ApiResponse(responseCode = "400", description = "Transação de receita não encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<ResponseDTO<Boolean>> deleteRevenueById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok("Receita deletada com sucesso!", transactionService.deleteRevenueById(id)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @GetMapping("/find-all/revenue/by/user/{uuid}")
    @Operation(summary = "Busca todas as transações de receita por usuário", description = "Retorna uma lista de todas as transações de receita por usuário")
    @Parameter(name = "uuid", description = "UUid único do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de receitas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Revenue.class))),
            @ApiResponse(responseCode = "400", description = "Nenhuma receita encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
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
    @Operation(summary = "Busca todas as transações de despesa", description = "Retorna uma lista de todas as transações de despesa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de despesas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Nenhuma despesa encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
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
    @Operation(summary = "Registra uma nova transação de despesa", description = "Registra uma nova transação de despesa")
    @Parameter(name = "expense", description = "DTO de despesa com valor e data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Despesa criada com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseDTO.class))),
            @ApiResponse(responseCode = "400", description = "A entidade já existe ou os campos não foram informados.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<ResponseDTO<ExpenseDTO>> createExpense(@RequestBody ExpenseDTO expense) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.ok("Despesa criada com sucesso!", transactionService.createExpense(expense)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @PatchMapping("/update/expense/{id}")
    @Operation(summary = "Atualiza uma transação de despesa pelo id", description = "Atualiza uma transação de despesa pelo id")
    @Parameters(value = {
            @Parameter(name = "expense", description = "DTO de despesa com valor e data"),
            @Parameter(name = "id", description = "ID único da transação de despesa")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Despesa de despesa atualizada com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseDTO.class))),
            @ApiResponse(responseCode = "400", description = "A entidade já existe ou os campos não foram informados.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<ResponseDTO<ExpenseDTO>> updateExpense(@PathVariable Long id, @RequestBody ExpenseDTO expense) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok("Despesa atualizada com sucesso!", transactionService.updateExpenseById(id, expense)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/expense/by/{id}")
    @Operation(summary = "Deleta uma transação de despesa pelo id", description = "Deleta uma transação de despesa pelo id")
    @Parameter(name = "id", description = "ID único da transação de despesa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Despesa deletada com sucesso!", content = @Content),
            @ApiResponse(responseCode = "400", description = "Despesa não encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<ResponseDTO<Boolean>> deleteExpenseById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok("Despesa deletada com sucesso!", transactionService.deleteExpenseById(id)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

}
