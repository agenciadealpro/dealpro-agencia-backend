package com.dealpro.dealpro_agencia.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contactos")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    private String codigoPais;   // NUEVO

    private String telefono;

    private String empresa;

    private String rucDni;       // NUEVO

    private String tipoServicio;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    private LocalDateTime fechaHora;

    public Contacto() {
        this.fechaHora = LocalDateTime.now();
    }

    public Contacto(String nombre,
                    String email,
                    String codigoPais,
                    String telefono,
                    String empresa,
                    String rucDni,
                    String tipoServicio,
                    String mensaje) {
        this.nombre = nombre;
        this.email = email;
        this.codigoPais = codigoPais;
        this.telefono = telefono;
        this.empresa = empresa;
        this.rucDni = rucDni;
        this.tipoServicio = tipoServicio;
        this.mensaje = mensaje;
        this.fechaHora = LocalDateTime.now();
    }

    // getters y setters

    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCodigoPais() { return codigoPais; }
    public void setCodigoPais(String codigoPais) { this.codigoPais = codigoPais; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public String getRucDni() { return rucDni; }
    public void setRucDni(String rucDni) { this.rucDni = rucDni; }

    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
}