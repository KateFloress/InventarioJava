package mi.inventario;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GestorInventario {
    public static void main(String[] args) {
        // CORRECCIÓN: Llamamos al método estático para verificar la tabla al arrancar
        ConexionDB.crearTabla();

        Scanner lector = new Scanner(System.in);
        int opcion = 0;

        System.out.println("--- BIENVENIDO AL GESTOR DE INVENTARIO CON BASE DE DATOS REAL ---");

        while (opcion != 3) {
            try {
                System.out.println("\n1. Añadir Producto");
                System.out.println("2. Mostrar Inventario");
                System.out.println("3. Salir");
                System.out.print("Elige una opción: ");
                opcion = lector.nextInt();
                lector.nextLine(); // Limpiamos el Enter residual

                if (opcion == 1) {
                    System.out.print("Nombre del producto: ");
                    String textoNombre = lector.nextLine();

                    System.out.print("Precio del producto: ");
                    double numeroPrecio = lector.nextDouble();
                    lector.nextLine(); // Limpiamos el Enter residual

                    // Funciona perfectamente gracias al método estático
                    ConexionDB.insertarProducto(textoNombre, numeroPrecio);

                    System.out.println("¡Producto guardado para siempre en la base de datos!");
                }
                else if (opcion == 2) {
                    System.out.println("\n--- Lista de Productos desde SQLite ---");

                    // Funciona perfectamente gracias al método estático
                    ArrayList<Producto> inventarioReal = ConexionDB.obtenerProductos();

                    if (inventarioReal.isEmpty()) {
                        System.out.println("El inventario está vacío.");
                    } else {
                        for (Producto p : inventarioReal) {
                            System.out.println("- " + p.getNombre() + " (" + p.getPrecio() + " €)");
                        }
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Por favor, introduce un número válido.");
                lector.nextLine(); // Limpiamos el Scanner para evitar bloqueos
            }
        }
        System.out.println("¡Gracias por usar el sistema!");
    }
}

class Producto {
    private String nombre;
    private double precio;

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
}
