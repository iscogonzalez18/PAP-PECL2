package interfaz;

import scala.Tuple2;
import scala.Tuple3;
import scala.collection.JavaConverters;
import scala.collection.immutable.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.lang.Thread;
import java.util.stream.Collectors;


public class Tablero implements ActionListener {

    JFrame frame;
    private JPanel panelSuperior;
    private JPanel panelInferior;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JButton casillas [][];


    private int filas;
    private int columnas;
    private int nColores;
    private int nVidas;
    private int nPuntos;
    private String modo;
    private int coordenada1X;
    private int coordenada1Y;
    Juego juego;
    List<Object> tablero;
    private boolean jugar;



    public Tablero(int filas, int columnas, int nColores, String modo) throws InterruptedException {
        //constructor
        //se inicializan las variables
        this.filas = filas;
        this.columnas = columnas;
        this.nColores = nColores;
        this.modo = modo;
        nVidas = 5;
        nPuntos = 0;

        //se inicializa el juego
        juego = new Juego(filas, columnas, nColores);
        //se crea el tablero en una lista
        tablero = juego.inicializarTablero();

        //se inicializan los botones y todos los componentes visuales
        casillas = new JButton[filas][columnas];

        frame = new JFrame("CCS");
        frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        // Panel superior
        panelSuperior = new JPanel(new GridLayout(filas, columnas));
        panelSuperior.setPreferredSize(new Dimension(1200, 550));
        panelSuperior.setBackground(new Color(242,167,200));
        for (int i = 0; i <filas; i++) {
            for (int j = 0; j <columnas; j++) {
                JButton button = new JButton();
                // se agrega el action listener a cada boton
                button.addActionListener(this);
                // se agrega el boton a la matriz de botones
                casillas[i][j] = button;
                // se ajusta el tamaño de cada boton
                button.setPreferredSize(new Dimension(1200 / filas, 550 / columnas));
                // se obtiene el valor de la casilla en la posicion i,j
                Object valor = tablero.apply((filas*i)+j);
                int value = ((Integer) valor).intValue();
                // se asigna el color correspondiente depndiendo del valor que tenga esa posicion en el tablero
                switch (value){
                    case 1://blue
                        button.setBackground(new Color(0,0,255));
                        break;
                    case 2://red
                        button.setBackground(new Color(255,126,0));
                        break;
                    case 3://yellow
                        button.setBackground(new Color(255,255,0));
                        break;
                    case 4://green
                        button.setBackground(new Color(0,255,0));
                        break;
                    case 5://brown
                        button.setBackground(new Color(128,0,0));
                        break;
                    case 6://light blue
                        button.setBackground(new Color(0,255,255));
                        break;
                }
                panelSuperior.add(button);
            }
        }
        frame.add(panelSuperior, BorderLayout.NORTH);

        // Panel inferior
        panelInferior = new JPanel(new GridLayout(1, 3));
        panelInferior.setPreferredSize(new Dimension(1200, 150));
        panelInferior.setBackground(new Color(242,167,200));
        label1 = new JLabel("Coordenadas seleccionadas: X Y");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
        label2 = new JLabel("Vidas restantes: "+nVidas);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
        label3 = new JLabel("Puntos obtenidos: "+nPuntos);
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setFont(new Font("Segoe UI Black", Font.BOLD, 16));

        panelInferior.add(label1);
        panelInferior.add(label2);
        panelInferior.add(label3);

        frame.add(panelInferior, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        //en el modo automatico se juega solo
        jugar = true;
        //se juega mientras haya vidas y se este en modo automatico
        while (modo=="a" && jugar){
            //se espera 2.5 segundos para que se vea el cambio de colores
            Thread.sleep(2500);
            procesoJuegoAutomatico();
        }
        //si se acaban las vidas se muestra el mensaje de game over
        if (jugar == false){
            JOptionPane.showMessageDialog(null, "GAME OVER");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // en el juego manual se juega cuando se presiona un boton
        procesoJuegoManual(e);
    }

    public void procesoJuegoManual(ActionEvent e) {
        if (nVidas > 0 && modo == "m") {

            //se obtiene la coordenada seleccionada
            coordenada1X = -1;
            coordenada1Y = -1;
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    if (casillas[i][j] == e.getSource()) {
                        coordenada1X = i+1;
                        coordenada1Y = j+1;
                        break;
                    }
                }
            }
            label1.setText("Coordenadas seleccionadas: " + coordenada1X + " " + coordenada1Y);

            //se actualiza el tablero
            Tuple2<List<Object>, Object> devolver = juego.actualizar(tablero, coordenada1X, coordenada1Y);
            tablero = devolver._1();

            //variable para saber si el que se ha eliminado un especial
            boolean especial = (boolean) devolver._2();

            //puntos obtenidos con la jugada + los que ya se tenian
            int puntos = nPuntos + juego.contarCeros(tablero);

            //si se obtiene un punto se aplica gravedad el tablero y se resta una vida
            if (puntos - nPuntos == 1) {
                tablero = juego.gravedad(tablero, nVidas - 1, puntos);
                nVidas--;
                label2.setText("Vidas restantes: " + nVidas);
            }
            // si se obtienen 2, 3 o 4 puntos se aplica gravedad el tablero
            else if (puntos - nPuntos < 5) {
                tablero = juego.gravedad(tablero, nVidas, puntos);
            }
            // si se obtienen 5 o mas puntos se aplica gravedad el tablero y se inserta un especial
            else {
                //si se elimino un especial no se inserta otro
                if (!especial) {
                    tablero = juego.insertarEspecial(tablero, puntos - nPuntos, coordenada1X, coordenada1Y);
                }
                tablero = juego.gravedad(tablero, nVidas, puntos);
            }
             //se actualizan los puntos
            nPuntos = puntos;
            label3.setText("Puntos obtenidos: " + puntos);

            //se actualizan visualmente los botones
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    JButton button = casillas[i][j];
                    Object valor = tablero.apply((filas * i) + j);
                    int value = ((Integer) valor).intValue();
                    switch (value) {
                        case 1://blue
                            button.setBackground(new Color(0, 0, 255));
                            button.setText("");
                            break;
                        case 2://red
                            button.setBackground(new Color(255, 126, 0));
                            button.setText("");
                            break;
                        case 3://yellow
                            button.setBackground(new Color(255, 255, 0));
                            button.setText("");
                            break;
                        case 4://green
                            button.setBackground(new Color(0, 255, 0));
                            button.setText("");
                            break;
                        case 5://brown
                            button.setBackground(new Color(128, 0, 0));
                            button.setText("");
                            break;
                        case 6://light blue
                            button.setBackground(new Color(0, 255, 255));
                            button.setText("");
                            break;
                        case 7://bomb
                            button.setBackground(new Color(0, 0, 0));
                            button.setText("BOMBA");
                            break;
                        case 8://tnt
                            button.setBackground(new Color(255, 0, 0));
                            button.setText("TNT");
                            break;
                        case 9://blanco
                            button.setBackground(new Color(255, 255, 255));
                            break;
                        case 10://R1
                            button.setBackground(new Color(255, 255, 255));
                            button.setText("R1");
                            break;
                        case 20://R2
                            button.setBackground(new Color(255, 255, 255));
                            button.setText("R2");
                            break;
                        case 30://R3
                            button.setBackground(new Color(255, 255, 255));
                            button.setText("R3");
                            break;
                        case 40://R4
                            button.setBackground(new Color(255, 255, 255));
                            button.setText("R4");
                            break;
                        case 50://R5
                            button.setBackground(new Color(255, 255, 255));
                            button.setText("R5");
                            break;
                        case 60://R6
                            button.setBackground(new Color(255, 255, 255));
                            button.setText("R6");
                            break;
                    }
                }
            }
        } else {
            // si no hay vidas se muestra un mensaje de game over
            JOptionPane.showMessageDialog(null, "GAME OVER");
        }
    }

    public void procesoJuegoAutomatico(){
        if (nVidas > 0){

            //se obtiene la coordenada ideal
            List<Object> visitados = juego.inicializarLista();
            Tuple3<Object, Object, List<Object>> coordenadas = juego.buscarCoordenadaIdeal(tablero, 1, 1, 1, 1, filas, columnas, 0, visitados);
            coordenada1X = (int) coordenadas._1();
            coordenada1Y = (int) coordenadas._2();
            label1.setText("Coordenadas seleccionadas: " + coordenada1X + " " + coordenada1Y);

            //se actualiza el tablero
            Tuple2<List<Object>, Object> devolver = juego.actualizar(tablero, coordenada1X, coordenada1Y);
            tablero = devolver._1();

            //variable para saber si el que se ha eliminado un especial
            boolean especial = (boolean) devolver._2();

            //puntos obtenidos con la jugada + los que ya se tenian
            int puntos = nPuntos + juego.contarCeros(tablero);

            //si se obtiene un punto se aplica gravedad el tablero y se resta una vida
            if (puntos-nPuntos == 1){
                tablero=juego.gravedad(tablero,nVidas-1,puntos);
                nVidas--;
                label2.setText("Vidas restantes: " + nVidas);
            }
            //si se obtienen 2, 3 o 4 puntos se aplica gravedad el tablero
            else if (puntos-nPuntos <5){
                tablero=juego.gravedad(tablero,nVidas,puntos);
            }
            //si se obtienen 5 o mas puntos se aplica gravedad el tablero y se inserta un especial
            else{
                //si se elimino un especial no se inserta otro
                if (!especial){
                    tablero=juego.insertarEspecial(tablero,puntos-nPuntos,coordenada1X,coordenada1Y);
                }
                tablero=juego.gravedad(tablero,nVidas,puntos);
            }

            //se actualizan los puntos
            nPuntos = puntos;
            label3.setText("Puntos obtenidos: " + puntos);

            //se actualizan visualmente los botones
            for (int i = 0; i <filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    JButton button = casillas[i][j];
                    Object valor = tablero.apply((filas*i)+j);
                    int value = ((Integer) valor).intValue();
                    switch (value){
                        case 1://blue
                            button.setBackground(new Color(0,0,255));
                            button.setText("");
                            break;
                        case 2://red
                            button.setBackground(new Color(255,126,0));
                            button.setText("");
                            break;
                        case 3://yellow
                            button.setBackground(new Color(255,255,0));
                            button.setText("");
                            break;
                        case 4://green
                            button.setBackground(new Color(0,255,0));
                            button.setText("");
                            break;
                        case 5://brown
                            button.setBackground(new Color(128,0,0));
                            button.setText("");
                            break;
                        case 6://light blue
                            button.setBackground(new Color(0,255,255));
                            button.setText("");
                            break;
                        case 7://bomb
                            button.setText("BOMBA");
                            button.setForeground(Color.WHITE);
                            button.setBackground(new Color(0,0,0));
                            break;
                        case 8://tnt
                            button.setBackground(new Color(255,0,0));
                            button.setText("TNT");
                            break;
                        case 9://blanco
                            button.setBackground(new Color(255,255,255));
                            break;
                        case 10://R1
                            button.setBackground(new Color(255,255,255));
                            button.setText("R AZUL");
                            break;
                        case 20://R2
                            button.setBackground(new Color(255,255,255));
                            button.setText("R NARANJA");
                            break;
                        case 30://R3
                            button.setBackground(new Color(255,255,255));
                            button.setText("R AMARILLO");
                            break;
                        case 40://R4
                            button.setBackground(new Color(255,255,255));
                            button.setText("R VERDE");
                            break;
                        case 50://R5
                            button.setBackground(new Color(255,255,255));
                            button.setText("R MARRON");
                            break;
                        case 60://R6
                            button.setBackground(new Color(255,255,255));
                            button.setText("R CELESTE");
                            break;
                    }
                }
            }
        }
        else{
            // si no hay vidas se cambia el valor de la variable jugar a false para que no vuelva a entrar en el bucle
            jugar=false;

        }
    }
}
