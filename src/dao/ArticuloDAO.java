package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticuloDAO {

    public void listar() throws SQLException {
        String sql = "SELECT IdArticulo, Nombre, StockActual, StockMinimo FROM ARTICULOS ORDER BY Nombre";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int actual = rs.getInt("StockActual");
                int minimo = rs.getInt("StockMinimo");
                System.out.printf("[%d] %-25s Stock: %-6d Minimo: %-6d %s%n",
                        rs.getInt("IdArticulo"), rs.getString("Nombre"), actual, minimo,
                        actual < minimo ? "*** REABASTECER ***" : "");
            }
        }
    }

    public void listarBajoMinimo() throws SQLException {
        String sql = "SELECT IdArticulo, Nombre, StockActual, StockMinimo FROM ARTICULOS "
                + "WHERE StockActual < StockMinimo ORDER BY Nombre";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean hayDatos = false;
            while (rs.next()) {
                hayDatos = true;
                System.out.printf("[%d] %-25s Stock: %d (minimo %d)%n",
                        rs.getInt("IdArticulo"), rs.getString("Nombre"),
                        rs.getInt("StockActual"), rs.getInt("StockMinimo"));
            }
            if (!hayDatos) {
                System.out.println("Ningun articulo esta por debajo del stock minimo.");
            }
        }
    }

    public boolean ajustarStock(Connection con, int idArticulo, int delta) throws SQLException {
        String sql = "UPDATE ARTICULOS SET StockActual = StockActual + ? WHERE IdArticulo = ? AND StockActual + ? >= 0";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, delta);
            ps.setInt(2, idArticulo);
            ps.setInt(3, delta);
            return ps.executeUpdate() > 0;
        }
    }
}
