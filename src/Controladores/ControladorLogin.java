/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controladores;
import Interfaces.interfazLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Persistencias.PersistenciaUsuario;
import static Persistencias.PersistenciaUsuario.UsuarioCorrecto;
import java.io.IOException;



public class ControladorLogin implements ActionListener {
    
    private interfazLogin interfaz;
    
    public ControladorLogin() throws SQLException {
        //genero la conexion a la base de datos
        interfaz = new interfazLogin(); // creo la instanacia de la ventana
        interfaz.setActionLisntener(this); // le digo que los botones de la ventana interfaz(los botones dentro del metodo)
        //los va a controlar este controlador, varios controladores pueden controlar un mismo elemento
        interfaz.setLocationRelativeTo(null);
        interfaz.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        //me fijo si se presiono el boton registro para crear un nuevo usuario
        if(ae.getSource()==interfaz.getRegistro()){ 
            try {
            // si se presionó boton regristrar de la ventana interfaz
                new ControladorRegistro(this);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
        
        // veo si se presiono el boton logear para poder acceder a la cuenta
        if (ae.getSource()==interfaz.getLogin()){
            try {
                if (PersistenciaUsuario.UsuarioCorrecto(this.interfaz.getUsuario().getText(),this.interfaz.getContrasenia().getText())){
                    interfaz.setVisible(false);
                    new ControladorVentana(this.interfaz.getUsuario().getText());
                }else{
                    JOptionPane.showMessageDialog(null, "Error: Usuario no existente o usuario y contrasena no coinciden", "ERROR", JOptionPane.ERROR_MESSAGE); //Cambie para que se muestre como error
                    this.interfaz.getUsuario().setText(null);
                    this.interfaz.getContrasenia().setText(null);
                    }
            } catch (SQLException ex) {
                    Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

