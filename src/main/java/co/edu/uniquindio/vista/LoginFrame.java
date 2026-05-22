package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.BitacoraDAO;
import co.edu.uniquindio.dao.UsuarioDAO;
import co.edu.uniquindio.modelo.Usuario;
import co.edu.uniquindio.util.Sesion;

import javax.swing.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;

    public LoginFrame() {
        setTitle("Login - Mundial");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(40, 30, 100, 25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(140, 30, 150, 25);
        add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(40, 70, 100, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(140, 70, 150, 25);
        add(txtPassword);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(110, 120, 120, 30);
        add(btnIngresar);

        btnIngresar.addActionListener(e -> iniciarSesion());
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el usuario.");
            txtUsuario.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la contraseña.");
            txtPassword.requestFocus();
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioLogueado = usuarioDAO.login(usuario, password);

        if (usuarioLogueado != null) {
            BitacoraDAO bitacoraDAO = new BitacoraDAO();

            Sesion.usuarioActual = usuarioLogueado;
            Sesion.idBitacoraActual = bitacoraDAO.registrarIngreso(usuarioLogueado.getIdUsuario());

            JOptionPane.showMessageDialog(this, "Bienvenido " + usuarioLogueado.getUsername());

            MenuPrincipalFrame menu = new MenuPrincipalFrame();
            menu.setVisible(true);
            dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }
}


