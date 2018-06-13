package Laberinto;

import java.awt.Image;
import javafx.animation.Timeline;
import javax.swing.*;
import javax.swing.SwingUtilities;

public class Ventana extends JFrame {
    int tamX = 20;              // tamaño de filas
    int tamY = 15;              // tamaño de columnas
    int tamCelda = 45;          // tamaño del espaciado
    Laberinto lab1 = new Laberinto(tamX, tamY);  // nuevo objeto de clase Laberinto con los tamaños de filas y columnas como argumento
    
    public Ventana(){             // constructor
       init();
    }
    
    public void init() {                                         // método que inicializa y dibuja el JFrame
        this.setLayout(null);
        setTitle("Laberinto Espacial");                             // le pone título a la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             // cuando se sale de la ventana, se termina el programa
        setSize(tamX * tamCelda + 30, tamY * tamCelda + 50);       // tamaño de la ventana
        MostrarLaberinto mlab = new MostrarLaberinto(lab1, tamCelda);
        add(mlab);                          // agrega la clase al JFrame
        addKeyListener(mlab);               // llama a la interfez KeyEvent, la cual procesa cada evento del teclado(tecla tipeada, apretada o soltada)
        setContentPane(mlab);               // reemplaza el contenido del JFrame por el dibujo del laberinto
        mlab.setFocusable(true);            // el método mostrar recibe el foco y ejecute las instrucciones del teclado
        setResizable(false);                // método para que no se pueda modificar el tamaño de la ventana
        setLocationRelativeTo(null);        // colocar la ventana en el centro de la pantalla
               
    }
         
    public static void main (String [] args){         
        
        SwingUtilities.invokeLater(new Runnable(){      // método para actualizar el GUI
            @Override
            public void run() {                         // método para ejecutar el juego
                Ventana lab2 = new Ventana();           // hace visible la ventana
                lab2.setVisible(true); 
            }
        }) ;
    } 
}
    