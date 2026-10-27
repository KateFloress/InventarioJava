package mi.inventario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConexionDB {
    //  'jdbc' debe ir estrictamente en minúsculas
    private static final String URL = "jdbc:sqlite:inventario.db";

    public static Connection conectar() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return con;
    }

    public static void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS productos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "precio REAL NOT NULL);";

        try (Connection con = conectar();
             Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }

    public static void insertarProducto(String nombre, double precio) {
        String sql = "INSERT INTO productos(nombre, precio) VALUES(?, ?)";

        try (Connection con = conectar();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setDouble(2, precio);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
        }
    }

    public static ArrayList<Producto> obtenerProductos() {
        ArrayList<Producto> lista = new ArrayList<>();
        String sql = "SELECT nombre, precio FROM productos";

        try (Connection con = conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                lista.add(new Producto(nombre, precio));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener productos: " + e.getMessage());
        }
        return lista;
    }
}
