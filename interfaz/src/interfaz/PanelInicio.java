package interfaz;
import javax.swing.*;
import java.awt.*;

public class PanelInicio extends JPanel{

    private JComboBox dificultadBox;
    private JTextField filasField;
    private JTextField columnasField;
    //private JLabel dificultadLabel;
    //private JLabel filasLabel;
    //private JLabel columnasLabel;
    //private JLabel modoLabel;
    private JComboBox modoBox;
    private JPanel panelDeLabels;
    private JPanel panelDeDatos;
    private JButton jugar;
    private JPanel panelBoton;
    //private JLabel titulo;
    private JPanel panelTitulo;
    private JPanel panel1;

    public PanelInicio() {
        try{
            jbInit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception{
        this.setLayout( null );
        this.setSize(800, 800);
        this.setBackground(Color.PINK);
        this.setVisible(true);
        this.add(panelDeLabels);
        this.add(panelDeDatos);
        this.add(panelBoton);
        this.add(panelTitulo);
        /*
        panelDeLabels.add(dificultadLabel);
        panelDeLabels.add(filasLabel);
        panelDeLabels.add(columnasLabel);
        panelDeLabels.add(modoLabel);
        panelTitulo.add(titulo);
        */
        panelDeDatos.add(dificultadBox);
        panelDeDatos.add(filasField);
        panelDeDatos.add(columnasField);
        panelDeDatos.add(modoBox);
        panelBoton.add(jugar);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here



    }
}
