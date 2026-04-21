package com.mycompany.restmaster;


import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
        
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LENOVO
 */
public class ColoresPaneles implements MouseListener {
 
    private final MenuPrincipalp view;

    public final  void events(){
    view.btnReservarMesas.addMouseListener(this);
    view.btnInventario.addMouseListener(this);
    view.btnCerrarSesion.addMouseListener(this);
    view.btnAsisReserva.addMouseListener(this);
    
}
    public ColoresPaneles(MenuPrincipalp view){
        this.view=view;
        events();
      
    }
       @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
       Object evt=me.getSource();
       
       if(evt.equals(view.btnReservarMesas)){
           changeColor(view.Pmesas, new Color(131,193,0));
       }else if(evt.equals(view.btnInventario)){
           changeColor(view.PanelInventario, new Color(131,193,0));
       }else if(evt.equals(view.btnCerrarSesion)){
            changeColor(view.PanelCerrarSesion, new Color(153,0,0));
       } else if(evt.equals(view.btnAsisReserva)){
            changeColor(view.PanelAsistenciaReserva, new Color(131,193,0));
    
    }
    
    }
    @Override
    public void mouseExited(MouseEvent me) {
        Object evt=me.getSource();
        
        if(evt.equals(view.btnReservarMesas)){
             changeColor(view.Pmesas, new Color(102,153,0));
      }else if(evt.equals(view.btnInventario)){
            changeColor(view.PanelInventario, new Color(102,153,0));
       }else if(evt.equals(view.btnCerrarSesion)){
            changeColor(view.PanelCerrarSesion, new Color(102,153,0));

     } else if(evt.equals(view.btnAsisReserva)){
            changeColor(view.PanelAsistenciaReserva, new Color(102,153,0));
    } 
    }
    private void changeColor(JPanel panel, Color color){
        panel.setBackground(color);
    }
}
