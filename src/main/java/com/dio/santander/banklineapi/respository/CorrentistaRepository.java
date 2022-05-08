package com.dio.santander.banklineapi.respository;

import com.dio.santander.banklineapi.model.Correntista;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CorrentistaRepository extends JpaRepository<Correntista, Integer> {
    
}
