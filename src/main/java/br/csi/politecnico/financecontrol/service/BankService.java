package br.csi.politecnico.financecontrol.service;

import br.csi.politecnico.financecontrol.repository.BankRepository;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }
}
