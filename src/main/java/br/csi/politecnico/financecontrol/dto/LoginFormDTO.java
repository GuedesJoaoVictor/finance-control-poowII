package br.csi.politecnico.financecontrol.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginFormDTO {
    private String email;
    private String password;
}
