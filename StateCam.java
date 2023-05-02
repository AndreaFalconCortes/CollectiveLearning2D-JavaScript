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
public class StateCam {
        
    public int px;                 //posicion del espacio en eje x
    public int py;                 //posicion del espacio en eje y
    public int target;            //variable booleana que define si en la posicion p hay target o no
    public double gamma;          //peso del target, probabilidad de que el caminante se quede en el
    public double vis;            //contador de visitas a cada posicion; 
    public double vist;           //contador para las visitas temporales al mejor target 
    
    public int labelcalc;
    public int labelreal;
    
    Posicion post;
    
    public StateCam (int px , int py , int target , double gamma , double vis){
        
        this.px = px;
        this.py = py;
        this.target = target;
        this.gamma = gamma;
        this.vis = vis;
        
//        post = new Posicion (px,py);
        
    }
    
    @Override
    public String toString(){
        return px + "," + py;
    }
    
//    public StateCam clones (){
//        StateCam stateclone = new StateCam (px, py, target, gamma, vis);
//        return stateclone;
//    }
    
   
    
}
