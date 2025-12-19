package br.csi.politecnico.financecontrol.service;

import br.csi.politecnico.financecontrol.dto.LoginFormDTO;
import br.csi.politecnico.financecontrol.exception.BadRequestException;
import br.csi.politecnico.financecontrol.exception.NotFoundException;
import br.csi.politecnico.financecontrol.model.User;
import br.csi.politecnico.financecontrol.repository.UserRepository;
import br.csi.politecnico.financecontrol.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public String register(User user) {
        Optional<User> alreadyExists = userRepository.findByEmail(user.getEmail());
        if (alreadyExists.isPresent()) {
            throw new BadRequestException("Usuário já existente");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (Objects.nonNull(user.getRole()) && user.getRole().equals("ADMIN")) {
            user.setRole(user.getRole());
        } else {
            user.setRole("USER");
        }
        userRepository.save(user);
        return "Usuário cadastrado com sucesso!";
    }

    public String login(LoginFormDTO loginFormDTO) {
        User user = userRepository.findByEmail(loginFormDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("Usuário não existe"));

        if (!passwordEncoder.matches(loginFormDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException("Senha incorreta");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginFormDTO.getEmail(), loginFormDTO.getPassword())
        );

        Map<String, Object> claims = new HashMap<>();
        claims.put("cpf", user.getCpf());
        claims.put("email", user.getEmail());
        claims.put("uuid", user.getUuid().toString());
        claims.put("name", user.getName());
        claims.put("role", user.getRole());

        // Gerar token com UUID como subject
        return jwtUtil.generateToken(user.getUuid().toString(), claims);
    }
}