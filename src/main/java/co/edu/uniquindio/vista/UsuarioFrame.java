package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.UsuarioDAO;
import co.edu.uniquindio.modelo.Usuario;

import javax.swing.*;

public class UsuarioFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cbRol;
    private JButton btnGuardar;

    public UsuarioFrame() {
        setTitle("Gestión de Usuarios");
        setSize(400, 280);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblUsername = new JLabel("Usuario:");
        lblUsername.setBounds(40, 40, 100, 25);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 40, 180, 25);
        add(txtUsername);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(40, 80, 100, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 80, 180, 25);
        add(txtPassword);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(40, 120, 100, 25);
        add(lblRol);

        cbRol = new JComboBox<>();
        cbRol.addItem("ADMINISTRADOR");
        cbRol.addItem("TRADICIONAL");
        cbRol.addItem("ESPORADICO");
        cbRol.setBounds(150, 120, 180, 25);
        add(cbRol);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(130, 170, 120, 30);
        add(btnGuardar);

        btnGuardar.addActionListener(e -> guardarUsuario());
    }

    private void guardarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsername(txtUsername.getText());
        usuario.setPassword(new String(txtPassword.getPassword()));
        usuario.setRol(cbRol.getSelectedItem().toString());
        usuario.setEstado("ACTIVO");

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (usuarioDAO.crearUsuario(usuario)) {
            JOptionPane.showMessageDialog(this, "Usuario creado correctamente");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear usuario");
        }
    }

    private void limpiarCampos() {
        txtUsername.setText("");
        txtPassword.setText("");
        cbRol.setSelectedIndex(0);
    }
}

