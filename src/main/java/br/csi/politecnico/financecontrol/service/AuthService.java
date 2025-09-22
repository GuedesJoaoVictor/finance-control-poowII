package br.csi.politecnico.financecontrol.service;

import br.csi.politecnico.financecontrol.exception.BadRequestException;
import br.csi.politecnico.financecontrol.model.User;
import br.csi.politecnico.financecontrol.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

}
