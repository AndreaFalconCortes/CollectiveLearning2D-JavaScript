/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redcaminante2d;

import java.util.*;

/**
 *
 * @author andrago
 */
public class Caminante {
    
    
    public int id;                         //walker id
    public int x;                          //position x
    public int y;                          //position y
    public double q;                       //memory use
    public double rho;                     //communication
    public int edges;                      
    public int ftime;                      //first passage time to any target
    public int bandfirst;                  //flag for ftime
    public int ninflun;                    
    public int success;                   
    public Posicion [] pos;                //positions
    public ArrayList<Caminante> vecinos; 
    
    public Caminante(int id, int x, int y, double q, double rho, int edges ,int walkLong){
        this.id = id;
        this.x = x;
        this.y = y;
        this.q = q;
        this.rho = rho;
        this.edges = edges;
        this.pos = new Posicion[walkLong+1];
        vecinos = new ArrayList<>();        
    }
}
