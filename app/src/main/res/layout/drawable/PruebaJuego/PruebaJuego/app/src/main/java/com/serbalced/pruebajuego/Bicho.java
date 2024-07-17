package com.serbalced.pruebajuego;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

public class Bicho {
    /* Responsabilidades del bicho
    * moverse x, y
    * velocidad vx, vy, deltaX, deltaY
    * estado = 0 -> arriba
    *          1 -> abajo
    *          2 -> derecha,
    *          3 -> izquierda
    * pintarse en relacion a su estado
    * */
    public float x, y, vx, vy, deltaX, deltaY;
    // vx es el tiempo que tarda el bicho en cruzar horizontalmente la pantalla
    // vy lo mismo pero en vertical
    public Juego juego;
    public Bitmap bitmapBicho[];
    public int estado = 0;

    public final int ARRIBA = 0;
    public final int ABAJO = 1;
    public final int DERECHA = 2;
    public final int IZQUIERDA = 3;
    public boolean rebote = false;

    public Bicho (Juego juego) {
        this.juego = juego;
        inicializar();
    }

    public void inicializar() {
        vx = 3;
        vy = 7;

        bitmapBicho = new Bitmap[4];
        bitmapBicho[ARRIBA] = BitmapFactory.decodeResource(juego.getResources(), R.drawable.bicho0);
        bitmapBicho[ABAJO] = BitmapFactory.decodeResource(juego.getResources(), R.drawable.bicho1);
        bitmapBicho[DERECHA] = BitmapFactory.decodeResource(juego.getResources(), R.drawable.bicho2);
        bitmapBicho[IZQUIERDA] = BitmapFactory.decodeResource(juego.getResources(), R.drawable.bicho3);

        deltaX = juego.maxX / vx / BucleJuego.MAX_FPS;
        deltaY = juego.maxY / vy / BucleJuego.MAX_FPS;

        x = 0;
        y = juego.maxY - bitmapBicho[estado].getHeight();
    }

    public void pintar(Canvas c) {
        c.drawBitmap(bitmapBicho[estado], x, y, null);
        Log.d("serbalced", "pos x: " + x + ", pos y: " + y);
    }

    public void mover() {
        if (estado == ARRIBA) {
            y -= deltaY;
        }

        if (estado == ABAJO) {
            y += deltaY;
        }

        if (estado == DERECHA) {
            x += deltaX;
        }

        if (estado == IZQUIERDA) {
            x -= deltaX;
        }

        if (x >= juego.maxX - bitmapBicho[estado].getWidth()) {
            estado = IZQUIERDA;
        }

        if (y >= juego.maxY - bitmapBicho[estado].getHeight()) {
            estado = ARRIBA;
        }

        if (x < 0) {
            estado = DERECHA;
        }

        if (y < 0) {
            estado = ABAJO;
        }
    }

    public Bitmap getBitmapActual() {
        return bitmapBicho[estado];
    }
}
