/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proyecto1_201213192;
import Analizador.analizadorConstants;
import Analizador.nodo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import Analizador.NodoError;


/**
 *
 * @author hboiton
 */
/*
ahi te acordas mula que el tipodatos solo me da el tipo que es
el tipo  solo asi meda si es un numero cadena caracter 
si es verdadero o falso

*/

public class analizarArbol {
nodo raiz;
nodoTabla aux;
Hashtable<String,nodoTabla> tablaSimbolos;
ArrayList<nodo> metodoscuerpo ;    
ArrayList<nodoLienzo> clasesLienzo;
nodoLienzo lienzo;
String nombreMA;
ArrayList<NodoError> listaErrores;
public analizarArbol( nodo r, Hashtable t ,ArrayList cl,ArrayList<NodoError>er){
        this.raiz=r;
        this.tablaSimbolos=t;
        this.clasesLienzo=cl;
        this.nombreMA="NULL";
        this.listaErrores=er;
}

void analizar(){
   
   lienzo=(nodoLienzo)accionesArbol(raiz);
   clasesLienzo.add(lienzo);
}
   
Object accionesArbol(nodo r){
      String tipoDato;
      String acceso;
      String ambito;
      String rol;
      String id;
      nodoTabla aux;
      String tipo=r.getEtiqueta();
     // hijos=null;
      switch(tipo){
           case "S":{
                    
               return accionesArbol(r.hijos.get(0));
           }
           case "Lienzo": {
           
                lienzo =new nodoLienzo();
                lienzo.setNombre(r.getValor());
                id=r.getValor();
                acceso=r.getNivel();
                tipoDato="clase";
                rol="Lienzo";
                ambito="---";
                aux=new nodoTabla();
                aux.setId(id);
                aux.setTipodato(tipoDato);
                aux.setRol(rol);
                aux.setAmbito(ambito);
                aux.setAccseso(acceso);
                agregarTablaSibolos(tipo+"_"+tipoDato, aux);
                if(r.hijos.size()==1){
                
                      accionesArbol(r.hijos.get(0));                     
                }else{
                // primero hago extender
                
                
                }
                
               return lienzo; 
           }
           // cuerpo lienzo regeso el principal clase y sus metodos una lista una clase metdos que va ser la misma para principal y metodo
           case"cuerpo":{
               metodoscuerpo =new ArrayList<nodo>();
               nodo metodo;
                for (nodo h :r.hijos) { 
                    
                    metodo=(nodo)accionesArbol(h);
                    
                    if(metodo.getEtiqueta().equals("principal")||metodo.getEtiqueta().equals("metodo")||metodo.equals("funcion")){
                    
                        metodoscuerpo.add(metodo);
                    }
                }
               return metodoscuerpo;
           }
           case"metodo":{
                nombreMA=r.getValor();
               
                
                nombreMA="NULL";
               return"";
           }
           //variables
           case"variables":{
               
               return"";
           }
           case"variable":{
               /*recordate que la variable tenes numero -> entero o decimal  cadena ->cadena caracter->caracter booleano-> verdarero o falso*/
               return"";
           }
           case"arreglo":{
           
               return"";
           }
           //valor variavle
           case"valor variable":{
           
               return"";
           }
           //valor vector
           case"valor vector":{
           
               return"";
           }
           // para operaciones de E
           case"E":{
               nodo n1=null,n2=null;
               String clave;
               int op=-1;
               
               if(r.hijos.size()==0){
                n1=(nodo)accionesArbol(r.hijos.get(0));
                return n1;
               }else{
                 n1=(nodo)accionesArbol(r.hijos.get(0));
                 op=(int)accionesArbol(r.hijos.get(1));
                 n2=(nodo)accionesArbol(r.hijos.get(2));
                 clave=n1.getValor();
                 n1=buscarIDytenervalor(clave, n1);
                 clave=n2.getValor();
                 n2=buscarIDytenervalor(clave, n2);
                 if(n1!=null&&n2!=null){
                       n1=hacerOperaccionE(n1, n2,op);
                         return n1;          
                 }else{
                     //ir estado de error
                     NodoError er=new NodoError();
                     er.setColumna("");
                     er.setLinea("");
                     er.setTipoError("Error Semnatico");
                     er.setDescripcion("no existen las variables");
                     agregarError(er);
                     n1.setEtiqueta("Error");
                 
                 }
               }
               return n1;
           }
           case"T":{
               nodo n1=null,n2=null;
               String clave;
               int op=-1;
               
               if(r.hijos.size()==0){
                n1=(nodo)accionesArbol(r.hijos.get(0));
                return n1;
               }else{
                 n1=(nodo)accionesArbol(r.hijos.get(0));
                 op=(int)accionesArbol(r.hijos.get(1));
                 n2=(nodo)accionesArbol(r.hijos.get(2));
                 clave=n1.getValor();
                 n1=buscarIDytenervalor(clave, n1);
                 clave=n2.getValor();
                 n2=buscarIDytenervalor(clave, n2);
                 if(n1!=null&&n2!=null){
                       n1=hacerOperaccionIF(n1, n2,op);
                         return n1;          
                 }else{
                     //ir estado de error
                     NodoError er=new NodoError();
                     er.setColumna("");
                     er.setLinea("");
                     er.setTipoError("Error Semnatico");
                     er.setDescripcion("no existen las variables");
                     agregarError(er);
                     n1.setEtiqueta("Error");
                 
                 }
               }
               return n1;
           }
           
           case"I":{
               nodo n1,n2;
               String clave;
               int op;
               
               if(r.hijos.size()==0){
                n1=(nodo)accionesArbol(r.hijos.get(0));
                return n1;
               }else{
                 n1=(nodo)accionesArbol(r.hijos.get(0));
                 op=(int)accionesArbol(r.hijos.get(1));
                 n2=(nodo)accionesArbol(r.hijos.get(2));
                 clave=n1.getValor();
                 n1=buscarIDytenervalor(clave, n1);
                 clave=n2.getValor();
                 n2=buscarIDytenervalor(clave, n2);
                 if(n1!=null&&n2!=null){
                       n1=hacerOperaccionIF(n1, n2,op);
                          return n1;         
                 }else
                 {
                     //ir estado de error
                     NodoError er=new NodoError();
                     er.setColumna("");
                     er.setLinea("");
                     er.setTipoError("Error Semnatico");
                     er.setDescripcion("no existen las variables");
                     agregarError(er);
                     n1.setEtiqueta("Error");
                 
                 }
                 
                 
                 
               
               }
               
               return n1;
           }
           // operaciones artimeticas
           case"mas":{
           
               return 4;
           }
           case"menos":{
           
               return 3;
           }
           case"por":{
           
               return 2;
           }
           case"dividir":{
           
               return 1;
           }
           case"potencia":{
           
               return 0;
           }
           //claves
           case "numero":{
               nodo n=r;
               n.setTipo("entero");
              return n;
           }       
           case "id":{
               nodo n=r;
               n.setTipo("id");
              return n;               
           }
           case "cadena":{
               nodo n=r;
               n.setTipo("cadena");
               return n;
           }
           case"caracer":{
               nodo n=r;
               n.setTipo("caracter");
               return n;
               
           }
           case"verdadero":{
               nodo n=r;
               n.setTipo("bool");
               return n;
           }
           case"falso":{
                nodo n=r;
               n.setTipo("bool");
              return n;
           }
           
           //llamar a funcion
           case "llamadaF":{
           
               return"";
           }
           case"valor vector pocion":{
           
               return"";
           }
           case"Error":{
           
           
              return r;
           }
           
           
           
               
             
      
      
      }
             
    
        
    return null;
}
nodo hacerOperaccionE(nodo n1,nodo n2,int op){
    
                    if((n1.getTipo().equals("entero")||n1.getTipo().equals("decimal"))&&(n2.getTipo().equals("entero")||n2.getTipo().equals("decimal"))){
                            if(op==0){
                            n1=operarPontencia(n1, n2);
                            }else if(op==1){
                             
                            
                            }else if(op==2){
                             
                            
                            }
                            return n1;
                 
                        }else if(n1.getTipo().equals("cadena")&&(n2.getTipo().equals("entero")||n2.getTipo().equals("decimal"))){
                                char a=n1.getValor().charAt(2);
                                int i=(int)a;
                                String aa=Integer.toString(i);
                                n1.setValor(aa);
                                if(op==0){
                                n1=operarPontencia(n1, n2);
                                }else if(op==1){
                                 n1=operarDiv(n1, n2);

                                }else if(op==2){
                                    n1=operarMul(n1, n2);

                                }
                                return n1;
                        
                        }else if(n1.getTipo().equals("bool")&&(n2.getTipo().equals("entero")||n2.getTipo().equals("decimal"))){
                                    if(n1.getValor().equals("verdadero")||n1.getValor().equals("true")){
                                    n1.setValor("1");
                                    }else{n1.setValor("0");}
                                    
                                   if(op==0){
                                    n1=operarPontencia(n1, n2);
                                    }else if(op==1){
                                     n1=operarDiv(n1, n2);
                                    }else if(op==2){
                                    n1=operarMul(n1, n2);
                                    
                                    }
                                     return n1;
                        
                        }else if((n1.getTipo().equals("entero")||n1.getTipo().equals("decimal"))&&n2.getTipo().equals("bool")){
                        
                             if(n2.getValor().equals("verdadero")||n2.getValor().equals("true")){
                                    n2.setValor("1");
                                    }else{n2.setValor("0");}
                                    
                                    if(op==0){
                                        n1=operarPontencia(n1, n2);
                                    }else if(op==1){
                                       n1=operarDiv(n1, n2);
                            
                                    }else if(op==2){
                                        n1=operarMul(n1, n2);
                            
                                     }
                                     return n1;
                            
                            
                        }else {
                           // eror de operacion
                           NodoError er=new NodoError();
                           er.setColumna("");
                           er.setLinea("");
                           er.setTipoError("Error Semnatico");
                           er.setDescripcion("no se puede hacer la operacion por que son de diferentes tipos");
                           agregarError(er);
                           n1.setEtiqueta("Error");
                          
                       }
return n1;
}
nodo hacerOperaccionIF(nodo n1,nodo n2 ,int op){
                        if((n1.getTipo().equals("entero")||n1.getTipo().equals("decimal"))&&(n2.getTipo().equals("entero")||n2.getTipo().equals("decimal"))){
                            if(op==0){
                            n1=operarPontencia(n1, n2);
                            }else if(op==1){
                             n1=operarDiv(n1, n2);
                            
                            }else if(op==2){
                                n1=operarMul(n1, n2);
                            
                            }
                            return n1;
                 
                        }else if(n1.getTipo().equals("cadena")&&(n2.getTipo().equals("entero")||n2.getTipo().equals("decimal"))){
                                char a=n1.getValor().charAt(2);
                                int i=(int)a;
                                String aa=Integer.toString(i);
                                n1.setValor(aa);
                                if(op==0){
                                n1=operarPontencia(n1, n2);
                                }else if(op==1){
                                 n1=operarDiv(n1, n2);

                                }else if(op==2){
                                    n1=operarMul(n1, n2);

                                }
                                return n1;
                        
                        }else if(n1.getTipo().equals("bool")&&(n2.getTipo().equals("entero")||n2.getTipo().equals("decimal"))){
                                    if(n1.getValor().equals("verdadero")||n1.getValor().equals("true")){
                                    n1.setValor("1");
                                    }else{n1.setValor("0");}
                                    
                                   if(op==0){
                                    n1=operarPontencia(n1, n2);
                                    }else if(op==1){
                                     n1=operarDiv(n1, n2);
                                    }else if(op==2){
                                    n1=operarMul(n1, n2);
                                    
                                    }
                                     return n1;
                        
                        }else if((n1.getTipo().equals("entero")||n1.getTipo().equals("decimal"))&&n2.getTipo().equals("bool")){
                        
                             if(n2.getValor().equals("verdadero")||n2.getValor().equals("true")){
                                    n2.setValor("1");
                                    }else{n2.setValor("0");}
                                    
                                    if(op==0){
                                        n1=operarPontencia(n1, n2);
                                    }else if(op==1){
                                       n1=operarDiv(n1, n2);
                            
                                    }else if(op==2){
                                        n1=operarMul(n1, n2);
                            
                                     }
                                     return n1;
                            
                            
                        }else {
                           // eror de operacion
                           NodoError er=new NodoError();
                           er.setColumna("");
                           er.setLinea("");
                           er.setTipoError("Error Semnatico");
                           er.setDescripcion("no se puede hacer la operacion por que son de diferentes tipos");
                           agregarError(er);
                           n1.setEtiqueta("Error");
                          
                       }
return n1;
}
nodo operarPontencia(nodo x , nodo y){
     float num1=Float.parseFloat(x.getValor());
     float num2=Float.parseFloat(y.getValor()); 
     float c=0;
     String resultado="";
        
     c=(float)Math.pow(num1,num2);
     resultado=Float.toString(c);
     x.setValor(resultado);
 return x;
}
nodo operarMul(nodo x,nodo y){
        float num1=Float.parseFloat(x.getValor());
        float num2=Float.parseFloat(y.getValor()); 
        float c=0;
        String resultado="";
        c=num1*num2;
        resultado=Float.toString(c);
        x.setValor(resultado);
        return x;
}
nodo operarDiv(nodo x,nodo y){
        float num1=Float.parseFloat(x.getValor());
        float num2=Float.parseFloat(y.getValor()); 
        float c=0;
        String resultado="";
        c=num1/num2;
        resultado=Float.toString(c);
        x.setValor(resultado);
        return x;
}
nodo oparacionSum(nodo x, nodo y){


}
nodo buscarIDytenervalor(String clave, nodo n){
   nodoTabla aux1,aux2;
   if(n.getEtiqueta().equals("ide")){
        
       aux1=buscarNodoTabla(metodoscuerpo+clave);
       aux2=buscarNodoTabla(clave);
       if(aux1!=null){
           n.setTipo(aux1.getTipodato());
           n.setValor(aux1.getValor());
           return n;
       }else if(aux2!=null){
           n.setTipo(aux2.getTipodato());
           n.setValor(aux2.getValor());
           return n;
       }else{
       
           n=null;
           return n;
       }
       
   }else{      
    
    return n;       
   }
}
void agregarTablaSibolos(String clave,nodoTabla aux){

    tablaSimbolos.put(clave, aux);
   

}
 nodoTabla buscarNodoTabla(String clave){
   //nodoTabla
if(tablaSimbolos.containsKey(clave)){
     
   return tablaSimbolos.get(clave);

}else{

  return null;
}

}
void ResivirTablas(Hashtable t){
     
    tablaSimbolos=t;

}
void escribirtabla() throws IOException{
    Date fecha = new Date();
    System.out.println (fecha);
    String hora=fecha.toString();
    FileWriter fichero = null;
    PrintWriter pw = null;
    try
        {
        fichero = new FileWriter("Tabla.html");
                        pw = new PrintWriter(fichero);
                        pw.println( "<html>\n"+"<head> ");
                        pw.println("<title>Tabla Simbolos</title>\n" +"<meta charset=\"utf-8\" >\n" +"</head>");
                        pw.println("<style type=\"text/css\">\n" +
                         "\n" +
                        "table{\n" +
                        "	border: 5px solid #8A0808;\n" +
                        "background-image: url('img/ani.png'); \n" +
                        " width: 100%;\n" +
                        "cellpadding=\"1\" ;cellspacing=\"2\";\n" +
                        "\n" +
                        "}\n" +
                        "tr,td{\n" +
                        "border: 2px solid #8A0808;\n" +
                         "\n" +
                         "width: 6%;\n" +
                        "	text-align: center;\n" +
                         "border-spacing: 2px;\n" +
                        "  padding: 0.3em;\n" +
                        "  font-size: 24px;  \n" +
                        "  font-family: Trebuchet MS;\n" +
                        "\n" +
                        "\n" +
                        "}\n" +
                        "h1{\n" +
                        "	color:#FF4000;\n" +
                        "	text-align: center;\n" +
                        "}\n" +
                        "h3{\n" +
                        "	color:#FF4000;\n" +
                        "	\n" +
                        "}"+        
                        ".h{\n" +
                        "background-color: #D7DF01; \n" +
                        "color:#210B61;\n" +
                         " font-family: Arial;\n" +
                        " font-weight: bold;\n" +
                         "  font-size: 54px;  \n" +
                        "\n" +
                        "}\n" +
                        "</style>\n" +
                        "<body>\n" +
                        "<h1>TABLA DE SIMBOLOS</h1>\n" +
                        "	<br/>        \n" +
                        "	<DIV ALIGN=left> \n" +
                        "	<h3>Hora: </h3>\n" +
                        "	<br/> \n" +
                        "         <h3> +"+hora+"</h3>\n" +
                        "        </DIV>" +
                        "	<table>\n" +
                        "	<tr class=\"h\">" +
                        "<td>ID</td>\n" +
                        "<td>TIPO</td>\n" +
                        "<td>ROL</td>\n" +
                        "<td>AMBITO</td>\n" +
                        "<td>ACCESO</td>\n" +
                        "</tr>\n"
                         
                         );
                       nodoTabla tem;
                       String ide, tipo,rol,ambito, acceso;
                           Enumeration<nodoTabla> enumeration = tablaSimbolos.elements();
                            while (enumeration.hasMoreElements()) {
                                  tem=enumeration.nextElement();
                                  ide=tem.getId();
                                  tipo=tem.getTipodato();
                                  rol=tem.getRol();
                                  ambito=tem.getAmbito();
                                  acceso=tem.getAccseso();
                                   pw.println("<tr>\n" +
                                  "<td>"+ide+"</td>\n" +
                                  "<td>"+tipo+"</td>\n" +
                                  "<td>"+rol+"</td>\n" +
                                  "<td>"+ambito+"</td>\n" +
                                  "<td>"+acceso+"</td>\n" + 
                                   "</tr>");
                                                     
                            }
                          pw.println("</tr> </table>\" \n" +"\n" +"\n" +"\n" +"</body>\n" +"\n" +"\n" +"<html>");                 
                            
    } catch (Exception e) {
            e.printStackTrace();
        }   finally  {
           try {
           
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    
}
void agregarError(NodoError n){
listaErrores.add(n);

}
}
