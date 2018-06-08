/* 

 */
package Laberinto;
// librerías
import java.applet.Applet;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Laberinto extends Applet {
    JPanel a;                               // clase JPanel para crear un panel
    JFrame frame;                           // clase JFrame para crear una ventana
    
    
    public Laberinto(){                         // constructor de la clase
        a = new JPanel();                   
        a.setBackground(Color.pink);            // metodo para ponerle color al fondo de la ventana
        frame = new JFrame("Laberinto");        // título de la ventana
        frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
        frame.add(a);                           // agregar el panel a la ventana
        //frame.pack();
        frame.setResizable(false);              // método para que no se pueda modificar el tamaño de la ventana
        
        frame.setSize(700, 600);                // tamaño de la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // cuando se sale de la ventana, se termina el progrma
        frame.setLocationRelativeTo(null);      // colocar la ventana en el centro de la pantalla
        frame.setVisible(true);                 // hacer visible la ventana
        //frame.setFocusable(true);  
        
    }
        
  public static void main (String [] args){
      new Laberinto();
  } 
} 