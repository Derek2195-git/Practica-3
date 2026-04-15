package com.example.practica_3;

import javafx.scene.control.Alert;

public class SudokuController {
    // aunque no fuera importante ni necesario, pienso que estaria bien no meterle tantas cosas a la vista
    // Es por esto que realice esta clase

    // Vamos a agarrar el tablero que se inicializó en la vista
    private TableroSudoku tableroLogico;
    // Tambien vamos a señalar cuando el usuario le haga click a una ficha
    private FichaNumero fichaActiva = null;
    // Esta ficha obviamente debe tener su fila y columna correspondiente
    private int filaActiva = -1;
    private int columnaActiva = -1;

    public SudokuController(TableroSudoku tableroLogico) {
        this.tableroLogico = tableroLogico;
    }

    // Este metodo era el que estaba dentro del setOnMouseClicked para la ficha en la vista
    public void seleccionarFicha(FichaNumero ficha, int fila, int columna) {
        // Si la casilla es fija o ya se completó el sudoku, no hacemos nada
        if (tableroLogico.getCasillaFija(fila, columna)) return;
        if (tableroLogico.estaCompletada()) return;

        /*
         En caso de que ya tengamos una ficha activa, removemos el estilo que tenia y se lo ponemos
         como si fuera una ficha normal
         */
        if (fichaActiva != null) {
            fichaActiva.getStyleClass().remove("ficha-activa");
            fichaActiva.getStyleClass().add("ficha-normal");
        }

        // Volvemos a la ficha activa como la ultima ficha que el usuario presionó
        fichaActiva = ficha;
        // Seguido de esto, agarramos la fila y columna en la que está
        filaActiva = fila;
        columnaActiva = columna;

        /*
        Por ultimo, removemos el estilo que anteriormente y se lo cambiamos por otro que se vea
        visualmente que este activa
         */
        fichaActiva.getStyleClass().remove("ficha-normal");
        fichaActiva.getStyleClass().add("ficha-activa");
        // Le pedimos al teclado con este metodo para que si se teclea algo, lo reciba esta ficha
        ficha.requestFocus();
    }

    // Este metodo tambien estaba en la vista, pero lo agrupee para que lo pudiera usar en otras partes
    // esta clase recibe un numero y lo cambiará en la ficha
    public void procesarEntradaNumero(int numero) {
        // Si no hay una ficha o si el tablero ya esta completado, no se hace nada
        if (fichaActiva == null) return;
        if (tableroLogico.estaCompletada()) return;

        // Si se manda un 0, el cual es exclusivo del boton de borrar, es como si no hubiera una ficha
        // actualmente
        if (numero == 0) {
            tableroLogico.setValor(filaActiva, columnaActiva, 0);
            fichaActiva.setValor(0);
            return;
        }

        // Si el numero ingresado en la casilla activa es valido, lo metemos a la casilla correspondiente
        if (tableroLogico.esUnMovimientoValido(filaActiva, columnaActiva, numero)) {
            // Cambiamos el valor del tablero para reflejar este cambio
            tableroLogico.setValor(filaActiva, columnaActiva, numero);
            // Asimismo, el valor de la ficha activa cambiará al numero ingresado
            fichaActiva.setValor(numero);

            // Si el tablero esta completamente rellenado, se acabó el juego.
            if (tableroLogico.estaCompletada()) {
                // Creamos una alerta que indique que el juego se acabo
                Alert sudokuCompletado = new Alert(Alert.AlertType.INFORMATION);
                sudokuCompletado.setTitle("Juego completado!");
                sudokuCompletado.setHeaderText("Exito!");
                sudokuCompletado.setContentText("Has completado el Sudoku!");
                sudokuCompletado.showAndWait();
            }
        } else {
            // En caso contrario, creamos una alerta de que el numero ingresado no era correcto
            Alert errorNumero = new Alert(Alert.AlertType.ERROR);
            errorNumero.setTitle("Error!");
            errorNumero.setContentText("El numero ingresado es erroneo, intenta con otro numero");
            errorNumero.showAndWait();
        }
    }
}