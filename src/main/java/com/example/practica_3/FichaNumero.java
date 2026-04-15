package com.example.practica_3;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

/**
 * Esta clase invoca a una ficha de sudoku
 *
 * @autor Derek Ramón Garzón Vizcarra
 * @version 15/04/26
 */
public class FichaNumero extends Pane {
    // Valor de la ficha
    private int valor;
    // Color de cada cuadro de la ficha
    private String color;

    // Fondo o cuadricula 5x5 de una ficha
    private ArrayList<Rectangle> fondo;
    // Cuadrados que muestran la silueta gráfica del valor
    private ArrayList<Rectangle> dibujo;

    // Constantes
    // Tamaño de la ficha en total, es de 48x48 px
    private final int TAM_FICHA_TOTAL = 48;
    // Margen que hay entre cada ficha, en este caso puse el margen a 0
    private final int ESPACIO = 0;
    // Numero de filas y columnas de la cuadricula
    private final int CUADRICULA = 5;
    // Tamaño que va a tomar cada cuadrado tanto el fondo como el dibujo, se calcula
    // diviviendo 48 / 5, lo que da mas o menos como 9 pixeles cada cuadrado
    private int tamCuadro;

    public FichaNumero(int valor) {
        // Inicializamos los valores
        this.valor = valor;
        // Este color se va a cambiar automaticamente, pero lo dejamos con este valor por mientras
        color = "black";
        // Inicializamos los ArrayLists
        fondo = new ArrayList<>();
        dibujo = new ArrayList<>();
        // Calculamos el tamaño del cuadro
        tamCuadro = (TAM_FICHA_TOTAL - ((CUADRICULA - 1) * ESPACIO)) / CUADRICULA;

        // Esto es de JavaFX, hacemos que el cuadro tome un tamaño de 48 x 48 px
        setPrefSize(TAM_FICHA_TOTAL, TAM_FICHA_TOTAL);

        // Le añadimos un estilo de los creados en el css
        getStyleClass().add("ficha-creada");
        // Esto ya es de la clase original, creamos la cuadricula de fondo, para despues asignar y
        // dibujar el valor
        crearCuadricula();
        setValor(valor);
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;

        // Getchildren() agarra todos los subnodos que tenga la cuadricula, y RemoveAll hace se eliminen todos
        // Los subnodos
        getChildren().removeAll(dibujo);
        // Limpiamos el arraylist
        dibujo.clear();

        crearFiguraNumero();
    }

    private void crearCuadricula() {
        for (int i = 0; i < CUADRICULA; i++) {
            for (int j = 0; j < CUADRICULA; j++) {
                Rectangle s = new Rectangle(tamCuadro, tamCuadro);
                s.setFill(Color.TRANSPARENT);

                s.setX(j * (tamCuadro + ESPACIO));
                s.setY(i * (tamCuadro + ESPACIO));


                fondo.add(s);
                // Getchildren() agarra todos los subnodos que tenga la cuadricula, y add hace que
                // se agreguen nuevos subnodos, en este caso son los cuadrados
                getChildren().add(s);
            }
        }
    }

