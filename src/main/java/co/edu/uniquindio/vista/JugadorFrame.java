package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.EquipoDAO;
import co.edu.uniquindio.dao.JugadorDAO;
import co.edu.uniquindio.dao.PosicionDAO;
import co.edu.uniquindio.modelo.Equipo;
import co.edu.uniquindio.modelo.Jugador;
import co.edu.uniquindio.modelo.Posicion;
import co.edu.uniquindio.util.ValidacionUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class JugadorFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtFechaNacimiento;
    private JTextField txtEstatura;
    private JTextField txtPeso;
    private JTextField txtValor;

    private JComboBox<Equipo> cbEquipo;
    private JComboBox<Posicion> cbPosicion;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaJugadores;
    private DefaultTableModel modeloTabla;

    private final JugadorDAO jugadorDAO;
    private final EquipoDAO equipoDAO;
    private final PosicionDAO posicionDAO;

    public JugadorFrame() {
        jugadorDAO = new JugadorDAO();
        equipoDAO = new EquipoDAO();
        posicionDAO = new PosicionDAO();

        setTitle("Gestión de Jugadores");
        setSize(950, 620);
        setLocationRelativeTo(null);
        setLayout(null);

        inicializarComponentes();
        cargarCombos();
        cargarTabla();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("Gestión de Jugadores");
        lblTitulo.setBounds(390, 15, 250, 25);
        add(lblTitulo);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(40, 60, 140, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(190, 60, 220, 25);
        txtId.setEditable(false);
        add(txtId);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(40, 100, 140, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(190, 100, 220, 25);
        add(txtNombre);

        JLabel lblFecha = new JLabel("Fecha nacimiento:");
        lblFecha.setBounds(40, 140, 140, 25);
        add(lblFecha);

        txtFechaNacimiento = new JTextField();
        txtFechaNacimiento.setBounds(190, 140, 220, 25);
        add(txtFechaNacimiento);

        JLabel lblFormato = new JLabel("Formato: yyyy-MM-dd");
        lblFormato.setBounds(420, 140, 180, 25);
        add(lblFormato);

        JLabel lblEstatura = new JLabel("Estatura:");
        lblEstatura.setBounds(40, 180, 140, 25);
        add(lblEstatura);

        txtEstatura = new JTextField();
        txtEstatura.setBounds(190, 180, 220, 25);
        add(txtEstatura);

        JLabel lblPeso = new JLabel("Peso:");
        lblPeso.setBounds(40, 220, 140, 25);
        add(lblPeso);

        txtPeso = new JTextField();
        txtPeso.setBounds(190, 220, 220, 25);
        add(txtPeso);

        JLabel lblValor = new JLabel("Valor:");
        lblValor.setBounds(40, 260, 140, 25);
        add(lblValor);

        txtValor = new JTextField();
        txtValor.setBounds(190, 260, 220, 25);
        add(txtValor);

        JLabel lblEquipo = new JLabel("Equipo:");
        lblEquipo.setBounds(40, 300, 140, 25);
        add(lblEquipo);

        cbEquipo = new JComboBox<>();
        cbEquipo.setBounds(190, 300, 220, 25);
        add(cbEquipo);

        JLabel lblPosicion = new JLabel("Posición:");
        lblPosicion.setBounds(40, 340, 140, 25);
        add(lblPosicion);

        cbPosicion = new JComboBox<>();
        cbPosicion.setBounds(190, 340, 220, 25);
        add(cbPosicion);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(700, 70, 140, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(700, 120, 140, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(700, 170, 140, 30);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(700, 220, 140, 30);
        add(btnLimpiar);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Fecha Nacimiento");
        modeloTabla.addColumn("Estatura");
        modeloTabla.addColumn("Peso");
        modeloTabla.addColumn("Valor");
        modeloTabla.addColumn("ID Equipo");
        modeloTabla.addColumn("ID Posición");

        tablaJugadores = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaJugadores);
        scrollPane.setBounds(40, 410, 850, 140);
        add(scrollPane);

        btnGuardar.addActionListener(e -> guardarJugador());
        btnActualizar.addActionListener(e -> actualizarJugador());
        btnEliminar.addActionListener(e -> eliminarJugador());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaJugadores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
    }

    private void cargarCombos() {
        cargarComboEquipos();
        cargarComboPosiciones();
    }

    private void cargarComboEquipos() {
        cbEquipo.removeAllItems();

        List<Equipo> equipos = equipoDAO.listarEquipos();

        for (Equipo equipo : equipos) {
            cbEquipo.addItem(equipo);
        }
    }

    private void cargarComboPosiciones() {
        cbPosicion.removeAllItems();

        List<Posicion> posiciones = posicionDAO.listarPosiciones();

        for (Posicion posicion : posiciones) {
            cbPosicion.addItem(posicion);
        }
    }

    private void guardarJugador() {
        if (!validarCampos()) {
            return;
        }

        Equipo equipoSeleccionado = (Equipo) cbEquipo.getSelectedItem();
        Posicion posicionSeleccionada = (Posicion) cbPosicion.getSelectedItem();
        String nombre = txtNombre.getText().trim();

        if (jugadorDAO.existeJugadorEnEquipo(nombre, equipoSeleccionado.getIdEquipo())) {
            JOptionPane.showMessageDialog(this, "Ya existe un jugador con ese nombre en este equipo.");
            return;
        }

        Jugador jugador = new Jugador(
                nombre,
                ValidacionUtil.convertirFecha(txtFechaNacimiento.getText()),
                ValidacionUtil.convertirDecimal(txtEstatura.getText()),
                ValidacionUtil.convertirDecimal(txtPeso.getText()),
                ValidacionUtil.convertirDecimal(txtValor.getText()),
                equipoSeleccionado.getIdEquipo(),
                posicionSeleccionada.getIdPosicion()
        );

        if (jugadorDAO.crearJugador(jugador)) {
            JOptionPane.showMessageDialog(this, "Jugador guardado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el jugador.");
        }
    }

    private void actualizarJugador() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona un jugador de la tabla.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        int idJugador = Integer.parseInt(txtId.getText());
        Equipo equipoSeleccionado = (Equipo) cbEquipo.getSelectedItem();
        Posicion posicionSeleccionada = (Posicion) cbPosicion.getSelectedItem();
        String nombre = txtNombre.getText().trim();

        if (jugadorDAO.existeJugadorEnEquipoEditando(nombre, equipoSeleccionado.getIdEquipo(), idJugador)) {
            JOptionPane.showMessageDialog(this, "Ya existe otro jugador con ese nombre en este equipo.");
            return;
        }

        Jugador jugador = new Jugador(
                idJugador,
                nombre,
                ValidacionUtil.convertirFecha(txtFechaNacimiento.getText()),
                ValidacionUtil.convertirDecimal(txtEstatura.getText()),
                ValidacionUtil.convertirDecimal(txtPeso.getText()),
                ValidacionUtil.convertirDecimal(txtValor.getText()),
                equipoSeleccionado.getIdEquipo(),
                posicionSeleccionada.getIdPosicion()
        );

        if (jugadorDAO.actualizarJugador(jugador)) {
            JOptionPane.showMessageDialog(this, "Jugador actualizado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el jugador.");
        }
    }

    private void eliminarJugador() {
        if (ValidacionUtil.estaVacio(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Selecciona un jugador de la tabla.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar este jugador?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        int idJugador = Integer.parseInt(txtId.getText());

        if (jugadorDAO.eliminarJugador(idJugador)) {
            JOptionPane.showMessageDialog(this, "Jugador eliminado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el jugador.");
        }
    }

    private boolean validarCampos() {
        if (ValidacionUtil.estaVacio(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "El nombre del jugador es obligatorio.");
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

        if (!ValidacionUtil.esDecimalValido(txtEstatura.getText())) {
            JOptionPane.showMessageDialog(this, "La estatura debe ser un número válido.");
            return false;
        }

        double estatura = ValidacionUtil.convertirDecimal(txtEstatura.getText());

        if (!ValidacionUtil.esValorPositivo(estatura)) {
            JOptionPane.showMessageDialog(this, "La estatura debe ser mayor a cero.");
            return false;
        }

        if (!ValidacionUtil.esDecimalValido(txtPeso.getText())) {
            JOptionPane.showMessageDialog(this, "El peso debe ser un número válido.");
            return false;
        }

        double peso = ValidacionUtil.convertirDecimal(txtPeso.getText());

        if (!ValidacionUtil.esValorPositivo(peso)) {
            JOptionPane.showMessageDialog(this, "El peso debe ser mayor a cero.");
            return false;
        }

        if (!ValidacionUtil.esDecimalValido(txtValor.getText())) {
            JOptionPane.showMessageDialog(this, "El valor debe ser un número válido.");
            return false;
        }

        double valor = ValidacionUtil.convertirDecimal(txtValor.getText());

        if (!ValidacionUtil.esValorMayorOIgualCero(valor)) {
            JOptionPane.showMessageDialog(this, "El valor debe ser mayor o igual a cero.");
            return false;
        }

        if (cbEquipo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un equipo.");
            return false;
        }

        if (cbPosicion.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una posición.");
            return false;
        }

        return true;
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        List<Jugador> jugadores = jugadorDAO.listarJugadores();

        for (Jugador jugador : jugadores) {
            Object[] fila = {
                    jugador.getIdJugador(),
                    jugador.getNombre(),
                    jugador.getFechaNacimiento(),
                    jugador.getEstatura(),
                    jugador.getPeso(),
                    jugador.getValor(),
                    jugador.getIdEquipo(),
                    jugador.getIdPosicion()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void seleccionarFila() {
        int filaSeleccionada = tablaJugadores.getSelectedRow();

        if (filaSeleccionada >= 0) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtFechaNacimiento.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtEstatura.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            txtPeso.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            txtValor.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());

            int idEquipo = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 6).toString());
            int idPosicion = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 7).toString());

            seleccionarEquipoPorId(idEquipo);
            seleccionarPosicionPorId(idPosicion);
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

    private void seleccionarPosicionPorId(int idPosicion) {
        for (int i = 0; i < cbPosicion.getItemCount(); i++) {
            Posicion posicion = cbPosicion.getItemAt(i);

            if (posicion.getIdPosicion() == idPosicion) {
                cbPosicion.setSelectedIndex(i);
                return;
            }
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtFechaNacimiento.setText("");
        txtEstatura.setText("");
        txtPeso.setText("");
        txtValor.setText("");

        if (cbEquipo.getItemCount() > 0) {
            cbEquipo.setSelectedIndex(0);
        }

        if (cbPosicion.getItemCount() > 0) {
            cbPosicion.setSelectedIndex(0);
        }

        tablaJugadores.clearSelection();
    }
}