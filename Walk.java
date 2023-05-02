/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redcaminante2d;

import java.util.Random;

/**
 *
 * @author andrea
 */
public class Walk {

    int t;                         
    int tpr;                       

    double gamma;
    double q;
    double rho;

    double alfa;                   
    double xm;                     
    double vuelo;                  
    double ang;
    double disx;
    double disy;

    double beta;                 

    double cp;                   

    int camN;                    

    StateCam[][] s;
    RedCaminante c;
    double[] disNr;
    double[] Pbestime;

    long visitas;                  
    int a;                         
    int b;

    int camtempx;                
    int camtempy;

    int indicex;                 
    int indicey;

    double radio;
    double Nr;

    Random r = new Random();

    public Walk(int camN, double cp, int a, int b, int t, double gamma, double q, double rho, StateCam[][] s, int indicex, int indicey, double radio, double alfa, double xm, double beta) {

        this.camN = camN;

        this.a = a;
        this.b = b;
        this.t = t;
        this.s = s;

        this.gamma = gamma;
        this.q = q;
        this.rho = rho;

        this.alfa = alfa;
        this.xm = xm;

        this.beta = beta;

        this.cp = cp;

        this.indicex = indicex;
        this.indicey = indicey;

        this.radio = radio;

        c = new RedCaminante(camN, cp);
        c.creaRedAleatoria(t, q, rho, a, b);

        disNr = new double[1000];
        Pbestime = new double[1000];

        reinicia();

    }

