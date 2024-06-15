package com.carmen.app_cuenta_bancaria.app_cuenta_bancaria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="cuenta")
public class Cuenta {

      @Column(name = "numero_cuenta")
      private String numeroCuenta;

      @Column(name = "saldo")
      private double saldo;
      
      public Cuenta() {
      }

      public Cuenta(String numeroCuenta, double saldo) {
            this.numeroCuenta = numeroCuenta;
            this.saldo = saldo;
      }
      
      // Getters y setters
      public String getNumeroCuenta() {
            return numeroCuenta;
      }
      public void setNumeroCuenta(String numeroCuenta) {
            this.numeroCuenta = numeroCuenta;
      }
      public double getSaldo() {
            return saldo;
      }
      public void setSaldo(double saldo) {
            this.saldo = saldo;
      }

      
}
