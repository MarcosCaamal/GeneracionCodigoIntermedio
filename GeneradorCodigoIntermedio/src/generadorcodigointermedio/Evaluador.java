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
    static ArrayList<String> numerosReales=new ArrayList();
   public static void evaluar(String infija){
        String infijaTemporal=checarDigitosNum(infija);
        String postfija= convertir(infijaTemporal);
        System.out.println(infijaTemporal);
        System.out.println(numerosReales);
        System.out.println("La expresion postfija es: "+postfija);
        
    }
    //Metodo que sirve para convertir la expresion infija introducida a una expresion postfifa
    //Una expresion postfija tiene la característica de no ser amibigua
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
    //Metodo para saber la prioridad de los operadores de la expresion infija
    private static int prioridadEnExpresion(char operador){
        if(operador=='^') return 4;
        if(operador=='*' || operador=='/') return 2;
        if(operador=='+' || operador=='-') return 1;
        if (operador=='(') return 5;
        return 0;
    }
    
    //Metodo para saber la prioridad de los operadores introducidos a la pila
     private static int prioridadEnPila(char operador){
        if(operador=='^') return 3;
        if(operador=='*' || operador=='/') return 2;
        if(operador=='+' || operador=='-') return 1;
        if (operador=='(') return 0;
        return 0;
    }
    

    //Funcion que nos indica si el caracter de una expresion aritmetica es operador
     //Retorna true si es un operador y false si no
    private static boolean esOperador(char letra) {
        if (letra=='^'||letra == '*' || letra=='/' || letra=='+' || 
                letra=='-' || letra=='(' || letra==')'){
            return true;
        }
        
        return false;
    }
    
   //Metodo que evalua la expresion postfija y devuelve los temporales para generar
   //El código intermdio de la expresion
    private static String temporal(char letra, String num1, String num2, int i) {
         if(letra=='*'){
             Evaluador.operaciones.add("T"+i+" = "+num1+" * "+num2);//guarda los temporales generados en un arraylist
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
    
    //Metodo que nos sirve para checar cuantos digitos tiene un determinado numero 
    private static String checarDigitosNum(String infija){
        String infijaTemporal="";
        String numero="";
        int indices=0;
        for (int i = 0; i < infija.length(); i++) {
            char letra=infija.charAt(i);
            if(!esOperador(letra)){
                numero+=infija.charAt(i);
            }
            else if(letra=='('){
                if(numero!=""){
                    infijaTemporal+=String.valueOf(indices);
                    Evaluador.numerosReales.add(numero);
                    numero="";
                    indices++;                            
                }
                infijaTemporal+=infija.charAt(i);
            }
            else if (letra==')'){
                    
                if(numero!=""){
                    infijaTemporal+=String.valueOf(indices);
                    infijaTemporal+=infija.charAt(i);
                    Evaluador.numerosReales.add(numero);
                    indices++;
                    numero="";
                }
                else{
                    infijaTemporal+=infija.charAt(i);
                }
            }else if(numero!=""){
                infijaTemporal+=String.valueOf(indices);
                infijaTemporal+=infija.charAt(i);     
                Evaluador.numerosReales.add(numero);
                numero="";
                indices++;
            }
            else{
                infijaTemporal+=infija.charAt(i);
            }
                    
                    
        }
        
        if(infija.charAt(infija.length()-1)!=')' && numero!=""){
            infijaTemporal+=String.valueOf(indices);
            Evaluador.numerosReales.add(numero);
        }
        return  infijaTemporal;
    }
    
    //Metodo que recorre la expresion postifija para poder generar sus temporales
    // y retorna los temporales guardados en el arraylist operaciones
    public ArrayList<String> temporales(String infija){
        Pila pilaTemporales=new Pila(100);
        int indiceTemporal=0;
        String infijaTemporal=checarDigitosNum(infija);
        String postfija= convertir(infijaTemporal);
        int indices=0;
        for (int i = 0; i < postfija.length(); i++) {
            char letra=postfija.charAt(i);
            if (!esOperador(letra)){
               
                String n=Character.toString(letra);
                String numero=Evaluador.numerosReales.get(indices).toString();
                pilaTemporales.apilar(numero);
                indices++;
                
            }else{
               
                Evaluador temp=new Evaluador();
                String n2=pilaTemporales.desapilar().toString();
                String n1=pilaTemporales.desapilar().toString();
                String n3=temp.temporal(letra, n1, n2, indiceTemporal);
                pilaTemporales.apilar(n3);
                indiceTemporal++;
            }
            
        }
        
        Evaluador.operaciones.add("X = "+pilaTemporales.elementoTope());//sirve para igualr X al último temporal
        return this.operaciones;//retorna todos los temporales de la expresion 
    
    }
}
