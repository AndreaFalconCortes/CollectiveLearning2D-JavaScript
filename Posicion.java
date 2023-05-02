/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redcaminante2d;

/**
 *
 * @author andrea
 */
public class Posicion {

    public int x;
    public int y;
    
    public Posicion(int x, int y) {
        
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString (){
        return x + "," + y;
    }
}
