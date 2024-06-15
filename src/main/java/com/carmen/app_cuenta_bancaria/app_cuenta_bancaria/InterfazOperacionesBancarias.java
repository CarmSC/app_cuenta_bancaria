package com.carmen.app_cuenta_bancaria.app_cuenta_bancaria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfazOperacionesBancarias extends JFrame {

    private OperacionesBancariasMySQL operaciones;

    public InterfazOperacionesBancarias() {
        super("Operaciones Bancarias");

        this.operaciones = new OperacionesBancariasMySQL();

        // Configurar la ventana principal
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setLayout(new BorderLayout()); // Diseño de borde para organizar componentes

        // Panel para los botones
        JPanel panelBotones = new JPanel(new GridLayout(6, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espaciado interno

        // Crear botones para las operaciones
        JButton btnCrearCuenta = new JButton("Crear Cuenta");
        JButton btnDepositar = new JButton("Depositar");
        JButton btnRetirar = new JButton("Retirar");
        JButton btnTransferir = new JButton("Transferir");
        JButton btnEliminarCuenta = new JButton("Eliminar Cuenta");
        JButton btnSalir = new JButton("Salir");

         // Agregar los botones al panel de botones
       panelBotones.add(btnCrearCuenta);
        panelBotones.add(btnDepositar);
        panelBotones.add(btnRetirar);
        panelBotones.add(btnTransferir);
        panelBotones.add(btnEliminarCuenta);
        panelBotones.add(btnSalir);

        // Panel para el título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("Operaciones Bancarias");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24)); // Tamaño y tipo de letra del título
        panelTitulo.add(lblTitulo);

        // Agregar el panel de botones y el panel del título a la ventana principal
        add(panelTitulo, BorderLayout.NORTH); // Título en la parte superior
        add(panelBotones, BorderLayout.CENTER); // Botones en el centro

        // Configurar acción para cada botón
        btnCrearCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoCrearCuenta();
            }
        });

        btnDepositar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoDepositar();
            }
        });

        btnRetirar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoRetirar();
            }
        });

        btnTransferir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoTransferir();
            }
        });

        btnEliminarCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoEliminarCuenta();
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Salir del programa
            }
        });
    }

    private void mostrarDialogoCrearCuenta() {
        JTextField tfNumeroCuenta = new JTextField();
        JTextField tfSaldoInicial = new JTextField();
        
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Número de Cuenta:"));
        panel.add(tfNumeroCuenta);
        panel.add(new JLabel("Saldo Inicial:"));
        panel.add(tfSaldoInicial);
        
        int option = JOptionPane.showConfirmDialog(this, panel, "Crear Cuenta",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String numeroCuenta = tfNumeroCuenta.getText().trim();
            double saldoInicial = Double.parseDouble(tfSaldoInicial.getText().trim());
            operaciones.crearCuenta(numeroCuenta, saldoInicial);
            JOptionPane.showMessageDialog(this, "Cuenta creada correctamente.");
        }
    }

    private void mostrarDialogoDepositar() {
        JTextField tfNumeroCuenta = new JTextField();
        JTextField tfMonto = new JTextField();
        
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Número de Cuenta:"));
        panel.add(tfNumeroCuenta);
        panel.add(new JLabel("Monto a Depositar:"));
        panel.add(tfMonto);
        
        int option = JOptionPane.showConfirmDialog(this, panel, "Depositar",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String numeroCuenta = tfNumeroCuenta.getText().trim();
            double monto = Double.parseDouble(tfMonto.getText().trim());
            operaciones.depositar(numeroCuenta, monto);
            JOptionPane.showMessageDialog(this, "Depósito realizado correctamente.");
        }
    }

    private void mostrarDialogoRetirar() {
        JTextField tfNumeroCuenta = new JTextField();
        JTextField tfMonto = new JTextField();
        
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Número de Cuenta:"));
        panel.add(tfNumeroCuenta);
        panel.add(new JLabel("Monto a Retirar:"));
        panel.add(tfMonto);
        
        int option = JOptionPane.showConfirmDialog(this, panel, "Retirar",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String numeroCuenta = tfNumeroCuenta.getText().trim();
            double monto = Double.parseDouble(tfMonto.getText().trim());
            operaciones.retirar(numeroCuenta, monto);
            JOptionPane.showMessageDialog(this, "Retiro realizado correctamente.");
        }
    }

    private void mostrarDialogoTransferir() {
        JTextField tfOrigen = new JTextField();
        JTextField tfDestino = new JTextField();
        JTextField tfMonto = new JTextField();
        
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Cuenta de Origen:"));
        panel.add(tfOrigen);
        panel.add(new JLabel("Cuenta de Destino:"));
        panel.add(tfDestino);
        panel.add(new JLabel("Monto a Transferir:"));
        panel.add(tfMonto);
        
        int option = JOptionPane.showConfirmDialog(this, panel, "Transferir",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String origen = tfOrigen.getText().trim();
            String destino = tfDestino.getText().trim();
            double monto = Double.parseDouble(tfMonto.getText().trim());
            operaciones.transferir(origen, destino, monto);
            JOptionPane.showMessageDialog(this, "Transferencia realizada correctamente.");
        }
    }

    private void mostrarDialogoEliminarCuenta() {
        JTextField tfNumeroCuenta = new JTextField();
        
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new JLabel("Número de Cuenta a Eliminar:"));
        panel.add(tfNumeroCuenta);
        
        int option = JOptionPane.showConfirmDialog(this, panel, "Eliminar Cuenta",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String numeroCuenta = tfNumeroCuenta.getText().trim();
            operaciones.eliminarCuenta(numeroCuenta);
            JOptionPane.showMessageDialog(this, "Cuenta eliminada correctamente.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                InterfazOperacionesBancarias ventana = new InterfazOperacionesBancarias();
                ventana.setVisible(true);
            }
        });
    }
}






