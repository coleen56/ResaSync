package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.TypeChambreDAO;
import fr.bts.sio.resasync.model.entity.TypeChambre;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeChambreDAOImpl implements TypeChambreDAO {
    private Connection connection;


    @Override
    public TypeChambre findById(int idTypeChambre) {
        String sql = "SELECT * FROM typechambre WHERE idtypechambre = ?";
        TypeChambre typeChambre = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idTypeChambre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                typeChambre = new TypeChambre(
                        rs.getInt("idtypechambre"),
                        rs.getString("libelle")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return typeChambre;
    }

    @Override
    public void save(TypeChambre typeChambre) {
        String sql = "INSERT INTO typechambre(libelle) VALUES (?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, typeChambre.getLibelle());

            stmt.executeUpdate();
            System.out.println("TypeChambre inséré en BDD");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(TypeChambre typeChambre) {
        String sql = "UPDATE typechambre SET libelle = ? WHERE idtypechambre = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, typeChambre.getLibelle());
            stmt.setInt(2, typeChambre.getIdTypeChambre());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(TypeChambre typeChambre) {
        String sql = "DELETE FROM typechambre WHERE idtypechambre = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, typeChambre.getIdTypeChambre());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
