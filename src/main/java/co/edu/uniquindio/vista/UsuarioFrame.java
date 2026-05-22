package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.UsuarioDAO;
import co.edu.uniquindio.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UsuarioFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cbRol;

    private JButton btnGuardar;
    private JButton btnLimpiar;

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    private final UsuarioDAO usuarioDAO;

    public UsuarioFrame() {
        usuarioDAO = new UsuarioDAO();

        setTitle("Gestión de Usuarios");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setLayout(null);

        inicializarComponentes();
        cargarTablaUsuarios();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("Gestión de Usuarios");
        lblTitulo.setBounds(300, 15, 200, 25);
        add(lblTitulo);

        JLabel lblUsername = new JLabel("Usuario:");
        lblUsername.setBounds(40, 60, 100, 25);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 60, 200, 25);
        add(txtUsername);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(40, 100, 100, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 100, 200, 25);
        add(txtPassword);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(40, 140, 100, 25);
        add(lblRol);

        cbRol = new JComboBox<>();
        cbRol.addItem("ADMINISTRADOR");
        cbRol.addItem("TRADICIONAL");
        cbRol.addItem("ESPORADICO");
        cbRol.setBounds(150, 140, 200, 25);
        add(cbRol);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(470, 60, 130, 30);
        add(btnGuardar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(470, 105, 130, 30);
        add(btnLimpiar);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Usuario");
        modeloTabla.addColumn("Rol");
        modeloTabla.addColumn("Estado");

        tablaUsuarios = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        scrollPane.setBounds(40, 220, 650, 200);
        add(scrollPane);

        btnGuardar.addActionListener(e -> guardarUsuario());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void guardarUsuario() {
        if (!validarCampos()) {
            return;
        }

        String username = txtUsername.getText().trim();

        if (usuarioDAO.existeUsername(username)) {
            JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese nombre.");
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(new String(txtPassword.getPassword()).trim());
        usuario.setRol(cbRol.getSelectedItem().toString());
        usuario.setEstado("ACTIVO");

        if (usuarioDAO.crearUsuario(usuario)) {
            JOptionPane.showMessageDialog(this, "Usuario creado correctamente.");
            limpiarCampos();
            cargarTablaUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear usuario.");
        }
    }

    private boolean validarCampos() {
        if (txtUsername.getText() == null || txtUsername.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El usuario es obligatorio.");
            return false;
        }

        if (txtUsername.getText().trim().length() > 50) {
            JOptionPane.showMessageDialog(this, "El usuario no puede superar 50 caracteres.");
            return false;
        }

        String password = new String(txtPassword.getPassword()).trim();

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La contraseña es obligatoria.");
            return false;
        }

        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener mínimo 4 caracteres.");
            return false;
        }

        if (password.length() > 100) {
            JOptionPane.showMessageDialog(this, "La contraseña no puede superar 100 caracteres.");
            return false;
        }

        if (cbRol.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un rol.");
            return false;
        }

        return true;
    }

    private void cargarTablaUsuarios() {
        modeloTabla.setRowCount(0);

        List<Usuario> usuarios = usuarioDAO.listarUsuarios();

        for (Usuario usuario : usuarios) {
            Object[] fila = {
                    usuario.getIdUsuario(),
                    usuario.getUsername(),
                    usuario.getRol(),
                    usuario.getEstado()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtUsername.setText("");
        txtPassword.setText("");

        if (cbRol.getItemCount() > 0) {
            cbRol.setSelectedIndex(0);
        }

        tablaUsuarios.clearSelection();
    }
}