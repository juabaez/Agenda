/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controladores;
import Interfaces.interfazAgregarContacto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Persistencias.PersistenciaContacto;
import static Persistencias.PersistenciaContacto.ExisteContacto;
import static Persistencias.PersistenciaContacto.InsertarContacto;
import java.io.IOException;
/**
 *
 * @author Juan
 */
public class ControladorAgregarContacto implements ActionListener{
    private interfazAgregarContacto interfaz;
    private String user;
    private ControladorVentana ven;
    
    public ControladorAgregarContacto(String userCorriente, ControladorVentana ventana) throws SQLException {
        this.user = userCorriente;
        ven = ventana;
        //genero la conexion a la base de datos
        interfaz = new interfazAgregarContacto(); // creo la instanacia de la ventana
        interfaz.setActionLisntener(this); // le digo que los botones de la ventana interfaz(los botones dentro del metodo)
        //los va a controlar este controlador, varios controladores pueden controlar un mismo elemento
        interfaz.setLocationRelativeTo(null);
        interfaz.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        
        //me fijo si se presiono el boton guardar que guarda un contacto y muestro un mensaje de exito o error
        if (ae.getSource()==interfaz.getGuardar()){
            try{
                if (!PersistenciaContacto.ExisteContacto(this.interfaz.getNombre().getText(),this.interfaz.getApellido().getText())){
                    //si el usuario no existe lo agrego a la base de datos
                    if(!this.interfaz.getNombre().getText().isEmpty() && !this.interfaz.getApellido().getText().isEmpty()){
                        if(ven.fueListado){
                            PersistenciaContacto.InsertarContacto(this.interfaz.getNombre().getText(),this.interfaz.getApellido().getText(),this.interfaz.getTelefono().getText(),this.interfaz.getDireccion().getText(),user);
                            JOptionPane.showMessageDialog(null, "Felicitaciones se guardo el contacto correctamente");
                            ven.actualizarListaContacto();
                        }else{
                            PersistenciaContacto.InsertarContacto(this.interfaz.getNombre().getText(),this.interfaz.getApellido().getText(),this.interfaz.getTelefono().getText(),this.interfaz.getDireccion().getText(),user);
                            JOptionPane.showMessageDialog(null, "Felicitaciones se guardo el contacto correctamente");
                        }
                        interfaz.setVisible(false);
                    }else{
                        JOptionPane.showMessageDialog(null, "Error: No puede registrar vacio");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Error: Usuario ya registrado");
                }                
            }catch (SQLException ex){
                Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ControladorAgregarContacto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
