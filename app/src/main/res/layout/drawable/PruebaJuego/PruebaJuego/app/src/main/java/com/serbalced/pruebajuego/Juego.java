package com.serbalced.pruebajuego;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class Juego extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    private SurfaceHolder holder;
    public BucleJuego bucle;
    private static final String TAG = Juego.class.getSimpleName();
    public float maxX;
    public float maxY;
    public Robot robot;
    public Bicho bicho;
    public boolean cazado = false;
    public Bitmap explosion;
    public Explosion boom;
    public Activity miContexto;

    public Juego(Activity context) {
        super(context);
        miContexto = context;
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // se crea la superficie, creamos el game loop

        // Para interceptar los eventos de la SurfaceView
        getHolder().addCallback(this);

        Canvas c = getHolder().lockCanvas();
        maxX = c.getWidth();
        maxY = c.getHeight();
        getHolder().unlockCanvasAndPost(c);

        inicializar();

        // creamos el game loop
        bucle = new BucleJuego(getHolder(), this);

        // Hacer la Vista focusable para que pueda capturar eventos
        setFocusable(true);

        //comenzar el bucle
        bucle.start();

        setOnTouchListener(this);
    }

    /**
     * Este método actualiza el estado del juego. Contiene la lógica del videojuego
     * generando los nuevos estados y dejando listo el sistema para un repintado.
     */
    public void actualizar() {
        if (!robot.cazado(bicho.getBitmapActual() ,bicho.x, bicho.y)) {
            robot.mover();
            bicho.mover();
        } else {
            cazado = true;
        }

        if (cazado && boom == null) {
            // CAMBIAR COORDENADAS
            boom = new Explosion(this, robot.x, robot.y);
        }

        if (boom != null) {
            boom.actualizarEstado();

            if (boom.haTerminado()) {
                finJuego();
            }
        }
    }

    /**
     * Este método dibuja el siguiente paso de la animación correspondiente
     */
    public void renderizar(Canvas canvas) {

        canvas.drawColor(Color.BLACK);

        //pintar mensajes que nos ayudan
        Paint p=new Paint();
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(Color.RED);
        p.setTextSize(50);
        canvas.drawText("Frame "+bucle.iteraciones+";"+"Tiempo "+bucle.tiempoTotal + " ["+bucle.maxX+","+bucle.maxY+"]",50,150,p);
        canvas.drawText("Velocidad de robot: (" + robot.vx + ", " + robot.vy + ")", 50, 200, p);

        if (!cazado)
            robot.pintar(canvas);

        bicho.pintar(canvas);

        if (cazado) {
            canvas.drawText("CAZADO! FIN DE PARTIDA", 50, 250, p);
        }

        if (boom != null) {
            boom.dibujar(canvas);
        }
    }

    public void finJuego() {
        bucle.JuegoEnEjecucion = false;
        miContexto.finish();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Juego destruido!");
        // cerrar el thread y esperar que acabe
        boolean retry = true;
        while (retry) {
            try {
                bucle.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }

    public void inicializar() {
        robot = new Robot(this);
        bicho = new Bicho(this);

        explosion = BitmapFactory.decodeResource(getResources(), R.drawable.explosion);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO: cambiar el estado del bicho dependiendo de las coordenadas
        float x, y;

        x = event.getX();
        y = event.getY();

        if (x >= 0 && x < maxX / 2) {
            if (y >= 0 && y < maxY / 2) {
                // esquina superior izquierda -> ARRIBA
                bicho.estado = 0;
            } else {
                // esquina inferior izquierda -> IZQUIERDA
                bicho.estado = 3;
            }
        } else {
            if (y >= 0 && y < maxY / 2) {
                // esquina superior derecha -> DERECHA
                bicho.estado = 2;
            } else  {
                // esquina inferior derecha -> ABAJO
                bicho.estado = 1;
            }
        }

        return false;
    }
}
