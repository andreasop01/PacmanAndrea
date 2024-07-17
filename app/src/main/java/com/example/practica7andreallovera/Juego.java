package com.example.practica7andreallovera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.practica7andreallovera.controles.Control;
import com.example.practica7andreallovera.controles.Toque;
import com.example.practica7andreallovera.dto.Fantasma;
import com.example.practica7andreallovera.dto.Pacman;
import com.example.practica7andreallovera.dto.Tablero;

import java.util.ArrayList;

public class Juego extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener {

    public Tablero tablero;
    public Pacman pacman;
    public Fantasma fantasma;
    private SurfaceHolder holder;
    private BucleJuego bucle;

    public Activity miContexto;

    //canvas
    public  float maxX;
    public  float maxY;
    private static final String TAG = Juego.class.getSimpleName();
    //CONTROLES
    public Control controles[]=new Control[4];
    private static final int CDERECHA=0;
    private static final int CABAJO=1;
    private static final int CIZQUIERDA=2;
    private static final int CARRIBA=3;
    private boolean hayToque=false;
    private ArrayList<Toque> toques= new ArrayList<>();

    //fantasmas
    private static final int NFANTASMAS=6;
    public int fantasmas_creados=0;
    private ArrayList<Fantasma> lista_fantasmas=new ArrayList<>();
   public boolean cazado=false;
    public boolean ganar=false;
    private int contadorFrames=0;

    //puntuacion
    public int vida=3;
    public int score=0;
    public Bitmap gameOver, win,heart;

    //musica
    MediaPlayer mp, mediaCome,gameOverJuego,winner;

