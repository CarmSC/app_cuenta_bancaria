package com.carmen.app_cuenta_bancaria.app_cuenta_bancaria;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class OperacionesBancariasMySQL {

      private static final String URL = "jdbc:mysql://localhost:3306/banco_db";
      private static final String USER = "root";
      private static final String PASSWORD = "sasa";

      // Método para crear una nueva cuenta en la base de datos
      public void crearCuenta(String numeroCuenta, double saldoInicial) {
            String sql = "INSERT INTO cuentas (numero_cuenta, saldo) VALUES (?, ?)";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                        PreparedStatement stmt = conn.prepareStatement(sql)) {
                  stmt.setString(1, numeroCuenta);
                  stmt.setDouble(2, saldoInicial);
                  stmt.executeUpdate();
                  System.out.println("Cuenta creada exitosamente en MySQL: " + numeroCuenta);
            } catch (SQLException e) {
                  System.err.println("Error al crear cuenta en MySQL: " + e.getMessage());
            }

      }

      // Método para obtener una cuenta de la base de datos por su número de cuenta
      public Cuenta getCuenta(String numeroCuenta) {
            String sql = "SELECT * FROM cuentas WHERE numero_cuenta = ?";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                        PreparedStatement stmt = conn.prepareStatement(sql)) {
                  stmt.setString(1, numeroCuenta);
                  ResultSet rs = stmt.executeQuery();
                  if (rs.next()) {
                        return new Cuenta(rs.getString("numero_cuenta"), rs.getDouble("saldo"));
                  }
            } catch (SQLException e) {
                  System.err.println("Error al obtener cuenta de MySQL: " + e.getMessage());
            }
            return null;
      }

      // Método para actualizar una cuenta en la base de datos
      public void actualizarCuenta(Cuenta cuenta) {
            String sql = "UPDATE cuentas SET saldo = ? WHERE numero_cuenta = ?";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                        PreparedStatement stmt = conn.prepareStatement(sql)) {
                  stmt.setDouble(1, cuenta.getSaldo());
                  stmt.setString(2, cuenta.getNumeroCuenta());
                  stmt.executeUpdate();
                  System.out.println("Cuenta actualizada correctamente en MySQL: " + cuenta.getNumeroCuenta());
            } catch (SQLException e) {
                  System.err.println("Error al actualizar cuenta en MySQL: " + e.getMessage());
            }
      }

      // Método para eliminar una cuenta de la base de datos
      public void eliminarCuenta(String numeroCuenta) {
            String sql = "DELETE FROM cuentas WHERE numero_cuenta = ?";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                        PreparedStatement stmt = conn.prepareStatement(sql)) {
                  stmt.setString(1, numeroCuenta);
                  int filasEliminadas = stmt.executeUpdate();
                  if (filasEliminadas > 0) {
                        System.out.println("Cuenta eliminada correctamente de MySQL: " + numeroCuenta);
                  } else {
                        System.out.println("No se encontró la cuenta con número " + numeroCuenta);
                  }
            } catch (SQLException e) {
                  System.err.println("Error al eliminar cuenta en MySQL: " + e.getMessage());
            }
      }

      // Método para realizar un depósito en una cuenta
      public void depositar(String numeroCuenta, double monto) {
            String sql = "UPDATE cuentas SET saldo = saldo + ? WHERE numero_cuenta = ?";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                        PreparedStatement stmt = conn.prepareStatement(sql)) {
                  stmt.setDouble(1, monto);
                  stmt.setString(2, numeroCuenta);
                  int filasActualizadas = stmt.executeUpdate();
                  if (filasActualizadas > 0) {
                        System.out.println(
                                    "Depósito de " + monto + " realizado correctamente en cuenta " + numeroCuenta);
                  } else {
                        System.out.println("Error: No se encontró la cuenta con número " + numeroCuenta);
                  }
            } catch (SQLException e) {
                  System.err.println("Error al realizar depósito en cuenta en MySQL: " + e.getMessage());
            }
      }

      // Método para realizar un retiro de una cuenta
      public void retirar(String numeroCuenta, double monto) {
            String sql = "UPDATE cuentas SET saldo = saldo - ? WHERE numero_cuenta = ? AND saldo >= ?";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                        PreparedStatement stmt = conn.prepareStatement(sql)) {
                  stmt.setDouble(1, monto);
                  stmt.setString(2, numeroCuenta);
                  stmt.setDouble(3, monto);
                  int filasActualizadas = stmt.executeUpdate();
                  if (filasActualizadas > 0) {
                        System.out.println("Retiro de " + monto + " realizado correctamente en cuenta " + numeroCuenta);
                  } else {
                        System.out.println("Error: Fondos insuficientes o no se encontró la cuenta con número "
                                    + numeroCuenta);
                  }
            } catch (SQLException e) {
                  System.err.println("Error al realizar retiro en cuenta en MySQL: " + e.getMessage());
            }
      }

      // Método para realizar una transferencia entre cuentas
      public void transferir(String origen, String destino, double monto) {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                  conn = DriverManager.getConnection(URL, USER, PASSWORD);
                  conn.setAutoCommit(false); // Iniciar transacción

                  // Verificar saldo suficiente en cuenta de origen
                  String sqlSaldo = "SELECT saldo FROM cuentas WHERE numero_cuenta = ?";
                  stmt = conn.prepareStatement(sqlSaldo);
                  stmt.setString(1, origen);
                  ResultSet rs = stmt.executeQuery();
                  if (rs.next()) {
                        double saldoOrigen = rs.getDouble("saldo");
                        if (saldoOrigen >= monto) {
                              // Realizar el retiro en cuenta de origen
                              String sqlRetiro = "UPDATE cuentas SET saldo = saldo - ? WHERE numero_cuenta = ?";
                              stmt = conn.prepareStatement(sqlRetiro);
                              stmt.setDouble(1, monto);
                              stmt.setString(2, origen);
                              stmt.executeUpdate();

                              // Realizar el depósito en cuenta de destino
                              String sqlDeposito = "UPDATE cuentas SET saldo = saldo + ? WHERE numero_cuenta = ?";
                              stmt = conn.prepareStatement(sqlDeposito);
                              stmt.setDouble(1, monto);
                              stmt.setString(2, destino);
                              stmt.executeUpdate();

                              conn.commit(); // Confirmar la transacción
                              System.out.println("Transferencia de " + monto + " desde cuenta " + origen + " a cuenta "
                                          + destino + " realizada.");
                        } else {
                              System.out.println("Error: Fondos insuficientes en la cuenta origen (" + origen + ").");
                        }
                  } else {
                        System.out.println("Error: No se encontró la cuenta con número " + origen);
                  }
            } catch (SQLException e) {
                  if (conn != null) {
                        try {
                              conn.rollback(); // Revertir la transacción en caso de error
                        } catch (SQLException ex) {
                              System.err.println("Error al hacer rollback de la transacción: " + ex.getMessage());
                        }
                  }
                  System.err.println("Error al realizar transferencia en MySQL: " + e.getMessage());
            } finally {
                  // Cerrar recursos
                  try {
                        if (stmt != null)
                              stmt.close();
                        if (conn != null)
                              conn.close();
                  } catch (SQLException e) {
                        System.err.println("Error al cerrar conexión o statement en MySQL: " + e.getMessage());
                  }
            }
      }

      // Método main para probar las operaciones CRUD
      public static void main(String[] args) {
            OperacionesBancariasMySQL operaciones = new OperacionesBancariasMySQL();

            // Crear una cuenta nueva
            operaciones.crearCuenta("12345", 1000);
           // operaciones.crearCuenta("54321", 500);

            // Realizar un depósito en una cuenta
            //operaciones.depositar("12345", 200);

            // Realizar un retiro de una cuenta
            //operaciones.retirar("12345", 100);

            // Realizar una transferencia entre cuentas
            //operaciones.transferir("12345", "54321", 300);

            // Eliminar una cuenta
            //operaciones.eliminarCuenta("12345");

      }

}
