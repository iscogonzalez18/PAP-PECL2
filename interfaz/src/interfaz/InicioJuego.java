package interfaz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InicioJuego implements ActionListener {

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


    public InicioJuego() {
        try{
            jbInit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void jbInit () {
        JFrame frame = new JFrame("Cundy Crosh Soga");
        frame.setResizable(false);
        frame.setContentPane(panelInicio);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 650);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        JUGARButton.addActionListener(this::actionPerformed);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtener los valores
        String valorModo = (String) modoBox.getSelectedItem();
        String valorDificultad = (String) dificultadBox.getSelectedItem();
        String valorFilas = filasField.getText();
        String valorColumnas = columnasField.getText();

        if (valorModo.equals("Manual")){
            String modo = "Manual";
            if (valorDificultad.equals("Fácil (4 colores)")) {
            }//String
        }else if (valorModo.equals("Automático")){
            //String modo = "Automático";
            if (valorDificultad.equals("Fácil (4 colores)")){

            }
        }
    }
}
