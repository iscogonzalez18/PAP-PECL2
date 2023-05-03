package interfaz;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

    private PanelInicio panelInicio = new PanelInicio();
    //private Tablero tablero = new Tablero();
    public MainFrame() {
        try{
            jbInit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception{
        this.setLayout(new BorderLayout());
        this.setTitle("Cundy Crosh Soga");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.add(panelInicio, BorderLayout.CENTER);
        /*this.add(tablero, BorderLayout.CENTER);
        tablero.inicializar();
        tablero.setSize(400,400);
        tablero.ordenar();*/
    }

}
