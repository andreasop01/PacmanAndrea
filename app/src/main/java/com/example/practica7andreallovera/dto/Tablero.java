package com.example.practica7andreallovera.dto;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.practica7andreallovera.Juego;

public class Tablero {
    private Juego juego;
    public float TAMAÑOBLOQ;//tamaño pixel
    private static final int TAMAÑOPUNTO=30;
    //private static final int FILAS=15,COLUM=15;
    public int matriz[][];


    public Tablero (Juego juego) {
        this.juego = juego;
        inicializarTablero();
    }
    public void inicializarTablero(){

        TAMAÑOBLOQ=juego.maxX/15;

        //MATRIZ PRUEBA
         /*matriz=new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 16, 0, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 16, 0, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 16, 0, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 16, 0, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 16, 16, 16, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };*/

       matriz= new int[][] {
               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
               {0, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0},
               {0, 16, 0, 0, 16, 0, 0, 16, 0, 0, 16, 0, 0, 16, 0},
               {0, 16, 0, 0, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0},
               {0, 16, 16, 16, 16, 0, 0, 0, 16, 0, 0, 0, 16, 16, 0},
               {0, 16, 0, 0, 0, 0, 16, 0, 16, 0, 16, 0, 0, 16, 0},
               {0, 16, 16, 16, 16, 16, 16, 0, 16, 16, 16, 16, 16, 16, 0},
               {0, 0, 0, 0, 0, 0, 16, 0, 0, 0, 16, 0, 0, 0, 0},
               {0, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0},
               {0, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 0},
               {0, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0},
               {0, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 0},
               {0, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0},
               {0, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0},
               {0, 0, 16, 0, 16, 0, 0, 16, 0, 0, 0, 16, 0, 0, 0},
               {0, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0},
               {0, 16, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 0, 16, 0},
               {0, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0},
               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
       };
    }


    public void pintarTablero(Canvas c, Paint p){
        int x=0, y=0;

        for(int f=0; f<matriz.length;f++){
            x = 0;
            for(int co=0;co<matriz[f].length;co++){

                if(matriz[f][co]==0){
                    p.setColor(Color.BLUE);
                    c.drawRect(x,y,x+TAMAÑOBLOQ,y+TAMAÑOBLOQ,p);
                }

                if(matriz[f][co]==16){
                    p.setColor(Color.YELLOW);
                    c.drawOval((x+TAMAÑOBLOQ/2)-TAMAÑOPUNTO/2,(y+TAMAÑOBLOQ/2)-TAMAÑOPUNTO/2,
                            (x+TAMAÑOBLOQ/2)+TAMAÑOPUNTO/2,(y+TAMAÑOBLOQ/2)+TAMAÑOPUNTO/2,p);
                }
                x +=TAMAÑOBLOQ;

            }
            y += TAMAÑOBLOQ;
        }

        /*c.drawRect(x,y,x+TAMAÑOBLOQ,y+TAMAÑOBLOQ,p);
        x+=200;
        c.drawRect(x,y,x+TAMAÑOBLOQ,y+TAMAÑOBLOQ,p);*/
    }



}
