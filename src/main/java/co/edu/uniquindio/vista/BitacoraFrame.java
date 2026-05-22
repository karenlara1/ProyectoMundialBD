package co.edu.uniquindio.vista;

import co.edu.uniquindio.dao.BitacoraDAO;
import co.edu.uniquindio.modelo.Bitacora;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class BitacoraFrame extends JFrame {

    private JTable tablaBitacora;
    private DefaultTableModel modeloTabla;
    private final DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");

    public BitacoraFrame() {

        setTitle("Bitácora del Sistema");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(null);

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "ID Usuario",
                        "Usuario",
                        "Ingreso",
                        "Salida"
                }, 0
        );

        tablaBitacora = new JTable(modeloTabla);

        JScrollPane scroll = new JScrollPane(tablaBitacora);
        scroll.setBounds(20, 20, 740, 280);

        add(scroll);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(320, 320, 140, 30);
        add(btnActualizar);

        btnActualizar.addActionListener(e -> cargarBitacora());

        cargarBitacora();
    }

    private void cargarBitacora() {

        modeloTabla.setRowCount(0);

        BitacoraDAO dao = new BitacoraDAO();

        List<Bitacora> lista = dao.listarBitacora();

        for (Bitacora b : lista) {

            String ingreso = b.getFechaHoraIngreso() != null
                    ? b.getFechaHoraIngreso().format(formato)
                    : "";

            String salida = b.getFechaHoraSalida() != null
                    ? b.getFechaHoraSalida().format(formato)
                    : "Sesión activa";


            modeloTabla.addRow(new Object[]{
                    b.getIdBitacora(),
                    b.getIdUsuario(),
                    b.getUsername(),
                    ingreso,
                    salida
            });
        }
    }
}