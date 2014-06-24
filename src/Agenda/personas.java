/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agenda;
import java.lang.String;
import java.util.LinkedList;

public class personas {
    String usuario;
    String contrasenia;
    String nombre;
    String apellido;
    String mail;

    
    
    public void personas(){
        usuario="";
        contrasenia="";
        nombre="";
        apellido="";
        mail="";
    }
    
    public void personas (String user, String pass,String nom,String ape,String mail){
        usuario=user;
        contrasenia=pass;
        nombre=nom;
        apellido=ape;
        mail=mail;
    }
    
    public void setUser(String user){
        usuario=user;
    }
    
    public void setPass(String pass){
        contrasenia=pass;
    }
    
    public String getUser(){
        return usuario;
    }
    
    public String getPass(){
        return contrasenia;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getApellido(){
        return apellido;
    }
    
    public void setNombre(String nom){
        nombre=nom;
    }
    
    public void setApellido(String ape){
        apellido=ape;
    }
    
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
