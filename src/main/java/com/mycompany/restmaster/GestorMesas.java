/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restmaster;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Edith Ramirez
 */

public class GestorMesas {

    private Map<String, Mesa> mesas;

    public GestorMesas() {
        mesas = new HashMap<>();
    }

    // gregar mesa
    public void agregarMesa(String idMesa, String estado) {
        mesas.put(idMesa, new Mesa(idMesa, estado));
    }

    // obtener una mesa
    public Mesa getMesa(String idMesa) {
        return mesas.get(idMesa);
    }

    // verificar si está reservada
    public boolean esMesaReservada(String idMesa) {
        return mesas.containsKey(idMesa) &&
               mesas.get(idMesa).getEstado().equals("reservada");
    }

    // actualizar estado
    public void actualizarEstadoMesa(String idMesa, String nuevoEstado) {
        if (mesas.containsKey(idMesa)) {
            mesas.get(idMesa).setEstado(nuevoEstado);
        }
    }

    // cargar mesas automáticamente (MUY útil)
    public void cargarMesasIniciales(String[] idsMesas) {
        for (String id : idsMesas) {
            mesas.put(id, new Mesa(id, "disponible"));
        }
    }

    // obtener todas (por si luego quieres recorrerlas)
    public Map<String, Mesa> getMesas() {
        return mesas;
    }
}