
package Laberinto;

// esta clase es para mostrar las celdas que conforman la matriz del laberinto y para quitar algunos lados de las celdas 
// y así crear los pasadizos del laberinto

public class Celda {                   
    byte [] lados = {1,1,1,1};      // las celdas se inicializan con todos sus lados
    byte [] bordes = {0,0,0,0};
    Integer x, y;
    
    public void imprimir1Celda(){    // método para imprimir cada lado de la celda
        System.out.println(" " + lados[0] + " ");           // lado norte
        System.out.println(lados[3] + " " + lados[1]);// lado oeste y lado este
        System.out.println(" " + lados[2] + " ");           // lado sur
    }
    
    public boolean checkLados(){    // método para checkear si una celda tiene todas sus lados o no
        if(lados[0] == 1 && lados[1] == 1 && lados[2] == 1 && lados[3] == 1){   // si una celda tiene todos los lados
            return true;
        }else {
            return false;
        }   
    }
}
 