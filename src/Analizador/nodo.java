/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;
import java.util.ArrayList;
 
public class nodo {
    private String cadena;
    private String etiqueta;
    private String nivel;
    private String valor;
    private String tipo;
    public ArrayList<nodo> hijos ;    
    
    public nodo(){
      this.cadena="";
      this.etiqueta="";
      this.nivel="";
      this.valor="";
      this.hijos = new ArrayList<nodo>();
    }

    public void agregarHijo(nodo hijo){
        hijos.add(hijo);
    }

   

    public ArrayList<nodo> getHijos() {
        return hijos;
    }

    public void setHijos(ArrayList<nodo> hijos) {
        this.hijos = hijos;
    }

    /**
     * @return the cadena
     */
    public String getCadena() {
        return cadena;
    }

    /**
     * @param cadena the cadena to set
     */
    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    /**
     * @return the etiqueta
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * @param etiqueta the etiqueta to set
     */
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * @return the nivel
     */
    public String getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
        if(tipo.equalsIgnoreCase("")){}
    }

    

   

}
