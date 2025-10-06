package br.csi.politecnico.financecontrol.model;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Entidade que representa o usuário no sistema.")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do usuário", example = "1")
    private Long id;
    @UuidGenerator
    @Column(columnDefinition = "uuid", unique = true, nullable = false, updatable = false)
    @Schema(description = "UUid único de cada usuário", example = "2c767215-5a45-4b4b-92c1-3bd8b294ed23")
    private UUID uuid;
    @Schema(description = "CPF do usuário", example = "000.000.000-00")
    private String cpf;
    @Schema(description = "Nome do usuário", example = "Guedes")
    private String name;
    @Schema(description = "Email do usuário", example = "guedes@email.com")
    private String email;
    @Schema(description = "Senha criptografada do usuário.")
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de bancos que o usuário está vinculado.", example = "[Banco do Brasil, Nubank]")
    private List<UserBank> userBanks;
}
