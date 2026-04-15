package com.example.practica_3;

import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;


import javafx.geometry.Insets;
import javafx.scene.control.Alert;

public class HelloApplication extends Application {
    @Override
    public void start(Stage ventana) throws IOException {

        TableroSudoku tableroLogico = new TableroSudoku();
        tableroLogico.inicializarTablero();

        GridPane cuadricula = new GridPane();
        cuadricula.setPadding(new Insets(20));
        cuadricula.setVgap(0);
        cuadricula.setHgap(0);

        cuadricula.setAlignment(Pos.CENTER);
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {

                int valor = tableroLogico.getValor(i, j);
                boolean esFija = tableroLogico.esFija(i, j);

                FichaNumero ficha = new FichaNumero(valor);
                ficha.marcarFichaComoFija(esFija);

                final int filaActual = i;
                final int columnaActual = j;

                // Esto es para los bordes, cada variable para cada parte de un borde
                double top = 1, right = 1, bottom = 1, left = 1;

                // Si es el final de una subcuadrícula horizontal (columna 2 o 5), borde derecho grueso
                if (j == 2 || j == 5) right = 3;
                // Si es el final de una subcuadrícula vertical (fila 2 o 5), borde inferior grueso
                if (i == 2 || i == 5) bottom = 3;

                String bordes = "-fx-border-color: gray; ";
                String estiloBordes = String.format("-fx-border-width: %.0f %.0f %.0f %.0f; ", top, right, bottom, left);

                String estiloBase = esFija ? "-fx-background-color: #C9C9C9; " : "-fx-background-color: white; ";

                ficha.setStyle(estiloBase + bordes + estiloBordes);

                ficha.setOnMouseClicked(evento -> {
                    // Revisamos si la casilla es fija
                    if (tableroLogico.esFija(filaActual, columnaActual)) {
                        Alert ventanaInformacion = new Alert(Alert.AlertType.WARNING);
                        ventanaInformacion.setTitle("Movimiento no permitido");
                        ventanaInformacion.setHeaderText(null);
                        ventanaInformacion.setContentText("Esta casilla es fija y no se puede modificar");
                        ventanaInformacion.showAndWait();
                        return;
                    }

                    // Calabaza calabaza
                    TextInputDialog dialogo = new TextInputDialog();
                    dialogo.setTitle("Ingresar numero");
                    dialogo.setHeaderText("Casilla seleccionada: Fila "+ filaActual + ", Columna " + columnaActual);
                    dialogo.setContentText("Escribe un numero del 1 al 9");

                    dialogo.showAndWait().ifPresent(respuesta -> {
                        try {
                            int numeroIngresado = Integer.parseInt(respuesta);

                            if (numeroIngresado >= 1 && numeroIngresado <= 9) {
                                if (tableroLogico.esUnMovimientoValido(filaActual, columnaActual, numeroIngresado)) {
                                    tableroLogico.setValor(filaActual, columnaActual, numeroIngresado);
                                    ficha.setValor(numeroIngresado);
                                    ficha.marcarFichaComoFija(true);
                                    if (tableroLogico.estaCompletada()) {
                                        Alert posicionCapturada = new Alert(Alert.AlertType.INFORMATION);
                                        posicionCapturada.setHeaderText("Sudoku Completado!");
                                        String cadenaExito = "Dato valido capturado: " + numeroIngresado +
                                                " en la posicion [" + filaActual + "][" + columnaActual + "]";
                                        posicionCapturada.setContentText("Haz completado el sudoku!");
                                        posicionCapturada.showAndWait();
                                    }

                                } else {
                                    Alert alertaNumeroEquivocado = new Alert(Alert.AlertType.ERROR);
                                    alertaNumeroEquivocado.setHeaderText("Número incorrecto");
                                    alertaNumeroEquivocado.setContentText("El numero ingresado no es correcto");
                                    alertaNumeroEquivocado.showAndWait();
                                }

                            } else {
                                // Si el numero no esta en el rango
                                Alert alertaRangoExcedido = new Alert(Alert.AlertType.ERROR);
                                alertaRangoExcedido.setHeaderText("Número invalido");
                                alertaRangoExcedido.setContentText("Por favor, ingresa numeros entre el rango del 1 al 9");
                                alertaRangoExcedido.showAndWait();
                            }
                        } catch (NumberFormatException e) {
                            Alert alertaDatoNoValido = new Alert(Alert.AlertType.ERROR);
                            alertaDatoNoValido.setHeaderText("Formato de número no valido");
                            alertaDatoNoValido.setContentText("No se pueden ingresar letras o simbolos, \ningresa solamente numeros.");
                            alertaDatoNoValido.showAndWait();
                        }
                    });
                });

                cuadricula.add(ficha, j, i);

            }
        }

        StackPane lienzo = new StackPane();
        lienzo.getChildren().add(cuadricula);

        StackPane.setAlignment(cuadricula, Pos.CENTER);
        Scene escena = new Scene(lienzo, 800, 600);

        ventana.setTitle("Práctica 3");
        ventana.setScene(escena);
        ventana.show();
    }

}