    public void step(int tempo, int cj) { 

        int bandera = 0;                    
        double g = 0;                       

        if (s[c.nodos[cj].x - a][c.nodos[cj].y - a].target == 1) {
            bandera = 1;
            visitas++;
            g = s[c.nodos[cj].x - a][c.nodos[cj].y - a].gamma;
        }

        if (bandera == 1) {
            double rnd = r.nextDouble();
            if (rnd < g) {
                c.nodos[cj].x = c.nodos[cj].x;
                c.nodos[cj].y = c.nodos[cj].y;
                c.nodos[cj].pos[tempo + 1] = new Posicion(c.nodos[cj].x, c.nodos[cj].y);

            } else {
                rnd = r.nextDouble();
                if (rnd > c.nodos[cj].q) {

//                    rnd = r.nextDouble();                   //n.n dynamics
//                    if (rnd < 0.5) {
//                        c.nodos[cj].x = rnd < 0.25 ? c.nodos[cj].x + 1 : c.nodos[cj].x - 1;
//                    } else {
//                        c.nodos[cj].y = rnd > 0.75 ? c.nodos[cj].y + 1 : c.nodos[cj].y - 1;
//                    }

                    vuelo = generadorlevyent(alfa, xm);       //LF dynamics
                    ang = r.nextDouble() * 2 * Math.PI;
                    disx = vuelo * Math.cos(ang);
                    disy = vuelo * Math.sin(ang);
                    c.nodos[cj].x = c.nodos[cj].x + (int) disx;
                    c.nodos[cj].y = c.nodos[cj].y + (int) disy;

                    camtempx = c.nodos[cj].pos[tempo].x;
                    camtempy = c.nodos[cj].pos[tempo].y;

                    while (c.nodos[cj].x > b || c.nodos[cj].x < a || c.nodos[cj].y > b || c.nodos[cj].y < a) {     //boundary

//                        rnd = r.nextDouble();                                       //n.n
//                        if (c.nodos[cj].x > b || c.nodos[cj].x < a) {
//                            c.nodos[cj].x = rnd < 0.5 ? camtempx + 1 : camtempx - 1;
//
//                       }
//                        if (c.nodos[cj].y > b || c.nodos[cj].y < a) {
//                            c.nodos[cj].y = rnd > 0.5 ? camtempy + 1 : camtempy - 1;
//                        }

                        vuelo = generadorlevyent(alfa, xm);       //LF
                        ang = r.nextDouble() * 2 * Math.PI;
                        disx = vuelo * Math.cos(ang);
                        disy = vuelo * Math.sin(ang);
                        c.nodos[cj].x = camtempx + (int) disx;
                        c.nodos[cj].y = camtempy + (int) disy;
                    }

                    c.nodos[cj].pos[tempo + 1] = new Posicion(c.nodos[cj].x, c.nodos[cj].y);

                    if (s[c.nodos[cj].x - a][c.nodos[cj].y - a].target == 1 && s[c.nodos[cj].x - a][c.nodos[cj].y - a].gamma >= 0.85 && c.nodos[cj].bandfirst == 0) {

                        c.nodos[cj].bandfirst = 1;
                        c.nodos[cj].ftime = c.nodos[cj].ftime + tempo + 1;
                        c.nodos[cj].success++;

                    }

                } else {
                    rnd = r.nextDouble();
                    if (rnd < c.nodos[cj].rho && c.nodos[cj].vecinos.size() != 0) {

                        int rndpos = (int) (r.nextDouble() * (c.nodos[cj].vecinos.size() - 1));
                        
//                      Infinite memory
                        tpr = (int) (r.nextDouble() * (tempo)); 
                        c.nodos[cj].x = c.nodos[cj].vecinos.get(rndpos).pos[tpr].x;
                        c.nodos[cj].y = c.nodos[cj].vecinos.get(rndpos).pos[tpr].y;

//                        Memory decay
//                        tpr = tempo == 0 ? 1 : (int) (generadorpowerlaw(beta, 1, tempo));
//                        c.nodos[cj].x = c.nodos[cj].vecinos.get(rndpos).pos[tempo - tpr + 1].x;
//                        c.nodos[cj].y = c.nodos[cj].vecinos.get(rndpos).pos[tempo - tpr + 1].y;


                        c.nodos[cj].pos[tempo + 1] = new Posicion(c.nodos[cj].x, c.nodos[cj].y);

                        if (s[c.nodos[cj].x - a][c.nodos[cj].y - a].target == 1 && s[c.nodos[cj].x - a][c.nodos[cj].y - a].gamma >= 0.85 && c.nodos[cj].bandfirst == 0) {

                            c.nodos[cj].bandfirst = 1;
                            c.nodos[cj].ftime = c.nodos[cj].ftime + tempo + 1;
                            c.nodos[cj].vecinos.get(rndpos).ninflun++;
                            c.nodos[cj].success++;

                        }

                    } else {
                      
//                      Infinite memory
                        rnd = r.nextDouble(); 
                        tpr = (int) (rnd * (tempo));
                        c.nodos[cj].x = c.nodos[cj].pos[tpr].x;
                        c.nodos[cj].y = c.nodos[cj].pos[tpr].y;

//                        Memory decay
//                        tpr = tempo == 0 ? 1 : (int) (generadorpowerlaw(beta, 1, tempo)); //decaimiento de memoria como ley de potencia
//                        c.nodos[cj].x = c.nodos[cj].pos[tempo - tpr + 1].x; //se actualiza la posici\'on del caminate (posici\'on ya visitada)
//                        c.nodos[cj].y = c.nodos[cj].pos[tempo - tpr + 1].y;


                        c.nodos[cj].pos[tempo + 1] = new Posicion(c.nodos[cj].x, c.nodos[cj].y);
                    }
                }
            }
        } else {
            double rnd = r.nextDouble();
            if (rnd > c.nodos[cj].q) {
//                rnd = r.nextDouble();        //n.n
//                if (rnd < 0.5) {
//                    c.nodos[cj].x = rnd < 0.25 ? c.nodos[cj].x + 1 : c.nodos[cj].x - 1;
//                } else {
//                    c.nodos[cj].y = rnd > 0.75 ? c.nodos[cj].y + 1 : c.nodos[cj].y - 1;
//                }

                vuelo = generadorlevyent(alfa, xm);       //LF
                ang = r.nextDouble() * 2 * Math.PI;
                disx = vuelo * Math.cos(ang);
                disy = vuelo * Math.sin(ang);

                c.nodos[cj].x = c.nodos[cj].x + (int) disx;
                c.nodos[cj].y = c.nodos[cj].y + (int) disy;

                camtempx = c.nodos[cj].pos[tempo].x;
                camtempy = c.nodos[cj].pos[tempo].y;

                while (c.nodos[cj].x > b || c.nodos[cj].x < a || c.nodos[cj].y > b || c.nodos[cj].y < a) {


//                    rnd = r.nextDouble();                                       //n.n
//                    if (c.nodos[cj].x > b || c.nodos[cj].x < a) {
//                        c.nodos[cj].x = rnd < 0.5 ? camtempx + 1 : camtempx - 1;
//
//                    }
//                    if (c.nodos[cj].y > b || c.nodos[cj].y < a) {
//                        c.nodos[cj].y = rnd > 0.5 ? camtempy + 1 : camtempy - 1;
//                    }

                    vuelo = generadorlevyent(alfa, xm);       //LF
                    ang = r.nextDouble() * 2 * Math.PI;
                    disx = vuelo * Math.cos(ang);
                    disy = vuelo * Math.sin(ang);
                    c.nodos[cj].x = camtempx + (int) disx;
                    c.nodos[cj].y = camtempy + (int) disy;
                }

                c.nodos[cj].pos[tempo + 1] = new Posicion(c.nodos[cj].x, c.nodos[cj].y);

                if (s[c.nodos[cj].x - a][c.nodos[cj].y - a].target == 1 && s[c.nodos[cj].x - a][c.nodos[cj].y - a].gamma >= 0.85 && c.nodos[cj].bandfirst == 0) {

                    c.nodos[cj].bandfirst = 1;
                    c.nodos[cj].ftime = c.nodos[cj].ftime + tempo + 1;
                    c.nodos[cj].success++;

                }

            } else {
                rnd = r.nextDouble();
                if (rnd < c.nodos[cj].rho && c.nodos[cj].vecinos.size() != 0) {

                    int rndpos = (int) (r.nextDouble() * (c.nodos[cj].vecinos.size() - 1));

//                  Infinite memory
                    tpr = (int) (r.nextDouble() * (tempo));  
                    c.nodos[cj].x = c.nodos[cj].vecinos.get(rndpos).pos[tpr].x;
                    c.nodos[cj].y = c.nodos[cj].vecinos.get(rndpos).pos[tpr].y;

//                  Memory decay                    
//                    tpr = tempo == 0 ? 1 : (int) (generadorpowerlaw(beta, 1, tempo)); 
//                    c.nodos[cj].x = c.nodos[cj].vecinos.get(rndpos).pos[tempo - tpr + 1].x;
//                    c.nodos[cj].y = c.nodos[cj].vecinos.get(rndpos).pos[tempo - tpr + 1].y;

                    
                    c.nodos[cj].pos[tempo + 1] = new Posicion(c.nodos[cj].x, c.nodos[cj].y);

                    if (s[c.nodos[cj].x - a][c.nodos[cj].y - a].target == 1 && s[c.nodos[cj].x - a][c.nodos[cj].y - a].gamma >= 0.85 && c.nodos[cj].bandfirst == 0) {

                        c.nodos[cj].bandfirst = 1;
                        c.nodos[cj].ftime = c.nodos[cj].ftime + tempo + 1;
                        c.nodos[cj].vecinos.get(rndpos).ninflun++;
                        c.nodos[cj].success++;

                    }

                } else {
                    rnd = r.nextDouble(); 
//                    //Infiite memory
                    tpr = (int) (rnd * (tempo));    
                    c.nodos[cj].x = c.nodos[cj].pos[tpr].x; 
                    c.nodos[cj].y = c.nodos[cj].pos[tpr].y;

                    //Memory decay
//                    tpr = tempo == 0 ? 1 : (int) (generadorpowerlaw(beta, 1, tempo));
//                    c.nodos[cj].x = c.nodos[cj].pos[tempo - tpr + 1].x; 
//                    c.nodos[cj].y = c.nodos[cj].pos[tempo - tpr + 1].y;

                    c.nodos[cj].pos[tempo + 1] = new Posicion(c.nodos[cj].x, c.nodos[cj].y);
                }
            }
        }

    }

