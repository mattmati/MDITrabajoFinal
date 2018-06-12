
package Ejecutable;


import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import Graficos.Assets;
import Input.Teclado;
import estados.EstadoJuego;


public class Ventana extends JFrame implements Runnable {                       // Se implementa la interfaz "Runnable" para que se defina el cuerpo del método abstracto "run"          
    
    public static final int WIDTH = 800, HEIGHT = 600;                          // static → porque se podrá acceder luego para las dimensiones de la ventana
                                                                                // final → porque son variables constantes
    
    private Canvas canvas;                                                      // Canvas (lienzo)
    private Thread thread;                                                      // Se crea un "hilo" ya que el hilo interno que maneja JFrame llama a los eventos que tiene la ventana,
                                                                                        // es por esto que se utiliza un hilo para no sobrecargarlo con la lógica del programa
                                                                                        // para evitar la sobrecarga
    private boolean running = false;                                            // Controla el estado del juego
    
//-----------------------------  
    
// Métodos para dibujar:
    
    private BufferStrategy bs;                                                  // Crea un espacio de almacenamiento donde el programa dibuja la imagen pero no la muestra hasta que finalice
    private Graphics g;                                                         // Se necesita un contexto gráfico para poder dibujar
    
//------------------------------    
    
// Contador de fotogramas por segundo:
    
    private final int FPS = 60;                                                 // Fotogramas por segundos
    private double TARGETTIME = 1000000000/FPS;                                 // Tiempo requerido para aumentar un fotograma p/s
                                                                                        // como 1 segundo es el menor tiempo preciso posible, se utilizan nanosegundos para obtener esa precisión
    private double delta = 0;                                                   // Esta variable almacena temporalmente el tiempo que vaya transcurriendo
                                                                                        // (se utiliza delta ya que representa el cambio con respecto al tiempo)
    private int AVERAGEFPS = FPS;                                               // Fotogramas p/s en promedio → nos permite saber a cuántos FPS está corriendo el programa
    
    
    private EstadoJuego ejuego;
    private Teclado teclado;
//------------------------------    
    
    public Ventana(){
        setTitle("Batalla Espacial Gatuna");                                    // Título del juego
        setSize(WIDTH, HEIGHT);                                                 // Acá se pasan los valores del ancho y la altura
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                         // Esto permite que se pueda cerrar la ventana cuando el usuario lo decida
        setResizable(false);                                                    // Esto es para que el usuario no pueda redimensionar la ventana (que quede fija)
        setLocationRelativeTo(null);                                            // Esto permite que la pantalla se despliegue en el centro de la ventana en la inicialización del programa
        setVisible(true);                                                       // Visibilidad de la ventana
        
        canvas = new Canvas(); 
        teclado = new Teclado();
        
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));                  // Tamaño preferido
        canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));                    // Tamaño máximo
        canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));                    // Tamaño mínimo
        canvas.setFocusable(true);                                              // Recibe entradas de teclado para que el usuario pueda interactuar
        
 
        add(canvas);                                                            // Se agrega el canvas a la ventana
        canvas.addKeyListener(teclado);                                         // Se agrega el teclado al canvas
    }

    
    public static void main(String[] args) {
        new Ventana().start();                                                  // "start" llama al método thread para arrancar el hilo
    }

    private void actualizar(){                                                  // Actualiza el estado del programa
        teclado.actualizar();
        ejuego.actualizar();
    }
    
    private void dibujar(){                                                     // Dibuja en el programa
        bs = canvas.getBufferStrategy();
        if (bs == null){                                                        // Si no hay nada dibujado...
            canvas.createBufferStrategy(2);                                     // ...se crea este número de buffers para utilizarse en el canvas
            return;
        }
        
        g = bs.getDrawGraphics();  
        
//------------------------------------------   
        g.setColor(Color.BLACK);                                                // Color fondo
        g.fillRect(0, 0, WIDTH, HEIGHT);
        ejuego.dibujar(g);
        
        g.drawString("" + AVERAGEFPS, 10, 10);                                  // PRUEBA - Se imprime string de fps promedio
//------------------------------------------

        g.dispose();                                                            // dispose() es un método de la clase Window el cual libera todos los recursos nativos de la pantalla utilizados por la ventana
        bs.show();
    }

    private void init(){                                                        // Método para inicializar los componentes que tengamos
        Assets.init();
        ejuego = new EstadoJuego();                                             // Inicializamos el estado del juego
 
    }    

    
    @Override
    public void run() {
       // Restricción → para que el ciclo vaya a 60 FPS tal como se lo indica más arriba
       long now = 0;                                                            // now (ahora) → para tener un registro del tiempo que va pasando
       long lastTime = System.nanoTime();                                       // Esta función nos da la hora actual del sistema en nanosegundos
       int frames = 0;
       long time = 0;
       
       init();
        
        // Ciclo principal
        while(running){                                                         // Se encarga de actualizar la posición de todos los objetos del juego para poder mantener el juego actualizado
            now = System.nanoTime();
            delta += (now - lastTime)/TARGETTIME;                               // Se suma el tiempo que ha pasado hasta ahora (now - lastTime) y se divide por el objetivo (TARGETTIME)
            time += (now - lastTime);                                           
            lastTime = now;                                                     // Se iguala a now = System.nanoTime(); para que en la siguiente iteración se obtenga un nuevo valor luego de restarlo
                                                                                       // al tiempo que teníamos en la iteración anterior y así obtendríamos el tiempo que ha pasado hasta ese momento
                                                                                       
            if (delta >= 1){                                                    // Cuando delta equivalga a "TARGETTIME" nos va a dar 1, lo que quiere decir que podemos...
                actualizar();                                                   //...actualizar
                dibujar();                                                      // y dibujar
                delta --;                                                       // Se resta una unidad al delta para poder cronometrar al siguiente fotograma
                frames++;                                                       // delta sea 1, se aumenta la cantidad de fotogramas (frames)
//                System.out.println(frames);                                     // PRUEBA - mostrar en consola los frames
            } 
            
            if (time >= 1000000000){
                AVERAGEFPS = frames;
                frames = 0;                                                     // 0 para volver a contar
                time = 0;
            }
        }
   
        stop();                                                                 // Se detiene el hilo 
    }
    
    private void start(){                                                       // Inicialización del hilo
        thread = new Thread(this);                                              // this = recibe como parámetro en el constructor una clase que implemente una interfaz "Runnable", en este caso, Ventana
        thread.start();                                                         // llama al método "run"
        running = true;
        
    }
    
    private void stop(){                                                        // Finalización del hilo
        try {
            thread.join();
            running = false;
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }  
}
