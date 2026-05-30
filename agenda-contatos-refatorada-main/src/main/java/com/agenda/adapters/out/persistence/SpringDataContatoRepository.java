package com.agenda.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

// Atenção: Veja que aqui dentro de <> nós usamos ContatoEntity, e não o Contato do core!
@Repository
public interface SpringDataContatoRepository extends JpaRepository<ContatoEntity, Long>, JpaSpecificationExecutor<ContatoEntity> {
    boolean existsByEmail(String email);
}