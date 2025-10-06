package br.csi.politecnico.financecontrol.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "category")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único da categoria")
    private Long id;
    @Schema(description = "Tipo da categoria", example = "Investimento, Salario, Alimentação")
    private String type;
    @Schema(description = "Nome da categoria", example = "Bar da tia")
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Schema(description = "Usuário vinculado a categoria, em caso de ser null. A categoria é 'global'")
    private User user;
}
