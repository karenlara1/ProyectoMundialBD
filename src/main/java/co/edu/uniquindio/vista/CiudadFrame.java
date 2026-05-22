package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.CiudadDAO;
import co.edu.uniquindio.dao.PaisDAO;
import co.edu.uniquindio.modelo.Ciudad;
import co.edu.uniquindio.modelo.Pais;
import co.edu.uniquindio.util.ValidacionUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CiudadFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtNombre;

    private JComboBox<Pais> cbPais;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaCiudades;
    private DefaultTableModel modeloTabla;

    private final CiudadDAO ciudadDAO;
    private final PaisDAO paisDAO;

    public CiudadFrame() {
        ciudadDAO = new CiudadDAO();
        paisDAO = new PaisDAO();

        setTitle("Gestión de Ciudades");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setLayout(null);

        inicializarComponentes();
        cargarComboPaises();
        cargarTabla();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("Gestión de Ciudades");
        lblTitulo.setBounds(300, 15, 200, 25);
        add(lblTitulo);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(40, 60, 120, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(170, 60, 220, 25);
        txtId.setEditable(false);
        add(txtId);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(40, 100, 120, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(170, 100, 220, 25);
        add(txtNombre);

        JLabel lblPais = new JLabel("País:");
        lblPais.setBounds(40, 140, 120, 25);
        add(lblPais);

        cbPais = new JComboBox<>();
        cbPais.setBounds(170, 140, 220, 25);
        add(cbPais);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(530, 60, 130, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(530, 105, 130, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(530, 150, 130, 30);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(530, 195, 130, 30);
        add(btnLimpiar);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("ID País");

        tablaCiudades = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaCiudades);
        scrollPane.setBounds(40, 260, 650, 160);
        add(scrollPane);

        btnGuardar.addActionListener(e -> guardarCiudad());
        btnActualizar.addActionListener(e -> actualizarCiudad());
        btnEliminar.addActionListener(e -> eliminarCiudad());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaCiudades.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
    }

    private void cargarComboPaises() {
        cbPais.removeAllItems();

        List<Pais> paises = paisDAO.listarPaises();

        for (Pais pais : paises) {
            cbPais.addItem(pais);
        }
    }

    private void guardarCiudad() {
        if (!validarCampos()) {
            return;
        }

        String nombre = txtNombre.getText().trim();
        Pais paisSeleccionado = (Pais) cbPais.getSelectedItem();

        if (ciudadDAO.existeCiudadEnPais(nombre, paisSeleccionado.getIdPais())) {
            JOptionPane.showMessageDialog(this, "Ya existe una ciudad con ese nombre en este país.");
            return;
        }

        Ciudad ciudad = new Ciudad(nombre, paisSeleccionado.getIdPais());

        if (ciudadDAO.crearCiudad(ciudad)) {
            JOptionPane.showMessageDialog(this, "Ciudad guardada correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar la ciudad.");
        }
    }

    private void actualizarCiudad() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona una ciudad de la tabla.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        int idCiudad = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText().trim();
        Pais paisSeleccionado = (Pais) cbPais.getSelectedItem();

        if (ciudadDAO.existeCiudadEnPaisEditando(nombre, paisSeleccionado.getIdPais(), idCiudad)) {
            JOptionPane.showMessageDialog(this, "Ya existe otra ciudad con ese nombre en este país.");
            return;
        }

        Ciudad ciudad = new Ciudad(idCiudad, nombre, paisSeleccionado.getIdPais());

        if (ciudadDAO.actualizarCiudad(ciudad)) {
            JOptionPane.showMessageDialog(this, "Ciudad actualizada correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la ciudad.");
        }
    }

    private void eliminarCiudad() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona una ciudad de la tabla.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar esta ciudad?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        int idCiudad = Integer.parseInt(txtId.getText());

        if (ciudadDAO.eliminarCiudad(idCiudad)) {
            JOptionPane.showMessageDialog(this, "Ciudad eliminada correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar. Puede que la ciudad tenga estadios asociados."
            );
        }
    }

    private boolean validarCampos() {
        if (ValidacionUtil.estaVacio(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "El nombre de la ciudad es obligatorio.");
            return false;
        }

        if (!ValidacionUtil.longitudMaxima(txtNombre.getText(), 80)) {
            JOptionPane.showMessageDialog(this, "El nombre de la ciudad no puede superar 80 caracteres.");
            return false;
        }

        if (cbPais.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un país.");
            return false;
        }

        return true;
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        List<Ciudad> ciudades = ciudadDAO.listarCiudades();

        for (Ciudad ciudad : ciudades) {
            Object[] fila = {
                    ciudad.getIdCiudad(),
                    ciudad.getNombre(),
                    ciudad.getIdPais()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void seleccionarFila() {
        int filaSeleccionada = tablaCiudades.getSelectedRow();

        if (filaSeleccionada >= 0) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());

            int idPais = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            seleccionarPaisPorId(idPais);
        }
    }

    private void seleccionarPaisPorId(int idPais) {
        for (int i = 0; i < cbPais.getItemCount(); i++) {
            Pais pais = cbPais.getItemAt(i);

            if (pais.getIdPais() == idPais) {
                cbPais.setSelectedIndex(i);
                return;
            }
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");

        if (cbPais.getItemCount() > 0) {
            cbPais.setSelectedIndex(0);
        }

        tablaCiudades.clearSelection();
    }
}