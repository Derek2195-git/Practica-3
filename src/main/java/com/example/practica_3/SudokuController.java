package com.example.practica_3;

import javafx.scene.control.Alert;

public class SudokuController {
    private TableroSudoku tableroLogico;

    // Variables de estado que extrajimos de HelloApplication
    private FichaNumero fichaActiva = null;
    private int filaActiva = -1;
    private int columnaActiva = -1;

    public SudokuController(TableroSudoku tableroLogico) {
        this.tableroLogico = tableroLogico;
    }

    // Lógica que tenías dentro del setOnMouseClicked de la ficha
    public void seleccionarFicha(FichaNumero ficha, int fila, int columna) {
        if (tableroLogico.getCasillaFija(fila, columna)) return;
        if (tableroLogico.estaCompletada()) return;

        if (fichaActiva != null) {
            fichaActiva.getStyleClass().remove("ficha-activa");
            fichaActiva.getStyleClass().add("ficha-normal");
        }

        fichaActiva = ficha;
        filaActiva = fila;
        columnaActiva = columna;

        fichaActiva.getStyleClass().remove("ficha-normal");
        fichaActiva.getStyleClass().add("ficha-activa");
        ficha.requestFocus();
    }

    // Lógica combinada de ingresarNumero y borrarNumero que tenías en HelloApplication
    public void procesarEntradaNumero(int numero) {
        if (fichaActiva == null) return;
        if (tableroLogico.estaCompletada()) return;

        // Si mandamos un 0, es la función de borrar
        if (numero == 0) {
            tableroLogico.setValor(filaActiva, columnaActiva, 0);
            fichaActiva.setValor(0);
            return;
        }

        // Función de ingresar número
        if (tableroLogico.esUnMovimientoValido(filaActiva, columnaActiva, numero)) {
            tableroLogico.setValor(filaActiva, columnaActiva, numero);
            fichaActiva.setValor(numero);

            if (tableroLogico.estaCompletada()) {
                Alert sudokuCompletado = new Alert(Alert.AlertType.INFORMATION);
                sudokuCompletado.setTitle("Juego completado!");
                sudokuCompletado.setHeaderText("Exito!");
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