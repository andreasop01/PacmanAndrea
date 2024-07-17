package com.example.practica7andreallovera.dto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.example.practica7andreallovera.BucleJuego;
import com.example.practica7andreallovera.Juego;
import com.example.practica7andreallovera.R;

public class Pacman {
    /*
    * inicializar
    * mover
    * pintar
    * */
    private Tablero tablero;
    private Juego juego;

    public int coord_x,coord_y;
    Bitmap bitmapPacman [];
    public Bitmap cerrado;
    public int estado=0;
    public final int DERECHA=0;
    public final int ABAJO=1;
    public final int IZQUIERDA=2;
    public final int ARRIBA=3;


    public Pacman (Juego juego) {
        this.juego = juego;
        inicializar();
    }

    private void inicializar() {
        tablero=new Tablero(juego);

        //inicializar para darle movimiento

        coord_x=1;
        coord_y=6;

        bitmapPacman=new Bitmap[4]; //TODO:colocar pacman cerrado
        bitmapPacman[DERECHA]= BitmapFactory.decodeResource(juego.getResources(), R.drawable.pacman1);
        bitmapPacman[ABAJO]=BitmapFactory.decodeResource(juego.getResources(),R.drawable.pacman2);
        bitmapPacman[IZQUIERDA]=BitmapFactory.decodeResource(juego.getResources(),R.drawable.pacman3);
        bitmapPacman[ARRIBA]=BitmapFactory.decodeResource(juego.getResources(),R.drawable.pacman4);

        //lo he cambiado a moverse por segundos
        //deltaX = juego.maxX / vx / BucleJuego.MAX_FPS;
        //deltaY = juego.maxY / vy / BucleJuego.MAX_FPS;

       /* x = 1;
        y = 1;*/

    }

    public void pintar(Canvas c) {

        c.drawBitmap(bitmapPacman[estado],
                new Rect(0,0, bitmapPacman[estado].getWidth(),bitmapPacman[estado].getHeight()),
                new RectF((coord_x*(juego.maxX/15)), (coord_y*(juego.maxX)/15),
                        (juego.maxX/15+ coord_x*(juego.maxX/15)),(juego.maxX/15+(coord_y*(juego.maxX)/15))),null);

        Log.d("serbalced", "tablero  x: " + coord_x + ", tablero y: " + coord_y+" mapax : "+(coord_x*(juego.maxX/15)+", mapay:"+coord_y*(juego.maxY)/19));
    }

    public void mover() {

        if(estado==IZQUIERDA)
            if(tablero.matriz[coord_y][coord_x-1]!=0)
                coord_x-=1;

        if(estado==DERECHA)
            if(tablero.matriz[coord_y][coord_x+1]!=0)
                coord_x+=1;

        if(estado==ARRIBA)
            if(tablero.matriz[coord_y-1][coord_x]!=0)
                coord_y-=1;

        if(estado==ABAJO)
            if(tablero.matriz[coord_y+1][coord_x]!=0){
                coord_y+=1;
            }
    }

    public Bitmap getBitmapActual() {
        return bitmapPacman[estado];
    }
}
