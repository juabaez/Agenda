/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controladores;

import Interfaces.interfazEditarContacto;
import Interfaces.interfazElimContacto;
import Persistencias.PersistenciaContacto;
import static Persistencias.PersistenciaContacto.idContacto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan
 */
public class ControladorElimContacto implements ActionListener{
    
    private interfazElimContacto interfaz;
    private String name;
    private String apellido;
    
    public ControladorElimContacto(String name,String apellido) throws SQLException {
        this.name=name;
        this.apellido=apellido;
        //genero la conexion a la base de datos
        interfaz = new interfazElimContacto(); // creo la instanacia de la ventana
        interfaz.setActionLisntener(this); // le digo que los botones de la ventana interfaz(los botones dentro del metodo)
        //los va a controlar este controlador, varios controladores pueden controlar un mismo elemento
        interfaz.setLocationRelativeTo(null);
        interfaz.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        
        //me fijo si se presiono el boton si que elimina un contacto y muestro un mensaje de exito o error
        if (ae.getSource()==interfaz.getSi()){
            try {
                //obtengo el id del contacto seleccionado por el usuario
                int id = idContacto(name,apellido);
                //llamo al metodo que elimina un contacto
                PersistenciaContacto.eliminarContacto(id);
                JOptionPane.showMessageDialog(null, "Felicitaciones se elimino el contacto correctamente");
                interfaz.setVisible(false);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorElimContacto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(ae.getSource()==interfaz.getNo()){
            interfaz.setVisible(false);
        }
    }
}
