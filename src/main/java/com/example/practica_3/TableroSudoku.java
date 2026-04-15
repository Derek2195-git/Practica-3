package com.example.practica_3;

import java.util.Random;
/**
 * Esta clase representa la lógica y el estado de un tablero de Sudoku.
 *
 * @autor Derek Ramón Garzón Vizcarra
 * @version 15/04/26
 */
public class TableroSudoku {
    // Este atributo corresponde a todos los valores del tablero
    private int[][] valoresTablero;
    // Este atributo se usa en conjunto con el anterior, aqui se revisa si la fila de un tablero
    // esta fija o "suelta", osea que si un usuario lo puede cambiar o no
    private boolean[][] casillasFijas;
    // Tableros ya resueltos, notese que tuve que usar una matriz de tres dimensiones
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
        // inicializo los arreglos del tablero y las casillas fijas
        valoresTablero = new int[9][9];
        casillasFijas = new boolean[9][9];

    }

    public void inicializarTablero() {
        Random rnd = new Random();
        // Elegimos uno de los tableros completos al azar
        int indiceTablero = rnd.nextInt(3);
        int[][] tableroEscogido = TABLEROSRESUELTOS[indiceTablero];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // Para cada valor del tablero, haremos el equivalente de un volado
                if (rnd.nextBoolean()) {
                    // Si es verdadero, volvemos a la casilla actual como fija
                    valoresTablero[i][j] = tableroEscogido[i][j];
                    casillasFijas[i][j] = true;
                } else {
                    // Si cae en falso, convertimos la casilla actual como una modificable, asignando su valor en 0
                    valoresTablero[i][j] = 0;
                    casillasFijas[i][j] = false;
                }
            }
        }
    }

    // Este metodo detecta si un "movimiento" hecho es valido
    public boolean esUnMovimientoValido(int fila, int columna, int numero) {
        // Si el numero esta fuera del rango de 1 a 9, asi como si la casilla es fija, no hacemos nada
        if (numero < 0 || numero > 9) return false;
        if (casillasFijas[fila][columna]) return false;
        // Si no hay problemas en la verificacion de la fila, columna y la subcuadricula, retornamos verdadero
        return verificarFila(fila, numero) && verificarColumna(columna, numero)
                && verificarSubcuadricula(fila, columna, numero);

    }

    // Verificamos si un numero esta en la fila correcta
    public boolean verificarFila(int fila, int numero) {
        // Vamos columna por columna en la misma fila, si hay un numero encontrado se devuelve un falso
        for (int columna = 0; columna < 9; columna++) {
            if(valoresTablero[fila][columna] == numero) return false;
        }
        // Si no se encontró al numero en la misma fila, se retorna el primer verdadero
        return true;
    }

    public boolean verificarColumna(int columna, int numero) {
        // recorremos fila por fila en la misma columna, si se encuentra el numero, el numero ingresado no es valido
        for (int fila = 0; fila < 9; fila++) {
            if(valoresTablero[fila][columna] == numero) return false;
        }
        // Si no se encontró el numero en la misma columna, se retorna otro verdadero
        return true;
    }

    public boolean verificarSubcuadricula(int fila, int columna, int numero) {
        /*

            Aqui vamos a hacer uso de una propiedad de los enteros,
            cuando dividimos, el valor decimal se descarta, sin redondearse el numero,
            explique más detalladamente como funciona esto en el reporte
         */

        // tomamos la fila inicial usando la formula propuesta en el reporte,
        int inicioDeFilas = (fila / 3) * 3;
        // tomamos la columna inicial como aquella dividia entre tres y posteriormente multiplicada por 3
        int inicioDeColumnas = (columna / 3) * 3;
        // Una subcuadricula 3x3, osea que solo debemos recorrer tres filas y tres columnas
        for (int i = inicioDeFilas; i < inicioDeFilas + 3; i++) {
            for (int j = inicioDeColumnas; j < inicioDeColumnas + 3; j++) {
                // Si encontramos el valor ingresado en la matricula, entonces retornamos un falso
                if (valoresTablero[i][j] == numero) return false;

            }
        }
        // Si no se encontró el numero en la subcuadricula, significa que no se puede ingresar este numero
        return true;
    }

    // retornamos un valor en una fila y columna deseada
    public int getValor(int fila, int columna) {
        return valoresTablero[fila][columna];
    }

    // cambiamos el valor en una fila y columna deseada
    public void setValor(int fila, int columna, int numero) {
        // esta verificación esta por más, pero la puse inicialmente, si la casilla es fija, no se
        // cambia el valor
        if (!casillasFijas[fila][columna]) {
            valoresTablero[fila][columna] = numero;
        }
    }

    // revisamos si la casilla dada es fija
    public boolean getCasillaFija(int fila, int columna) {
        return casillasFijas[fila][columna];
    }

    // Revisamos si la cuadricula de sudoku ha sido resuelta
    public boolean estaCompletada() {
        for (int i = 0; i < 9; i ++) {
            for (int j = 0; j < 9; j++) {
                // Esto lo vamos a usar en la vista
                if (valoresTablero[i][j] == 0) return false;
            }
        }
        return true;
    }

}
