package br.csi.politecnico.financecontrol.repository;

import br.csi.politecnico.financecontrol.model.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevenuesRepository extends JpaRepository<Revenue, Long> {
}
