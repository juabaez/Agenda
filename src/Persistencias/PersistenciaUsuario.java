/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencias;

import static Persistencias.ConexionDB.Disconnect;
import static Persistencias.ConexionDB.GetConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
/**
 *
 * @author Juan
 */
public class PersistenciaUsuario {
    private static Connection con;
    
    //me fijo en la base de datos si el usuario que quiero registrar existe o no
    public static Boolean ExisteUsuario(String nombre) throws SQLException, IOException{
        con = GetConnection();
        boolean existe = false;
        // llamo a la base de datos y preparo la consulta y capturo la excepcion
        Statement s = con.createStatement();
        //realizo la busqueda en la base de datos
        //Recorremos el resultado, mientras haya registros para leer
        ResultSet rs = s.executeQuery ("select * from User");
        //mientras que exista registro para leer sigue el while sino sale
        while (rs.next() && !existe){ //CAMBIE ESTO TAMBIEN, no estaba bien lo que hacias, se iba de rango
            //comparo si lo que esta en la base de datos 
            if (rs.getObject("usuario").equals(nombre)){
                existe = true;
            }
        }
        s.close();
        Disconnect(con);
        return existe;
    }
    
    //me fijo si el usuario y la pass ingresadas son correctos
    public static Boolean UsuarioCorrecto(String nombre,String contrasenia) throws SQLException, IOException{
        con = GetConnection();
        boolean existe = false;
        // llamo a la base de datos y preparo la consulta y capturo la excepcion
        Statement s = con.createStatement();
        //realizo la busqueda en la base de datos
        //Recorremos el resultado, mientras haya registros para leer
        ResultSet rs = s.executeQuery ("select * from User");
        //mientras que exista registro para leer sigue el while sino sale
        while (rs.next() && !existe){ //CAMBIE ESTO TAMBIEN, no estaba bien lo que hacias, se iba de rango
            //comparo si lo que esta en la base de datos 
            if (rs.getObject("usuario").equals(nombre)
                && rs.getObject("pass").equals(contrasenia)){
                existe = true;
            }
        }
        s.close();
        Disconnect(con);
        return existe;
    }
    
    //registro un nuevo usuario en la base de datos
    public static void InsertarUsuario (String usuario,String contrasenia,String nombre,String apellido,String email) throws SQLException, IOException{
        // llamo a la base de datos y preparo la consulta y capturo la excepcion
        con = GetConnection();
        Statement s = con.createStatement();
        s.executeUpdate("INSERT INTO user (usuario,pass,nombre,apellido,email) VALUES ('"+usuario+"','"+contrasenia+"','"+nombre+"','"+apellido+"','"+email+"')");
        s.close();
        Disconnect(con);
    }

}
