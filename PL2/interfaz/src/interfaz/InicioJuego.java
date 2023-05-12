package interfaz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InicioJuego implements ActionListener {

    private JFrame frame;
    private JLabel imagen;
    private JLabel modoDeJuegoLabel;
    private JLabel dificultadLabel;
    private JLabel númeroDeColumnasLabel;
    private JLabel númeroDeFilasLabel;
    private JComboBox modoBox;
    private JComboBox dificultadBox;
    private JTextField filasField;
    private JTextField columnasField;
    private JButton JUGARButton;
    private JPanel panelInicio;

    private String modo;
    private int nColores;
    private int filas;
    private int columnas;
    private boolean accionCompletada;



    public InicioJuego() {
        try{
            jbInit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void jbInit () {
        // Crear el frame y crear los componentes
        frame = new JFrame("Cundy Crosh Soga");
        frame.setResizable(false);
        frame.setContentPane(panelInicio);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 650);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        JUGARButton.addActionListener(this::actionPerformed);
        boolean accionCompletada = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtener los valores
        String valorModo = (String) modoBox.getSelectedItem();
        String valorDificultad = (String) dificultadBox.getSelectedItem();
        String valorFilas = filasField.getText();
        String valorColumnas = columnasField.getText();

        // Verificar si los campos están vacíos
        if (valorFilas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: tienes que indicar el número de filas");
            return;
        }

        if (valorColumnas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: tienes que indicar el número de columnas");
            return;
        }

        try {
            filas = Integer.parseInt(valorFilas);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error: El valor de filas debe ser un número entero");
            return;
        }

        try {
            columnas = Integer.parseInt(valorColumnas);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error: El valor de columnas debe ser un número entero");
            return;
        }

        // Asignar los valores a las variables correspondientes
        nColores = valorDificultad.equals("Fácil (4 colores)") ? 4 : 6;
        modo = valorModo.equals("Manual") ? "m" : "a";

        accionCompletada = true;
    }

    public String getModo(){
        return modo;
    }

    public int getNColores(){
        return nColores;
    }

    public int getFilas(){
        return filas;
    }

    public int getColumnas(){
        return columnas;
    }

    public boolean getAccionCompletada(){
        return accionCompletada;
    }

    public void ocultar (){
        frame.setVisible(false);
    }

}
