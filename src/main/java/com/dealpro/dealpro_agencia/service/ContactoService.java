package com.dealpro.dealpro_agencia.service;

import com.dealpro.dealpro_agencia.model.Contacto;
import com.dealpro.dealpro_agencia.repository.ContactoRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactoService {

    private final ContactoRepository contactoRepository;

    public ContactoService(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    public void guardarContacto(String nombreCompleto,
                                String email,
                                String codigoPais,
                                String telefono,
                                String empresa,
                                String rucDni,
                                String tipoServicio,
                                String detalle) {

        Contacto contacto = new Contacto(
                nombreCompleto,
                email,
                codigoPais,
                telefono,
                empresa,
                rucDni,
                tipoServicio,
                detalle
        );

        contactoRepository.save(contacto);
    }
}