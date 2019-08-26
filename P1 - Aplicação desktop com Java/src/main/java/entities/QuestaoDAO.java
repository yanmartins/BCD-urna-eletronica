package entities;

import db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestaoDAO {
    public static List<Questao> listarTodas() {
        List<Questao> questoes = new ArrayList<>();
        try (Connection conexao = ConnectionFactory.getConnection()) {
            String sql = "SELECT * from Questoes";
            try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Questao q = new Questao(
                            rs.getInt("idQuestao"),
                            rs.getInt("idEleicao"),
                            rs.getString("questao"),
                            rs.getInt("numeroDeAlternativas"));
                    questoes.add(q);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }

        return questoes;
    }

}
