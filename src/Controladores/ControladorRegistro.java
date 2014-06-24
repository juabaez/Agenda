/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controladores;
import Interfaces.interfazLogin;
import Interfaces.interfazRegistro;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Persistencias.PersistenciaUsuario;
import Agenda.personas;
/**
 *
 * @author Juan
 */
public class ControladorRegistro implements ActionListener{
    
    private interfazRegistro interfazReg;
    private interfazLogin interfazLog;
    public LinkedList<personas> pers = new LinkedList();
    private ControladorLogin login;
//    private final Connection conexion;
    
    public ControladorRegistro(ControladorLogin login) throws SQLException{
        //genero la conexion a la base de datos
//        this.conexion = DriverManager.getConnection ("jdbc:mysql://localhost/prueba","root", "root");
        interfazReg = new interfazRegistro(); // creo la instanacia de la ventana
        interfazReg.setActionLisntener(this); // le digo que los botones de la ventana interfaz(los botones dentro del metodo)
        //los va a controlar este controlador, varios controladores pueden controlar un mismo elemento
        interfazReg.setLocationRelativeTo(null);
        interfazReg.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        
        //me fijo si se presiono el boton registrar para guardar un nuevo usuario
        if (ae.getSource()==interfazReg.getRegistrar()){
            try{
                if (!PersistenciaUsuario.ExisteUsuario(this.interfazReg.getUsuario().getText())){
                    //si el usuario no existe lo agrego a la base de datos
                    if(!this.interfazReg.getUsuario().getText().isEmpty() && !this.interfazReg.getPass().getText().isEmpty()){
                        PersistenciaUsuario.InsertarUsuario(this.interfazReg.getUsuario().getText(),this.interfazReg.getPass().getText(),this.interfazReg.getNombre().getText(),this.interfazReg.getApellido().getText(),this.interfazReg.getMail().getText());
                        interfazReg.setVisible(false);
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Error: No puede registrar vacio");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Error: Usuario ya registrado");
                }                
            }catch (SQLException ex){
                Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
