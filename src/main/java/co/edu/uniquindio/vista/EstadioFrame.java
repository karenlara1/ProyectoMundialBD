package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.CiudadDAO;
import co.edu.uniquindio.dao.EstadioDAO;
import co.edu.uniquindio.modelo.Ciudad;
import co.edu.uniquindio.modelo.Estadio;
import co.edu.uniquindio.util.ValidacionUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class EstadioFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtCapacidad;
    private JTextField txtDireccion;

    private JComboBox<Ciudad> cbCiudad;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaEstadios;
    private DefaultTableModel modeloTabla;

    private final EstadioDAO estadioDAO;
    private final CiudadDAO ciudadDAO;

    public EstadioFrame() {
        estadioDAO = new EstadioDAO();
        ciudadDAO = new CiudadDAO();

        setTitle("Gestión de Estadios");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setLayout(null);

        inicializarComponentes();
        cargarComboCiudades();
        cargarTabla();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("Gestión de Estadios");
        lblTitulo.setBounds(350, 15, 200, 25);
        add(lblTitulo);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(40, 60, 120, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(170, 60, 240, 25);
        txtId.setEditable(false);
        add(txtId);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(40, 100, 120, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(170, 100, 240, 25);
        add(txtNombre);

        JLabel lblCapacidad = new JLabel("Capacidad:");
        lblCapacidad.setBounds(40, 140, 120, 25);
        add(lblCapacidad);

        txtCapacidad = new JTextField();
        txtCapacidad.setBounds(170, 140, 240, 25);
        add(txtCapacidad);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(40, 180, 120, 25);
        add(lblDireccion);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(170, 180, 240, 25);
        add(txtDireccion);

        JLabel lblCiudad = new JLabel("Ciudad:");
        lblCiudad.setBounds(40, 220, 120, 25);
        add(lblCiudad);

        cbCiudad = new JComboBox<>();
        cbCiudad.setBounds(170, 220, 240, 25);
        add(cbCiudad);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(620, 60, 140, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(620, 105, 140, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(620, 150, 140, 30);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(620, 195, 140, 30);
        add(btnLimpiar);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Capacidad");
        modeloTabla.addColumn("Dirección");
        modeloTabla.addColumn("ID Ciudad");

        tablaEstadios = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaEstadios);
        scrollPane.setBounds(40, 300, 760, 170);
        add(scrollPane);

        btnGuardar.addActionListener(e -> guardarEstadio());
        btnActualizar.addActionListener(e -> actualizarEstadio());
        btnEliminar.addActionListener(e -> eliminarEstadio());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaEstadios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
    }

    private void cargarComboCiudades() {
        cbCiudad.removeAllItems();

        List<Ciudad> ciudades = ciudadDAO.listarCiudades();

        for (Ciudad ciudad : ciudades) {
            cbCiudad.addItem(ciudad);
        }
    }

    private void guardarEstadio() {
        if (!validarCampos()) {
            return;
        }

        String nombre = txtNombre.getText().trim();

        if (estadioDAO.existeNombreEstadio(nombre)) {
            JOptionPane.showMessageDialog(this, "Ya existe un estadio con ese nombre.");
            return;
        }

        Ciudad ciudadSeleccionada = (Ciudad) cbCiudad.getSelectedItem();

        Estadio estadio = new Estadio(
                nombre,
                ValidacionUtil.convertirEntero(txtCapacidad.getText()),
                txtDireccion.getText().trim(),
                ciudadSeleccionada.getIdCiudad()
        );

        if (estadioDAO.crearEstadio(estadio)) {
            JOptionPane.showMessageDialog(this, "Estadio guardado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el estadio.");
        }
    }

    private void actualizarEstadio() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona un estadio de la tabla.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        int idEstadio = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText().trim();

        if (estadioDAO.existeNombreEstadioEditando(nombre, idEstadio)) {
            JOptionPane.showMessageDialog(this, "Ya existe otro estadio con ese nombre.");
            return;
        }

        Ciudad ciudadSeleccionada = (Ciudad) cbCiudad.getSelectedItem();

        Estadio estadio = new Estadio(
                idEstadio,
                nombre,
                ValidacionUtil.convertirEntero(txtCapacidad.getText()),
                txtDireccion.getText().trim(),
                ciudadSeleccionada.getIdCiudad()
        );

        if (estadioDAO.actualizarEstadio(estadio)) {
            JOptionPane.showMessageDialog(this, "Estadio actualizado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el estadio.");
        }
    }

    private void eliminarEstadio() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona un estadio de la tabla.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar este estadio?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        int idEstadio = Integer.parseInt(txtId.getText());

        if (estadioDAO.eliminarEstadio(idEstadio)) {
            JOptionPane.showMessageDialog(this, "Estadio eliminado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar. Puede que el estadio tenga partidos asociados."
            );
        }
    }

    private boolean validarCampos() {
        if (ValidacionUtil.estaVacio(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "El nombre del estadio es obligatorio.");
            return false;
        }

        if (!ValidacionUtil.longitudMaxima(txtNombre.getText(), 100)) {
            JOptionPane.showMessageDialog(this, "El nombre del estadio no puede superar 100 caracteres.");
            return false;
        }

        if (!ValidacionUtil.esEnteroValido(txtCapacidad.getText())) {
            JOptionPane.showMessageDialog(this, "La capacidad debe ser un número entero válido.");
            return false;
        }

        int capacidad = ValidacionUtil.convertirEntero(txtCapacidad.getText());

        if (!ValidacionUtil.esCapacidadValida(capacidad)) {
            JOptionPane.showMessageDialog(this, "La capacidad debe ser mayor a cero.");
            return false;
        }

        if (!ValidacionUtil.esCapacidadEstadioRealista(capacidad)) {
            JOptionPane.showMessageDialog(this, "La capacidad debe estar entre 10.000 y 120.000 espectadores.");
            return false;
        }

        if (ValidacionUtil.estaVacio(txtDireccion.getText())) {
            JOptionPane.showMessageDialog(this, "La dirección del estadio es obligatoria.");
            return false;
        }

        if (!ValidacionUtil.longitudMaxima(txtDireccion.getText(), 150)) {
            JOptionPane.showMessageDialog(this, "La dirección no puede superar 150 caracteres.");
            return false;
        }

        if (cbCiudad.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una ciudad.");
            return false;
        }

        return true;
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        List<Estadio> estadios = estadioDAO.listarEstadios();

        for (Estadio estadio : estadios) {
            Object[] fila = {
                    estadio.getIdEstadio(),
                    estadio.getNombre(),
                    estadio.getCapacidad(),
                    estadio.getDireccion(),
                    estadio.getIdCiudad()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void seleccionarFila() {
        int filaSeleccionada = tablaEstadios.getSelectedRow();

        if (filaSeleccionada >= 0) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtCapacidad.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtDireccion.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());

            int idCiudad = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            seleccionarCiudadPorId(idCiudad);
        }
    }

    private void seleccionarCiudadPorId(int idCiudad) {
        for (int i = 0; i < cbCiudad.getItemCount(); i++) {
            Ciudad ciudad = cbCiudad.getItemAt(i);

            if (ciudad.getIdCiudad() == idCiudad) {
                cbCiudad.setSelectedIndex(i);
                return;
            }
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtCapacidad.setText("");
        txtDireccion.setText("");

        if (cbCiudad.getItemCount() > 0) {
            cbCiudad.setSelectedIndex(0);
        }

        tablaEstadios.clearSelection();
    }
}