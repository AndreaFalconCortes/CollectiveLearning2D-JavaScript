/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redcaminante2d;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 *
 * @author andrea
 */
public class RedCaminante2D {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int a = -100;     //space delimiters
        int b = 100;

        double d = 0.0025;    //target density
        double gamma = 0.9;   //gamma_max

        int t = 100000;       //simulation steps
        int n = 1;            // number of different simulations

        StateCam[][] s;       //space
        s = new StateCam[b - a + 1][b - a + 1];

        Random rand = new Random();


        for (int esi = 0; esi <= 0; esi++) {    //this for runs over different space configurations

            int indicex = 0;
            int indicey = 0;
            int cantar = 0;

            for (int i = 0; i < s.length; i++) {     //fill the space
                for (int j = 0; j < s.length; j++) {
                    double rnd = rand.nextDouble();
                    if (rnd < d) {
                        cantar++;
                        double rndg = rand.nextDouble() * gamma;
                        s[i][j] = new StateCam(i + a, j + a, 1, rndg, 0.0);
                        if (s[i][j].gamma > s[indicex][indicey].gamma) {
                            indicex = i;
                            indicey = j;
                        } else {
                            indicex = indicex;
                            indicey = indicey;
                        }
                    } else {
                        s[i][j] = new StateCam(i + a, j + a, 0, 0.0, 0.0);
                    }
                }
            }

            PosGamTar postar = new PosGamTar();
            postar.posgamtar("TargetPos_2D.txt", s);
            try {
                PrintWriter pw = new PrintWriter(new FileWriter("Test.txt"));
            for (int h = 0; h < pc.length; h++) {

                double sc = 1.0;        //erdos-renyi index

                double rho = pc[h];    //communicaton rate
                double q = 0.25;     //memory rate

                double alfa = 1.5;   //Levy index
                double xm = 1.0;

                double beta = 1.00001;    //memory decay exponent

                double radio = 7.0;       //ratio of the circuference centered on each walker

                int camN = 200;           //number of walkers

                int tau = 0;            //tau

                Walk w = new Walk(camN, sc, a, b, t, gamma, q, rho, s, indicex, indicey, radio, alfa, xm, beta);     //dynamic

                for (int j = 1; j <= n; j++) {

                    w.inicia();

                    w.reinicia();
                }
                

                for (int k = 0; k < w.disNr.length; k++) {
                    w.disNr[k] = w.disNr[k] / n;
                    w.Pbestime[k] = w.Pbestime[k] / n;
                }

                for (int l = 0; l < w.disNr.length - 1; l++) {
                    if (w.disNr[l] - w.disNr[w.disNr.length - 1] / 2 < 0.1) {
                        tau = l * 100;
                    }
                }

                int idx = indicex + a;
                int idy = indicey + a;

                System.out.println("esp" + esi + " " + "rho " + rho + ", s " + sc + ", q " + q + ", posicion max " + idx + "," + idy + ", gamma " + s[indicex][indicey].gamma + ", P0 " + w.s[indicex][indicey].vis / (((long) (1)) * n * camN) + ", tau " + tau + " max_nr " + w.disNr[w.disNr.length - 1]);
                
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
