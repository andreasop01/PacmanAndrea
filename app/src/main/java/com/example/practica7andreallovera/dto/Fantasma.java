package com.example.practica7andreallovera.dto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.practica7andreallovera.BucleJuego;
import com.example.practica7andreallovera.Juego;
import com.example.practica7andreallovera.R;

public class Fantasma {
    private Juego juego;

    public int coord_fx,coord_fy;
    private Bitmap bitmaFantasma;
   private int estado=0;
   private final int DERECHAF=0,ABAJOF=1,IZQUIERDAF=2,ARRIBAF=3;


    public Fantasma (Juego juego) {
        this.juego = juego;
        inicializarFantasma();
    }


    private void inicializarFantasma(){

        //posiciones donde sale el fantasma
        if(Math.random()<0.25){
            coord_fx=1;
            coord_fy=1;
        }else if(Math.random()<0.25){
            coord_fx=13;
            coord_fy=17;
        }else if(Math.random()<0.25){
            coord_fx=13;
            coord_fy=10;
        }else{
            coord_fx=1;
            coord_fy=10;
        }

        bitmaFantasma= BitmapFactory.decodeResource(juego.getResources(), R.drawable.rojo);

    }

    public void pintarFantasma(Canvas c) {

        c.drawBitmap(bitmaFantasma,
                new Rect(0,0, bitmaFantasma.getWidth(),bitmaFantasma.getHeight()),
                new RectF((coord_fx*(juego.maxX/15)), (coord_fy*(juego.maxX)/15),
                        (juego.maxX/15+ coord_fx*(juego.maxX/15)),(juego.maxX/15+(coord_fy*(juego.maxX)/15))),null);

    }

    public void moverFantasma() {

        //20% de probalidad que cambien de posi ale
        if(Math.random()<0.50){
            estado =(int)(Math.random()*4);
        }

        if(estado==IZQUIERDAF)
            if(juego.tablero.matriz[coord_fy][coord_fx-1]!=0)
                coord_fx-=1;

        if(estado==DERECHAF)
            if(juego.tablero.matriz[coord_fy][coord_fx+1]!=0)
                coord_fx+=1;

        if(estado==ARRIBAF)
            if(juego.tablero.matriz[coord_fy-1][coord_fx]!=0)
                coord_fy-=1;

        if(estado==ABAJOF)
            if(juego.tablero.matriz[coord_fy+1][coord_fx]!=0)
                coord_fy+=1;



        //colisiones con las paredes

        //derecha
        if (juego.tablero.matriz[coord_fy][coord_fx+1]==0 && estado==DERECHAF) {
            estado =(int)(Math.random()*4);
        }
        //izq
        if (juego.tablero.matriz[coord_fy][coord_fx-1]==0 && estado==IZQUIERDAF) {
            estado =(int)(Math.random()*4);
        }

        //arriba
        if (juego.tablero.matriz[coord_fy-1][coord_fx]==0 && estado==ARRIBAF) {
            estado =(int)(Math.random()*4);
        }
        //abajo
        if (juego.tablero.matriz[coord_fy+1][coord_fx]==0 && estado==ABAJOF) {
            estado =(int)(Math.random()*4);

        }
    }


}
