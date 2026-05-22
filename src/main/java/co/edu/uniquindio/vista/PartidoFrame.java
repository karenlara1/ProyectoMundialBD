package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.EquipoDAO;
import co.edu.uniquindio.dao.EstadioDAO;
import co.edu.uniquindio.dao.GrupoDAO;
import co.edu.uniquindio.dao.PartidoDAO;
import co.edu.uniquindio.modelo.Equipo;
import co.edu.uniquindio.modelo.Estadio;
import co.edu.uniquindio.modelo.Grupo;
import co.edu.uniquindio.modelo.Partido;
import co.edu.uniquindio.util.ValidacionUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PartidoFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtFechaPartido;
    private JTextField txtHoraPartido;
    private JTextField txtGolesLocal;
    private JTextField txtGolesVisitante;

    private JComboBox<Estadio> cbEstadio;
    private JComboBox<Grupo> cbGrupo;
    private JComboBox<Equipo> cbEquipoLocal;
    private JComboBox<Equipo> cbEquipoVisitante;
    private JComboBox<String> cbEstado;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaPartidos;
    private DefaultTableModel modeloTabla;

    private final PartidoDAO partidoDAO;
    private final EstadioDAO estadioDAO;
    private final GrupoDAO grupoDAO;
    private final EquipoDAO equipoDAO;

    public PartidoFrame() {
        partidoDAO = new PartidoDAO();
        estadioDAO = new EstadioDAO();
        grupoDAO = new GrupoDAO();
        equipoDAO = new EquipoDAO();

        setTitle("Gestión de Partidos");
        setSize(1050, 650);
        setLocationRelativeTo(null);
        setLayout(null);

        inicializarComponentes();
        cargarCombos();
        cargarTabla();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("Gestión de Partidos");
        lblTitulo.setBounds(450, 15, 250, 25);
        add(lblTitulo);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(40, 60, 140, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(190, 60, 220, 25);
        txtId.setEditable(false);
        add(txtId);

        JLabel lblFecha = new JLabel("Fecha partido:");
        lblFecha.setBounds(40, 100, 140, 25);
        add(lblFecha);

        txtFechaPartido = new JTextField();
        txtFechaPartido.setBounds(190, 100, 220, 25);
        add(txtFechaPartido);

        JLabel lblFormatoFecha = new JLabel("Formato: yyyy-MM-dd");
        lblFormatoFecha.setBounds(420, 100, 180, 25);
        add(lblFormatoFecha);

        JLabel lblHora = new JLabel("Hora partido:");
        lblHora.setBounds(40, 140, 140, 25);
        add(lblHora);

        txtHoraPartido = new JTextField();
        txtHoraPartido.setBounds(190, 140, 220, 25);
        add(txtHoraPartido);

        JLabel lblFormatoHora = new JLabel("Formato: HH:mm");
        lblFormatoHora.setBounds(420, 140, 180, 25);
        add(lblFormatoHora);

        JLabel lblEstadio = new JLabel("Estadio:");
        lblEstadio.setBounds(40, 180, 140, 25);
        add(lblEstadio);

        cbEstadio = new JComboBox<>();
        cbEstadio.setBounds(190, 180, 260, 25);
        add(cbEstadio);

        JLabel lblGrupo = new JLabel("Grupo:");
        lblGrupo.setBounds(40, 220, 140, 25);
        add(lblGrupo);

        cbGrupo = new JComboBox<>();
        cbGrupo.setBounds(190, 220, 260, 25);
        add(cbGrupo);

        JLabel lblEquipoLocal = new JLabel("Equipo local:");
        lblEquipoLocal.setBounds(40, 260, 140, 25);
        add(lblEquipoLocal);

        cbEquipoLocal = new JComboBox<>();
        cbEquipoLocal.setBounds(190, 260, 260, 25);
        add(cbEquipoLocal);

        JLabel lblEquipoVisitante = new JLabel("Equipo visitante:");
        lblEquipoVisitante.setBounds(40, 300, 140, 25);
        add(lblEquipoVisitante);

        cbEquipoVisitante = new JComboBox<>();
        cbEquipoVisitante.setBounds(190, 300, 260, 25);
        add(cbEquipoVisitante);

        JLabel lblGolesLocal = new JLabel("Goles local:");
        lblGolesLocal.setBounds(570, 60, 140, 25);
        add(lblGolesLocal);

        txtGolesLocal = new JTextField();
        txtGolesLocal.setBounds(720, 60, 180, 25);
        txtGolesLocal.setText("0");
        add(txtGolesLocal);

        JLabel lblGolesVisitante = new JLabel("Goles visitante:");
        lblGolesVisitante.setBounds(570, 100, 140, 25);
        add(lblGolesVisitante);

        txtGolesVisitante = new JTextField();
        txtGolesVisitante.setBounds(720, 100, 180, 25);
        txtGolesVisitante.setText("0");
        add(txtGolesVisitante);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(570, 140, 140, 25);
        add(lblEstado);

        cbEstado = new JComboBox<>();
        cbEstado.setBounds(720, 140, 180, 25);
        cbEstado.addItem("PROGRAMADO");
        cbEstado.addItem("EN JUEGO");
        cbEstado.addItem("FINALIZADO");
        cbEstado.addItem("CANCELADO");
        add(cbEstado);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(570, 200, 140, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(730, 200, 140, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(570, 250, 140, 30);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(730, 250, 140, 30);
        add(btnLimpiar);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Hora");
        modeloTabla.addColumn("ID Estadio");
        modeloTabla.addColumn("ID Grupo");
        modeloTabla.addColumn("ID Local");
        modeloTabla.addColumn("ID Visitante");
        modeloTabla.addColumn("Goles Local");
        modeloTabla.addColumn("Goles Visitante");
        modeloTabla.addColumn("Estado");

        tablaPartidos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPartidos);
        scrollPane.setBounds(40, 380, 960, 180);
        add(scrollPane);

        btnGuardar.addActionListener(e -> guardarPartido());
        btnActualizar.addActionListener(e -> actualizarPartido());
        btnEliminar.addActionListener(e -> eliminarPartido());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaPartidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
    }

    private void cargarCombos() {
        cargarComboEstadios();
        cargarComboGrupos();
        cargarComboEquipos();
    }

    private void cargarComboEstadios() {
        cbEstadio.removeAllItems();

        List<Estadio> estadios = estadioDAO.listarEstadios();

        for (Estadio estadio : estadios) {
            cbEstadio.addItem(estadio);
        }
    }

    private void cargarComboGrupos() {
        cbGrupo.removeAllItems();

        List<Grupo> grupos = grupoDAO.listarGrupos();

        for (Grupo grupo : grupos) {
            cbGrupo.addItem(grupo);
        }
    }

    private void cargarComboEquipos() {
        cbEquipoLocal.removeAllItems();
        cbEquipoVisitante.removeAllItems();

        List<Equipo> equipos = equipoDAO.listarEquipos();

        for (Equipo equipo : equipos) {
            cbEquipoLocal.addItem(equipo);
            cbEquipoVisitante.addItem(equipo);
        }
    }

    private void guardarPartido() {
        if (!validarCampos()) {
            return;
        }

        LocalDate fechaPartido = ValidacionUtil.convertirFecha(txtFechaPartido.getText());
        LocalTime horaPartido = ValidacionUtil.convertirHora(txtHoraPartido.getText());
        Estadio estadioSeleccionado = (Estadio) cbEstadio.getSelectedItem();

        if (partidoDAO.existePartidoMismoHorarioYEstadio(
                fechaPartido,
                horaPartido,
                estadioSeleccionado.getIdEstadio()
        )) {
            JOptionPane.showMessageDialog(this, "Ya existe un partido en ese estadio, fecha y hora.");
            return;
        }

        Partido partido = construirPartidoDesdeFormulario(0);

        if (partidoDAO.crearPartido(partido)) {
            JOptionPane.showMessageDialog(this, "Partido guardado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el partido.");
        }
    }

    private void actualizarPartido() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona un partido de la tabla.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        int idPartido = Integer.parseInt(txtId.getText());
        LocalDate fechaPartido = ValidacionUtil.convertirFecha(txtFechaPartido.getText());
        LocalTime horaPartido = ValidacionUtil.convertirHora(txtHoraPartido.getText());
        Estadio estadioSeleccionado = (Estadio) cbEstadio.getSelectedItem();

        if (partidoDAO.existePartidoMismoHorarioYEstadioEditando(
                fechaPartido,
                horaPartido,
                estadioSeleccionado.getIdEstadio(),
                idPartido
        )) {
            JOptionPane.showMessageDialog(this, "Ya existe otro partido en ese estadio, fecha y hora.");
            return;
        }

        Partido partido = construirPartidoDesdeFormulario(idPartido);

        if (partidoDAO.actualizarPartido(partido)) {
            JOptionPane.showMessageDialog(this, "Partido actualizado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el partido.");
        }
    }

    private void eliminarPartido() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona un partido de la tabla.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar este partido?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        int idPartido = Integer.parseInt(txtId.getText());

        if (partidoDAO.eliminarPartido(idPartido)) {
            JOptionPane.showMessageDialog(this, "Partido eliminado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el partido.");
        }
    }

    private Partido construirPartidoDesdeFormulario(int idPartido) {
        Estadio estadioSeleccionado = (Estadio) cbEstadio.getSelectedItem();
        Grupo grupoSeleccionado = (Grupo) cbGrupo.getSelectedItem();
        Equipo equipoLocal = (Equipo) cbEquipoLocal.getSelectedItem();
        Equipo equipoVisitante = (Equipo) cbEquipoVisitante.getSelectedItem();

        if (idPartido == 0) {
            return new Partido(
                    ValidacionUtil.convertirFecha(txtFechaPartido.getText()),
                    ValidacionUtil.convertirHora(txtHoraPartido.getText()),
                    estadioSeleccionado.getIdEstadio(),
                    grupoSeleccionado.getIdGrupo(),
                    equipoLocal.getIdEquipo(),
                    equipoVisitante.getIdEquipo(),
                    ValidacionUtil.convertirEntero(txtGolesLocal.getText()),
                    ValidacionUtil.convertirEntero(txtGolesVisitante.getText()),
                    cbEstado.getSelectedItem().toString()
            );
        }

        return new Partido(
                idPartido,
                ValidacionUtil.convertirFecha(txtFechaPartido.getText()),
                ValidacionUtil.convertirHora(txtHoraPartido.getText()),
                estadioSeleccionado.getIdEstadio(),
                grupoSeleccionado.getIdGrupo(),
                equipoLocal.getIdEquipo(),
                equipoVisitante.getIdEquipo(),
                ValidacionUtil.convertirEntero(txtGolesLocal.getText()),
                ValidacionUtil.convertirEntero(txtGolesVisitante.getText()),
                cbEstado.getSelectedItem().toString()
        );
    }

    private boolean validarCampos() {
        if (!ValidacionUtil.esFechaValida(txtFechaPartido.getText())) {
            JOptionPane.showMessageDialog(this, "La fecha del partido debe tener formato yyyy-MM-dd.");
            return false;
        }

        if (!ValidacionUtil.esHoraValida(txtHoraPartido.getText())) {
            JOptionPane.showMessageDialog(this, "La hora del partido debe tener formato HH:mm.");
            return false;
        }

        if (cbEstadio.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un estadio.");
            return false;
        }

        if (cbGrupo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un grupo.");
            return false;
        }

        if (cbEquipoLocal.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar el equipo local.");
            return false;
        }

        if (cbEquipoVisitante.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar el equipo visitante.");
            return false;
        }

        Equipo equipoLocal = (Equipo) cbEquipoLocal.getSelectedItem();
        Equipo equipoVisitante = (Equipo) cbEquipoVisitante.getSelectedItem();

        if (!ValidacionUtil.equiposDiferentes(equipoLocal.getIdEquipo(), equipoVisitante.getIdEquipo())) {
            JOptionPane.showMessageDialog(this, "El equipo local y el visitante no pueden ser el mismo.");
            return false;
        }

        if (!ValidacionUtil.esEnteroValido(txtGolesLocal.getText())) {
            JOptionPane.showMessageDialog(this, "Los goles del equipo local deben ser un número entero.");
            return false;
        }

        if (!ValidacionUtil.esEnteroValido(txtGolesVisitante.getText())) {
            JOptionPane.showMessageDialog(this, "Los goles del equipo visitante deben ser un número entero.");
            return false;
        }

        int golesLocal = ValidacionUtil.convertirEntero(txtGolesLocal.getText());
        int golesVisitante = ValidacionUtil.convertirEntero(txtGolesVisitante.getText());

        if (!ValidacionUtil.esGolesValido(golesLocal)) {
            JOptionPane.showMessageDialog(this, "Los goles del equipo local no pueden ser negativos.");
            return false;
        }

        if (!ValidacionUtil.esGolesValido(golesVisitante)) {
            JOptionPane.showMessageDialog(this, "Los goles del equipo visitante no pueden ser negativos.");
            return false;
        }

        if (cbEstado.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar el estado del partido.");
            return false;
        }

        if (!ValidacionUtil.esEstadoPartidoValido(cbEstado.getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(this, "Estado de partido no válido.");
            return false;
        }

        return true;
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        List<Partido> partidos = partidoDAO.listarPartidos();

        for (Partido partido : partidos) {
            Object[] fila = {
                    partido.getIdPartido(),
                    partido.getFechaPartido(),
                    partido.getHoraPartido(),
                    partido.getIdEstadio(),
                    partido.getIdGrupo(),
                    partido.getIdEquipoLocal(),
                    partido.getIdEquipoVisitante(),
                    partido.getGolesLocal(),
                    partido.getGolesVisitante(),
                    partido.getEstado()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void seleccionarFila() {
        int filaSeleccionada = tablaPartidos.getSelectedRow();

        if (filaSeleccionada >= 0) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtFechaPartido.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtHoraPartido.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtGolesLocal.setText(modeloTabla.getValueAt(filaSeleccionada, 7).toString());
            txtGolesVisitante.setText(modeloTabla.getValueAt(filaSeleccionada, 8).toString());
            cbEstado.setSelectedItem(modeloTabla.getValueAt(filaSeleccionada, 9).toString());

            int idEstadio = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            int idGrupo = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            int idEquipoLocal = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
            int idEquipoVisitante = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 6).toString());

            seleccionarEstadioPorId(idEstadio);
            seleccionarGrupoPorId(idGrupo);
            seleccionarEquipoLocalPorId(idEquipoLocal);
            seleccionarEquipoVisitantePorId(idEquipoVisitante);
        }
    }

    private void seleccionarEstadioPorId(int idEstadio) {
        for (int i = 0; i < cbEstadio.getItemCount(); i++) {
            Estadio estadio = cbEstadio.getItemAt(i);

            if (estadio.getIdEstadio() == idEstadio) {
                cbEstadio.setSelectedIndex(i);
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

    private void seleccionarEquipoLocalPorId(int idEquipo) {
        for (int i = 0; i < cbEquipoLocal.getItemCount(); i++) {
            Equipo equipo = cbEquipoLocal.getItemAt(i);

            if (equipo.getIdEquipo() == idEquipo) {
                cbEquipoLocal.setSelectedIndex(i);
                return;
            }
        }
    }

    private void seleccionarEquipoVisitantePorId(int idEquipo) {
        for (int i = 0; i < cbEquipoVisitante.getItemCount(); i++) {
            Equipo equipo = cbEquipoVisitante.getItemAt(i);

            if (equipo.getIdEquipo() == idEquipo) {
                cbEquipoVisitante.setSelectedIndex(i);
                return;
            }
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtFechaPartido.setText("");
        txtHoraPartido.setText("");
        txtGolesLocal.setText("0");
        txtGolesVisitante.setText("0");

        if (cbEstadio.getItemCount() > 0) {
            cbEstadio.setSelectedIndex(0);
        }

        if (cbGrupo.getItemCount() > 0) {
            cbGrupo.setSelectedIndex(0);
        }

        if (cbEquipoLocal.getItemCount() > 0) {
            cbEquipoLocal.setSelectedIndex(0);
        }

        if (cbEquipoVisitante.getItemCount() > 0) {
            cbEquipoVisitante.setSelectedIndex(0);
        }

        if (cbEstado.getItemCount() > 0) {
            cbEstado.setSelectedIndex(0);
        }

        tablaPartidos.clearSelection();
    }
}