package com.example.practica_3;

import java.util.Random;

public class TableroSudoku {
    // Esta clase representará la lógica y el estado del tablero de Sudoku.
    private int[][] valoresTablero;
    private boolean[][] casillasFijas;
    private final int[][][] TABLEROSRESUELTOS = {
            {
                {5,3,1,9,8,4,6,7,2}, {6,4,9,2,5,7,8,3,1}, {8,2,7,6,1,3,5,4,9},
                {9,6,2,3,7,8,4,1,5}, {1,8,5,4,2,9,7,6,3}, {3,7,4,5,6,1,9,2,8},
                {4,9,6,8,3,2,1,5,7}, {7,5,3,1,9,6,2,8,4}, {2,1,8,7,4,5,3,9,6}
            },
            {
                {2,5,1,4,9,8,3,7,6}, {9,7,8,6,1,3,2,4,5}, {4,3,6,7,2,5,9,8,1},
                {5,4,3,9,6,7,1,2,8}, {8,2,9,1,5,4,6,3,7}, {1,6,7,3,8,2,4,5,9},
                {3,1,2,8,7,6,5,9,4}, {7,9,5,2,4,1,8,6,3}, {6,8,4,5,3,9,7,1,2}
            },
            {
                {1,4,8,5,9,6,3,2,7}, {7,6,5,3,1,2,8,4,9}, {3,2,9,8,7,4,1,5,6},
                {9,7,6,2,8,1,4,3,5}, {4,8,1,7,5,3,9,6,2}, {5,3,2,4,6,9,7,1,8},
                {8,1,3,6,2,7,5,9,4}, {2,5,4,9,3,8,6,7,1}, {6,9,7,1,4,5,2,8,3}
            }
    };

    public TableroSudoku() {
        valoresTablero = new int[9][9];
        casillasFijas = new boolean[9][9];

    }

    public void inicializarTablero() {
        Random rnd = new Random();
        int indiceTablero = rnd.nextInt(3);
        int[][] tableroEscogido = TABLEROSRESUELTOS[indiceTablero];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (rnd.nextBoolean()) {
                    valoresTablero[i][j] = tableroEscogido[i][j];
                    casillasFijas[i][j] = true;
                } else {
                    valoresTablero[i][j] = 0;
                    casillasFijas[i][j] = false;
                }

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

    public boolean getCasillaFija(int fila, int columna) {
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
