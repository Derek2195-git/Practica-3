package com.example.practica_3;

public class TableroSudoku {
    // Esta clase representará la lógica y el estado del tablero de Sudoku.

    private int[][] valoresTablero;
    private boolean[][] casillasFijas;

    public TableroSudoku() {
        valoresTablero = new int[9][9];
        casillasFijas = new boolean[9][9];
    }

    public void cargarTableroInicial(int[][] tableroInicial) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                valoresTablero[i][j] = tableroInicial[i][j];

                // Si la casilla tiene un numero que no es igual a cero, es fija
                if (valoresTablero[i][j] != 0) casillasFijas[i][j] = true;
                else casillasFijas[i][j] = false;

            }
        }
    }

    public boolean esUnMovimientoValido(int fila, int columna, int numero) {
        // Por si acaso el usuario mete un valor que no puede existir en el tablero
        if (numero < 0 || numero > 9) return false;
        if (casillasFijas[fila][columna]) return false;
        return verificarFila(fila, numero) && verificarColumna(columna, numero)
                && verificarSubcuadricula(fila, columna, numero);

    }

    public boolean verificarFila(int fila, int numero) {
        for (int columna = 0; columna < 9; columna++) {
            if(valoresTablero[fila][columna] == numero) return false;
        }
        return true;
    }

    public boolean verificarColumna(int columna, int numero) {
        for (int fila = 0; fila < 9; fila++) {
            if(valoresTablero[fila][columna] == numero) return false;
        }
        return true;
    }

    public boolean verificarSubcuadricula(int fila, int columna, int numero) {
        /*
            Aqui vamos a hacer uso de una propiedad de los enteros
            Cuando dividimos, el valor decimal se descarta, sin redondearse el numero
            esto significa que al dividir un numero impar como 5, su valor al dividirse en
            enteros será de 1 (5 / 3 = 1.666 -> 1 al quitarse los decimales)
            Si volvemos a hacer otra operacion con dicho numero, este se tomara como si siempre
            hubiera sido el numero resultante, sin importar sus decimales (1 * 3 = 3)
         */
        int inicioDeFilas = (fila / 3) * 3;
        int inicioDeColumnas = (columna / 3) * 3;
        // Una subcuadricula 3x3, osea que solo debemos recorrer tres filas y tres columnas
        for (int i = inicioDeFilas; i < inicioDeFilas + 3; i++) {
            for (int j = inicioDeColumnas; j < inicioDeColumnas + 3; j++) {
                if (valoresTablero[i][j] == numero) return false;
            }
        }
        return true;
    }

    public int getValor(int fila, int columna) {
        return valoresTablero[fila][columna];
    }

    public void setValor(int fila, int columna, int numero) {
        if (!casillasFijas[fila][columna]) {
            valoresTablero[fila][columna] = numero;

        }
    }

    public boolean esFija(int fila, int columna) {
        return casillasFijas[fila][columna];
    }

    public boolean estaCompletada() {
        for (int i = 0; i < 9; i ++) {
            for (int j = 0; j < 9; j++) {
                if (valoresTablero[i][j] == 0) return false;
            }
        }
        return true;
    }

}
