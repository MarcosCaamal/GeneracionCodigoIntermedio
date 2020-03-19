/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generadorcodigointermedio;

public class Pila {
    private int n;//tamaño maximo de la pila
    private int tope;//cuantos elementos tenemos apilados
    private Object pila[];
    
    public Pila(int n){
        this.n=n;
        tope=0;
        pila =new Object[n];
    }
    
    public boolean estaVacia(){
        return tope==0;
    
    }
    
    public boolean estaLlena(){
        return tope==n;
    }
    
    public boolean apilar(Object dato){
        if(estaLlena()){
            return false;
        }
        pila[tope]=dato;
        tope++;;
        return true;
    }
    
    public Object desapilar(){
        if(estaVacia()){
            return null;
        }
        tope--;
        return pila[tope];
    }
    
    public Object elementoTope(){
        return pila[tope-1];
    }
}
