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
        System.out.println(">>> Entró al POST /contacto: " + email);
        try {
            contactoService.guardarContacto(
                    nombre,
                    email,
                    telefono,
                    empresa,
                    tipoServicio,
                    mensaje
            );
            System.out.println(">>> Contacto guardado en BD");

            emailService.enviarContacto(
                    nombre,
                    email,
                    telefono,
                    empresa,
                    tipoServicio,
                    mensaje
            );
            System.out.println(">>> EmailService.enviarContacto() terminó OK");

            return "redirect:/?enviado=true";
        } catch (Exception e) {
            System.out.println(">>> ERROR en /contacto: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/?error=true";
        }
    }
}