package br.csi.politecnico.financecontrol.service;

import br.csi.politecnico.financecontrol.dto.BankDTO;
import br.csi.politecnico.financecontrol.exception.BadRequestException;
import br.csi.politecnico.financecontrol.model.Bank;
import br.csi.politecnico.financecontrol.repository.BankRepository;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public String create(BankDTO dto) {
        Bank alreadyExists = bankRepository.findBankByName(dto.getName()).
                orElseThrow(() -> new BadRequestException("Banco já existe na nossa base."));

        return "Created";
    }
}
