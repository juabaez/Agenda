/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agenda;

import Controladores.ControladorLogin;
import static Persistencias.ConexionDB.Disconnect;
import static Persistencias.ConexionDB.GetConnection;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Juan
 */
public class Main {

    public static void main(String[] args) throws SQLException {
       ControladorLogin contr= new ControladorLogin();
    }
    
}
