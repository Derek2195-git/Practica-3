package com.example.practica_3;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.io.IOException;

public class SudokuApplication extends Application {

    // Solo conservamos la matriz para dibujar

    public void start(Stage ventana) throws IOException {

        TableroSudoku tableroLogico = new TableroSudoku();
        tableroLogico.inicializarTablero();

        // INSTANCIAMOS EL CONTROLADOR
        SudokuController controlador = new SudokuController(tableroLogico);

        Label labelTitulo = new Label("¡Sudoku!");
        labelTitulo.getStyleClass().add("label");

        GridPane cuadricula = new GridPane();
        cuadricula.setPadding(new Insets(20, 20, 20, 20));
        cuadricula.setVgap(0);
        cuadricula.setHgap(0);
        cuadricula.setAlignment(Pos.CENTER);

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                int valor = tableroLogico.getValor(i, j);
                boolean esFija = tableroLogico.getCasillaFija(i, j);

                FichaNumero ficha = new FichaNumero(valor);


                final int filaActual = i;
                final int columnaActual = j;

                double top = 1, right = 1, bottom = 1, left = 1;
                if (j == 2 || j == 5) right = 3;
                if (i == 2 || i == 5) bottom = 3;

                if (esFija) {
                    ficha.getStyleClass().add("ficha-fija");
                } else {
                    ficha.getStyleClass().add("ficha-normal");
                }
                String estiloBordes = String.format("-fx-border-width: %.0f %.0f %.0f %.0f; ", top, right, bottom, left);
                ficha.setStyle(estiloBordes);

                ficha.setOnMouseClicked(evento -> {
                    if (evento.getClickCount() == 1) {
                        controlador.seleccionarFicha(ficha, filaActual, columnaActual);
                    }
                });

                cuadricula.add(ficha, j, i);
            }
        }

        GridPane panelBotones = new GridPane();
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setHgap(10);
        panelBotones.setVgap(10);

        int numeroBoton = 1;
        int factor = 2;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button botonNumero = new Button(String.valueOf(numeroBoton));
                botonNumero.getStyleClass().add("boton-teclado");

                int numeroAIngresar = numeroBoton;
                // DELEGAMOS EL CLIC DEL TECLADO NUMÉRICO AL CONTROLADOR
                botonNumero.setOnMouseClicked(evento -> {
                    controlador.procesarEntradaNumero(numeroAIngresar);
                });

                panelBotones.add(botonNumero, i, j);
                numeroBoton += 3;
            }
            numeroBoton = 1 * factor;
            factor++;
        }

        Button botonBorrar = new Button("Borrar");
        botonBorrar.getStyleClass().add("boton-teclado");
        // MANDAMOS UN 0 AL CONTROLADOR PARA BORRAR
        botonBorrar.setOnMouseClicked(evento -> {
            controlador.procesarEntradaNumero(0);
        });
        panelBotones.add(botonBorrar, 0, 3, 3, 1);

        HBox contenedorCentral = new HBox();
        contenedorCentral.setAlignment(Pos.CENTER);
        contenedorCentral.getChildren().addAll(cuadricula, panelBotones);

        VBox contenedorVertical = new VBox();
        contenedorVertical.setAlignment(Pos.CENTER);
        contenedorVertical.getChildren().addAll(labelTitulo, contenedorCentral);

        Scene escena = new Scene(contenedorVertical, 800, 600);
        escena.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());

        // DELEGAMOS EL TECLADO FÍSICO AL CONTROLADOR
        escena.setOnKeyTyped(evento -> {
            String teclaPulsada = evento.getCharacter();
            if (teclaPulsada.matches("[1-9]")) {
                int numeroTeclado = Integer.parseInt(teclaPulsada);
                controlador.procesarEntradaNumero(numeroTeclado);
            }
        });

        ventana.setTitle("Práctica 3");
        ventana.setScene(escena);
        ventana.show();
    }
}