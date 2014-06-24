/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencias;

import static Persistencias.PersistenciaContacto.base;
import static Persistencias.PersistenciaContacto.pass;
import static Persistencias.PersistenciaContacto.user;
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
    //private static String base="prueba";
    //private static String user="root";
    //private static String pass="root";
    
    //me fijo en la base de datos si el usuario que quiero registrar existe o no
    public static Boolean ExisteUsuario(String nombre) throws SQLException{
        con = DriverManager.getConnection ("jdbc:mysql://localhost/"+base,user,pass);
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
        con.close();
        return existe;
    }
    
    //me fijo si el usuario y la pass ingresadas son correctos
    public static Boolean UsuarioCorrecto(String nombre,String contrasenia) throws SQLException{
        con = DriverManager.getConnection ("jdbc:mysql://localhost/"+base,user,pass);
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
        con.close();
        return existe;
    }
    
    //registro un nuevo usuario en la base de datos
    public static void InsertarUsuario (String usuario,String contrasenia,String nombre,String apellido,String email) throws SQLException{
        // llamo a la base de datos y preparo la consulta y capturo la excepcion
        con = DriverManager.getConnection ("jdbc:mysql://localhost/"+base,user,pass);
        Statement s = con.createStatement();
        s.executeUpdate("INSERT INTO user (usuario,pass,nombre,apellido,email) VALUES ('"+usuario+"','"+contrasenia+"','"+nombre+"','"+apellido+"','"+email+"')");
        con.close();
        s.close();
    }

}
