package com.dio.santander.banklineapi.service;

import java.time.LocalDateTime;

import com.dio.santander.banklineapi.dto.NovaMovimentacao;
import com.dio.santander.banklineapi.model.Correntista;
import com.dio.santander.banklineapi.model.Movimentacao;
import com.dio.santander.banklineapi.model.MovimentacaoTipo;
import com.dio.santander.banklineapi.respository.CorrentistaRepository;
import com.dio.santander.banklineapi.respository.MovimentacaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository repository;

    @Autowired
    private CorrentistaRepository correntistaRespository;

    public void save(NovaMovimentacao novaMovimentacao){

        Movimentacao movimentacao = new Movimentacao();
        
        Double valor = novaMovimentacao.getTipo() == MovimentacaoTipo.RECEITA ? novaMovimentacao.getValor() : novaMovimentacao.getValor() * -1;

        movimentacao.setDataHora(LocalDateTime.now());
        movimentacao.setDescricao(novaMovimentacao.getDescricao());
        movimentacao.setTipo(novaMovimentacao.getTipo());
        movimentacao.setValor(valor);
        movimentacao.setIdConta(novaMovimentacao.getIdConta());

        Correntista correntista = correntistaRespository.findById(novaMovimentacao.getIdConta()).orElse(null);

        if(correntista != null) {
            correntista.getConta().setSaldo(correntista.getConta().getSaldo() + valor);
            correntistaRespository.save(correntista);
            repository.save(movimentacao);
        }
    }
}
