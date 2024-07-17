package com.serbalced.pruebajuego;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

public class Robot {
    /* Responsabilidades
    * sabe moverse: va a tener coordenadas x, y
    * conoce su velocidad: va a tener velocidad x, y
    * pintarse: se pinta en coordenadas x, y en cada iteracion
    * detectar si me han cazado
    * */
    public float x, y, vx, vy, deltaX, deltaY;
    public Juego juego;
    public Bitmap bitmapRobot;

    public Robot (Juego juego) {
        this.juego = juego;
        inicializar();
    }

    public void inicializar() {
        x = 0;
        y = 0;

        Random r = new Random();
        vx = r.nextInt(3) + 3;
        vy = r.nextInt(10) + 5;

        deltaX = juego.maxX / vx / BucleJuego.MAX_FPS;
        deltaY = juego.maxY / vy / BucleJuego.MAX_FPS;

        Log.i("serbalced", "Velocidad de robot: (" + vx + ", " + vy + ")");

        bitmapRobot = BitmapFactory.decodeResource(juego.getResources(), R.drawable.robot1);
    }

    public void pintar(Canvas c) {
        c.drawBitmap(bitmapRobot, x, y, null);
    }

    public boolean cazado(Bitmap bicho, float x_bicho, float y_bicho) {
        // PARA DETECTAR COLISION HAY QUE PASARLE LOS BITMAPS Y COORDENADAS DE LOS OBJETOS IMPLICADOS
        // EN NUESTRO CASO: robot, x_robot, y_robot
        //                  bicho, x_bicho, y_bicho
        return Colision.hayColision(bitmapRobot, (int)x, (int)y,
                bicho,(int)x_bicho, (int)y_bicho);

    }

    public void mover() {
        x += deltaX;
        y += deltaY;

        if (x >= juego.maxX - bitmapRobot.getWidth()) {
            deltaX *= -1;
        }

        if (y >= juego.maxY - bitmapRobot.getHeight()) {
            deltaY *= -1;
        }

        if (x <= 0) {
            deltaX *= -1;
        }

        if (y <= 0) {
            deltaY *= -1;
        }
    }
}
