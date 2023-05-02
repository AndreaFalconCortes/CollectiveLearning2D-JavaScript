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
public class RedCaminante {

    int camN;
    Caminante[] nodos;
    Random r;
    Random rq;
    Random rrho;
    double s;

    public RedCaminante(int camN, double s) {
        this.camN = camN;
        this.s = s;
        nodos = new Caminante[camN];
        r = new Random();
        rq = new Random();
        rrho = new Random();
    }

    public void creaRedAleatoria(int t, double q, double rho, int a, int b) {

        for (int i = 0; i < nodos.length; i++) {
            int rx = (int) ((r.nextDouble() * (b-a)) + a);
            int ry = (int) ((r.nextDouble() * (b-a)) + a);
            nodos[i] = new Caminante(i, rx, ry, q, rho, 0 ,t);
        }

        for (int i = 0; i < nodos.length; i++) {
            Caminante c = nodos[i];
            ArrayList<Caminante> vecinos = c.vecinos;

            for (int j = i + 1; j < nodos.length; j++) {
                double al = r.nextDouble();

                if (al < s) {
                    vecinos.add(nodos[j]);
                    nodos[j].vecinos.add(c);
                }
            }
        }

    }

    public void printRed() {
        for (Caminante c : nodos) {
            System.out.print(c.id + " - [");

            for (Caminante w : c.vecinos) {
                System.out.print(w.id + ",");
            }
            System.out.println("] ,  " + c.x + " , " + c.y + " , " + c.q + " , " + c.rho);
        }
    }

    public double gradoPromedio() {

        double prom = 0;

        for (Caminante c : nodos) {
            prom += c.vecinos.size();
        }

        return prom / nodos.length;

    }

}
