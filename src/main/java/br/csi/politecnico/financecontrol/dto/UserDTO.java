package br.csi.politecnico.financecontrol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
