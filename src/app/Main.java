package app;

import dao.ArticuloDAO;
import dao.MovimientoBodegaDAO;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static final Scanner TECLADO = new Scanner(System.in);
    private static final ArticuloDAO articuloDAO = new ArticuloDAO();
    private static final MovimientoBodegaDAO movimientoDAO = new MovimientoBodegaDAO();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1 -> articuloDAO.listar();
                    case 2 -> registrarMovimiento();
                    case 3 -> articuloDAO.listarBajoMinimo();
                    case 0 -> System.out.println("Hasta luego.");
                    default -> System.out.println("Opcion invalida.");
                }
            } catch (SQLException e) {
                System.out.println("Error de base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== CONTROL DE BODEGA ===");
        System.out.println("1. Listar articulos (marca los que necesitan reabastecerse)");
        System.out.println("2. Registrar entrada/salida de un articulo");
        System.out.println("3. Ver articulos bajo el stock minimo");
        System.out.println("0. Salir");
    }

    private static void registrarMovimiento() throws SQLException {
        int idArticulo = leerEntero("Id del articulo (ver opcion 1): ");
        System.out.print("Tipo (E = entrada, S = salida): ");
        String tipo = TECLADO.nextLine().trim();
        int cantidad = leerEntero("Cantidad: ");

        boolean exito = movimientoDAO.registrarMovimiento(idArticulo, tipo, cantidad);
        System.out.println(exito ? "Movimiento registrado." : "No hay suficiente stock para esa salida.");
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!TECLADO.hasNextInt()) {
            System.out.print("Ingrese un numero valido: ");
            TECLADO.next();
        }
        int valor = TECLADO.nextInt();
        TECLADO.nextLine();
        return valor;
    }
}