    public Juego(Activity context) {
        super(context);
        miContexto=context;
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

    public void inicializar() {

        tablero=new Tablero(this);
        pacman = new Pacman(this);
        fantasma=new Fantasma(this);

        //MUSICA
        mp = MediaPlayer.create(miContexto, R.raw.pacmanmusic);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                mp.start();
            }
        });
        mp.start();

        mediaCome = MediaPlayer.create(miContexto, R.raw.pacmandies);
        gameOverJuego=MediaPlayer.create(miContexto,R.raw.gameover);
        winner=MediaPlayer.create(miContexto,R.raw.win);
        //controles colocarlos en el canvas
        //flecha_izda
        Bitmap bm=BitmapFactory.decodeResource(getResources(),R.drawable.flecha_izda);

        controles[CIZQUIERDA]=new Control(getContext(),0, maxY-bm.getHeight());
        controles[CIZQUIERDA].cargar( R.drawable.flecha_izda);
        controles[CIZQUIERDA].nombre="IZQUIERDA";
        //flecha_derecha
        controles[CDERECHA]=new Control(getContext(),
                controles[2].ancho()+5,maxY-controles[2].alto());
        controles[CDERECHA].cargar(R.drawable.flecha_dcha);
        controles[CDERECHA].nombre="DERECHA";
        //up
        controles[CARRIBA]=new Control(getContext(),
                maxX-bm.getWidth()*2-5,maxY-controles[2].alto());
        controles[CARRIBA].cargar(R.drawable.flecha_up);
        controles[CARRIBA].nombre="ARRIBA";
        //down
        controles[CABAJO]=new Control(getContext(),
                maxX-bm.getWidth(),maxY-controles[2].alto());
        controles[CABAJO].cargar(R.drawable.flecha_down);
        controles[CABAJO].nombre="ABAJO";

        crearNuevoFantasmas();
        //gameOver
        gameOver=BitmapFactory.decodeResource(getResources(),R.drawable.game_over);
        //winner
        win=BitmapFactory.decodeResource(getResources(),R.drawable.winner);
        //vida
        heart=BitmapFactory.decodeResource(getResources(),R.drawable.heart);

    }

    /**
     * Este método actualiza el estado del juego. Contiene la lógica del videojuego
     * generando los nuevos estados y dejando listo el sistema para un repintado.
     */
    public void actualizar() {

        contadorFrames++;

        if(cazado ||ganar){
            return;
        }

        //dar funcionabilidad a los botones

        if(controles[CARRIBA].pulsado){
            pacman.estado=3;
        }
        if(controles[CIZQUIERDA].pulsado){
            pacman.estado=2;
        }
        if(controles[CDERECHA].pulsado){
            pacman.estado=0;
        }
        if(controles[CABAJO].pulsado){
            pacman.estado=1;
        }


        //crearNuevoFantasmas();
        //mover fantasma

        if(contadorFrames%10==0){
            for (Fantasma f:lista_fantasmas) {
                f.moverFantasma();
            }
        }

        if(contadorFrames%250==0){
            crearNuevoFantasmas();
        }

        //moverpacman velocidad
        if(contadorFrames%10==0)
            pacman.mover();

        //come puntos
        if(tablero.matriz[pacman.coord_y][pacman.coord_x]==16){
            tablero.matriz[pacman.coord_y][pacman.coord_x]=1;

            score++;
        }


        //colision perder chocar con fantasmas
        for (int i=0;i<lista_fantasmas.size();i++) {

            Fantasma f=lista_fantasmas.get(i);
            if(f.coord_fx==pacman.coord_x && f.coord_fy==pacman.coord_y){
                pacman.coord_x=1;
                pacman.coord_y=6;
                lista_fantasmas.clear();
                fantasmas_creados=0;
                vida--;
                mediaCome.start();
            }
        }

        if(vida==0){
            cazado=true;
            mp.stop();
            gameOverJuego.start();
        }

        //ganar
        ganar =true;
        for(int f=0;f<tablero.matriz.length;f++){
            for(int c=0;c<tablero.matriz[f].length;c++){

                if(tablero.matriz[f][c]==16){
                    ganar=false;
                }
            }
        }

        if(ganar){
            mp.stop();
            winner.start();
        }

    }

    /**
     * Este método dibuja el siguiente paso de la animación correspondiente
     */
    public void renderizar(Canvas canvas) {

        canvas.drawColor(Color.BLACK);

        //pintar mensajes que nos ayudan
        Paint p=new Paint();
        /*p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(Color.RED);
        p.setTextSize(50);
        canvas.drawText("Frame "+bucle.iteraciones+";"+"Tiempo "+bucle.tiempoTotal + " ["+bucle.maxX+","+bucle.maxY+"]",50,150,p);*/
        //tablero
        tablero.pintarTablero(canvas,p);

        //pacman pintado
        pacman.pintar(canvas);

        //pintar fantasma
        for (Fantasma f:lista_fantasmas) {
            f.pintarFantasma(canvas);
        }

        //pintar score
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(Color.RED);
        p.setTextSize(130);
        canvas.drawText(" SCORE: "+score,
                0,21*tablero.TAMAÑOBLOQ,p);

        //controles
        for(int i = 0; i<controles.length;i++)
            controles[i].dibujar(canvas,p);

        //pinto game over
        if(cazado){
            canvas.drawBitmap(gameOver,
                    new Rect(0,0,gameOver.getWidth(),gameOver.getHeight()),
                    new RectF(maxX/2-gameOver.getWidth()/4,maxY/2-gameOver.getHeight()/4,
                            maxX/2+gameOver.getWidth()/4,maxY/2+gameOver.getHeight()/4),p);
        }


        //pintar win
        if(ganar){
            canvas.drawBitmap(win,
                    new Rect(0,0,win.getWidth(),win.getHeight()),
                    new RectF(maxX/2-win.getWidth()/8,maxY/2-win.getHeight()/8,
                            maxX/2+win.getWidth()/8,maxY/2+win.getHeight()/8),p);
        }


        //heart
        for(int i=0;i<vida;i++){
            canvas.drawBitmap(heart,
                    new Rect(0,0,heart.getWidth(),heart.getHeight()),
                    new RectF(maxX/2+heart.getWidth()*2*i,20*tablero.TAMAÑOBLOQ-50,maxX/2+heart.getWidth()*2*(i+1),20*tablero.TAMAÑOBLOQ-50+heart.getHeight()*2),p);
        }

    }


    //fantasmas enemigos
    public void crearNuevoFantasmas(){

        if(NFANTASMAS-fantasmas_creados>0) {
            lista_fantasmas.add(new Fantasma(this));
            fantasmas_creados++;
        }

    }

    //NO TOCAR
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO: cambiar el estado del pacman dependiendo de las coordenadas
        int index;
        int x,y;

        // Obtener el pointer asociado con la acción
        index = event.getActionIndex();


        x = (int) event.getX(index);
        y = (int) event.getY(index);

        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                hayToque=true;

                synchronized(this) {
                    toques.add(index, new Toque(index, x, y));
                }

                //se comprueba si se ha pulsado
                for(int i=0;i<controles.length;i++)
                    controles[i].compruebaPulsado(x,y);
                break;

            case MotionEvent.ACTION_POINTER_UP:
                synchronized(this) {
                    toques.remove(index);
                }

                //se comprueba si se ha soltado el botón
                for(int i=0;i<controles.length;i++)
                    controles[i].compruebaSoltado(toques);
                break;

            case MotionEvent.ACTION_UP:
                synchronized(this) {
                    toques.clear();
                }
                hayToque=false;
                //se comprueba si se ha soltado el botón
                for(int i=0;i<controles.length;i++)
                    controles[i].compruebaSoltado(toques);
                break;
        }

        return true;
    }

    public void finJuego() {
        bucle.JuegoEnEjecucion = false;
        mp.release();
        mediaCome.release();
        miContexto.finish();
    }

}
