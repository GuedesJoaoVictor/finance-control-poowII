package br.csi.politecnico.financecontrol.controller;

import br.csi.politecnico.financecontrol.dto.CategoryDTO;
import br.csi.politecnico.financecontrol.dto.ResponseDTO;
import br.csi.politecnico.financecontrol.exception.BadRequestException;
import br.csi.politecnico.financecontrol.exception.EntityExistsException;
import br.csi.politecnico.financecontrol.exception.NotFoundException;
import br.csi.politecnico.financecontrol.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/find-all")
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
    public ResponseEntity<ResponseDTO<CategoryDTO>> createByUserUuid(@PathVariable String uuid, @RequestBody CategoryDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.ok("Categoria criada com sucesso!", service.createByUserUuid(uuid, dto)));
        } catch (BadRequestException | EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.err(e.getMessage()));
        }
    }

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
