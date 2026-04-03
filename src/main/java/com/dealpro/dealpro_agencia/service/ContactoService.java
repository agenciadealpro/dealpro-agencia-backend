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

    public void guardarContacto(String nombre,
                                String email,
                                String telefono,
                                String empresa,
                                String tipoServicio,
                                String mensaje) {

        Contacto contacto = new Contacto(
                nombre,
                email,
                telefono,
                empresa,
                tipoServicio,
                mensaje
        );

        contactoRepository.save(contacto);
    }
}