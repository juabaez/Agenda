/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencias;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import Agenda.Contacto;
/**
 *
 * @author Juan
 */
public class PersistenciaContacto {
    
    private static Connection con;
    static String base="prueba";
    static String user="root";
    static String pass="root";
    
    //me fijo en la base de datos si existe el contacto que quiero agregar
    public static Boolean ExisteContacto(String nombre,String ape) throws SQLException{
        con = DriverManager.getConnection ("jdbc:mysql://localhost/"+base,user,pass);
        boolean existe = false;
        // llamo a la base de datos y preparo la consulta y capturo la excepcion
        Statement s = con.createStatement();
        //realizo la busqueda en la base de datos
        //Recorremos el resultado, mientras haya registros para leer
        ResultSet rs = s.executeQuery ("select * from contacto");
        //mientras que exista registro para leer sigue el while sino sale
        while (rs.next() && !existe){ //CAMBIE ESTO TAMBIEN, no estaba bien lo que hacias, se iba de rango
            //comparo si lo que esta en la base de datos 
            if (rs.getObject("nombre").equals(nombre) && rs.getObject("apellido").equals(ape)){
                existe = true;
            }
        }
        s.close();
        con.close();
        return existe;
    }
    
    //inserto el contracto en la base de datos para un usuario dado
    public static void InsertarContacto (String nombre,String ape,String tel,String dir,String usuario) throws SQLException{
        // llamo a la base de datos y preparo la consulta y capturo la excepcion
        con = DriverManager.getConnection ("jdbc:mysql://localhost/"+base,user,pass);
        Statement s = con.createStatement();
        s.executeUpdate("INSERT INTO contacto (nombre,apellido,telefono,direccion,nameUser) VALUES ('"+nombre+"','"+ape+"','"+tel+"','"+dir+"','"+usuario+"')");
        con.close();
        s.close();
    }
    
    //devuelvo una lista de todos los contactos de un usuario 
    public static LinkedList contacto(String name) throws SQLException{
        con = DriverManager.getConnection ("jdbc:mysql://localhost/"+base,user,pass);
        LinkedList<Contacto> user = new LinkedList();
        // llamo a la base de datos y preparo la consulta y capturo la excepcion
        Statement s = con.createStatement();
        //realizo la busqueda en la base de datos
        //Recorremos el resultado, mientras haya registros para leer
        ResultSet rs = s.executeQuery ("select * from Contacto where nameUser='"+name+"'");
        //mientras que exista registro para leer sigue el while sino sale
        while (rs.next()){ //CAMBIE ESTO TAMBIEN, no estaba bien lo que hacias, se iba de rango
            Contacto per = new Contacto();
            //comparo si lo que esta en la base de datos 
            per.setNombre((String)rs.getObject("nombre"));
            per.setApellido((String) rs.getObject("apellido"));
            per.setTelefono((String) rs.getObject("telefono"));
            per.setDireccion((String) rs.getObject("direccion"));
            user.add(per);
        }
        s.close();
        con.close();
        return user;
    }
    
    //devuelvo el id de un contacto 
    public static int idContacto(String name,String apellido) throws SQLException{
        con = DriverManager.getConnection ("jdbc:mysql://localhost/"+base,user,pass);
        int id=-1;
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("select idcontacto from Contacto where (nombre='"+name+"' and apellido='"+apellido+"')");
        rs.first();
        id = (int)rs.getObject("idcontacto");
        s.close();
        con.close();
        return id;
    }
    
    //edito un contacto con los parametros pasados
    public static void editarContacto(String name,String apellido,String telefono,String direccion,int id) throws SQLException{
        con = DriverManager.getConnection ("jdbc:mysql://localhost/"+base,user,pass);
        Statement s = con.createStatement();
        int rs = s.executeUpdate("UPDATE contacto set nombre='"+name+"' where idcontacto="+id);
        rs = s.executeUpdate("UPDATE contacto set apellido='"+apellido+"' where idcontacto="+id);
        rs = s.executeUpdate("UPDATE contacto set telefono='"+telefono+"' where idcontacto="+id);
        rs = s.executeUpdate("UPDATE contacto set direccion='"+direccion+"' where idcontacto="+id);
        s.close();
        con.close();
    }
    
    //elimino un contacto dado un id
    public static void eliminarContacto(int id) throws SQLException{
        con = DriverManager.getConnection ("jdbc:mysql://localhost/"+base,user,pass);
        Statement s = con.createStatement();
        int rs = s.executeUpdate("delete from contacto where idcontacto='"+id+"'");
        s.close();
        con.close();
    }
    
}
