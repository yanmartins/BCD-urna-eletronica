package entities;

import db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlternativaDAO {

    public static List<Alternativa> listarTodas() {
        List<Alternativa> alternativas = new ArrayList<>();
        try (Connection conexao = ConnectionFactory.getConnection()) {
            String sql = "SELECT * from Alternativa";
            try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Alternativa a = new Alternativa(
                            rs.getInt("idAlternativa"),
                            rs.getInt("idQuestao"),
                            rs.getString("alternativa"),
                            rs.getInt("totalDeVotos"));
                    alternativas.add(a);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }

        return alternativas;
    }
}
