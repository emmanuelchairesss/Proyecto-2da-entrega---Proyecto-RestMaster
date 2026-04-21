/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restmaster;

/**
 *
 * @author Edith Ramirez
 */
public class Mesa {

    private String idMesa;
    private String estado;

    public Mesa(String idMesa, String estado) {
        this.idMesa = idMesa;
        this.estado = estado;
    }

    public String getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(String idMesa) {
        this.idMesa = idMesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Mesa " + idMesa + " - Estado: " + estado;
    }
}