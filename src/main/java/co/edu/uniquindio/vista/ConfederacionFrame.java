package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.ConfederacionDAO;
import co.edu.uniquindio.modelo.Confederacion;
import co.edu.uniquindio.util.ValidacionUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ConfederacionFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtContinente;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaConfederaciones;
    private DefaultTableModel modeloTabla;

    private final ConfederacionDAO confederacionDAO;

    public ConfederacionFrame() {
        confederacionDAO = new ConfederacionDAO();

        setTitle("Gestión de Confederaciones");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(null);

        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("Gestión de Confederaciones");
        lblTitulo.setBounds(250, 15, 250, 25);
        add(lblTitulo);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(40, 60, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150, 60, 180, 25);
        txtId.setEditable(false);
        add(txtId);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(40, 100, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(150, 100, 180, 25);
        add(txtNombre);

        JLabel lblContinente = new JLabel("Continente:");
        lblContinente.setBounds(40, 140, 100, 25);
        add(lblContinente);

        txtContinente = new JTextField();
        txtContinente.setBounds(150, 140, 180, 25);
        add(txtContinente);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(380, 60, 120, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(520, 60, 120, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(380, 110, 120, 30);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(520, 110, 120, 30);
        add(btnLimpiar);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Continente");

        tablaConfederaciones = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaConfederaciones);
        scrollPane.setBounds(40, 200, 600, 220);
        add(scrollPane);

        btnGuardar.addActionListener(e -> guardarConfederacion());
        btnActualizar.addActionListener(e -> actualizarConfederacion());
        btnEliminar.addActionListener(e -> eliminarConfederacion());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaConfederaciones.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
    }

    private void guardarConfederacion() {
        if (!validarCampos()) {
            return;
        }

        String nombre = txtNombre.getText().trim();
        String continente = txtContinente.getText().trim();

        if (confederacionDAO.existeNombreConfederacion(nombre)) {
            JOptionPane.showMessageDialog(this, "Ya existe una confederación con ese nombre.");
            return;
        }

        Confederacion confederacion = new Confederacion(nombre, continente);

        if (confederacionDAO.crearConfederacion(confederacion)) {
            JOptionPane.showMessageDialog(this, "Confederación guardada correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar la confederación.");
        }
    }

    private void actualizarConfederacion() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona una confederación de la tabla.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        int idConfederacion = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText().trim();
        String continente = txtContinente.getText().trim();

        Confederacion confederacion = new Confederacion(idConfederacion, nombre, continente);

        if (confederacionDAO.actualizarConfederacion(confederacion)) {
            JOptionPane.showMessageDialog(this, "Confederación actualizada correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la confederación.");
        }
    }

    private void eliminarConfederacion() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona una confederación de la tabla.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar esta confederación?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        int idConfederacion = Integer.parseInt(txtId.getText());

        if (confederacionDAO.eliminarConfederacion(idConfederacion)) {
            JOptionPane.showMessageDialog(this, "Confederación eliminada correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar. Puede que la confederación tenga países asociados."
            );
        }
    }

    private boolean validarCampos() {
        if (ValidacionUtil.estaVacio(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "El nombre de la confederación es obligatorio.");
            return false;
        }

        if (ValidacionUtil.estaVacio(txtContinente.getText())) {
            JOptionPane.showMessageDialog(this, "El continente es obligatorio.");
            return false;
        }

        return true;
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        List<Confederacion> confederaciones = confederacionDAO.listarConfederaciones();

        for (Confederacion confederacion : confederaciones) {
            Object[] fila = {
                    confederacion.getIdConfederacion(),
                    confederacion.getNombre(),
                    confederacion.getContinente()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void seleccionarFila() {
        int filaSeleccionada = tablaConfederaciones.getSelectedRow();

        if (filaSeleccionada >= 0) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtContinente.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtContinente.setText("");
        tablaConfederaciones.clearSelection();
    }
}