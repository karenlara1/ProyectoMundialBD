package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.BitacoraDAO;
import co.edu.uniquindio.util.Sesion;
import co.edu.uniquindio.vista.LoginFrame;

import javax.swing.*;

public class MenuPrincipalFrame extends JFrame {

    private JButton btnUsuarios;
    private JButton btnCrud;
    private JButton btnConsultas;
    private JButton btnBitacora;
    private JButton btnCerrarSesion;

    public MenuPrincipalFrame() {
        setTitle("Menú Principal - Mundial");
        setSize(450, 370);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(null);

        JLabel lblBienvenida = new JLabel(
                "Usuario: " + Sesion.usuarioActual.getUsername() +
                        " | Rol: " + Sesion.usuarioActual.getRol()
        );
        lblBienvenida.setBounds(40, 20, 350, 25);
        add(lblBienvenida);

        btnUsuarios = new JButton("Gestión de Usuarios");
        btnUsuarios.setBounds(100, 70, 230, 30);
        add(btnUsuarios);

        btnCrud = new JButton("CRUD Datos Mundial");
        btnCrud.setBounds(100, 110, 230, 30);
        add(btnCrud);

        btnConsultas = new JButton("Consultas y Reportes");
        btnConsultas.setBounds(100, 150, 230, 30);
        add(btnConsultas);

        btnBitacora = new JButton("Ver Bitácora");
        btnBitacora.setBounds(100, 190, 230, 30);
        add(btnBitacora);

        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBounds(100, 230, 230, 30);
        add(btnCerrarSesion);

        configurarPermisos();

        btnUsuarios.addActionListener(e -> {
            UsuarioFrame frame = new UsuarioFrame();
            frame.setVisible(true);
        });

        btnCrud.addActionListener(e -> abrirMenuCrud());

        btnConsultas.addActionListener(e -> {
            ReporteFrame frame = new ReporteFrame();
            frame.setVisible(true);
        });

        btnBitacora.addActionListener(e -> {
            BitacoraFrame frame = new BitacoraFrame();
            frame.setVisible(true);
        });

        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private void configurarPermisos() {
        String rol = Sesion.usuarioActual.getRol();

        if (rol.equals("ADMINISTRADOR")) {
            btnUsuarios.setEnabled(true);
            btnCrud.setEnabled(true);
            btnConsultas.setEnabled(true);
            btnBitacora.setEnabled(true);

        } else if (rol.equals("TRADICIONAL")) {
            btnUsuarios.setEnabled(false);
            btnCrud.setEnabled(true);
            btnConsultas.setEnabled(true);
            btnBitacora.setEnabled(false);

        } else if (rol.equals("ESPORADICO")) {
            btnUsuarios.setEnabled(false);
            btnCrud.setEnabled(false);
            btnConsultas.setEnabled(true);
            btnBitacora.setEnabled(false);
        }
    }

    private void abrirMenuCrud() {
        String[] opciones = {
                "Confederaciones",
                "Grupos",
                "Equipos",
                "Jugadores",
                "Directores Técnicos"
        };

        String opcionSeleccionada = (String) JOptionPane.showInputDialog(
                this,
                "Selecciona el módulo que deseas gestionar:",
                "CRUD Datos Mundial",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (opcionSeleccionada != null) {
            abrirFrameCrud(opcionSeleccionada);
        }
    }

    private void abrirFrameCrud(String opcionSeleccionada) {
        switch (opcionSeleccionada) {
            case "Confederaciones":
                ConfederacionFrame confederacionFrame = new ConfederacionFrame();
                confederacionFrame.setVisible(true);
                break;

            case "Grupos":
                GrupoFrame grupoFrame = new GrupoFrame();
                grupoFrame.setVisible(true);
                break;

            case "Equipos":
                EquipoFrame equipoFrame = new EquipoFrame();
                equipoFrame.setVisible(true);
                break;

            case "Jugadores":
                JugadorFrame jugadorFrame = new JugadorFrame();
                jugadorFrame.setVisible(true);
                break;

            case "Directores Técnicos":
                DirectorTecnicoFrame directorTecnicoFrame = new DirectorTecnicoFrame();
                directorTecnicoFrame.setVisible(true);
                break;

            default:
                JOptionPane.showMessageDialog(this, "Opción no válida.");
                break;
        }
    }

    private void cerrarSesion() {
        BitacoraDAO bitacoraDAO = new BitacoraDAO();
        bitacoraDAO.registrarSalida(Sesion.idBitacoraActual);

        JOptionPane.showMessageDialog(this, "Sesión cerrada correctamente");

        LoginFrame login = new LoginFrame();
        login.setVisible(true);
        dispose();
    }
}