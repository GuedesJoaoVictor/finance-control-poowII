package br.csi.politecnico.financecontrol.dto;

import br.csi.politecnico.financecontrol.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String uuid;
    private String cpf;
    private String name;
    private String email;
    private String password;

    public UserDTO (User user) {
        this.id = user.getId();
        this.uuid = user.getUuid().toString();
        this.cpf = user.getCpf();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
