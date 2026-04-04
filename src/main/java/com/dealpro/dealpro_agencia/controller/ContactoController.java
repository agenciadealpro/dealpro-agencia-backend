package com.dealpro.dealpro_agencia.controller;

import com.dealpro.dealpro_agencia.service.ContactoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class ContactoController {

    private final ContactoService contactoService;

    // Número de WhatsApp de Dealpro: SIN +, sin espacios, sin guiones
    // Ejemplo: +51 942 262 448 -> "51942262448"
    private static final String NUMERO_WHATSAPP_DEALPRO = "51942262448";

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @PostMapping("/contacto-whatsapp")
    public String enviarFormularioWhatsapp(
            @RequestParam String nombreCompleto,
            @RequestParam(required = false) String email,
            @RequestParam String codigoPais,
            @RequestParam String telefono,
            @RequestParam String empresa,
            @RequestParam String rucDni,
            @RequestParam String tipoServicio,
            @RequestParam String detalle
    ) throws UnsupportedEncodingException {

        System.out.println(">>> Entró al POST /contacto-whatsapp: " + telefono);

        // 1) Guardar en BD
        try {
            contactoService.guardarContacto(
                    nombreCompleto,
                    email,
                    codigoPais,
                    telefono,
                    empresa,
                    rucDni,
                    tipoServicio,
                    detalle
            );
            System.out.println(">>> Contacto guardado en BD");
        } catch (Exception e) {
            System.out.println(">>> ERROR guardando contacto en BD: " + e.getMessage());
            e.printStackTrace();
            // Si quieres, podrías redirigir a una página de error en vez de seguir al WhatsApp
        }

        // 2) Armar mensaje para WhatsApp
        String emailMostrar = (email == null || email.isBlank())
                ? "No especificado"
                : email;

        String mensaje = """
                Hola Dealpro, tengo una consulta:
                - Nombre y apellidos: %s
                - Email: %s
                - Teléfono: +%s %s
                - Empresa / Representante: %s
                - RUC/DNI: %s
                - Servicio de interés: %s
                - Detalle del pedido: %s
                """.formatted(
                nombreCompleto,
                emailMostrar,
                codigoPais,
                telefono,
                empresa,
                rucDni,
                tipoServicio,
                detalle
        );

        String encoded = URLEncoder.encode(mensaje, StandardCharsets.UTF_8.toString());
        String urlWhatsapp = "https://wa.me/" + NUMERO_WHATSAPP_DEALPRO + "?text=" + encoded;

        System.out.println(">>> Redirigiendo a WhatsApp: " + urlWhatsapp);

        return "redirect:" + urlWhatsapp;
    }
}