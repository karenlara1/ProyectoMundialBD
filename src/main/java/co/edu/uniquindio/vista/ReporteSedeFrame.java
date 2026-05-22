package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.EstadioDAO;
import co.edu.uniquindio.dao.ReporteSedeDAO;
import co.edu.uniquindio.modelo.Estadio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ReporteSedeFrame extends JFrame {

    private JComboBox<String> cbReporte;
    private JComboBox<Estadio> cbEstadio;

    private JButton btnConsultar;
    private JButton btnLimpiar;

    private JTable tablaReportes;
    private DefaultTableModel modeloTabla;

    private final ReporteSedeDAO reporteSedeDAO;
    private final EstadioDAO estadioDAO;

    public ReporteSedeFrame() {
        reporteSedeDAO = new ReporteSedeDAO();
        estadioDAO = new EstadioDAO();

        setTitle("Reportes de Sedes y Partidos");
        setSize(1050, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        inicializarComponentes();
        cargarComboEstadios();
        configurarCamposPorReporte();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("Reportes de Sedes y Partidos");
        lblTitulo.setBounds(420, 15, 300, 25);
        add(lblTitulo);

        JLabel lblReporte = new JLabel("Tipo de reporte:");
        lblReporte.setBounds(40, 60, 140, 25);
        add(lblReporte);

        cbReporte = new JComboBox<>();
        cbReporte.setBounds(190, 60, 430, 25);
        cbReporte.addItem("Partidos por estadio");
        cbReporte.addItem("Equipo más costoso que juega en cada país anfitrión");
        cbReporte.addItem("Países y partidos que se jugarán en cada país anfitrión");
        cbReporte.addItem("Cantidad de partidos por país anfitrión");
        add(cbReporte);

        JLabel lblEstadio = new JLabel("Estadio:");
        lblEstadio.setBounds(40, 105, 140, 25);
        add(lblEstadio);

        cbEstadio = new JComboBox<>();
        cbEstadio.setBounds(190, 105, 430, 25);
        add(cbEstadio);

        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(700, 60, 140, 30);
        add(btnConsultar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(700, 105, 140, 30);
        add(btnLimpiar);

        modeloTabla = new DefaultTableModel();
        tablaReportes = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaReportes);
        scrollPane.setBounds(40, 180, 960, 330);
        add(scrollPane);

        cbReporte.addActionListener(e -> configurarCamposPorReporte());
        btnConsultar.addActionListener(e -> consultarReporte());
        btnLimpiar.addActionListener(e -> limpiar());
    }

    private void cargarComboEstadios() {
        cbEstadio.removeAllItems();

        List<Estadio> estadios = estadioDAO.listarEstadios();

        for (Estadio estadio : estadios) {
            cbEstadio.addItem(estadio);
        }
    }

    private void configurarCamposPorReporte() {
        int opcion = cbReporte.getSelectedIndex();

        cbEstadio.setEnabled(opcion == 0);
    }

    private void consultarReporte() {
        int opcion = cbReporte.getSelectedIndex();

        switch (opcion) {
            case 0:
                consultarPartidosPorEstadio();
                break;

            case 1:
                consultarEquipoMasCostosoPorPaisAnfitrion();
                break;

            case 2:
                consultarPartidosPorPaisAnfitrion();
                break;

            case 3:
                consultarCantidadPartidosPorPaisAnfitrion();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Selecciona un reporte válido.");
                break;
        }
    }

    private void consultarPartidosPorEstadio() {
        if (cbEstadio.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un estadio.");
            return;
        }

        Estadio estadioSeleccionado = (Estadio) cbEstadio.getSelectedItem();

        String[] columnas = {
                "Estadio",
                "Fecha",
                "Hora",
                "Equipo Local",
                "Equipo Visitante",
                "Grupo",
                "Estado"
        };

        prepararTabla(columnas);

        List<Object[]> resultados = reporteSedeDAO.obtenerPartidosPorEstadio(
                estadioSeleccionado.getIdEstadio()
        );

        for (Object[] fila : resultados) {
            modeloTabla.addRow(fila);
        }
    }

    private void consultarEquipoMasCostosoPorPaisAnfitrion() {
        String[] columnas = {
                "País Anfitrión",
                "Equipo",
                "Valor Total Jugadores"
        };

        prepararTabla(columnas);

        List<Object[]> resultados = reporteSedeDAO.obtenerEquipoMasCostosoPorPaisAnfitrion();

        for (Object[] fila : resultados) {
            modeloTabla.addRow(fila);
        }
    }

    private void consultarPartidosPorPaisAnfitrion() {
        String[] columnas = {
                "País Anfitrión",
                "Ciudad",
                "Estadio",
                "Fecha",
                "Hora",
                "Equipo Local",
                "Equipo Visitante",
                "Grupo",
                "Estado"
        };

        prepararTabla(columnas);

        List<Object[]> resultados = reporteSedeDAO.obtenerPartidosPorPaisAnfitrion();

        for (Object[] fila : resultados) {
            modeloTabla.addRow(fila);
        }
    }

    private void consultarCantidadPartidosPorPaisAnfitrion() {
        String[] columnas = {
                "País Anfitrión",
                "Cantidad de Partidos"
        };

        prepararTabla(columnas);

        List<Object[]> resultados = reporteSedeDAO.obtenerCantidadPartidosPorPaisAnfitrion();

        for (Object[] fila : resultados) {
            modeloTabla.addRow(fila);
        }
    }

    private void prepararTabla(String[] columnas) {
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnCount(0);

        for (String columna : columnas) {
            modeloTabla.addColumn(columna);
        }
    }

    private void limpiar() {
        modeloTabla.setRowCount(0);

        if (cbEstadio.getItemCount() > 0) {
            cbEstadio.setSelectedIndex(0);
        }
    }
}