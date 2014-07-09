/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controladores;

import Agenda.Contacto;
import Interfaces.interfazEditarContacto;
import Interfaces.interfazLogin;
import Interfaces.interfazVentana;
import static Persistencias.PersistenciaContacto.InsertarContacto;
import static Persistencias.PersistenciaContacto.buscarContacto;
import static Persistencias.PersistenciaContacto.contacto;
import static Persistencias.PersistenciaContacto.idContacto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author Juan
 */
public class ControladorVentana implements ActionListener, KeyListener{
    private ControladorBackupRestore backupRestor;
    private interfazVentana interfazVen;
    private interfazEditarContacto interfazCon;
    private interfazLogin interfazLogin;
    private static String user;
    public boolean fueListado = false;
    
    public ControladorVentana(String user) throws SQLException {
        this.user = user;
        interfazVen = new interfazVentana(); // creo la instanacia de la ventana
        interfazVen.setActionLisntener(this); // le digo que los botones de la ventana interfaz(los botones dentro del metodo)
        interfazVen.setKeyListener(this);
        //los va a controlar este controlador, varios controladores pueden controlar un mismo elemento
        interfazVen.setLocationRelativeTo(null);
        interfazVen.setVisible(true);
    }
    
    
    public void actionPerformed(ActionEvent ae) {
        //me fijo si se preciono el boton cerrar sesion
        if(ae.getSource()==interfazVen.getCerrarSesion()){ 
            interfazVen.setVisible(false);
            try {
                new ControladorLogin();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //me fijo si se preciono el boton listar contactos
        if (ae.getSource()==interfazVen.getListar()){
            try {
                LinkedList<Contacto> contactos = contacto(user);
                DefaultTableModel dt = interfazVen.getTabla();
                dt.setRowCount(0);
                mostrarContacto(dt,contactos);
                fueListado = true;
            } catch (SQLException ex) {
                Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //me fijo si se preciono el boton agregar contacto
        if (ae.getSource()==interfazVen.getAgregarContacto()) {
            try {
                //llamo al controlador que se encarga de agregar un contacto
                new ControladorAgregarContacto(user,this);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //me fijo si se preciono el boton editar contacto
        if (ae.getSource()==interfazVen.getEditContacto()) {
            JTable dt = interfazVen.getTable1();
            int fila = dt.getSelectedRow();
            if (fila == -1){
                JOptionPane.showMessageDialog(null, "Error: No hay filas seleccionadas", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                try {
                    //obtengo el id del contacto seleccionado por el usuario
                    int id = idContacto((String)dt.getValueAt(fila, 0),(String)dt.getValueAt(fila, 1));
                    //llamo al controlador que se encarga de editar un contacto
                    new ControladorEditarContacto((String)dt.getValueAt(fila, 0),(String)dt.getValueAt(fila, 1),(String)dt.getValueAt(fila, 2),(String)dt.getValueAt(fila, 3),id,this);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        //me fijo si se preciono el eliminar contacto
        if (ae.getSource()==interfazVen.getElimContacto()) {
            JTable dt = interfazVen.getTable1();
            int fila = dt.getSelectedRow();
            if (fila == -1){
                JOptionPane.showMessageDialog(null, "Error: No hay filas seleccionadas", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                try {
                    //obtengo el id del contacto seleccionado por el usuario
                    int id = idContacto((String)dt.getValueAt(fila, 0),(String)dt.getValueAt(fila, 1));
                    //llamo al controlador que se encarga de eliminar un contacto
                    new ControladorElimContacto((String)dt.getValueAt(fila, 0),(String)dt.getValueAt(fila, 1),this);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        //Selecciono el Archivo VCF
        if (ae.getSource()==interfazVen.getSeleccionArchivo()) {
            seleccionArchivo();
        }
        
        //Hacer backup y restaurar base de datos
        if (ae.getSource()==interfazVen.getBotonBackupyRestauracion()) {
            if (backupRestor==null) {
                backupRestor = new ControladorBackupRestore();
            } else {
                backupRestor.visible(true);
            }
            
        }
        
        //cargar los contactos del archivo VCF a la base de datos
        if (ae.getSource()==interfazVen.getCargarContactos()) {
            if (interfazVen.getjTextFieldArchivoVCF().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe cargar un archivo ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            }else{
                try {
                    tratamientoDeArchivo(interfazVen.getjTextFieldArchivoVCF().getText());
                    interfazVen.getjTextFieldArchivoVCF().setText("");
                    if(fueListado){
                        this.actualizarLista();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        //realiza las busqueda de los contactos por el atributo nombre o apellido
        if (ae.getSource()==interfazVen.getBuscar()) {
            try {
                LinkedList<Contacto> contactos = buscarContacto(interfazVen.getCampoBuscado().getText(),user);
                DefaultTableModel dt = interfazVen.getTabla();
                dt.setRowCount(0);
                mostrarContacto(dt,contactos);
                interfazVen.getCampoBuscado().setText("");
            } catch (IOException ex) {
                Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //metodo que actializa la lista de contactos 
    public void actualizarLista() throws IOException{
        try {
            LinkedList<Contacto> users = contacto(user);
            DefaultTableModel dt = interfazVen.getTabla();
            dt.setRowCount(0);
            Contacto per;
            //voy agregando los contactos de un usuario a la tabla
            while (!users.isEmpty()){
                per = users.getFirst();
                dt.addRow(new Object[]{per.getNombre(),per.getApellido(),per.getTelefono(),per.getDireccion(),new JRadioButton()});
                users.remove();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorVentana.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Este metodo me da como eleccion de un directorio o un directorio con el archivo elegido
    public void seleccionArchivo(){
        //Crear un objeto FileChooser
        JFileChooser fc = new JFileChooser();
        fc.setApproveButtonText("Abrir");
        //Mostrar la ventana para abrir archivo y recoger la respuesta
        //En el parámetro del showOpenDialog se indica la ventana
        //  al que estará asociado. Con el valor this se asocia a la
        //  ventana que la abre.
        int respuesta = fc.showOpenDialog(this.interfazVen);
        //Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION){
            //Crear un objeto File con el archivo elegido
            File archivoElegido = fc.getSelectedFile();
            //Mostrar el nombre del archvivo en un campo de texto
            String direccion = archivoElegido.getPath().replace(archivoElegido.getName(), "");
            String archivo = archivoElegido.getName();
            if (!archivo.contains(".vcf")&& !archivo.contains(".VCF")) {
                interfazVen.getjTextFieldArchivoVCF().setText("");
                JOptionPane.showMessageDialog(null, "Extencion invalida (.VCF)", "Error ", JOptionPane.ERROR_MESSAGE);
            }else{
                interfazVen.getjTextFieldArchivoVCF().setText(direccion+archivo);
            }   
        }
    }//--------------------------------------------------------------------------
    
    /*aca va el metodo que carga una lista de contactos con los contactos del archivo VCF*/
    public static void tratamientoDeArchivo(String dirArchivo) throws FileNotFoundException, IOException, SQLException{
        FileReader fr = new FileReader(dirArchivo);
        BufferedReader bf = new BufferedReader(fr);
        String sCadena;
        sCadena = bf.readLine();
        sCadena = bf.readLine();
        sCadena = bf.readLine();
        Contacto auxContacto = new Contacto();;
        while ((sCadena = bf.readLine())!=null) { 
            if(sCadena.contains("BEGIN:")){ 
                sCadena = bf.readLine();
                sCadena = bf.readLine();
                if(sCadena.contains("N:")){
                    sCadena = sCadena.substring(2, sCadena.length()-3);
                    String aux[] = sCadena.split(";");
                    auxContacto.setApellido(aux[0]);
                    auxContacto.setNombre(aux[1]);
                    sCadena = bf.readLine();
                    sCadena = bf.readLine();
                    if(sCadena.contains("TEL;CELL:")){
                        sCadena = sCadena.substring(9, sCadena.length());
                        auxContacto.setTelefono(sCadena);
                        sCadena = bf.readLine();
                        if(sCadena.contains("EMAIL;HOME:")){
                            sCadena = sCadena.substring(11, sCadena.length());
                            auxContacto.setDireccion(sCadena);
                            sCadena = bf.readLine();
                            if(sCadena.contains("END:")){
                                InsertarContacto(auxContacto.getNombre(),auxContacto.getApellido(),auxContacto.getTelefono(),auxContacto.getDireccion(),user);
                            }
                        }else{
                            auxContacto.setDireccion("");
                            if(sCadena.contains("END:")){
                                InsertarContacto(auxContacto.getNombre(),auxContacto.getApellido(),auxContacto.getTelefono(),auxContacto.getDireccion(),user);
                                
                            }
                        }
                    }    
                }else{
                    sCadena = bf.readLine();
                    sCadena = bf.readLine();
                    sCadena = bf.readLine();
                }   
            }      
        }
    }
    
    public static void mostrarContacto(DefaultTableModel dt,LinkedList<Contacto> contacto){
        Contacto per;
        //voy agregando los contactos de un usuario a la tabla
        while (!contacto.isEmpty()){
            per = contacto.getFirst();
            dt.addRow(new Object[]{per.getNombre(),per.getApellido(),per.getTelefono(),per.getDireccion(),new JRadioButton()});
            contacto.remove();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char caracter = e.getKeyChar();
        if((caracter == 'A') || (caracter < 'C')){
            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(interfazVen.getTabla());
            if (interfazVen.getCampoBuscado().getText()!=null) {
                System.out.println("entre");    
            //Insensible a mayúsculas y minúsculas
            sorter.setRowFilter(RowFilter.regexFilter("(?i).*" +interfazVen.getCampoBuscado().getText()+ ".*"));
            interfazVen.getTable1().setRowSorter(sorter);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
