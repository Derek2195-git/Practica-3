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
import java.io.IOException;


import javafx.geometry.Insets;
import javafx.scene.control.Alert;

public class HelloApplication extends Application {
    private FichaNumero fichaActiva = null;
    private int filaActiva = -1;
    private int columnaActiva = -1;
    private FichaNumero[][] matrizJuego;

    public void start(Stage ventana) throws IOException {

        TableroSudoku tableroLogico = new TableroSudoku();
        matrizJuego = new FichaNumero[9][9];
        tableroLogico.inicializarTablero();

        Label labelTitulo = new Label("denme ideas no se me ocurre nada");
        labelTitulo.getStyleClass().add("label");

        GridPane cuadricula = new GridPane();
        cuadricula.setPadding(new Insets(20, 20, 20, 20));
        cuadricula.setVgap(0);
        cuadricula.setHgap(0);
        cuadricula.setAlignment(Pos.CENTER);


        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {

                int valor = tableroLogico.getValor(i, j);
                boolean esFija = tableroLogico.esFija(i, j);


                FichaNumero ficha = new FichaNumero(valor);
                matrizJuego[i][j] = ficha;
                ficha.marcarFichaComoFija(esFija);

                final int filaActual = i;
                final int columnaActual = j;

                // Esto es para los bordes, cada variable para cada parte de un borde
                double top = 1, right = 1, bottom = 1, left = 1;

                // Si es el final de una subcuadrícula horizontal (columna 2 o 5), borde derecho grueso
                if (j == 2 || j == 5) right = 3;
                // Si es el final de una subcuadrícula vertical (fila 2 o 5), borde inferior grueso
                if (i == 2 || i == 5) bottom = 3;

                if (esFija) {
                    ficha.getStyleClass().add("ficha-fija");
                } else {
                    ficha.getStyleClass().add("ficha-normal");
                }
                String estiloBordes = String.format("-fx-border-width: %.0f %.0f %.0f %.0f; ", top, right, bottom, left);


                ficha.setStyle(estiloBordes);

                ficha.setOnMouseClicked(evento -> {
                    // Revisamos si la casilla es fija
                    if(tableroLogico.esFija(filaActual, columnaActual)) return;

                    if (evento.getClickCount() == 1) {
                        if (fichaActiva != null) {
                            fichaActiva.getStyleClass().remove("ficha-activa");
                            fichaActiva.getStyleClass().add("ficha-normal");
                        }

                        fichaActiva = ficha;
                        filaActiva = filaActual;
                        columnaActiva = columnaActual;

                        fichaActiva.getStyleClass().remove("ficha-normal");
                        fichaActiva.getStyleClass().add("ficha-activa");

                        // Le pedimos al teclado que se enfoque en la ficha seleccionada
                        ficha.requestFocus();
                    }
                });

                cuadricula.add(ficha, j, i);

            }
        }

        // Teclado númerico para los botones del sudoku

        GridPane panelBotones = new GridPane();
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setHgap(10);
        panelBotones.setVgap(10);

        int numeroBoton = 1;
        int factor = 2;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button botonNumero = new Button(String.valueOf(numeroBoton));
                // botonNumero.setPrefSize(40, 40);
                botonNumero.getStyleClass().add("boton-teclado");

                int numeroAIngresar = numeroBoton;
                botonNumero.setOnMouseClicked(evento -> {
                    if (fichaActiva != null) {
                        ingresarNumero(tableroLogico, filaActiva, columnaActiva, numeroAIngresar, fichaActiva);
                    }
                });
                panelBotones.add(botonNumero, i, j);
                numeroBoton += 3;
            }
            numeroBoton = 1 * factor;
            factor++;
        }

        Button botonBorrar = new Button("Borrar");
        botonBorrar.getStyleClass().add("boton-teclado");

        botonBorrar.setOnMouseClicked(evento -> {
            if (fichaActiva != null) {
                borrarNumero(tableroLogico, filaActiva, columnaActiva, fichaActiva);
            }
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
        escena.setOnKeyTyped(evento -> {
            if (fichaActiva != null) {
                String teclaPulsada = evento.getCharacter();
                if (teclaPulsada.matches("[1-9]")) {
                    int numeroTeclado = Integer.parseInt(teclaPulsada);
                    ingresarNumero(tableroLogico, filaActiva, columnaActiva, numeroTeclado, fichaActiva);
                }
            }
        });

        ventana.setTitle("Práctica 3");
        ventana.setScene(escena);
        ventana.show();
    }

    private void actualizarGUI(TableroSudoku tablero) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                FichaNumero ficha = matrizJuego[i][j];
                ficha.setValor(tablero.getValor(i, j));
                ficha.marcarFichaComoFija(tablero.esFija(i, j));

                ficha.getStyleClass().removeAll("ficha-fija", "ficha-normal", "ficha-activa");
                if (tablero.esFija(i, j)) ficha.getStyleClass().add("ficha-fija");
                else ficha.getStyleClass().add("ficha-normal");
            }
        }
        fichaActiva = null;
    }

    private void borrarNumero(TableroSudoku tablero, int fila, int columna, FichaNumero ficha) {
        tablero.setValor(fila, columna, 0);
        ficha.setValor(0);
    }

    private void ingresarNumero(TableroSudoku tablero, int fila, int columna, int numero, FichaNumero ficha) {

        if (tablero.esUnMovimientoValido(fila, columna, numero)) {
            tablero.setValor(fila, columna, numero);
            ficha.setValor(numero);

            if (tablero.estaCompletada()) {
                Alert sudokuCompletado = new Alert(Alert.AlertType.INFORMATION);
                sudokuCompletado.setTitle("Juego completado!");
                sudokuCompletado.setContentText("Has completado el Sudoku!");
                sudokuCompletado.showAndWait();
            }
        } else {
            Alert errorNumero = new Alert(Alert.AlertType.ERROR);
            errorNumero.setTitle("Error!");
            errorNumero.setContentText("El numero ingresado es erroneo, intenta con otro numero");
            errorNumero.showAndWait();
        }
    }

}
