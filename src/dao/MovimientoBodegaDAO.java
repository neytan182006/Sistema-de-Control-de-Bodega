package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MovimientoBodegaDAO {

    private final ArticuloDAO articuloDAO = new ArticuloDAO();

    public boolean registrarMovimiento(int idArticulo, String tipo, int cantidad) throws SQLException {
        int delta = "E".equalsIgnoreCase(tipo) ? cantidad : -cantidad;

        try (Connection con = ConexionBD.obtenerConexion()) {
            con.setAutoCommit(false);
            try {
                boolean ok = articuloDAO.ajustarStock(con, idArticulo, delta);
                if (!ok) {
                    con.rollback();
                    return false;
                }

                String sql = "INSERT INTO MOVIMIENTOS_BODEGA (IdArticulo, Tipo, Cantidad) VALUES (?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, idArticulo);
                    ps.setString(2, tipo.toUpperCase());
                    ps.setInt(3, cantidad);
                    ps.executeUpdate();
                }

                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }
}
