/*
Utilizando depth first search:
- Celda inicial[0][0], la marco como visitada
- Busco celdas adyacentes sin visitar, elijo una de las 2 al azar
- Me muevo a esa celda
- La marco como celda actual, marco como visitada
- Repeto hasta que no hayan celdas sin visitar
 */
package Laberinto;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public final class Laberinto  {
    int tamX, tamY;
    Celda[][] celdas;
    
    public Laberinto(){                 // Constructor 1
        tamX = 25;
        tamY= 25;
        celdas = new Celda[tamX][tamY];
        inicializarCeldas();
        crearLaberinto();
        
    }
    
    public Laberinto(int x, int y){     // Constructor 2
        tamX = x;
        tamY = y;
        celdas = new Celda [tamX][tamY];
        inicializarCeldas();
        crearLaberinto();    
    }
    
    public void mostrarCeldas(){            // método para imprimir las celdas
        for(int i = 0; i < tamX; i++){      // recorre filas
            for(int j = 0; j < tamY; j++){  // recorre columnas
                System.out.println(i + " " + j);    
                celdas[i][j].imprimir1Celda();
                System.out.println("\n");
            }
        }
    }    
   
    private void inicializarCeldas() {                  // inicializo las celdas en un arreglo 2D
        for(int i = 0; i < tamX; i++){
            for(int j = 0; j < tamY; j++){
                celdas[i][j] = new Celda();
                celdas[i][j].x = i;
                celdas[i][j].y = j;
                                                        // inicializo bordes de la celda
                if(i == 0){
                    celdas[i][j].bordes[0] = 1;        // pared Norte = 0                               
                }
                if(j == 0){                            // pared Oeste = 3 
                    celdas[i][j].bordes[3] = 1;                                                     
                }
                if(i == tamX -1){
                    celdas[i][j].bordes[2] = 1;        // pared Sur = 2 
                }
                 if(j == tamY -1){
                    celdas[i][j].bordes[1] = 1;        // pared Este = 1 
                 }
            }
        }
    }

    public void crearLaberinto() {
        Random rand = new Random();         
        int x = rand.nextInt(tamX);                 // empieza en una ubicacion al azar en (x,y)
        int y = rand.nextInt(tamY);
        
        Stack<Celda> stackCeldas = new Stack<Celda>();  // se guardan en un Stack las celdas que se re-visitan
        int totalCeldas = tamY * tamX;      // total de celdas es ancho * altura
        int celdasVisitadas = 1;            // contador para contar las celdas que ya fueron visitadas, empieza en 1 porque se cuenta la celda actual
        Celda celdaActual = celdas [x][y];
        
        ArrayList<Vertice> celdaVecina = new ArrayList<Vertice>();
        Vertice tempV = new Vertice();
        
        while(celdasVisitadas < totalCeldas){   // mientras el nro de celdas visitadas sea menor que el nro de celdas totales
            celdaVecina.clear();                // limpiar lista
            tempV = new Vertice();              
            
            if(y-1 >= 0 && celdas[x][y-1].checkLados() == true){        // guarda la celda vecina que se encuentra a la izquierda(y-1)     
                tempV.x1 = x;
                tempV.y1 = y;
                tempV.x2 = x;
                tempV.y2 = y-1; // celda a la izquierda
                tempV.lado1 = 0;
                tempV.lado2 = 2;
                celdaVecina.add(tempV);     // agrega la celda vecina a la lista            
            }
            
            tempV = new Vertice();              // limpia tempV
            if(y+1 < tamY && celdas[x][y+1].checkLados() == true){           // guarda la celda vecina que se encuentra a la derecha(y+1)          
                tempV.x1 = x;
                tempV.y1 = y;
                tempV.x2 = x;
                tempV.y2 = y+1; //celda a la derecha
                tempV.lado1 = 2;
                tempV.lado2 = 0;
                celdaVecina.add(tempV);     // agrega la celda vecina a la lista           
            }
            
            tempV = new Vertice();   
            if(x-1 >= 0 && celdas[x-1][y].checkLados() == true){         // guarda la celda vecina que se encuentra arriba(x-1)            
                tempV.x1 = x;
                tempV.y1 = y;
                tempV.x2 = x-1; // celda arriba
                tempV.y2 = y;
                tempV.lado1 = 3;
                tempV.lado2 = 1;
                celdaVecina.add(tempV);     // agrega la celda vecina a la lista           
            }
            tempV = new Vertice(); 
            if(x+1 < tamX && celdas[x+1][y].checkLados() == true){     // guarda la celda vecina que se encuentra abajo(x+1)           
                tempV.x1 = x;
                tempV.y1 = y;
                tempV.x2 = x+1; // celda debajo
                tempV.y2 = y;
                tempV.lado1 = 1;
                tempV.lado2 = 3;
                celdaVecina.add(tempV);     // agrega la celda vecina a la lista           
            }
            
            if(celdaVecina.size() >= 1){        // si encuentro una celda sin visitar
                int r1 = rand.nextInt(celdaVecina.size()); // elijo un vecino al azar
                tempV = celdaVecina.get(r1);
                       
                celdas[tempV.x1][tempV.y1].lados[tempV.lado1] = 0; // tira las paredes que separan una celda de su vecina
                celdas[tempV.x2][tempV.y2].lados[tempV.lado2] = 0;
                
               
                stackCeldas.push(celdaActual);  // agrego la celda actual al stack
                
                celdaActual = celdas[tempV.x2][tempV.y2]; // la nueva celda ahora es la celda actual
                
                x = celdaActual.x; // actualizo x e y
                y = celdaActual.y;
                
                celdasVisitadas++;  // incremento las celdas visitadas
                
            }else{  // sino, igualo la celda actual con la ultima guardada en el stack
                celdaActual = stackCeldas.pop();
                x = celdaActual.x;      // actualizo x e y
                y = celdaActual.y;
            }            
    }   
    celdas[0][0].lados[3] = 0;      
    }
}
