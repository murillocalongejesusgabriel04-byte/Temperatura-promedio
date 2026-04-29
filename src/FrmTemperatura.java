import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import Controladores.TemperaturaControlador;
import Modelos.Temperatura;
import Servicios.TemperaturaServicio;
import datechooser.beans.DateChooserCombo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;



public class FrmTemperatura extends JFrame{
    private DateChooserCombo dccDesde, dccHasta, dccEspecifica;
    private JPanel pnlgrafica;
    private JTabbedPane tpTemperaturaDatos;

    private List<Temperatura> datos;


    public FrmTemperatura(){
        setTitle("Temperatura");
        setSize(700, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JToolBar toolbar = new JToolBar();
        add(toolbar, BorderLayout.NORTH);

        ImageIcon iconoTemperatura = new ImageIcon(getClass().getResource("Iconos/temperatura.png"));
        iconoTemperatura = new ImageIcon(iconoTemperatura.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        ImageIcon Iconografica = new ImageIcon(getClass().getResource("Iconos/grafica.png"));
        Iconografica = new ImageIcon(Iconografica.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));


        JButton btnCiudadMasCalurosa = new JButton();
        btnCiudadMasCalurosa.setIcon(iconoTemperatura);
        btnCiudadMasCalurosa.setToolTipText("Ciudad más calurosa");
        btnCiudadMasCalurosa.addActionListener(e -> btnCiudadMasCalurosaclick());
        toolbar.add(btnCiudadMasCalurosa);

        JButton btnGrafica = new JButton();
        btnGrafica.setIcon(Iconografica);
        btnGrafica.setToolTipText("Ver gráfica");
        btnGrafica.addActionListener(e -> btnGraficaclick());
        toolbar.add(btnGrafica);



        JPanel pnltemp = new JPanel();
        pnltemp.setLayout(new BoxLayout(pnltemp, BoxLayout.Y_AXIS));
        add(pnltemp, BorderLayout.CENTER);



        JPanel pnlDatostemp = new JPanel();
        pnlDatostemp.setPreferredSize(new Dimension(pnlDatostemp.getWidth(), 50)); 
        pnlDatostemp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        pnlDatostemp.setLayout(null);

        JLabel lblDesde = new JLabel("Desde:");
        lblDesde.setBounds(10, 10, 50, 25);
        pnlDatostemp.add(lblDesde);

        dccDesde = new DateChooserCombo();
        dccDesde.setBounds(60, 10, 100, 25);
        pnlDatostemp.add(dccDesde);


        JLabel lblHasta = new JLabel("Hasta:");
        lblHasta.setBounds(170, 10, 50, 25);
        pnlDatostemp.add(lblHasta);

        dccHasta = new DateChooserCombo();
        dccHasta.setBounds(220, 10, 100, 25);
        pnlDatostemp.add(dccHasta);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(330, 10, 50, 25);
        pnlDatostemp.add(lblFecha);

        dccEspecifica = new DateChooserCombo();
        dccEspecifica.setBounds(380, 10, 100, 25);
        pnlDatostemp.add(dccEspecifica);
        




        pnlgrafica = new JPanel();
        JScrollPane spgrafica = new JScrollPane(pnlgrafica);

        tpTemperaturaDatos = new JTabbedPane();
        tpTemperaturaDatos.addTab("Gráfica", spgrafica);
        

        pnltemp.add(pnlDatostemp);
        pnltemp.add(tpTemperaturaDatos);
       

        cargarDatos();
    }

    private void cargarDatos() {
       String nombreArchivo = System.getProperty("user.dir") + "/src/Datos/Temperatura.csv";
       datos = TemperaturaServicio.getDatos(nombreArchivo);
    }
    

    private void btnGraficaclick() {
         LocalDate desde = dccDesde.getSelectedDate().toInstant()
            .atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate hasta = dccHasta.getSelectedDate().toInstant()
            .atZone(ZoneId.systemDefault()).toLocalDate();
        TemperaturaControlador.grafica(pnlgrafica, datos, null, desde, hasta);

    }

    private void btnCiudadMasCalurosaclick() {
        LocalDate fecha = dccEspecifica.getSelectedDate().toInstant()
            .atZone(ZoneId.systemDefault()).toLocalDate();
        Temperatura mas = TemperaturaServicio.getCiudadMasCalurosa(datos, fecha);
        Temperatura menos = TemperaturaServicio.getCiudadMenosCalurosa(datos, fecha);
        if (mas == null) {
          JOptionPane.showMessageDialog(this, "No hay datos.");
          return;
        }
        JOptionPane.showMessageDialog(this,
           "Más calurosa: " + mas.getCiudad() + " (" + mas.getTemperatura() + " C)\n" +
           "Menos calurosa: " + menos.getCiudad() + " (" + menos.getTemperatura() + " C)");

        
    }

}
