package com.dealpro.dealpro_agencia.repository;

import com.dealpro.dealpro_agencia.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
}