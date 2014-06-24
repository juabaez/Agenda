/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controladores;
import Interfaces.interfazEditarContacto;
import Persistencias.PersistenciaContacto;
import static Persistencias.PersistenciaContacto.ExisteContacto;
import static Persistencias.PersistenciaContacto.editarContacto;
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
public class ControladorEditarContacto implements ActionListener{
    private interfazEditarContacto interfaz;
    private String name;
    private String apellido;
    private String telefono;
    private String direccion;
    private int id;
    
    public ControladorEditarContacto(String name,String apellido,String telefono,String direccion,int id) throws SQLException {
        this.name=name;
        this.apellido=apellido;
        this.telefono=telefono;
        this.direccion=direccion;
        this.id=id;
        interfaz = new interfazEditarContacto(); // creo la instanacia de la ventana
        //cargo la interfaz con los datos del contacto seleccionado
        interfaz.getNombre().setText(name);
        interfaz.getApellido().setText(apellido);
        interfaz.getTelefono().setText(telefono);
        interfaz.getDireccion().setText(direccion);
        interfaz.setActionLisntener(this); // le digo que los botones de la ventana interfaz(los botones dentro del metodo)
        //los va a controlar este controlador, varios controladores pueden controlar un mismo elemento
        interfaz.setLocationRelativeTo(null);
        interfaz.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        
        //me fijo si se presiono el boton guardar que guarda un contacto y muestro un mensaje de exito o error
        if (ae.getSource()==interfaz.getGuardar()){
            try{
                if(!this.interfaz.getNombre().getText().isEmpty() && !this.interfaz.getApellido().getText().isEmpty()){
                    name = interfaz.getNombre().getText();
                    apellido = interfaz.getApellido().getText();
                    telefono = interfaz.getTelefono().getText();
                    direccion = interfaz.getDireccion().getText();
                    //llamo a la funcion que edita un contacto
                    PersistenciaContacto.editarContacto(name,apellido,telefono,direccion,id);
                    JOptionPane.showMessageDialog(null, "Felicitaciones se actualizo el contacto correctamente");
                    interfaz.setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(null, "Error: No se pudo actualizar el contacto asegurese que no haya campos vacios");
                }              
            }catch (SQLException ex){
                Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
