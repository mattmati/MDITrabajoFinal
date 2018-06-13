package Laberinto;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;

public class MostrarLaberinto extends JPanel implements ActionListener, KeyListener {    
    Laberinto lab1;
    int offsetX = 10;       // el espacio entre la grilla y el borde de la ventana JFrame
    int offsetY = 10;      
    int tamCelda;           // tamaño de las celdas
       
    int pX , pY;            // punteros de X e Y
    int oldX, oldY;         // "old" hace referencia a las coordenadas anteriores
               
    public MostrarLaberinto(Laberinto lab2, int tamCelda2){ // Constructor que recibe como argumento una clase laberinto y el tamaño de la celdas
        lab1 = lab2;
        tamCelda = tamCelda2;
        pX = offsetX  + tamCelda / 2; 
        pY = offsetY + tamCelda / 2; 
        oldX = pX;
        oldY = pY;
        addKeyListener(this);               
    }
    
    public void paint(Graphics g) {                 // método que dibuja el laberinto
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.white);                  // colorea las lineas del laberinto 
        g2d.setStroke(new BasicStroke(8));          // inicializa el grosor de las lineas
        g2d.draw(new Line2D.Float(30, 20, 80, 90)); // las dibuja 
        
        Dimension tamaño = getSize();               // tamaño del componente      
        g2d.setBackground(Color.magenta);           // establece el color del fondo
        g2d.clearRect(0, 0, tamaño.width, tamaño.height);   // toma las coordenadas del rectángulo, ancho y altura y lo pinta de color blanco
        
        Path2D formaLaberinto = new Path2D.Double();        // permite definir un camino entre un punto y otro
        
        int x, y;
        for(Integer i = 0; i < lab1.tamX; i++){             // recorre todas las celdas, si alguna tiene un lado, dibuja una linea
            x = i * tamCelda + offsetX;                     
            for(Integer j = 0; j < lab1.tamY; j++){
                y = j * tamCelda + offsetY;
                if (lab1.celdas[i][j].lados[0] == 1 ){               // si el lado Norte está, 
                    formaLaberinto.moveTo(x, y);                     // crea un pasaje de unas coordenas a otras
                    formaLaberinto.lineTo(x + tamCelda, y);          // dibuja una linea de un punto a otro, de (x,y) a (x+tamCelda,y)
                    g2d.drawLine(x, y, x+tamCelda, y);               // dibuja la linea en el JPanel
                }
                if (lab1.celdas[i][j].lados[1] == 1 ){                          //si el lado Este está,
                    formaLaberinto.moveTo(x + tamCelda, y);                     // crea un pasaje de unas coordenas a otras
                    formaLaberinto.lineTo(x + tamCelda, y + tamCelda);          // de (x+tamCelda,y) a (x+tamCelda,y+tamCelda)
                    g2d.drawLine(x + tamCelda, y, x + tamCelda, y + tamCelda);  // dibuja la linea en el JPanel
                }
                if (lab1.celdas[i][j].lados[2] == 1 ){                          //si el lado Sur está,
                    formaLaberinto.moveTo(x, y + tamCelda);                     // crea un pasaje de unas coordenas a otras
                    formaLaberinto.lineTo(x + tamCelda, y + tamCelda);          // de (x,y+tamCelda) a (x+tamCelda,y+tamCelda)
                    g2d.drawLine(x, y + tamCelda, x + tamCelda, y + tamCelda);  // dibuja la linea en el JPanel
                }
                if (lab1.celdas[i][j].lados[3] == 1 ){              //si el lado Oeste está,
                    formaLaberinto.moveTo(x, y);                    // crea un pasaje de unas coordenas a otras
                    formaLaberinto.lineTo(x, y + tamCelda);         // de (x,y) a (x,y+tamCelda)
                    g2d.drawLine(x, y, x, y + tamCelda);            // dibuja la linea en el JPanel
                }
            }    
        }
        
       x = (oldX - offsetX - tamCelda / 2) / tamCelda;              
       y = (oldY - offsetY - tamCelda / 2) / tamCelda;
       
       // utilizo clipping para crear "collisions", restringiendo el paso del jugador cuando se encuentra con una pared del laberinto
      
       if(x >= 0 && x < lab1.tamX && oldX > pX && lab1.celdas[x][y].lados[3] == 1){         // Izquierda
           pX = oldX;
           pY = oldY;
                         
       } else if(x >= 0 && x < lab1.tamX && oldX < pX && lab1.celdas[x][y].lados[1] ==1){   // Derecha
           pX = oldX;
           pY = oldY;
             
        } else if(y >= 0 && y < lab1.tamY && oldY > pY && lab1.celdas[x][y].lados[0] == 1){  // Arriba
           pX = oldX;
           pY = oldY;           
     
        } else if(y >= 0 && y < lab1.tamY && oldY < pY && lab1.celdas[x][y].lados[2] == 1){    // Abajo     
           pX = oldX;
           pY = oldY;
        }
        
        if(y == lab1.tamY - 1 && x == lab1.tamX - 1){ 
            //System.out.println("¡GANASTE!");
            JOptionPane.showMessageDialog(this, "            ¡GANASTE!", "  WE HAVE A WINNER", JOptionPane.INFORMATION_MESSAGE);
            //JOptionPane.showMessageDialog(this, "CONTINUAR?","SSSSS", JOptionPane.OK_CANCEL_OPTION);
            System.exit(0);           
        }  
        g.setColor(Color.cyan); // dibuja el cursor y establece su color
        g.fillRect(pX - 2, pY - 2, 15, 15); // pinta el cursor   
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        paint(g);
    }

    @Override
    public void keyPressed(KeyEvent tecla){     // Mueve al jugador cada vez que se aprietan las teclas
        oldX = pX;
        oldY = pY;
      
        if(tecla.getKeyCode() == tecla.VK_DOWN){    // Abajo
            pY = pY + tamCelda;                     // Y + el tamaño de la celda
            if(pY > getBounds().height){            // si Y es mayor que la altura
               pY = getBounds().height;             // igual el puntero a la altura
            }
        }
        
        if(tecla.getKeyCode() == tecla.VK_UP){  // Arriba
            pY = pY - tamCelda;
            if(pY < 0){
               pY = 0;
            }
        }
        
        if(tecla.getKeyCode() == tecla.VK_LEFT){  // Izquierda
            pX = pX - tamCelda;
            if(pX < 0){
               pX = 0;
            }
        }
        
        if(tecla.getKeyCode() == tecla.VK_RIGHT){    // Derecha
            pX= pX + tamCelda;
            if(pX > getBounds().width){
               pX = getBounds().width;
            }
        }
        repaint();
    }
           
    @Override
    public void keyReleased(KeyEvent e){
    }
    
    @Override
    public void keyTyped(KeyEvent e){
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {  
    }
    
}
