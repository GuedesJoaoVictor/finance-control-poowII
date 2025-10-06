package br.csi.politecnico.financecontrol.controller;

import br.csi.politecnico.financecontrol.dto.LoginFormDTO;
import br.csi.politecnico.financecontrol.exception.BadRequestException;
import br.csi.politecnico.financecontrol.exception.NotFoundException;
import br.csi.politecnico.financecontrol.model.User;
import br.csi.politecnico.financecontrol.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/auth")
@RestController
@Tag(name = "Auth", description = "Controller relacionado a registro, login e logout.")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Loga o usuário via token", description = "Faz o login do usuário na plataforma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o token de login"),
            @ApiResponse(responseCode = "404", description = "Usuário não existe"),
            @ApiResponse(responseCode = "400", description = "Senha incorreta"),
            @ApiResponse(responseCode = "500", description = "Corpo do erro")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginFormDTO loginFormDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginFormDTO));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Registrar usuário", description = "Registra usuários no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Usuário já existente"),
            @ApiResponse(responseCode = "400", description = "Mensagem do erro"),
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(user));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
