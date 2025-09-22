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
        Optional<User> alreadExists = userRepository.findByEmail(user.getEmail());
        if (alreadExists.isPresent()) {
            throw new BadRequestException("Usuário já existente");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Usuário cadastrado com sucesso!";
    }

    public String login(LoginFormDTO loginFormDTO) {
        User user = userRepository.findByEmail(loginFormDTO.getEmail()).
                orElseThrow(() -> new NotFoundException("Usuário não existe"));

        if (!passwordEncoder.matches(loginFormDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException("Senha incorreta");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginFormDTO.getEmail(), loginFormDTO.getPassword()));
        return jwtUtil.generateToken(loginFormDTO.getEmail());
    }

}
