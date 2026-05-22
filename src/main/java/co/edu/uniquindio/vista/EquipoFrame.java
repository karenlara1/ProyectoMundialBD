package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.EquipoDAO;
import co.edu.uniquindio.dao.GrupoDAO;
import co.edu.uniquindio.dao.PaisDAO;
import co.edu.uniquindio.modelo.Equipo;
import co.edu.uniquindio.modelo.Grupo;
import co.edu.uniquindio.modelo.Pais;
import co.edu.uniquindio.util.ValidacionUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class EquipoFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtFechaFundacion;

    private JComboBox<Pais> cbPais;
    private JComboBox<Grupo> cbGrupo;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;

    private final EquipoDAO equipoDAO;
    private final PaisDAO paisDAO;
    private final GrupoDAO grupoDAO;

    public EquipoFrame() {
        equipoDAO = new EquipoDAO();
        paisDAO = new PaisDAO();
        grupoDAO = new GrupoDAO();

        setTitle("Gestión de Equipos");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setLayout(null);

        inicializarComponentes();
        cargarCombos();
        cargarTabla();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("Gestión de Equipos");
        lblTitulo.setBounds(360, 15, 200, 25);
        add(lblTitulo);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(40, 60, 130, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(180, 60, 200, 25);
        txtId.setEditable(false);
        add(txtId);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(40, 100, 130, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(180, 100, 200, 25);
        add(txtNombre);

        JLabel lblFecha = new JLabel("Fecha fundación:");
        lblFecha.setBounds(40, 140, 130, 25);
        add(lblFecha);

        txtFechaFundacion = new JTextField();
        txtFechaFundacion.setBounds(180, 140, 200, 25);
        add(txtFechaFundacion);

        JLabel lblFormato = new JLabel("Formato: yyyy-MM-dd");
        lblFormato.setBounds(390, 140, 180, 25);
        add(lblFormato);

        JLabel lblPais = new JLabel("País:");
        lblPais.setBounds(40, 180, 130, 25);
        add(lblPais);

        cbPais = new JComboBox<>();
        cbPais.setBounds(180, 180, 200, 25);
        add(cbPais);

        JLabel lblGrupo = new JLabel("Grupo:");
        lblGrupo.setBounds(40, 220, 130, 25);
        add(lblGrupo);

        cbGrupo = new JComboBox<>();
        cbGrupo.setBounds(180, 220, 200, 25);
        add(cbGrupo);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(600, 60, 140, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(600, 105, 140, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(600, 150, 140, 30);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(600, 195, 140, 30);
        add(btnLimpiar);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Fecha Fundación");
        modeloTabla.addColumn("ID País");
        modeloTabla.addColumn("ID Grupo");

        tablaEquipos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaEquipos);
        scrollPane.setBounds(40, 290, 760, 180);
        add(scrollPane);

        btnGuardar.addActionListener(e -> guardarEquipo());
        btnActualizar.addActionListener(e -> actualizarEquipo());
        btnEliminar.addActionListener(e -> eliminarEquipo());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaEquipos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
    }

    private void cargarCombos() {
        cargarComboPaises();
        cargarComboGrupos();
    }

    private void cargarComboPaises() {
        cbPais.removeAllItems();

        List<Pais> paises = paisDAO.listarPaises();

        for (Pais pais : paises) {
            cbPais.addItem(pais);
        }
    }

    private void cargarComboGrupos() {
        cbGrupo.removeAllItems();

        List<Grupo> grupos = grupoDAO.listarGrupos();

        for (Grupo grupo : grupos) {
            cbGrupo.addItem(grupo);
        }
    }

    private void guardarEquipo() {
        if (!validarCampos()) {
            return;
        }

        String nombre = txtNombre.getText().trim();

        if (equipoDAO.existeNombreEquipo(nombre)) {
            JOptionPane.showMessageDialog(this, "Ya existe un equipo con ese nombre.");
            return;
        }

        Pais paisSeleccionado = (Pais) cbPais.getSelectedItem();
        Grupo grupoSeleccionado = (Grupo) cbGrupo.getSelectedItem();

        Equipo equipo = new Equipo(
                nombre,
                ValidacionUtil.convertirFecha(txtFechaFundacion.getText()),
                paisSeleccionado.getIdPais(),
                grupoSeleccionado.getIdGrupo()
        );

        if (equipoDAO.crearEquipo(equipo)) {
            JOptionPane.showMessageDialog(this, "Equipo guardado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el equipo.");
        }
    }

    private void actualizarEquipo() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona un equipo de la tabla.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        Pais paisSeleccionado = (Pais) cbPais.getSelectedItem();
        Grupo grupoSeleccionado = (Grupo) cbGrupo.getSelectedItem();

        Equipo equipo = new Equipo(
                Integer.parseInt(txtId.getText()),
                txtNombre.getText().trim(),
                ValidacionUtil.convertirFecha(txtFechaFundacion.getText()),
                paisSeleccionado.getIdPais(),
                grupoSeleccionado.getIdGrupo()
        );

        if (equipoDAO.actualizarEquipo(equipo)) {
            JOptionPane.showMessageDialog(this, "Equipo actualizado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el equipo.");
        }
    }

    private void eliminarEquipo() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona un equipo de la tabla.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar este equipo?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        int idEquipo = Integer.parseInt(txtId.getText());

        if (equipoDAO.eliminarEquipo(idEquipo)) {
            JOptionPane.showMessageDialog(this, "Equipo eliminado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar. Puede que el equipo tenga jugadores, técnicos o partidos asociados."
            );
        }
    }

    private boolean validarCampos() {
        if (ValidacionUtil.estaVacio(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "El nombre del equipo es obligatorio.");
            return false;
        }

        if (ValidacionUtil.estaVacio(txtFechaFundacion.getText())) {
            JOptionPane.showMessageDialog(this, "La fecha de fundación es obligatoria.");
            return false;
        }

        if (!ValidacionUtil.esFechaValida(txtFechaFundacion.getText())) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener el formato yyyy-MM-dd.");
            return false;
        }

        if (!ValidacionUtil.fechaNoFutura(ValidacionUtil.convertirFecha(txtFechaFundacion.getText()))) {
            JOptionPane.showMessageDialog(this, "La fecha de fundación no puede ser futura.");
            return false;
        }

        if (cbPais.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un país.");
            return false;
        }

        if (cbGrupo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un grupo.");
            return false;
        }

        return true;
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        List<Equipo> equipos = equipoDAO.listarEquipos();

        for (Equipo equipo : equipos) {
            Object[] fila = {
                    equipo.getIdEquipo(),
                    equipo.getNombre(),
                    equipo.getFechaFundacion(),
                    equipo.getIdPais(),
                    equipo.getIdGrupo()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void seleccionarFila() {
        int filaSeleccionada = tablaEquipos.getSelectedRow();

        if (filaSeleccionada >= 0) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtFechaFundacion.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());

            int idPais = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            int idGrupo = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 4).toString());

            seleccionarPaisPorId(idPais);
            seleccionarGrupoPorId(idGrupo);
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

    private void seleccionarGrupoPorId(int idGrupo) {
        for (int i = 0; i < cbGrupo.getItemCount(); i++) {
            Grupo grupo = cbGrupo.getItemAt(i);

            if (grupo.getIdGrupo() == idGrupo) {
                cbGrupo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtFechaFundacion.setText("");

        if (cbPais.getItemCount() > 0) {
            cbPais.setSelectedIndex(0);
        }

        if (cbGrupo.getItemCount() > 0) {
            cbGrupo.setSelectedIndex(0);
        }

        tablaEquipos.clearSelection();
    }
}