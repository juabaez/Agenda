/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencias;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Juan baez
 */
public class ConexionDB {
      
    public static Connection GetConnection()
    {
        Connection conexion=null;
        if (conexion==null){
            try{
                String servidor = "jdbc:mysql://localhost/agenda";
                String usuarioDB="root";
                String passwordDB="root";
                conexion = DriverManager.getConnection(servidor,usuarioDB,passwordDB);
            }catch(SQLException ex){
                conexion=null;
            }
        }
        return conexion;
    }
    
    public static void Disconnect(Connection con) throws SQLException{
        con.close();
    }
}
