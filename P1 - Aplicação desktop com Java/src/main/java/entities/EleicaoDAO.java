package entities;

import db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EleicaoDAO {

    public static List<Eleicao> listarTodas() {
        List<Eleicao> eleicoes = new ArrayList<>();
        try (Connection conexao = ConnectionFactory.getConnection()) {
            String sql = "SELECT * from Eleicao";
            try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Eleicao e = new Eleicao(
                            rs.getInt("idEleicao"),
                            rs.getString("nome"),
                            rs.getBoolean("iniciada"),
                            rs.getBoolean("encerrada"));
                    eleicoes.add(e);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }

        return eleicoes;
    }
}
