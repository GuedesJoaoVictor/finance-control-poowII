package br.csi.politecnico.financecontrol.service;

import br.csi.politecnico.financecontrol.dto.UserDTO;
import br.csi.politecnico.financecontrol.model.User;
import br.csi.politecnico.financecontrol.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            return Collections.emptyList();
        }

        List<UserDTO> dtos = new ArrayList<>();
        users.forEach(user -> dtos.add(new UserDTO(user)));

        return dtos;
    }
}
