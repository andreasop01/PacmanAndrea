package com.example.practica7andreallovera;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPlay=findViewById(R.id.btnPlay);
        animarBoton(btnPlay);

        ImageView imageViewGreen=findViewById(R.id.imageViewRojo);
        imageViewGreen.startAnimation(AnimationUtils.loadAnimation(this,R.anim.bloque_rota));

        ImageView imageViewBlue=findViewById(R.id.imageViewAmarillo);
        imageViewBlue.startAnimation(AnimationUtils.loadAnimation(this,R.anim.bloque_rota));

        ImageView imageViewPurple=findViewById(R.id.imageViewRojo2);
        imageViewPurple.startAnimation(AnimationUtils.loadAnimation(this,R.anim.bloque_rota));

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itJugar=new Intent(getApplicationContext(),JuegoActivity.class); //si lo hago dentro de un boton pongo getaplication context
                startActivity(itJugar);
            }
        });
    }

   public void animarBoton(Button botonJuego){
        AnimatorSet animadorBoton = new AnimatorSet();
        //1ª animación, trasladar desde la izquierda (800 pixeles menos hasta la posición
        //inicial (0)
        ObjectAnimator trasladar=ObjectAnimator.ofFloat(botonJuego,"translationX",-800,0);
        trasladar.setDuration(5000); //duración 5 segundos
        ObjectAnimator trasladarY=ObjectAnimator.ofFloat(botonJuego,"translationY",-800,0);
        trasladarY.setDuration(5000); //duración 5 segundos

        //2ª Animación fade in de 8 segundos
        ObjectAnimator fade = ObjectAnimator.ofFloat(botonJuego, "alpha", 0f, 1f);
        fade.setDuration(8000);
        //se visualizan las dos animaciones a la vez
        animadorBoton.play(trasladar).with(fade).with(trasladarY);
        //comenzar animación
        animadorBoton.start();
    }

}