package br.csi.politecnico.financecontrol.controller;

import br.csi.politecnico.financecontrol.dto.BankDTO;
import br.csi.politecnico.financecontrol.dto.ResponseDTO;
import br.csi.politecnico.financecontrol.dto.UserBankDTO;
import br.csi.politecnico.financecontrol.exception.BadRequestException;
import br.csi.politecnico.financecontrol.exception.NotFoundException;
import br.csi.politecnico.financecontrol.model.Bank;
import br.csi.politecnico.financecontrol.service.BankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bank")
@RestController
@Tag(name = "Bank", description = "Controller dedicado as operações relacionadas ao banco.")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping
    @Operation(summary = "Cira um novo banco", description = "Recebe um DTO e cria o banco na base.")
    @Parameter(name = "dto", description = "Nome e tipo do banco", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Banco criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Bank.class))),
            @ApiResponse(responseCode = "400", description = "Banco já existe", content = @Content),
            @ApiResponse(responseCode = "500", description = "Mensagem do erro", content = @Content)
    })
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
    @Operation(summary = "Busca todos os bancos cadastrados", description = "Devolve uma lista de todos os bancos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de bancos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "204", description = "Sem bancos cadastrados.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor, mensagem do erro", content = @Content)
    })
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

    @GetMapping("/find-by-id/{id}")
    @Operation(summary = "Busca o banco por id", description = "Retorna um DTO do banco buscado por ID")
    @Parameter(name = "id", description = "ID do banco", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um DTO do banco", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Bank.class))),
            @ApiResponse(responseCode = "404", description = "Banco não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do banco")
    })
    public ResponseEntity<ResponseDTO<BankDTO>> findBankById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok(bankService.findById(id)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @PatchMapping("/update-by-id/{id}")
    @Operation(summary = "Atualiza o banco pelo ID", description = "Recebe um DTO e o ID pela url para atualizar o banco.")
    @Parameters(value = {
            @Parameter(name = "id", description = "ID do banco", required = true),
            @Parameter(name = "dto", description = "Nome do banco e tipo do banco a ser atualizado", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Bank.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum banco encontrado.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor, mensagem do erro", content = @Content)
    })
    public ResponseEntity<ResponseDTO<BankDTO>> updateBank(@RequestBody BankDTO dto, @PathVariable("id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok("Atualizado com sucesso!", bankService.update(id, dto)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @DeleteMapping("/delete-by-id/{id}")
    @Operation(summary = "Deleta o banco pelo ID", description = "Recebe o id pela url e deleta caso exista")
    @Parameter(name = "id", description = "ID do banco", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Banco excluido com sucesso!", content = @Content),
            @ApiResponse(responseCode = "404", description = "Banco não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
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

    @PostMapping("/vinculate/by/user/{uuid}")
    public ResponseEntity<ResponseDTO<UserBankDTO>> vinculateUserBank(@PathVariable String uuid, @RequestBody UserBankDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.ok("Vinculado com sucesso!", bankService.vinculateUserBank(uuid, dto)));
        } catch (BadRequestException | NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/user-bank/by/{id}")
    public ResponseEntity<ResponseDTO<Boolean>> deleteUserBankById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok("Deletado com sucesso!", bankService.deleteUserBankById(id)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }
}
