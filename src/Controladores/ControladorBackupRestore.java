/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controladores;

import Interfaces.interfazBackupRestore;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author NaN
 */
public class ControladorBackupRestore implements ActionListener {
    
    public interfazBackupRestore ventanaBackupRestore;
    private String baseDatos = "";
    private String usuarioDB = "";
    private String passwordDB = "";
    private boolean banderaRB = false; 
    
    public ControladorBackupRestore(){
        configuracionBDD(); //Cargo configuracion de base de dato como usuario pass y base de dato
        ventanaBackupRestore = new interfazBackupRestore();
        ventanaBackupRestore.setActionLisntener(this); 
        ventanaBackupRestore.setLocationRelativeTo(null);
        ventanaBackupRestore.setVisible(true);
    }
    
    //Dado el archivo de configuaracion de base de datos recupero la base de datos el usuario y contraseña
    private void configuracionBDD(){
        FileReader fr = null;
        try {
            fr = new FileReader(System.getProperty("user.dir")+"\\config.txt");
            BufferedReader bf = new BufferedReader(fr);
            String sCadena;
            while ((sCadena = bf.readLine())!=null) {
                if(sCadena.equals("base de datos:")){
                    sCadena=bf.readLine();
                    baseDatos=sCadena;
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControladorBackupRestore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControladorBackupRestore.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(ControladorBackupRestore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    //Este prodecedimiento es el encargado de ejecutar un proceso en consola y crear un archivo .sql
    public void execBackup(String user, String pass, String bDd, String dirBin, String dirGuardar){         
            try{         
                Runtime runtime = Runtime.getRuntime();
                String comando = '"'+dirBin+"\\mysqldump.exe"+'"'+" "+"-u"+user+" "+"-p"+pass+" "+bDd+" "+"-r"+" "+dirGuardar;
                Process child = runtime.exec(comando); 
                //Process es el que ejecuta el comando para buscar el mysqldump.exe
                JOptionPane.showMessageDialog(null, "Archivo generado", "Verificar",JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error no se genero el archivo por el siguiente motivo: " + e.getMessage(), "Verificar",JOptionPane.ERROR_MESSAGE);
            }
 
    }
    
    //Este prodecedimiento es el encargado de ejecutar un proceso en consola y crear un archivo .sql
    public void execRestaurar(String user, String pass, String bDd, String dirBin, String dirArchivo){         
            try{         
                Runtime runtime = Runtime.getRuntime();
                String comando = '"'+dirBin+"\\mysql.exe"+'"'+" "+"-u"+user+" "+"-p"+pass+" "+bDd+" "+"<"+" "+dirArchivo;
                Process child = runtime.exec(comando); 
                //Process es el que ejecuta el comando para buscar el mysql.exe
                JOptionPane.showMessageDialog(null, "Archivo generado", "Verificar",JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error no se genero el archivo por el siguiente motivo: " + e.getMessage(), "Verificar",JOptionPane.ERROR_MESSAGE);
            }
 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        //selecciona donde esta la carpeta del ejecutable
        if(e.getSource()==ventanaBackupRestore.getSeleccionBin()){ 
            String a = seleccionarCarpeta();
            if (a.matches("")) {
                JOptionPane.showMessageDialog(null, "Seleccion nula de carpeta"+'\n'+"C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\", "Seleccione Carpeta",JOptionPane.INFORMATION_MESSAGE);
            } else {
                ventanaBackupRestore.getDireccionMysql().setText(a);
            }        
        }
        
        if(e.getSource()==ventanaBackupRestore.getSeleccionArchivo()){
            if (ventanaBackupRestore.getRadioBackup().isSelected()) {
                //Radio Butom verdadero en backup te deja seleccionar carpeta
                String a = seleccionarCarpeta();
                if (a.matches("")) {
                    JOptionPane.showMessageDialog(null, "Seleccion donde guardar archivo", "Seleccione Carpeta",JOptionPane.INFORMATION_MESSAGE);
                } else {
                    ventanaBackupRestore.getDireccionArchivo().setText(a);
                }
            } else {
                //Radio Butom verdadero en Restaurar te deja seleccionar archivo .sql
                String a = seleccionArchivo();
                if (a.matches("")) {
                    JOptionPane.showMessageDialog(null, "Seleccion un archivo .SQL ", "Seleccione nula",JOptionPane.INFORMATION_MESSAGE);
                } else {
                    ventanaBackupRestore.getDireccionArchivo().setText(a);
                }
            }        
        }
        if (ventanaBackupRestore.getRadioBackup().isSelected()){
            if(banderaRB){
                ventanaBackupRestore.getDireccionArchivo().setText("");
                banderaRB = false;
            }
        }
        if (ventanaBackupRestore.getRadioRestore().isSelected()){
            if(!banderaRB){
                ventanaBackupRestore.getDireccionArchivo().setText("");
                banderaRB = true;
            }
        }
        if(e.getSource()==ventanaBackupRestore.getBotonEjecutar()){ 
            if (ventanaBackupRestore.getRadioBackup().isSelected()) {
                if (ventanaBackupRestore.getDireccionArchivo().getText().matches("")) {
                    JOptionPane.showMessageDialog(null, "Seleccion donde guardar archivo en 'Accion a Realizar'", "Seleccione Carpeta",JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String dirbin = ventanaBackupRestore.getDireccionMysql().getText();
                    String fecha = DateToString(Date.valueOf(LocalDate.now()));
                    String dirarch = ventanaBackupRestore.getDireccionArchivo().getText()+"\\agenda"+fecha+".sql";
                    execBackup(this.usuarioDB,this.passwordDB,this.baseDatos,dirbin,dirarch);
                }
            } else {
                if (ventanaBackupRestore.getDireccionArchivo().getText().matches("")) {
                    JOptionPane.showMessageDialog(null, "Seleccion un archivo .SQL ", "Seleccione nula",JOptionPane.INFORMATION_MESSAGE);
                } else {
                    /*String dirbin = ventanaBackupRestore.getDireccionMysql().getText();
                    String fecha = DateToString(Date.valueOf(LocalDate.now()));
                    String dirarch = ventanaBackupRestore.getDireccionArchivo().getText();
                    execRestaurar(this.usuarioDB,this.passwordDB,this.baseDatos,dirbin,dirarch);*/
                }
            }        
        }
    }
    
    
    
    // Selecciona carpeta
    public String seleccionarCarpeta(){
        //Crear un objeto FileChooser
        JFileChooser fc = new JFileChooser();
        fc.setApproveButtonText("Aceptar");
        //Mostrar la ventana para abrir archivo y recoger la respuesta
        //En el parámetro del showOpenDialog se indica la ventana
        //  al que estará asociado. Con el valor this se asocia a la
        //  ventana que la abre.
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //elegir directorio solo
        int respuesta = fc.showOpenDialog(this.ventanaBackupRestore);
        //Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION){
            //Crear un objeto File con el archivo elegido
            File archivoElegido = fc.getSelectedFile();
            //Mostrar el nombre del archvivo en un campo de texto
            String direccion = archivoElegido.getPath().replace(archivoElegido.getName(), "");
            String archivo = archivoElegido.getName();
            return direccion+archivo;
        }else{
            return "";
        }
    }
    
    // Seleccion de archivo .sql
    public String seleccionArchivo(){
        String retorno = null;
        //Crear un objeto FileChooser
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter =  new FileNameExtensionFilter("Archivos sql", "sql");
        fc.addChoosableFileFilter(filter);
        fc.setApproveButtonText("Abrir");
        //Mostrar la ventana para abrir archivo y recoger la respuesta
        //En el parámetro del showOpenDialog se indica la ventana
        //  al que estará asociado. Con el valor this se asocia a la
        //  ventana que la abre.
        int respuesta = fc.showOpenDialog(this.ventanaBackupRestore);
        //Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION){
            //Crear un objeto File con el archivo elegido
            File archivoElegido = fc.getSelectedFile();
            //Mostrar el nombre del archvivo en un campo de texto
            String direccion = archivoElegido.getPath().replace(archivoElegido.getName(), "");
            String archivo = archivoElegido.getName();
            if (!archivo.contains(".sql")&& !archivo.contains(".SQL")) {
                retorno = "";
            }else{
                retorno = direccion+archivo;
            }   
        }else{
            retorno = "";
        }
        return retorno;
    }//--------------------------------------------------------------------------
    
    
    // Dado un tipo date me lo pasa a String
    private String DateToString(Date a){
        String dia = (a.getDate()<10)?"0"+a.getDate():a.getDate()+"";
        String mes = (a.getMonth()+1<10)?"0"+(a.getMonth()+1):(a.getMonth()+1)+"";
        String fecha = dia+"-"+mes+"-"+(a.getYear()+1900);
        return fecha;
    }
    
    public void visible(boolean a){
        ventanaBackupRestore.setVisible(a);
    }
}
