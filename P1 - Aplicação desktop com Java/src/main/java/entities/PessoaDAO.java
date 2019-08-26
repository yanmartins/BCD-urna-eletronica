package entities;

import db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    public static List<Pessoa> listarTodas() {
        List<Pessoa> pessoas = new ArrayList<>();
        try (Connection conexao = ConnectionFactory.getConnection()) {
            String sql = "SELECT * from Pessoa";
            try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pessoa c = new Pessoa(
                            rs.getString("login"),
                            rs.getString("nome"),
                            rs.getString("senha"));
                    pessoas.add(c);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }

        return pessoas;
    }
}