    // Lo de crearFiguraNumero() no se cambio en absoluto
    public void crearFiguraNumero() {
        switch (valor) {
            case 1:

                agregarCuadroDibujo(2, 1);
                agregarCuadroDibujo(2, 2);
                agregarCuadroDibujo(2, 3);
                cambiarColor("darkest_blue");
                break;
            case 2:


                agregarCuadroDibujo(1, 0); agregarCuadroDibujo(2, 0);
                agregarCuadroDibujo(3, 0);

                agregarCuadroDibujo(3, 1);
                agregarCuadroDibujo(1, 2);  agregarCuadroDibujo(2, 2);
                agregarCuadroDibujo(3, 2);
                agregarCuadroDibujo(1, 3);

                agregarCuadroDibujo(1, 4); agregarCuadroDibujo(2, 4);
                agregarCuadroDibujo(3, 4);
                cambiarColor("dark_blue");
                break;
            case 3:


                agregarCuadroDibujo(1,0); agregarCuadroDibujo(2,0); agregarCuadroDibujo(3,0);

                agregarCuadroDibujo(3,1);
                agregarCuadroDibujo(1,2); agregarCuadroDibujo(2,2); agregarCuadroDibujo(3,2);
                agregarCuadroDibujo(3,3);

                agregarCuadroDibujo(1,4); agregarCuadroDibujo(2,4); agregarCuadroDibujo(3,4);
                cambiarColor("darklight_blue");
                break;
            case 4:


                agregarCuadroDibujo(0,0); agregarCuadroDibujo(3,0);
                agregarCuadroDibujo(0,1); agregarCuadroDibujo(3,1);

                agregarCuadroDibujo(0,2); agregarCuadroDibujo(1,2); agregarCuadroDibujo(2,2);
                agregarCuadroDibujo(3,2); agregarCuadroDibujo(4,2);

                agregarCuadroDibujo(3,3);
                agregarCuadroDibujo(3,4);
                cambiarColor("teal");
                break;
            case 5:


                agregarCuadroDibujo(1, 0); agregarCuadroDibujo(2, 0); agregarCuadroDibujo(3, 0);

                agregarCuadroDibujo(1, 1);
                agregarCuadroDibujo(1, 2);  agregarCuadroDibujo(2, 2); agregarCuadroDibujo(3, 2);
                agregarCuadroDibujo(3, 3);

                agregarCuadroDibujo(1, 4); agregarCuadroDibujo(2, 4); agregarCuadroDibujo(3, 4);
                cambiarColor("light_green");
                break;
            case 6:


                agregarCuadroDibujo(1,0); agregarCuadroDibujo(2,0); agregarCuadroDibujo(3,0);

                agregarCuadroDibujo(1,1);
                agregarCuadroDibujo(1,2); agregarCuadroDibujo(2,2); agregarCuadroDibujo(3,2);
                agregarCuadroDibujo(1,3); agregarCuadroDibujo(3,3);

                agregarCuadroDibujo(1,4); agregarCuadroDibujo(2,4); agregarCuadroDibujo(3,4);
                cambiarColor("dark_green");
                break;
            case 7:

                agregarCuadroDibujo(1,1); agregarCuadroDibujo(2,1); agregarCuadroDibujo(3,1);

                agregarCuadroDibujo(3,2);
                agregarCuadroDibujo(3,3);

                agregarCuadroDibujo(3,4);
                cambiarColor("light_orange");
                break;
            case 8:


                agregarCuadroDibujo(1,0); agregarCuadroDibujo(2,0); agregarCuadroDibujo(3,0);

                agregarCuadroDibujo(1,1); agregarCuadroDibujo(3,1);
                agregarCuadroDibujo(1,2); agregarCuadroDibujo(2,2); agregarCuadroDibujo(3,2);
                agregarCuadroDibujo(1,3); agregarCuadroDibujo(3,3);

                agregarCuadroDibujo(1,4); agregarCuadroDibujo(2,4); agregarCuadroDibujo(3,4);
                cambiarColor("orange");
                break;
            case 9:


                agregarCuadroDibujo(1,0); agregarCuadroDibujo(2,0); agregarCuadroDibujo(3,0);

                agregarCuadroDibujo(1,1); agregarCuadroDibujo(3,1);
                agregarCuadroDibujo(1,2); agregarCuadroDibujo(2,2); agregarCuadroDibujo(3,2);
                agregarCuadroDibujo(3,3);

                agregarCuadroDibujo(1,4); agregarCuadroDibujo(2,4); agregarCuadroDibujo(3,4);
                cambiarColor("light_red");
                break;

        }
    }

    // Esta clase la usé para poder cambiar los colores de los cuadrados a mi elección
    public Color getColor(String color) {
        switch(color) {
            case "red": return Color.RED;
            case "blue": return Color.BLUE;
            case "green": return Color.GREEN;
            case "teal": return Color.rgb(38, 222, 150);
            case "darkest_blue": return Color.rgb(15, 2, 71);
            case "dark_blue": return Color.rgb(50, 70, 168);
            case "darklight_blue": return Color.rgb(15,118,191);
            case "light_gray": return Color.rgb(201, 201, 201);
            case "dark_green": return Color.rgb(4, 194, 46);
            case "light_green": return Color.rgb(47, 224, 112);
            case "light_orange": return Color.rgb(191, 117, 13);
            case "orange": return Color.rgb(252, 129, 71);
            case "light_red": return Color.rgb(247, 64, 64);
            default: return Color.BLACK;
        }
    }

    // Esta clase crea una instancia de cuadrado, cambia su color y lo ubica en la columna y
    // fila dada por el usuario
    private void agregarCuadroDibujo(int col, int fila) {
        Rectangle s = new Rectangle(tamCuadro, tamCuadro);
        // Crear getter
        s.setFill(getColor(color));
        s.setX(col * (tamCuadro + ESPACIO));
        s.setY(fila * (tamCuadro + ESPACIO));

        dibujo.add(s);
        getChildren().add(s);
    }

    // Esto es un setter para cambiar el color, solo que como es un poco más complejo no le puse ese nombre
    public void cambiarColor(String nuevoColor) {

        Color colorACambiar = getColor(nuevoColor);
        for (Rectangle s : dibujo) {
            s.setFill(colorACambiar);
        }
    }

}
