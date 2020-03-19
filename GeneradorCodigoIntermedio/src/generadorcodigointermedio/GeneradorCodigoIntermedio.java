
package generadorcodigointermedio;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;

/*
Proyecto: Generacion de Codigo Intermedio por Triplos
Autores: Marcos Abraham Caamal Tzuc
         Marco Antonio Chi Castillo
Fecha: 19/03/2020
 */
public class GeneradorCodigoIntermedio {

   
    public static void main(String[] args) {
  
        frmPrincipal frm=new frmPrincipal();
        frm.setTitle("Generación de Código Intermedio por Triplos");
       
      
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(500,500);
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);//centrar ventana
        
    }
    
}
