package entities;

import db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EleitorDAO {
    public static List<Eleitor> listarTodas() {
        List<Eleitor> eleitores = new ArrayList<>();
        try (Connection conexao = ConnectionFactory.getConnection()) {
            String sql = "SELECT * from Eleitor";
            try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Eleitor e = new Eleitor(
                            rs.getInt("idEleitor"),
                            rs.getBoolean("situacaoVoto"),
                            rs.getString("login"),
                            rs.getInt("idEleicao"));
                    eleitores.add(e);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }

        return eleitores;
    }
}
