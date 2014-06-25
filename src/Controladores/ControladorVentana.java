/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controladores;

import Interfaces.interfazEditarContacto;
import Interfaces.interfazLogin;
import Interfaces.interfazVentana;
import static Persistencias.PersistenciaContacto.contacto;
import static Persistencias.PersistenciaContacto.idContacto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Agenda.Contacto;
/**
 *
 * @author Juan
 */
public class ControladorVentana implements ActionListener{
    
    private interfazVentana interfazVen;
    private interfazEditarContacto interfazCon;
    private interfazLogin interfazLogin;
    private String user;
    public boolean fueListado = false;
    
    public ControladorVentana(String user) throws SQLException {
        this.user = user;
        interfazVen = new interfazVentana(); // creo la instanacia de la ventana
        interfazVen.setActionLisntener(this); // le digo que los botones de la ventana interfaz(los botones dentro del metodo)
        //los va a controlar este controlador, varios controladores pueden controlar un mismo elemento
        interfazVen.setLocationRelativeTo(null);
        interfazVen.setVisible(true);
    }
    
    
    public void actionPerformed(ActionEvent ae) {
        //me fijo si se preciono el boton cerrar sesion
        if(ae.getSource()==interfazVen.getCerrarSesion()){ 
            interfazVen.setVisible(false);
            try {
                new ControladorLogin();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //me fijo si se preciono el boton listar contactos
        if (ae.getSource()==interfazVen.getListar()){
            try {
                LinkedList<Contacto> users = contacto(user);
                DefaultTableModel dt = interfazVen.getTabla();
                dt.setRowCount(0);
                Contacto per;
                //voy agregando los contactos de un usuario a la tabla
                while (!users.isEmpty()){
                    per = users.getFirst();
                    dt.addRow(new Object[]{per.getNombre(),per.getApellido(),per.getTelefono(),per.getDireccion(),new JRadioButton()});
                    users.remove();
                }
                fueListado = true;
            } catch (SQLException ex) {
                Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //me fijo si se preciono el boton agregar contacto
        if (ae.getSource()==interfazVen.getAgregarContacto()) {
            try {
                //llamo al controlador que se encarga de agregar un contacto
                new ControladorAgregarContacto(user,this);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //me fijo si se preciono el boton editar contacto
        if (ae.getSource()==interfazVen.getEditContacto()) {
            JTable dt = interfazVen.getTable1();
            int fila = dt.getSelectedRow();
            if (fila == -1){
                JOptionPane.showMessageDialog(null, "Error: No hay filas seleccionadas", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                try {
                    //obtengo el id del contacto seleccionado por el usuario
                    int id = idContacto((String)dt.getValueAt(fila, 0),(String)dt.getValueAt(fila, 1));
                    //llamo al controlador que se encarga de editar un contacto
                    new ControladorEditarContacto((String)dt.getValueAt(fila, 0),(String)dt.getValueAt(fila, 1),(String)dt.getValueAt(fila, 2),(String)dt.getValueAt(fila, 3),id,this);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        //me fijo si se preciono el eliminar contacto
        if (ae.getSource()==interfazVen.getElimContacto()) {
            JTable dt = interfazVen.getTable1();
            int fila = dt.getSelectedRow();
            if (fila == -1){
                JOptionPane.showMessageDialog(null, "Error: No hay filas seleccionadas", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                try {
                    //obtengo el id del contacto seleccionado por el usuario
                    int id = idContacto((String)dt.getValueAt(fila, 0),(String)dt.getValueAt(fila, 1));
                    //llamo al controlador que se encarga de eliminar un contacto
                    new ControladorElimContacto((String)dt.getValueAt(fila, 0),(String)dt.getValueAt(fila, 1),this);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public void actualizarLista(){
        try {
            LinkedList<Contacto> users = contacto(user);
            DefaultTableModel dt = interfazVen.getTabla();
            dt.setRowCount(0);
            Contacto per;
            //voy agregando los contactos de un usuario a la tabla
            while (!users.isEmpty()){
                per = users.getFirst();
                dt.addRow(new Object[]{per.getNombre(),per.getApellido(),per.getTelefono(),per.getDireccion(),new JRadioButton()});
                users.remove();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
