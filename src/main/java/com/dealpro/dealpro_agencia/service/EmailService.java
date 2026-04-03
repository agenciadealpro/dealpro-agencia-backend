package com.dealpro.dealpro_agencia.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private static final String FROM_NAME = "DealPro Agencia";
    private static final DateTimeFormatter FECHA_FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarContacto(String nombre,
                               String email,
                               String telefono,
                               String empresa,
                               String tipoServicio,
                               String mensaje) {

        String telefonoTexto = (telefono != null && !telefono.isBlank())
                ? telefono
                : "No especificado";

        String empresaTexto = (empresa != null && !empresa.isBlank())
                ? empresa
                : "No especificada";

        String tipoServicioTexto = (tipoServicio != null && !tipoServicio.isBlank())
                ? tipoServicio
                : "No especificado";

        String fechaTexto = LocalDateTime.now().format(FECHA_FORMATO);

        try {
            enviarCorreoInterno(nombre, email, telefonoTexto, empresaTexto, tipoServicioTexto, mensaje, fechaTexto);
            enviarCorreoCliente(nombre, email, telefonoTexto, empresaTexto, tipoServicioTexto, mensaje, fechaTexto);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void enviarCorreoInterno(String nombre,
                                     String email,
                                     String telefono,
                                     String empresa,
                                     String tipoServicio,
                                     String mensaje,
                                     String fechaTexto) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setTo("dealpro29@gmail.com");
        helper.setSubject("[DealPro] Nuevo contacto recibido: " + nombre);
        helper.setReplyTo(email);
        // helper.setFrom("no-reply@agenciadealpro.com", FROM_NAME);

        String html =
                "<html>" +
                "<body style=\"margin:0;padding:0;background-color:#0b1120;font-family:system-ui,-apple-system,BlinkMacSystemFont,'Segoe UI',sans-serif;\">" +
                "  <table role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"background:radial-gradient(circle at top left,#1f2937,#020617);padding:32px 0;\">" +
                "    <tr>" +
                "      <td align=\"center\">" +
                "        <table role=\"presentation\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" style=\"background-color:#020617;border-radius:18px;overflow:hidden;border:1px solid #1f2937;box-shadow:0 18px 60px rgba(0,0,0,0.8);\">" +
                "          <tr>" +
                "            <td style=\"padding:18px 24px 14px;border-bottom:1px solid #111827;\">" +
                "              <table role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">" +
                "                <tr>" +
                "                  <td align=\"right\" style=\"font-size:11px;color:#9ca3af;\">" +
                "                    <div style=\"text-transform:uppercase;letter-spacing:.14em;color:#f97373;font-weight:600;\">Nuevo lead</div>" +
                "                    <div style=\"margin-top:2px;\">Formulario de contacto web</div>" +
                "                  </td>" +
                "                </tr>" +
                "              </table>" +
                "            </td>" +
                "          </tr>" +
                "          <tr>" +
                "            <td style=\"padding:20px 24px 0;\">" +
                "              <h1 style=\"margin:0;font-size:19px;font-weight:700;color:#f9fafb;\">Nuevo contacto desde agenciadealpro.com</h1>" +
                "              <p style=\"margin:6px 0 0;font-size:13px;color:#9ca3af;\">" +
                "                Se ha registrado un nuevo contacto en el sitio a las <strong style=\"color:#e5e7eb;\">" + fechaTexto + "</strong>." +
                "              </p>" +
                "            </td>" +
                "          </tr>" +
                "          <tr>" +
                "            <td style=\"padding:16px 24px 8px;\">" +
                "              <h2 style=\"margin:0 0 8px;font-size:14px;font-weight:600;color:#e5e7eb;\">Datos del contacto</h2>" +
                "              <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" style=\"width:100%;font-size:13px;color:#e5e7eb;\">" +
                "                <tr>" +
                "                  <td style=\"padding:4px 0;width:160px;color:#9ca3af;\">Nombre:</td>" +
                "                  <td style=\"padding:4px 0;\">" + nombre + "</td>" +
                "                </tr>" +
                "                <tr>" +
                "                  <td style=\"padding:4px 0;color:#9ca3af;\">Correo electrónico:</td>" +
                "                  <td style=\"padding:4px 0;\">" + email + "</td>" +
                "                </tr>" +
                "                <tr>" +
                "                  <td style=\"padding:4px 0;color:#9ca3af;\">Teléfono:</td>" +
                "                  <td style=\"padding:4px 0;\">" + telefono + "</td>" +
                "                </tr>" +
                "                <tr>" +
                "                  <td style=\"padding:4px 0;color:#9ca3af;\">Empresa:</td>" +
                "                  <td style=\"padding:4px 0;\">" + empresa + "</td>" +
                "                </tr>" +
                "                <tr>" +
                "                  <td style=\"padding:4px 0;color:#9ca3af;\">Servicio de interés:</td>" +
                "                  <td style=\"padding:4px 0;\">" + tipoServicio + "</td>" +
                "                </tr>" +
                "              </table>" +
                "            </td>" +
                "          </tr>" +
                "          <tr>" +
                "            <td style=\"padding:8px 24px 18px;\">" +
                "              <h2 style=\"margin:12px 0 8px;font-size:14px;font-weight:600;color:#e5e7eb;\">Mensaje</h2>" +
                "              <div style=\"padding:12px 14px;background-color:#020617;border-radius:10px;border:1px solid #1f2937;font-size:13px;white-space:pre-line;color:#e5e7eb;\">" +
                                 escapeHtml(mensaje) +
                "              </div>" +
                "            </td>" +
                "          </tr>" +
                "          <tr>" +
                "            <td style=\"padding:0 24px 20px;\">" +
                "              <a href=\"mailto:" + email + "\" style=\"display:inline-block;padding:9px 18px;border-radius:999px;background:linear-gradient(135deg,#22c55e,#16a34a);color:#ecfdf5;text-decoration:none;font-size:13px;font-weight:600;box-shadow:0 10px 30px rgba(34,197,94,0.35);\">" +
                "                Responder ahora" +
                "              </a>" +
                "            </td>" +
                "          </tr>" +
                "          <tr>" +
                "            <td style=\"padding:10px 24px 16px;font-size:11px;color:#6b7280;border-top:1px solid #111827;\">" +
                "              <p style=\"margin:0;\">Este mensaje fue generado automáticamente desde el formulario de contacto de <strong>DealPro Agencia</strong>.</p>" +
                "            </td>" +
                "          </tr>" +
                "        </table>" +
                "      </td>" +
                "    </tr>" +
                "  </table>" +
                "</body>" +
                "</html>";

        helper.setText(html, true);
        mailSender.send(mimeMessage);
    }

    private void enviarCorreoCliente(String nombre,
                                     String email,
                                     String telefono,
                                     String empresa,
                                     String tipoServicio,
                                     String mensaje,
                                     String fechaTexto) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Confirmación de recepción – DealPro Agencia");
        // helper.setFrom("no-reply@agenciadealpro.com", FROM_NAME);

        String html =
                "<html>" +
                "<body style=\"margin:0;padding:0;background-color:#f3f4f6;font-family:system-ui,-apple-system,BlinkMacSystemFont,'Segoe UI',sans-serif;\">" +
                "  <table role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"background-color:#f3f4f6;padding:24px 0;\">" +
                "    <tr>" +
                "      <td align=\"center\">" +
                "        <table role=\"presentation\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" style=\"background-color:#ffffff;border-radius:12px;overflow:hidden;box-shadow:0 10px 30px rgba(15,23,42,0.18);\">" +
                "          <tr>" +
                "            <td style=\"padding:14px 24px 10px;border-bottom:1px solid #f97373;\">" +
                "              <table role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">" +
                "                <tr>" +
                "                  <td align=\"right\" style=\"font-size:11px;color:#9ca3af;\">" +
                "                    <div style=\"text-transform:uppercase;letter-spacing:.16em;color:#b91c1c;font-weight:600;\">DealPro Agencia</div>" +
                "                    <div style=\"margin-top:2px;\">Diseño, impresión y publicidad</div>" +
                "                  </td>" +
                "                </tr>" +
                "              </table>" +
                "            </td>" +
                "          </tr>" +
                "          <tr>" +
                "            <td style=\"background:linear-gradient(135deg,#DA0000,#7f1d1d);padding:20px 24px;color:#f9fafb;\">" +
                "              <h1 style=\"margin:0;font-size:20px;font-weight:700;\">Hemos recibido tu mensaje</h1>" +
                "              <p style=\"margin:4px 0 0;font-size:13px;opacity:0.9;\">Gracias por contactar a DealPro Agencia.</p>" +
                "            </td>" +
                "          </tr>" +
                "          <tr>" +
                "            <td style=\"padding:20px 24px 8px;font-size:14px;color:#111827;\">" +
                "              <p style=\"margin:0 0 12px;\">Hola " + nombre + ",</p>" +
                "              <p style=\"margin:0 0 12px;\">" +
                "                Te confirmamos que hemos recibido tu mensaje el <strong>" + fechaTexto + "</strong>." +
                "                Nuestro equipo revisará tu consulta y te responderá a la brevedad." +
                "              </p>" +
                "              <h2 style=\"margin:12px 0 8px;font-size:15px;font-weight:600;\">Resumen de tu solicitud</h2>" +
                "              <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" style=\"width:100%;font-size:14px;color:#111827;\">" +
                "                <tr><td style=\"padding:4px 0;width:160px;color:#6b7280;\">Nombre:</td><td style=\"padding:4px 0;\">" + nombre + "</td></tr>" +
                "                <tr><td style=\"padding:4px 0;color:#6b7280;\">Correo electrónico:</td><td style=\"padding:4px 0;\">" + email + "</td></tr>" +
                "                <tr><td style=\"padding:4px 0;color:#6b7280;\">Teléfono:</td><td style=\"padding:4px 0;\">" + telefono + "</td></tr>" +
                "                <tr><td style=\"padding:4px 0;color:#6b7280;\">Empresa:</td><td style=\"padding:4px 0;\">" + empresa + "</td></tr>" +
                "                <tr><td style=\"padding:4px 0;color:#6b7280;\">Servicio de interés:</td><td style=\"padding:4px 0;\">" + tipoServicio + "</td></tr>" +
                "              </table>" +
                "            </td>" +
                "          </tr>" +
                "          <tr>" +
                "            <td style=\"padding:8px 24px 20px;\">" +
                "              <h2 style=\"margin:12px 0 8px;font-size:15px;font-weight:600;color:#111827;\">Mensaje enviado</h2>" +
                "              <div style=\"padding:12px 14px;background-color:#f9fafb;border-radius:8px;border:1px solid #e5e7eb;font-size:14px;white-space:pre-line;color:#111827;\">" +
                                 escapeHtml(mensaje) +
                "              </div>" +
                "              <p style=\"margin:16px 0 0;font-size:13px;color:#4b5563;\">" +
                "                Si deseas agregar más detalles o realizar una consulta adicional, puedes responder directamente a este correo." +
                "              </p>" +
                "            </td>" +
                "          </tr>" +
                "          <tr>" +
                "            <td style=\"padding:12px 24px 18px;font-size:11px;color:#9ca3af;border-top:1px solid #e5e7eb;text-align:center;\">" +
                "              <p style=\"margin:0;\">© " + LocalDateTime.now().getYear() + " " + FROM_NAME + ". Todos los derechos reservados.</p>" +
                "              <p style=\"margin:4px 0 0;\">DealPro Agencia · WhatsApp: +51 942 262 448 · Instagram: @agenciadealpro.com</p>" +
                "            </td>" +
                "          </tr>" +
                "        </table>" +
                "      </td>" +
                "    </tr>" +
                "  </table>" +
                "</body>" +
                "</html>";

        helper.setText(html, true);
        mailSender.send(mimeMessage);
    }

    private String escapeHtml(String input) {
        if (input == null) return "";
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}