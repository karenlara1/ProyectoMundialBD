package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.DirectorTecnicoDAO;
import co.edu.uniquindio.dao.EquipoDAO;
import co.edu.uniquindio.modelo.DirectorTecnico;
import co.edu.uniquindio.modelo.Equipo;
import co.edu.uniquindio.util.ValidacionUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DirectorTecnicoFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtFechaNacimiento;
    private JTextField txtNacionalidad;

    private JComboBox<Equipo> cbEquipo;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaDirectores;
    private DefaultTableModel modeloTabla;

    private final DirectorTecnicoDAO directorTecnicoDAO;
    private final EquipoDAO equipoDAO;

    public DirectorTecnicoFrame() {
        directorTecnicoDAO = new DirectorTecnicoDAO();
        equipoDAO = new EquipoDAO();

        setTitle("Gestión de Directores Técnicos");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setLayout(null);

        inicializarComponentes();
        cargarComboEquipos();
        cargarTabla();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("Gestión de Directores Técnicos");
        lblTitulo.setBounds(320, 15, 250, 25);
        add(lblTitulo);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(40, 60, 140, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(190, 60, 210, 25);
        txtId.setEditable(false);
        add(txtId);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(40, 100, 140, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(190, 100, 210, 25);
        add(txtNombre);

        JLabel lblFecha = new JLabel("Fecha nacimiento:");
        lblFecha.setBounds(40, 140, 140, 25);
        add(lblFecha);

        txtFechaNacimiento = new JTextField();
        txtFechaNacimiento.setBounds(190, 140, 210, 25);
        add(txtFechaNacimiento);

        JLabel lblFormato = new JLabel("Formato: yyyy-MM-dd");
        lblFormato.setBounds(410, 140, 180, 25);
        add(lblFormato);

        JLabel lblNacionalidad = new JLabel("Nacionalidad:");
        lblNacionalidad.setBounds(40, 180, 140, 25);
        add(lblNacionalidad);

        txtNacionalidad = new JTextField();
        txtNacionalidad.setBounds(190, 180, 210, 25);
        add(txtNacionalidad);

        JLabel lblEquipo = new JLabel("Equipo:");
        lblEquipo.setBounds(40, 220, 140, 25);
        add(lblEquipo);

        cbEquipo = new JComboBox<>();
        cbEquipo.setBounds(190, 220, 210, 25);
        add(cbEquipo);

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
        modeloTabla.addColumn("Fecha Nacimiento");
        modeloTabla.addColumn("Nacionalidad");
        modeloTabla.addColumn("ID Equipo");

        tablaDirectores = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaDirectores);
        scrollPane.setBounds(40, 290, 760, 180);
        add(scrollPane);

        btnGuardar.addActionListener(e -> guardarDirectorTecnico());
        btnActualizar.addActionListener(e -> actualizarDirectorTecnico());
        btnEliminar.addActionListener(e -> eliminarDirectorTecnico());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaDirectores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
    }

    private void cargarComboEquipos() {
        cbEquipo.removeAllItems();

        List<Equipo> equipos = equipoDAO.listarEquipos();

        for (Equipo equipo : equipos) {
            cbEquipo.addItem(equipo);
        }
    }

    private void guardarDirectorTecnico() {
        if (!validarCampos()) {
            return;
        }

        Equipo equipoSeleccionado = (Equipo) cbEquipo.getSelectedItem();

        if (directorTecnicoDAO.existeDirectorParaEquipo(equipoSeleccionado.getIdEquipo())) {
            JOptionPane.showMessageDialog(this, "Ese equipo ya tiene un director técnico registrado.");
            return;
        }

        DirectorTecnico directorTecnico = new DirectorTecnico(
                txtNombre.getText().trim(),
                ValidacionUtil.convertirFecha(txtFechaNacimiento.getText()),
                txtNacionalidad.getText().trim(),
                equipoSeleccionado.getIdEquipo()
        );

        if (directorTecnicoDAO.crearDirectorTecnico(directorTecnico)) {
            JOptionPane.showMessageDialog(this, "Director técnico guardado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el director técnico.");
        }
    }

    private void actualizarDirectorTecnico() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona un director técnico de la tabla.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        int idDirectorTecnico = Integer.parseInt(txtId.getText());
        Equipo equipoSeleccionado = (Equipo) cbEquipo.getSelectedItem();

        if (directorTecnicoDAO.existeDirectorParaEquipoEditando(
                equipoSeleccionado.getIdEquipo(),
                idDirectorTecnico
        )) {
            JOptionPane.showMessageDialog(this, "Ese equipo ya tiene otro director técnico registrado.");
            return;
        }

        DirectorTecnico directorTecnico = new DirectorTecnico(
                idDirectorTecnico,
                txtNombre.getText().trim(),
                ValidacionUtil.convertirFecha(txtFechaNacimiento.getText()),
                txtNacionalidad.getText().trim(),
                equipoSeleccionado.getIdEquipo()
        );

        if (directorTecnicoDAO.actualizarDirectorTecnico(directorTecnico)) {
            JOptionPane.showMessageDialog(this, "Director técnico actualizado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el director técnico.");
        }
    }

    private void eliminarDirectorTecnico() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona un director técnico de la tabla.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar este director técnico?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        int idDirectorTecnico = Integer.parseInt(txtId.getText());

        if (directorTecnicoDAO.eliminarDirectorTecnico(idDirectorTecnico)) {
            JOptionPane.showMessageDialog(this, "Director técnico eliminado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el director técnico.");
        }
    }

    private boolean validarCampos() {
        if (ValidacionUtil.estaVacio(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "El nombre del director técnico es obligatorio.");
            return false;
        }

        if (ValidacionUtil.estaVacio(txtFechaNacimiento.getText())) {
            JOptionPane.showMessageDialog(this, "La fecha de nacimiento es obligatoria.");
            return false;
        }

        if (!ValidacionUtil.esFechaValida(txtFechaNacimiento.getText())) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener el formato yyyy-MM-dd.");
            return false;
        }

        if (!ValidacionUtil.fechaNoFutura(ValidacionUtil.convertirFecha(txtFechaNacimiento.getText()))) {
            JOptionPane.showMessageDialog(this, "La fecha de nacimiento no puede ser futura.");
            return false;
        }

        if (ValidacionUtil.estaVacio(txtNacionalidad.getText())) {
            JOptionPane.showMessageDialog(this, "La nacionalidad es obligatoria.");
            return false;
        }

        if (cbEquipo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un equipo.");
            return false;
        }

        return true;
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        List<DirectorTecnico> directores = directorTecnicoDAO.listarDirectoresTecnicos();

        for (DirectorTecnico director : directores) {
            Object[] fila = {
                    director.getIdDirectorTecnico(),
                    director.getNombre(),
                    director.getFechaNacimiento(),
                    director.getNacionalidad(),
                    director.getIdEquipo()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void seleccionarFila() {
        int filaSeleccionada = tablaDirectores.getSelectedRow();

        if (filaSeleccionada >= 0) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtFechaNacimiento.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtNacionalidad.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());

            int idEquipo = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            seleccionarEquipoPorId(idEquipo);
        }
    }

    private void seleccionarEquipoPorId(int idEquipo) {
        for (int i = 0; i < cbEquipo.getItemCount(); i++) {
            Equipo equipo = cbEquipo.getItemAt(i);

            if (equipo.getIdEquipo() == idEquipo) {
                cbEquipo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtFechaNacimiento.setText("");
        txtNacionalidad.setText("");

        if (cbEquipo.getItemCount() > 0) {
            cbEquipo.setSelectedIndex(0);
        }

        tablaDirectores.clearSelection();
    }
}