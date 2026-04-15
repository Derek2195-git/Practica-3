package com.example.practica_3;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;


public class FichaNumero extends Pane {
    private int valor;
    private String color;

    private ArrayList<Rectangle> fondo;
    private ArrayList<Rectangle> dibujo;

    private final int TAM_FICHA_TOTAL = 48;
    private final int ESPACIO = 0;
    private final int CUADRICULA = 5;
    private int tamCuadro;

    public FichaNumero(int valor, String color) {
        this.valor = valor;
        this.color = color;
        fondo = new ArrayList<>();
        dibujo = new ArrayList<>();

        tamCuadro = (TAM_FICHA_TOTAL - ((CUADRICULA - 1) * ESPACIO)) / CUADRICULA;

        setPrefSize(TAM_FICHA_TOTAL, TAM_FICHA_TOTAL);
        getStyleClass().add("ficha-creada");
        crearCuadricula();
        setValor(valor);
    }

    public FichaNumero(int valor) {

        this.valor = valor;
        color = "black";
        fondo = new ArrayList<>();
        dibujo = new ArrayList<>();

        tamCuadro = (TAM_FICHA_TOTAL - ((CUADRICULA - 1) * ESPACIO)) / CUADRICULA;

        setPrefSize(TAM_FICHA_TOTAL, TAM_FICHA_TOTAL);

        getStyleClass().add("ficha-creada");
        crearCuadricula();
        setValor(valor);
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;

        getChildren().removeAll(dibujo);
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
                getChildren().add(s);
            }
        }
    }

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

    private void agregarCuadroDibujo(int col, int fila) {
        Rectangle s = new Rectangle(tamCuadro, tamCuadro);
        // Crear getter
        s.setFill(getColor(color));
        s.setX(col * (tamCuadro + ESPACIO));
        s.setY(fila * (tamCuadro + ESPACIO));

        dibujo.add(s);
        getChildren().add(s);
    }

    public void cambiarColor(String nuevoColor) {

        Color colorACambiar = getColor(nuevoColor);
        for (Rectangle s : dibujo) {
            s.setFill(colorACambiar);
        }
    }

}
