package br.csi.politecnico.financecontrol.model;

import java.time.LocalDate;

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
    @Builder.Default
    @Column(name = "receipt_date", nullable = false)
    private LocalDate receiptDate = LocalDate.now();
}
