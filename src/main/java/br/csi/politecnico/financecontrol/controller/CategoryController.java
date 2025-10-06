package br.csi.politecnico.financecontrol.controller;

import br.csi.politecnico.financecontrol.dto.CategoryDTO;
import br.csi.politecnico.financecontrol.dto.ResponseDTO;
import br.csi.politecnico.financecontrol.exception.BadRequestException;
import br.csi.politecnico.financecontrol.exception.EntityExistsException;
import br.csi.politecnico.financecontrol.exception.NotFoundException;
import br.csi.politecnico.financecontrol.service.CategoryService;
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
import java.util.UUID;

@Controller
@RestController
@RequestMapping("/category")
@Tag(name = "Category", description = "Controller relacionado as operações de categoria")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/find-all")
    @Operation(summary = "Busca todos as categorias cadastrados", description = "Devolve uma lista de todas as categorias cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "204", description = "Sem categorias cadastrados.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor, mensagem do erro", content = @Content)
    })
    public ResponseEntity<ResponseDTO<List<CategoryDTO>>> findAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok(service.findALl()));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @GetMapping("/find-all-by-user/{uuid}")
    @Operation(summary = "Busca todas as categorias por usuário", description = "Devolve uma lista de categorias cadastradas pelo usuário")
    @Parameter(name = "uuid", description = "UUid único do usuário", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias cadastradas pelo usuário", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "O usuário não tem categorias cadastradas.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<ResponseDTO<List<CategoryDTO>>> findAllByUser(@PathVariable String uuid) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok(service.findAllByUser(uuid)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @PostMapping("/create-by-user-uuid/{uuid}")
    @Operation(summary = "Registra uma nova categoria criada pelo usuário")
    @Parameters(value = {
            @Parameter(name = "uuid", description = "UUid único do usuário", required = true),
            @Parameter(name = "dto", description = "DTO de categoria com nome e tipo")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criou com sucesso a categoria, retorna um DTO com a categoria criada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "A entidade já existe ou os campos não foram informados.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<ResponseDTO<CategoryDTO>> createByUserUuid(@PathVariable String uuid, @RequestBody CategoryDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.ok("Categoria criada com sucesso!", service.createByUserUuid(uuid, dto)));
        } catch (BadRequestException | EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @Operation(summary = "Deleta uma categoria pelo id")
    @Parameter(name = "id", description = "ID único da categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria foi deletada com sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "Categoria não foi encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content)
    })
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<ResponseDTO<Boolean>> deleteById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok("Deletado com sucesso!", service.deleteById(id)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

    @Operation(summary = "Atualiza a categoria pelo ID")
    @Parameters(value = {
            @Parameter(name = "uuid", description = "UUid único do usuário", required = true),
            @Parameter(name = "dto", description = "DTO de categoria com nome e tipo")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizou com sucesso a categoria, retorna um DTO com a categoria criada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "A categoria não foi encontrada para ser atualizada.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @PatchMapping("/update-by-id/{id}")
    public ResponseEntity<ResponseDTO<CategoryDTO>> updateById(@PathVariable("id") Long categoryId, @RequestBody CategoryDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.ok("Atualizado com sucesso!", service.updateById(categoryId, dto)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

}
