package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.BitacoraDAO;
import co.edu.uniquindio.modelo.Bitacora;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class BitacoraFrame extends JFrame {

    private JTable tablaBitacora;
    private DefaultTableModel modeloTabla;

    private JTextField txtFechaHoraInicio;
    private JTextField txtFechaHoraFin;

    private JButton btnActualizar;
    private JButton btnConsultar;
    private JButton btnLimpiar;

    private final DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");

    private final DateTimeFormatter formatoFiltro =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public BitacoraFrame() {

        setTitle("Bitácora del Sistema");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Bitácora del Sistema");
        lblTitulo.setBounds(370, 15, 200, 25);
        add(lblTitulo);

        JLabel lblFechaHoraInicio = new JLabel("Fecha/hora inicio:");
        lblFechaHoraInicio.setBounds(20, 55, 140, 25);
        add(lblFechaHoraInicio);

        txtFechaHoraInicio = new JTextField();
        txtFechaHoraInicio.setBounds(160, 55, 180, 25);
        add(txtFechaHoraInicio);

        JLabel lblFormatoInicio = new JLabel("yyyy-MM-dd HH:mm");
        lblFormatoInicio.setBounds(350, 55, 140, 25);
        add(lblFormatoInicio);

        JLabel lblFechaHoraFin = new JLabel("Fecha/hora fin:");
        lblFechaHoraFin.setBounds(20, 90, 140, 25);
        add(lblFechaHoraFin);

        txtFechaHoraFin = new JTextField();
        txtFechaHoraFin.setBounds(160, 90, 180, 25);
        add(txtFechaHoraFin);

        JLabel lblFormatoFin = new JLabel("yyyy-MM-dd HH:mm");
        lblFormatoFin.setBounds(350, 90, 140, 25);
        add(lblFormatoFin);

        btnConsultar = new JButton("Consultar filtro");
        btnConsultar.setBounds(530, 55, 150, 30);
        add(btnConsultar);

        btnActualizar = new JButton("Ver todo");
        btnActualizar.setBounds(700, 55, 130, 30);
        add(btnActualizar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(620, 95, 130, 30);
        add(btnLimpiar);

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "ID Usuario",
                        "Usuario",
                        "Ingreso",
                        "Salida"
                }, 0
        );

        tablaBitacora = new JTable(modeloTabla);

        JScrollPane scroll = new JScrollPane(tablaBitacora);
        scroll.setBounds(20, 145, 840, 280);

        add(scroll);

        btnActualizar.addActionListener(e -> cargarBitacora());
        btnConsultar.addActionListener(e -> consultarBitacoraPorFechaHora());
        btnLimpiar.addActionListener(e -> limpiarFiltros());

        cargarBitacora();
    }

    private void cargarBitacora() {

        modeloTabla.setRowCount(0);

        BitacoraDAO dao = new BitacoraDAO();

        List<Bitacora> lista = dao.listarBitacora();

        cargarDatosEnTabla(lista);
    }

    private void consultarBitacoraPorFechaHora() {
        if (!validarFiltros()) {
            return;
        }

        LocalDateTime fechaHoraInicio = LocalDateTime.parse(
                txtFechaHoraInicio.getText().trim(),
                formatoFiltro
        );

        LocalDateTime fechaHoraFin = LocalDateTime.parse(
                txtFechaHoraFin.getText().trim(),
                formatoFiltro
        );

        if (fechaHoraInicio.isAfter(fechaHoraFin)) {
            JOptionPane.showMessageDialog(this, "La fecha/hora inicio no puede ser mayor que la fecha/hora fin.");
            return;
        }

        BitacoraDAO dao = new BitacoraDAO();

        List<Bitacora> lista = dao.listarBitacoraPorFechaHora(
                fechaHoraInicio,
                fechaHoraFin
        );

        cargarDatosEnTabla(lista);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron registros en ese rango de fecha y hora.");
        }
    }

    private boolean validarFiltros() {
        if (txtFechaHoraInicio.getText() == null || txtFechaHoraInicio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar la fecha/hora de inicio.");
            return false;
        }

        if (txtFechaHoraFin.getText() == null || txtFechaHoraFin.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar la fecha/hora de fin.");
            return false;
        }

        try {
            LocalDateTime.parse(txtFechaHoraInicio.getText().trim(), formatoFiltro);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "La fecha/hora de inicio debe tener formato yyyy-MM-dd HH:mm.");
            return false;
        }

        try {
            LocalDateTime.parse(txtFechaHoraFin.getText().trim(), formatoFiltro);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "La fecha/hora de fin debe tener formato yyyy-MM-dd HH:mm.");
            return false;
        }

        return true;
    }

    private void cargarDatosEnTabla(List<Bitacora> lista) {
        modeloTabla.setRowCount(0);

        for (Bitacora b : lista) {

            String ingreso = b.getFechaHoraIngreso() != null
                    ? b.getFechaHoraIngreso().format(formato)
                    : "";

            String salida = b.getFechaHoraSalida() != null
                    ? b.getFechaHoraSalida().format(formato)
                    : "Sesión activa";

            modeloTabla.addRow(new Object[]{
                    b.getIdBitacora(),
                    b.getIdUsuario(),
                    b.getUsername(),
                    ingreso,
                    salida
            });
        }
    }

    private void limpiarFiltros() {
        txtFechaHoraInicio.setText("");
        txtFechaHoraFin.setText("");
        cargarBitacora();
    }
}