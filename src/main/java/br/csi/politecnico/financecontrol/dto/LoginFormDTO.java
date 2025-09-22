package br.csi.politecnico.financecontrol.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginFormDTO {
    private String email;
    private String password;
}
