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
    public void start(Stage ventana) throws IOException {
        // Instanciamos el tablero de sudoku
        TableroSudoku tableroLogico = new TableroSudoku();
        // Llenamos de numeros el tablero en base a una de nuestras hojas completas
        tableroLogico.inicializarTablero();

        // Seguido de esto instanciamos el controlador y le damos el tablero ya rellenado
        SudokuController controlador = new SudokuController(tableroLogico);

        /*
            Creamos un label el cual represente el titulo del sudoku
            iba a poner un titulo más interesante pero no me se ocurrio nada
         */
        Label labelTitulo = new Label("¡Sudoku!");
        // Le añadimos al archivo css el estilo que queremos que tenga el label
        labelTitulo.getStyleClass().add("label");

        // Ahora, creamos la cuadricula del sudoku
        GridPane cuadricula = new GridPane();
        /*
         En este caso, centre centre la cuadricula y le añadi un poco de padding para que no se vea
         tan pegado
         */
        cuadricula.setPadding(new Insets(20, 20, 20, 20));
        cuadricula.setAlignment(Pos.CENTER);

        // Ahora, cada valor que hay en el tablero de sudoku, lo vamos a añadir a la cuadricula
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                // Obtenemos el valor actual junto con la información de si la casilla es fija
                int valor = tableroLogico.getValor(i, j);
                boolean esFija = tableroLogico.getCasillaFija(i, j);

                // Ahora creamos la ficha y le asignamos el valor que obtenimos
                FichaNumero ficha = new FichaNumero(valor);

                // Asignamos de valores a la fila y columna actual
                int filaActual = i;
                int columnaActual = j;

                /*
                    Esto es un poco de verificación para dibujar los bordes
                    todas las fichas tienen un borde de 1 pixel por default
                 */
                double top = 1, right = 1, bottom = 1, left = 1;
                /*
                    pero si la ficha se ubica en los extremos de la derecha, su borde derecho
                    va a ser de un valor mayor para diferenciar mejor cada subcuadricula
                 */
                if (j == 2 || j == 5) right = 3;
                // Hacemos el mismo calculo para los bordes de abajo en la cuadricula
                if (i == 2 || i == 5) bottom = 3;

                // Si la ficha es fija, le añadimos un estilo el cual haga que se vea más gris
                if (esFija) {
                    ficha.getStyleClass().add("ficha-fija");
                } else {
                    // si la ficha no es ficha, la ficha será de un color blanco
                    ficha.getStyleClass().add("ficha-normal");
                }
                // Asignamos en un string los valores que van a tener los bordes
                String estiloBordes = String.format("-fx-border-width: %.0f %.0f %.0f %.0f; ", top, right, bottom, left);
                // seguido de esto, cambiamos el estilo de las fichas para que se puedan graficar bien
                ficha.setStyle(estiloBordes);

                // Agregamos un evento para cuando el usuario clickea a una casilla
                ficha.setOnMouseClicked(evento -> {
                    // Si hacemos click por al menos una vez, seleccionamos la ficha
                    controlador.seleccionarFicha(ficha, filaActual, columnaActual);
                });

                // Por ultimo, añadimos la ficha creada a la columna actual
                cuadricula.add(ficha, j, i);
            }
        }

        // Ahora inicializamos un teclado numerico compuesto de botones
        GridPane panelBotones = new GridPane();
        // Este teclado tambien vamos a ponerlo en la posicion central en la ventana
        panelBotones.setAlignment(Pos.CENTER);
        // Cada boton tiene una separación de 10 pixeles entre cada uno
        panelBotones.setHgap(10);
        panelBotones.setVgap(10);

        /*
            Como quiero que el boton se vea del 1 al 9, de izquierda a derecha, arriba a abajo
            hacemos uso de una pequeña operacion
         */
        // Inicializamos el boton en 1
        int numeroBoton = 1;
        // Ahora añadimos un factor de filas, esto aumentara el valor de cada fila en 1
        int factorFilas = 2;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Creamos un boton con el valor definido antes del ciclo
                Button botonNumero = new Button(String.valueOf(numeroBoton));
                // Le añadimos un estilo al boton
                botonNumero.getStyleClass().add("boton-teclado");

                // le asignamos el valor del numero a ingresar, como el mismo que tiene el boton
                int numeroAIngresar = numeroBoton;

                // Le añadimos un evento al boton para que al pulsarlo se registre el numero
                botonNumero.setOnMouseClicked(evento -> {
                    controlador.procesarEntradaNumero(numeroAIngresar);
                });

                // añadimos el boton que creamos al panel, en la posicion dada por los ciclos
                panelBotones.add(botonNumero, i, j);
                // Como cada numero de la columna del teclado es sumado por 3, la variable en 3
                numeroBoton += 3;
            }
            /*
                Al acabar, reiniciamos nuestra variable numerica y la volvemos el valor del factor de filas
                esto resulta que cada fila tambien va a aumentar su valor inicial en 1
             */
            numeroBoton = factorFilas;
            // por ultimo, aumentamos el factor de las filas en 1
            factorFilas++;
        }

        // Ahora creamos un boton que nos sirva para borrar un numero
        Button botonBorrar = new Button("Borrar");
        // le añadimos su estilo propio
        botonBorrar.getStyleClass().add("boton-teclado");
        // cuando el usuario le de click, va a enviar un cero a la casilla, efectivamente borrandola
        botonBorrar.setOnMouseClicked(evento -> {
            controlador.procesarEntradaNumero(0);
        });
        // añadimos el boton de borrar al panel que creamos anteriormente
        panelBotones.add(botonBorrar, 0, 3, 3, 1);

        // Creamos una caja horizontal para añadir la cuadricula y el panel de botones
        HBox contenedorCentral = new HBox();
        // Centramos la caja
        contenedorCentral.setAlignment(Pos.CENTER);
        contenedorCentral.getChildren().addAll(cuadricula, panelBotones);

        /*
            Creamos una caja vertical para añadir el label que creamos al inicio, asi
            como el contenedor que creamos anteriormente
         */
        VBox contenedorVertical = new VBox();
        contenedorVertical.setAlignment(Pos.CENTER);
        contenedorVertical.getChildren().addAll(labelTitulo, contenedorCentral);

        // Creamos el contenedor donde mostraremos nuestro sudoku en la ventana
        Scene escena = new Scene(contenedorVertical, 800, 600);
        /*
            Aqui vamos a meter un archivo css para que todas las veces a las que se llama a un grupo
            para cambiar el estilo de uno de los objetos gráficos, pueda cambiar el aspecto de estos objetos
         */
        escena.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());

        // Creamos un evento que ingrese un valor a la cuadricula cuando pulsamos una tecla
        escena.setOnKeyTyped(evento -> {
            // Obtenemos el caracter pulsado
            String teclaPulsada = evento.getCharacter();
            // Usando expresiones regulares, si el valor esta en el rango de 1 a 9, realizamos el añadido
            if (teclaPulsada.matches("[1-9]")) {
                // Convertimos el numero ingresado a uno entero
                int numeroTeclado = Integer.parseInt(teclaPulsada);
                // y por ultimo, hacemos que el controlador meta el valor escrito a la casilla
                controlador.procesarEntradaNumero(numeroTeclado);
            }
        });

        // Le añadimos un titulo a la ventana
        ventana.setTitle("Práctica 3");
        // Ponemos como escena principal a la escena con todos los elementos del sudoku
        ventana.setScene(escena);
        // Mostramos la ventana
        ventana.show();
    }
}