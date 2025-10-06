package br.csi.politecnico.financecontrol.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "bank")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de cada banco", example = "1")
    private Long id;
    @Schema(description = "Nome do banco", example = "Banco do brasil")
    private String name;
    @Schema(description = "Tipo do banco", example = "Comercial, investimento, digital")
    private String type;
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de usuários vinculados ao banco")
    private List<UserBank> userBanks;
}
