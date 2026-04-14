package com.example.practica_3;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;


import javafx.geometry.Insets;


public class HelloApplication extends Application {
    @Override
    public void start(Stage ventana) throws IOException {
        GridPane cuadricula = new GridPane();

        cuadricula.setPadding(new Insets(20));
        cuadricula.setVgap(5);
        cuadricula.setHgap(5);

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                int valor = (i * 3) + j + 1;

                FichaNumero ficha = new FichaNumero(valor);

                cuadricula.add(ficha, j, i);

            }
        }

        Pane lienzo = new Pane();
        lienzo.getChildren().add(cuadricula);

        Scene escena = new Scene(lienzo, 800, 600);

        ventana.setTitle("Práctica 3");
        ventana.setScene(escena);
        ventana.show();
    }

}
