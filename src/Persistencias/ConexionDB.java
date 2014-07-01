/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencias;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Juan baez
 */
public class ConexionDB {
      
    public static Connection GetConnection() throws FileNotFoundException, IOException
    {
        Connection conexion=null;
        if (conexion==null){
            try{
                FileReader fr = new FileReader(System.getProperty("user.dir")+"\\config.txt");
                BufferedReader bf = new BufferedReader(fr);
                String sCadena;
                String servidor = "";
                String usuarioDB = "";
                String passwordDB = "";
                while ((sCadena = bf.readLine())!=null) {
                    if(sCadena.equals("base de datos:")){
                        sCadena=bf.readLine();
                        servidor=sCadena;
                    }
                    if(sCadena.equals("usuario:")){
                        sCadena=bf.readLine();
                        usuarioDB=sCadena;
                    }
                    if(sCadena.equals("contrasenia:")){
                        sCadena=bf.readLine();
                        passwordDB=sCadena;
                    }
                }              
                conexion = DriverManager.getConnection("jdbc:mysql://localhost/"+servidor,usuarioDB,passwordDB);
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
