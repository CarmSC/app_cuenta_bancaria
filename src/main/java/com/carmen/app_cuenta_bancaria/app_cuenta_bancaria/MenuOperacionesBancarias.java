package com.carmen.app_cuenta_bancaria.app_cuenta_bancaria;

import java.util.Scanner;

public class MenuOperacionesBancarias {

      private OperacionesBancariasMySQL operaciones;

    public MenuOperacionesBancarias() {
        this.operaciones = new OperacionesBancariasMySQL();
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("~~~ Menú de Operaciones Bancarias ~~~");
            System.out.println("1. Crear cuenta");
            System.out.println("2. Depositar");
            System.out.println("3. Retirar");
            System.out.println("4. Transferir");
            System.out.println("5. Eliminar cuenta");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada
            
            switch (opcion) {
                case 1:
                    crearCuenta(scanner);
                    break;
                case 2:
                    depositar(scanner);
                    break;
                case 3:
                    retirar(scanner);
                    break;
                case 4:
                    transferir(scanner);
                    break;
                case 5:
                    eliminarCuenta(scanner);
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción del 1 al 6.");
            }

            System.out.println(); // Separador entre cada operación

        } while (opcion != 6);

        scanner.close();
    }

    private void crearCuenta(Scanner scanner) {
        System.out.print("Ingrese el número de cuenta: ");
        String numeroCuenta = scanner.nextLine();
        System.out.print("Ingrese el saldo inicial: ");
        double saldoInicial = scanner.nextDouble();
        operaciones.crearCuenta(numeroCuenta, saldoInicial);
    }

    private void depositar(Scanner scanner) {
        System.out.print("Ingrese el número de cuenta: ");
        String numeroCuenta = scanner.nextLine();
        System.out.print("Ingrese el monto a depositar: ");
        double monto = scanner.nextDouble();
        operaciones.depositar(numeroCuenta, monto);
    }

    private void retirar(Scanner scanner) {
        System.out.print("Ingrese el número de cuenta: ");
        String numeroCuenta = scanner.nextLine();
        System.out.print("Ingrese el monto a retirar: ");
        double monto = scanner.nextDouble();
        operaciones.retirar(numeroCuenta, monto);
    }

    private void transferir(Scanner scanner) {
        System.out.print("Ingrese el número de cuenta de origen: ");
        String origen = scanner.nextLine();
        System.out.print("Ingrese el número de cuenta de destino: ");
        String destino = scanner.nextLine();
        System.out.print("Ingrese el monto a transferir: ");
        double monto = scanner.nextDouble();
        operaciones.transferir(origen, destino, monto);
    }

    private void eliminarCuenta(Scanner scanner) {
        System.out.print("Ingrese el número de cuenta a eliminar: ");
        String numeroCuenta = scanner.nextLine();
        operaciones.eliminarCuenta(numeroCuenta);
    }

    public static void main(String[] args) {
        MenuOperacionesBancarias menu = new MenuOperacionesBancarias();
        menu.mostrarMenu();
    }

}
