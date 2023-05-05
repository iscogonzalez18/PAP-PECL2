package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import logica.*;

//import logica.{Juego};

public class Ejemplo implements ActionListener {

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
    private int coordenadaX;
    private int coordenadaY;



    public Ejemplo(int filas, int columnas, int nColores){
        this.filas = filas;
        this.columnas = columnas;
        this.nColores = nColores;

        casillas = new JButton[filas][columnas];

        frame = new JFrame("Ejemplo Paneles");
        frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel superior
        panelSuperior = new JPanel(new GridLayout(filas, columnas));
        //panelSuperior.setMinimumSize(new Dimension(1200, 550));
        //panelSuperior.setMaximumSize(new Dimension(1200, 550));
        panelSuperior.setPreferredSize(new Dimension(1200, 550));
        panelSuperior.setBackground(new Color(242,167,200));
        for (int i = 0; i <filas; i++) {
            for (int j = 0; j <columnas; j++) {
                JButton button = new JButton();
                button.addActionListener(this);
                casillas[i][j] = button;
                button.setPreferredSize(new Dimension(1200 / filas, 550 / columnas));
                panelSuperior.add(button);
            }
        }
        frame.add(panelSuperior, BorderLayout.NORTH);

        // Panel inferior
        panelInferior = new JPanel(new GridLayout(1, 3));
        //panelInferior.setMinimumSize(new Dimension(1200, 150));
        //panelInferior.setMaximumSize(new Dimension(1200, 150));
        panelInferior.setPreferredSize(new Dimension(1200, 150));
        panelInferior.setBackground(new Color(242,167,200));
        label1 = new JLabel("Coordenadas seleccionadas: X Y");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
        label2 = new JLabel("Vidas restantes: 3");
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
        label3 = new JLabel("Puntos obtenidos: 0");
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setFont(new Font("Segoe UI Black", Font.BOLD, 16));

        panelInferior.add(label1);
        panelInferior.add(label2);
        panelInferior.add(label3);

        frame.add(panelInferior, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        //Juego juego = new Juego();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int coordenadaX = -1;
        int coordenadaY = -1;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (casillas[i][j] == e.getSource()) {
                    coordenadaX = i+1;
                    coordenadaY = j+1;
                    break;
                }
            }
        }
        label1.setText("Coordenadas seleccionadas: " + coordenadaX + " " + coordenadaY);
    }
}
