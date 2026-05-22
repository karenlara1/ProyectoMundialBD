package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.GrupoDAO;
import co.edu.uniquindio.modelo.Grupo;
import co.edu.uniquindio.util.ValidacionUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class GrupoFrame extends JFrame {

    private JTextField txtId;
    private JComboBox<String> cbNombreGrupo;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaGrupos;
    private DefaultTableModel modeloTabla;

    private final GrupoDAO grupoDAO;

    public GrupoFrame() {
        grupoDAO = new GrupoDAO();

        setTitle("Gestión de Grupos");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(null);

        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("Gestión de Grupos");
        lblTitulo.setBounds(230, 15, 200, 25);
        add(lblTitulo);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(40, 60, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150, 60, 150, 25);
        txtId.setEditable(false);
        add(txtId);

        JLabel lblNombre = new JLabel("Grupo:");
        lblNombre.setBounds(40, 100, 100, 25);
        add(lblNombre);

        cbNombreGrupo = new JComboBox<>();
        cbNombreGrupo.setBounds(150, 100, 150, 25);
        cargarComboGrupos();
        add(cbNombreGrupo);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(350, 55, 120, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(350, 95, 120, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(350, 135, 120, 30);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(350, 175, 120, 30);
        add(btnLimpiar);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Grupo");

        tablaGrupos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaGrupos);
        scrollPane.setBounds(40, 230, 500, 150);
        add(scrollPane);

        btnGuardar.addActionListener(e -> guardarGrupo());
        btnActualizar.addActionListener(e -> actualizarGrupo());
        btnEliminar.addActionListener(e -> eliminarGrupo());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaGrupos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
    }

    private void cargarComboGrupos() {
        cbNombreGrupo.removeAllItems();
        cbNombreGrupo.addItem("Seleccione");

        for (char letra = 'A'; letra <= 'L'; letra++) {
            cbNombreGrupo.addItem(String.valueOf(letra));
        }
    }

    private void guardarGrupo() {
        String nombreGrupo = obtenerGrupoSeleccionado();

        if (!validarGrupo(nombreGrupo)) {
            return;
        }

        if (grupoDAO.existeNombreGrupo(nombreGrupo)) {
            JOptionPane.showMessageDialog(this, "Ya existe el grupo " + nombreGrupo + ".");
            return;
        }

        Grupo grupo = new Grupo(nombreGrupo);

        if (grupoDAO.crearGrupo(grupo)) {
            JOptionPane.showMessageDialog(this, "Grupo guardado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el grupo.");
        }
    }

    private void actualizarGrupo() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona un grupo de la tabla.");
            return;
        }

        String nombreGrupo = obtenerGrupoSeleccionado();

        if (!validarGrupo(nombreGrupo)) {
            return;
        }

        int idGrupo = Integer.parseInt(txtId.getText());
        Grupo grupo = new Grupo(idGrupo, nombreGrupo);

        if (grupoDAO.actualizarGrupo(grupo)) {
            JOptionPane.showMessageDialog(this, "Grupo actualizado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el grupo.");
        }
    }

    private void eliminarGrupo() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona un grupo de la tabla.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar este grupo?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        int idGrupo = Integer.parseInt(txtId.getText());

        if (grupoDAO.eliminarGrupo(idGrupo)) {
            JOptionPane.showMessageDialog(this, "Grupo eliminado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar. Puede que el grupo tenga equipos asociados."
            );
        }
    }

    private boolean validarGrupo(String nombreGrupo) {
        if (ValidacionUtil.estaVacio(nombreGrupo) || nombreGrupo.equals("Seleccione")) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un grupo.");
            return false;
        }

        if (!ValidacionUtil.esGrupoValido(nombreGrupo)) {
            JOptionPane.showMessageDialog(this, "El grupo debe estar entre A y L.");
            return false;
        }

        return true;
    }

    private String obtenerGrupoSeleccionado() {
        Object seleccionado = cbNombreGrupo.getSelectedItem();

        if (seleccionado == null) {
            return "";
        }

        return seleccionado.toString();
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        List<Grupo> grupos = grupoDAO.listarGrupos();

        for (Grupo grupo : grupos) {
            Object[] fila = {
                    grupo.getIdGrupo(),
                    grupo.getNombre()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void seleccionarFila() {
        int filaSeleccionada = tablaGrupos.getSelectedRow();

        if (filaSeleccionada >= 0) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            cbNombreGrupo.setSelectedItem(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        cbNombreGrupo.setSelectedIndex(0);
        tablaGrupos.clearSelection();
    }
}