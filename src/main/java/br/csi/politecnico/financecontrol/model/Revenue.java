package br.csi.politecnico.financecontrol.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "revenues")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Revenue extends Transaction {
    @Column(name = "receipt_date", nullable = false)
    @Schema(description = "Momento em que foi feita a renda", example = "07/04/2004")
    private LocalDate receiptDate = LocalDate.now();
}
