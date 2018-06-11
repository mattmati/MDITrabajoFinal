
package recursion;

import javax.swing.JOptionPane;


public class Recursion {

   public static int factorial(int a){
       if (a <= 1){
           return 1;
       } else {
           return a*factorial(a-1);
       }
   }
    public static void main(String[] args) {
        int n = Integer.parseInt(JOptionPane.showInputDialog("Ingrese un nro: "));
        
        
        System.out.println(factorial(n));
    }
    
}
