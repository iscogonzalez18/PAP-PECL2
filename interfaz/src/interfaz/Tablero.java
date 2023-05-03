package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Random;

public class Tablero extends JPanel implements ComponentListener, ActionListener {

    private int filas = 8;
    private int columnas = 8;

    private JButton[][] casillas = null;

    public void ordenar (){
        int anchoTotal = this.getWidth();
        int altoTotal = this.getHeight();
        int anchoCasilla = anchoTotal / columnas;
        int altoCasilla = altoTotal / filas;
        for(int i = 0; i < filas; i++){
            for(int j = 0; j < columnas; j++){
                JButton boton = casillas[i][j];
                boton.setBounds(j*anchoCasilla, i*altoCasilla, anchoCasilla, altoCasilla);
            }
        }
    }
    public void inicializar(){
        casillas = new JButton[filas][columnas];
        for(int i = 0; i < filas; i++){
            for(int j = 0; j < columnas; j++){
                JButton boton = new JButton();
                boton.setText("("+i+","+j+")");
                boton.addActionListener(this);
                this.add(boton);
                casillas[i][j] = boton;
            }
        }
    }
    public Tablero() {
        try{
            jbInit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception{
        this.setLayout( null );
        this.setBackground(Color.PINK);
        this.addComponentListener(this);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        this.ordenar();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    private Random r = new Random();
    private Color getRandomColor(){
        return new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton boton = (JButton) e.getSource();
            boton.setBackground(getRandomColor());
        }
    }
}
