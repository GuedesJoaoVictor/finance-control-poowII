package br.csi.politecnico.financecontrol.controller;

import br.csi.politecnico.financecontrol.dto.LoginFormDTO;
import br.csi.politecnico.financecontrol.dto.ResponseDTO;
import br.csi.politecnico.financecontrol.exception.BadRequestException;
import br.csi.politecnico.financecontrol.exception.NotFoundException;
import br.csi.politecnico.financecontrol.model.User;
import br.csi.politecnico.financecontrol.repository.UserRepository;
import br.csi.politecnico.financecontrol.security.JwtUtil;
import br.csi.politecnico.financecontrol.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil1, UserRepository userRepository) {
        this.authService = authService;
        this.jwtUtil = jwtUtil1;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginFormDTO loginFormDTO) {
        try {
            String token = authService.login(loginFormDTO);

            String uuid = jwtUtil.extractUserUuid(token);
            String email = jwtUtil.extractEmail(token);
            String name = jwtUtil.extractName(token);
            String role = jwtUtil.extractRole(token);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("type", "Bearer");
            response.put("user", Map.of(
                    "uuid", uuid,
                    "email", email,
                    "name", name,
                    "role", role
            ));

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (BadRequestException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<String>> register(@RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.ok(authService.register(user)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.err(e.getMessage()));
        }
    }

}