    public void inicia() {

        for (int m = 0; m < c.nodos.length; m++) {
            c.nodos[m].pos[0] = new Posicion(c.nodos[m].x, c.nodos[m].y);
        }

        int cont = 0;

        for (int l = 0; l < t; l++) {
            Nr = 0;
            for (int j = 0; j < c.nodos.length; j++) {
                step(l, j);
                if (c.nodos[j].pos[l + 1].x == s[indicex][indicey].px && c.nodos[j].pos[l + 1].y == s[indicex][indicey].py) {
                    s[indicex][indicey].vist++;
                }
                if (l == t - 1) {               
                    s[c.nodos[j].pos[l + 1].x - a][c.nodos[j].pos[l + 1].y - a].vis++;
                }

            }

            if (l % 100 == 0) {
                for (int i = 0; i < c.nodos.length; i++) {
                    for (int m = 0; m < c.nodos.length; m++) {
                        double disx = (double) (Math.pow(c.nodos[i].pos[l + 1].x - c.nodos[m].pos[l + 1].x, 2));
                        double disy = (double) (Math.pow(c.nodos[i].pos[l + 1].y - c.nodos[m].pos[l + 1].y, 2));
                        double dis1 = Math.sqrt(disx + disy);
                        if (dis1 < radio && i != m) {
                            Nr++;
                        }
                    }
                }
                disNr[cont] = disNr[cont] + (Nr / camN);
                Pbestime[cont] = Pbestime[cont] + (s[indicex][indicey].vist / camN);
                cont++;
            }

            s[indicex][indicey].vist = 0;
        }

    }

    public void reinicia() {
        visitas = 0;

        for (int i = 0; i < c.nodos.length; i++) {
            int rx = (int) ((r.nextDouble() * (b - a)) + a);
            int ry = (int) ((r.nextDouble() * (b - a)) + a);
            c.nodos[i].x = rx;
            c.nodos[i].y = ry;
            c.nodos[i].pos = new Posicion[t + 1];
            c.nodos[i].bandfirst = 0;
        }

    }

    public double generadorpowerlaw(double a, double xm, double xM) {
        Random r = new Random();
        double y = r.nextDouble();
        double exp = 1 - a;
        double xmprim = Math.pow(xm, exp);
        double xMprim = Math.pow(xM, exp);
        double yprim = xmprim - y * (xmprim - xMprim);
        double aprim = 1 / (1 - a);
        double x = (Math.pow(yprim, aprim));
        return x;
    }

    public double generadorlevyent(double a, double xm) {
        Random r = new Random();
        double y = r.nextDouble();
        double yprim = 1 - y;
        double aprim = 1 / (1 - a);
        double x = (xm - 1 / 2) * Math.pow(yprim, aprim) + 1 / 2;
        return x;
    }

}
