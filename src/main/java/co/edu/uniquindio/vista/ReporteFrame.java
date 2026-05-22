package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.ConfederacionDAO;
import co.edu.uniquindio.dao.EquipoDAO;
import co.edu.uniquindio.dao.ReporteDAO;
import co.edu.uniquindio.modelo.Confederacion;
import co.edu.uniquindio.modelo.Equipo;
import co.edu.uniquindio.util.ValidacionUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ReporteFrame extends JFrame {

    private JComboBox<String> cbReporte;
    private JComboBox<Equipo> cbEquipo;
    private JComboBox<Confederacion> cbConfederacion;

    private JTextField txtPesoMinimo;
    private JTextField txtPesoMaximo;
    private JTextField txtEstaturaMinima;
    private JTextField txtEstaturaMaxima;

    private JButton btnConsultar;
    private JButton btnLimpiar;

    private JTable tablaReportes;
    private DefaultTableModel modeloTabla;

    private final ReporteDAO reporteDAO;
    private final EquipoDAO equipoDAO;
    private final ConfederacionDAO confederacionDAO;

    public ReporteFrame() {
        reporteDAO = new ReporteDAO();
        equipoDAO = new EquipoDAO();
        confederacionDAO = new ConfederacionDAO();

        setTitle("Consultas y Reportes");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        inicializarComponentes();
        cargarCombos();
        configurarCamposPorReporte();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("Consultas y Reportes");
        lblTitulo.setBounds(390, 15, 250, 25);
        add(lblTitulo);

        JLabel lblReporte = new JLabel("Tipo de reporte:");
        lblReporte.setBounds(40, 60, 140, 25);
        add(lblReporte);

        cbReporte = new JComboBox<>();
        cbReporte.setBounds(190, 60, 380, 25);
        cbReporte.addItem("Jugador más costoso por confederación");
        cbReporte.addItem("Cantidad de jugadores menores de 21 años por equipo");
        cbReporte.addItem("Jugadores filtrados por peso, estatura y equipo");
        cbReporte.addItem("Valor total de jugadores por equipo y confederación");
        add(cbReporte);

        JLabel lblEquipo = new JLabel("Equipo:");
        lblEquipo.setBounds(40, 100, 140, 25);
        add(lblEquipo);

        cbEquipo = new JComboBox<>();
        cbEquipo.setBounds(190, 100, 250, 25);
        add(cbEquipo);

        JLabel lblConfederacion = new JLabel("Confederación:");
        lblConfederacion.setBounds(40, 140, 140, 25);
        add(lblConfederacion);

        cbConfederacion = new JComboBox<>();
        cbConfederacion.setBounds(190, 140, 250, 25);
        add(cbConfederacion);

        JLabel lblPesoMinimo = new JLabel("Peso mínimo:");
        lblPesoMinimo.setBounds(500, 100, 120, 25);
        add(lblPesoMinimo);

        txtPesoMinimo = new JTextField();
        txtPesoMinimo.setBounds(620, 100, 120, 25);
        add(txtPesoMinimo);

        JLabel lblPesoMaximo = new JLabel("Peso máximo:");
        lblPesoMaximo.setBounds(500, 140, 120, 25);
        add(lblPesoMaximo);

        txtPesoMaximo = new JTextField();
        txtPesoMaximo.setBounds(620, 140, 120, 25);
        add(txtPesoMaximo);

        JLabel lblEstaturaMinima = new JLabel("Estatura mín:");
        lblEstaturaMinima.setBounds(500, 180, 120, 25);
        add(lblEstaturaMinima);

        txtEstaturaMinima = new JTextField();
        txtEstaturaMinima.setBounds(620, 180, 120, 25);
        add(txtEstaturaMinima);

        JLabel lblEstaturaMaxima = new JLabel("Estatura máx:");
        lblEstaturaMaxima.setBounds(500, 220, 120, 25);
        add(lblEstaturaMaxima);

        txtEstaturaMaxima = new JTextField();
        txtEstaturaMaxima.setBounds(620, 220, 120, 25);
        add(txtEstaturaMaxima);

        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(780, 100, 120, 30);
        add(btnConsultar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(780, 145, 120, 30);
        add(btnLimpiar);

        modeloTabla = new DefaultTableModel();
        tablaReportes = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaReportes);
        scrollPane.setBounds(40, 290, 860, 230);
        add(scrollPane);

        cbReporte.addActionListener(e -> configurarCamposPorReporte());
        btnConsultar.addActionListener(e -> consultarReporte());
        btnLimpiar.addActionListener(e -> limpiar());
    }

    private void cargarCombos() {
        cargarComboEquipos();
        cargarComboConfederaciones();
    }

    private void cargarComboEquipos() {
        cbEquipo.removeAllItems();

        List<Equipo> equipos = equipoDAO.listarEquipos();

        for (Equipo equipo : equipos) {
            cbEquipo.addItem(equipo);
        }
    }

    private void cargarComboConfederaciones() {
        cbConfederacion.removeAllItems();

        List<Confederacion> confederaciones = confederacionDAO.listarConfederaciones();

        for (Confederacion confederacion : confederaciones) {
            cbConfederacion.addItem(confederacion);
        }
    }

    private void configurarCamposPorReporte() {
        int opcion = cbReporte.getSelectedIndex();

        cbEquipo.setEnabled(false);
        cbConfederacion.setEnabled(false);

        txtPesoMinimo.setEnabled(false);
        txtPesoMaximo.setEnabled(false);
        txtEstaturaMinima.setEnabled(false);
        txtEstaturaMaxima.setEnabled(false);

        if (opcion == 2) {
            cbEquipo.setEnabled(true);
            txtPesoMinimo.setEnabled(true);
            txtPesoMaximo.setEnabled(true);
            txtEstaturaMinima.setEnabled(true);
            txtEstaturaMaxima.setEnabled(true);
        }

        if (opcion == 3) {
            cbConfederacion.setEnabled(true);
        }
    }

    private void consultarReporte() {
        int opcion = cbReporte.getSelectedIndex();

        switch (opcion) {
            case 0:
                consultarJugadorMasCostosoPorConfederacion();
                break;
            case 1:
                consultarMenoresDe21PorEquipo();
                break;
            case 2:
                consultarJugadoresFiltrados();
                break;
            case 3:
                consultarValorTotalPorEquipoYConfederacion();
                break;
            default:
                JOptionPane.showMessageDialog(this, "Selecciona un reporte válido.");
                break;
        }
    }

    private void consultarJugadorMasCostosoPorConfederacion() {
        String[] columnas = {"Confederación", "Jugador", "Equipo", "Valor"};
        prepararTabla(columnas);

        List<Object[]> resultados = reporteDAO.obtenerJugadorMasCostosoPorConfederacion();

        for (Object[] fila : resultados) {
            modeloTabla.addRow(fila);
        }
    }

    private void consultarMenoresDe21PorEquipo() {
        String[] columnas = {"Equipo", "Cantidad menores de 21"};
        prepararTabla(columnas);

        List<Object[]> resultados = reporteDAO.obtenerCantidadJugadoresMenores21PorEquipo();

        for (Object[] fila : resultados) {
            modeloTabla.addRow(fila);
        }
    }

    private void consultarJugadoresFiltrados() {
        if (!validarFiltrosJugadores()) {
            return;
        }

        Equipo equipoSeleccionado = (Equipo) cbEquipo.getSelectedItem();

        double pesoMinimo = ValidacionUtil.convertirDecimal(txtPesoMinimo.getText());
        double pesoMaximo = ValidacionUtil.convertirDecimal(txtPesoMaximo.getText());
        double estaturaMinima = ValidacionUtil.convertirDecimal(txtEstaturaMinima.getText());
        double estaturaMaxima = ValidacionUtil.convertirDecimal(txtEstaturaMaxima.getText());

        String[] columnas = {"Jugador", "Equipo", "Peso", "Estatura", "Valor"};
        prepararTabla(columnas);

        List<Object[]> resultados = reporteDAO.obtenerJugadoresFiltrados(
                pesoMinimo,
                pesoMaximo,
                estaturaMinima,
                estaturaMaxima,
                equipoSeleccionado.getIdEquipo()
        );

        for (Object[] fila : resultados) {
            modeloTabla.addRow(fila);
        }
    }

    private void consultarValorTotalPorEquipoYConfederacion() {
        if (cbConfederacion.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una confederación.");
            return;
        }

        Confederacion confederacionSeleccionada = (Confederacion) cbConfederacion.getSelectedItem();

        String[] columnas = {"Confederación", "Equipo", "Valor total"};
        prepararTabla(columnas);

        List<Object[]> resultados = reporteDAO.obtenerValorTotalJugadoresPorEquipoYConfederacion(
                confederacionSeleccionada.getIdConfederacion()
        );

        for (Object[] fila : resultados) {
            modeloTabla.addRow(fila);
        }
    }

    private boolean validarFiltrosJugadores() {
        if (cbEquipo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un equipo.");
            return false;
        }

        if (!ValidacionUtil.esDecimalValido(txtPesoMinimo.getText())) {
            JOptionPane.showMessageDialog(this, "El peso mínimo debe ser un número válido.");
            return false;
        }

        if (!ValidacionUtil.esDecimalValido(txtPesoMaximo.getText())) {
            JOptionPane.showMessageDialog(this, "El peso máximo debe ser un número válido.");
            return false;
        }

        if (!ValidacionUtil.esDecimalValido(txtEstaturaMinima.getText())) {
            JOptionPane.showMessageDialog(this, "La estatura mínima debe ser un número válido.");
            return false;
        }

        if (!ValidacionUtil.esDecimalValido(txtEstaturaMaxima.getText())) {
            JOptionPane.showMessageDialog(this, "La estatura máxima debe ser un número válido.");
            return false;
        }

        double pesoMinimo = ValidacionUtil.convertirDecimal(txtPesoMinimo.getText());
        double pesoMaximo = ValidacionUtil.convertirDecimal(txtPesoMaximo.getText());
        double estaturaMinima = ValidacionUtil.convertirDecimal(txtEstaturaMinima.getText());
        double estaturaMaxima = ValidacionUtil.convertirDecimal(txtEstaturaMaxima.getText());

        if (pesoMinimo <= 0 || pesoMaximo <= 0) {
            JOptionPane.showMessageDialog(this, "Los pesos deben ser mayores a cero.");
            return false;
        }

        if (estaturaMinima <= 0 || estaturaMaxima <= 0) {
            JOptionPane.showMessageDialog(this, "Las estaturas deben ser mayores a cero.");
            return false;
        }

        if (pesoMinimo > pesoMaximo) {
            JOptionPane.showMessageDialog(this, "El peso mínimo no puede ser mayor que el peso máximo.");
            return false;
        }

        if (estaturaMinima > estaturaMaxima) {
            JOptionPane.showMessageDialog(this, "La estatura mínima no puede ser mayor que la estatura máxima.");
            return false;
        }

        return true;
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

        txtPesoMinimo.setText("");
        txtPesoMaximo.setText("");
        txtEstaturaMinima.setText("");
        txtEstaturaMaxima.setText("");

        if (cbEquipo.getItemCount() > 0) {
            cbEquipo.setSelectedIndex(0);
        }

        if (cbConfederacion.getItemCount() > 0) {
            cbConfederacion.setSelectedIndex(0);
        }
    }
}