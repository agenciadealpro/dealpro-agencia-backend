package com.dealpro.dealpro_agencia.controller;

import com.dealpro.dealpro_agencia.service.ContactoService;
import com.dealpro.dealpro_agencia.service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactoController {

    private final EmailService emailService;
    private final ContactoService contactoService;

    public ContactoController(EmailService emailService, ContactoService contactoService) {
        this.emailService = emailService;
        this.contactoService = contactoService;
    }

    @PostMapping("/contacto")
    public String enviarFormulario(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String empresa,
            @RequestParam String tipoServicio,
            @RequestParam String mensaje
    ) {
        // 1) Guardar en BD
        contactoService.guardarContacto(
                nombre,
                email,
                telefono,
                empresa,
                tipoServicio,
                mensaje
        );

        // 2) Enviar correos (ajusta firma de enviarContacto si quieres incluir los nuevos campos)
        emailService.enviarContacto(
        	    nombre,
        	    email,
        	    telefono,
        	    empresa,
        	    tipoServicio,
        	    mensaje
        	);
        return "redirect:/?enviado=true";
    }
}