/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generadorcodigointermedio;

import java.util.ArrayList;
import jdk.nashorn.internal.codegen.CompilerConstants;



public class Evaluador {
    static ArrayList<String> operaciones=new ArrayList();
     

    private static String convertir(String infija) {
        String postfija="";
        Pila pila =new Pila(100);
        for (int i=0; i<infija.length(); i++){
            char letra=infija.charAt(i);
            if (esOperador(letra)){
                if(pila.estaVacia()){
                    pila.apilar(letra);
                }else{
                    int pe=prioridadEnExpresion(letra);
                    int pp=prioridadEnPila((char)pila.elementoTope());
                    
                    if(pe>pp){
                        pila.apilar(letra);
                    }
                    else if(letra==')'){
                        String aux=pila.desapilar().toString();
                        while (!aux.equals("(")) {
                            postfija+=aux;
                            aux=pila.desapilar().toString();
                        }
                       
                    }
                    else{
                      postfija+=pila.desapilar();
                      pila.apilar(letra);
                    }
                }
            
            }else{
                postfija+=letra;
            }
            
        }
        while(!pila.estaVacia()){
            postfija+=pila.desapilar();
        }
        return postfija;
    }
    private static int prioridadEnExpresion(char operador){
        if(operador=='^') return 4;
        if(operador=='*' || operador=='/') return 2;
        if(operador=='+' || operador=='-') return 1;
        if (operador=='(') return 5;
        return 0;
    }
    
     private static int prioridadEnPila(char operador){
        if(operador=='^') return 3;
        if(operador=='*' || operador=='/') return 2;
        if(operador=='+' || operador=='-') return 1;
        if (operador=='(') return 0;
        return 0;
    }

    private static boolean esOperador(char letra) {
        if (letra=='^'||letra == '*' || letra=='/' || letra=='+' || 
                letra=='-' || letra=='(' || letra==')'){
            return true;
        }
        
        return false;
    }
 
    private static String temporal(char letra, String num1, String num2, int i) {
         if(letra=='*'){
             Evaluador.operaciones.add("T"+i+" = "+num1+" * "+num2);
             return "T"+i;}
         if(letra=='/'){
             Evaluador.operaciones.add("T"+i+" = "+num1+" / "+num2);
             return "T"+i;}
         if(letra=='+'){
             Evaluador.operaciones.add("T"+i+" = "+num1+" + "+num2);
             return "T"+i;}
         if(letra=='-'){
              Evaluador.operaciones.add("T"+i+" = "+num1+" - "+num2);
             return "T"+i;}
         if(letra=='^'){
              Evaluador.operaciones.add("T"+i+" = "+num1+" ^ "+num2);
             return "T"+i;}
         return "";
    }
    
    public ArrayList<String> temporales(String infija){
       // ArrayList<String> operaciones=new ArrayList<>();
        Pila pilaTemporales=new Pila(100);
        int indiceTemporal=0;
        String postfija= convertir(infija);
        for (int i = 0; i < postfija.length(); i++) {
            char letra=postfija.charAt(i);
            if (!esOperador(letra)){
               
                String n=Character.toString(letra);
                pilaTemporales.apilar(n);
                
            }else{
               
                Evaluador temp=new Evaluador();
                String n2=pilaTemporales.desapilar().toString();
                String n1=pilaTemporales.desapilar().toString();
                String n3=temp.temporal(letra, n1, n2, indiceTemporal);
                pilaTemporales.apilar(n3);
                indiceTemporal++;
            }
            
        }
        //System.out.println(""+pilaTemporales.desapilar());
        Evaluador.operaciones.add("X = "+pilaTemporales.elementoTope());
        return this.operaciones;
    
    }
}
