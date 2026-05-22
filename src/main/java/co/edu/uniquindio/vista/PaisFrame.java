package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.ConfederacionDAO;
import co.edu.uniquindio.dao.PaisDAO;
import co.edu.uniquindio.modelo.Confederacion;
import co.edu.uniquindio.modelo.Pais;
import co.edu.uniquindio.util.ValidacionUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PaisFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtNombre;

    private JComboBox<String> cbEsAnfitrion;
    private JComboBox<Confederacion> cbConfederacion;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaPaises;
    private DefaultTableModel modeloTabla;

    private final PaisDAO paisDAO;
    private final ConfederacionDAO confederacionDAO;

    public PaisFrame() {
        paisDAO = new PaisDAO();
        confederacionDAO = new ConfederacionDAO();

        setTitle("Gestión de Países");
        setSize(800, 520);
        setLocationRelativeTo(null);
        setLayout(null);

        inicializarComponentes();
        cargarComboConfederaciones();
        cargarTabla();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("Gestión de Países");
        lblTitulo.setBounds(340, 15, 200, 25);
        add(lblTitulo);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(40, 60, 130, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(180, 60, 220, 25);
        txtId.setEditable(false);
        add(txtId);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(40, 100, 130, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(180, 100, 220, 25);
        add(txtNombre);

        JLabel lblAnfitrion = new JLabel("¿Es anfitrión?");
        lblAnfitrion.setBounds(40, 140, 130, 25);
        add(lblAnfitrion);

        cbEsAnfitrion = new JComboBox<>();
        cbEsAnfitrion.setBounds(180, 140, 220, 25);
        cbEsAnfitrion.addItem("S");
        cbEsAnfitrion.addItem("N");
        add(cbEsAnfitrion);

        JLabel lblConfederacion = new JLabel("Confederación:");
        lblConfederacion.setBounds(40, 180, 130, 25);
        add(lblConfederacion);

        cbConfederacion = new JComboBox<>();
        cbConfederacion.setBounds(180, 180, 220, 25);
        add(cbConfederacion);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(560, 60, 140, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(560, 105, 140, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(560, 150, 140, 30);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(560, 195, 140, 30);
        add(btnLimpiar);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Anfitrión");
        modeloTabla.addColumn("ID Confederación");

        tablaPaises = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPaises);
        scrollPane.setBounds(40, 260, 700, 180);
        add(scrollPane);

        btnGuardar.addActionListener(e -> guardarPais());
        btnActualizar.addActionListener(e -> actualizarPais());
        btnEliminar.addActionListener(e -> eliminarPais());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaPaises.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
    }

    private void cargarComboConfederaciones() {
        cbConfederacion.removeAllItems();

        List<Confederacion> confederaciones = confederacionDAO.listarConfederaciones();

        for (Confederacion confederacion : confederaciones) {
            cbConfederacion.addItem(confederacion);
        }
    }

    private void guardarPais() {
        if (!validarCampos()) {
            return;
        }

        String nombre = txtNombre.getText().trim();

        if (paisDAO.existeNombrePais(nombre)) {
            JOptionPane.showMessageDialog(this, "Ya existe un país con ese nombre.");
            return;
        }

        Confederacion confederacionSeleccionada = (Confederacion) cbConfederacion.getSelectedItem();

        Pais pais = new Pais(
                nombre,
                cbEsAnfitrion.getSelectedItem().toString(),
                confederacionSeleccionada.getIdConfederacion()
        );

        if (paisDAO.crearPais(pais)) {
            JOptionPane.showMessageDialog(this, "País guardado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el país.");
        }
    }

    private void actualizarPais() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona un país de la tabla.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        int idPais = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText().trim();

        if (paisDAO.existeNombrePaisEditando(nombre, idPais)) {
            JOptionPane.showMessageDialog(this, "Ya existe otro país con ese nombre.");
            return;
        }

        Confederacion confederacionSeleccionada = (Confederacion) cbConfederacion.getSelectedItem();

        Pais pais = new Pais(
                idPais,
                nombre,
                cbEsAnfitrion.getSelectedItem().toString(),
                confederacionSeleccionada.getIdConfederacion()
        );

        if (paisDAO.actualizarPais(pais)) {
            JOptionPane.showMessageDialog(this, "País actualizado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el país.");
        }
    }

    private void eliminarPais() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona un país de la tabla.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar este país?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        int idPais = Integer.parseInt(txtId.getText());

        if (paisDAO.eliminarPais(idPais)) {
            JOptionPane.showMessageDialog(this, "País eliminado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar. Puede que el país tenga ciudades o equipos asociados."
            );
        }
    }

    private boolean validarCampos() {
        if (ValidacionUtil.estaVacio(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "El nombre del país es obligatorio.");
            return false;
        }

        if (!ValidacionUtil.longitudMaxima(txtNombre.getText(), 80)) {
            JOptionPane.showMessageDialog(this, "El nombre del país no puede superar 80 caracteres.");
            return false;
        }

        if (cbEsAnfitrion.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar si el país es anfitrión.");
            return false;
        }

        if (!ValidacionUtil.esAnfitrionValido(cbEsAnfitrion.getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(this, "El valor de anfitrión debe ser S o N.");
            return false;
        }

        if (cbConfederacion.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una confederación.");
            return false;
        }

        return true;
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        List<Pais> paises = paisDAO.listarPaises();

        for (Pais pais : paises) {
            Object[] fila = {
                    pais.getIdPais(),
                    pais.getNombre(),
                    pais.getEsAnfitrion(),
                    pais.getIdConfederacion()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void seleccionarFila() {
        int filaSeleccionada = tablaPaises.getSelectedRow();

        if (filaSeleccionada >= 0) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            cbEsAnfitrion.setSelectedItem(modeloTabla.getValueAt(filaSeleccionada, 2).toString());

            int idConfederacion = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            seleccionarConfederacionPorId(idConfederacion);
        }
    }

    private void seleccionarConfederacionPorId(int idConfederacion) {
        for (int i = 0; i < cbConfederacion.getItemCount(); i++) {
            Confederacion confederacion = cbConfederacion.getItemAt(i);

            if (confederacion.getIdConfederacion() == idConfederacion) {
                cbConfederacion.setSelectedIndex(i);
                return;
            }
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");

        if (cbEsAnfitrion.getItemCount() > 0) {
            cbEsAnfitrion.setSelectedIndex(0);
        }

        if (cbConfederacion.getItemCount() > 0) {
            cbConfederacion.setSelectedIndex(0);
        }

        tablaPaises.clearSelection();
    }
}