
import db.ConnectionFactory;

import java.io.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Urna {


    /**
     * Função que adiciona uma linha na tabela Eleicao
     * no banco de dados banco_eleicao.
     * @param tituloEleicao nome dado a eleição.
     */
    public void criarEleicao(String tituloEleicao){
        Connection conexao = ConnectionFactory.getConnection();

        String sql = "INSERT INTO Eleicao (nome, iniciada, encerrada) VALUES "
                + "("
                + "'" + tituloEleicao + "',"
                + 0 + ","
                + 0
                + ")";

        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Função que adiciona uma linha na tabela Questoes
     * no banco de dados banco_eleicao.
     * @param idEleicao indica em qual eleição a questão adicionada faz parte.
     * @param questao texto da pergunta.
     * @param nMax número máximo de alternativas que esta questão pode receber.
     */
    public void criarQuestao(int idEleicao, String questao, int nMax){
        Connection conexao = ConnectionFactory.getConnection();

        String sql = "INSERT INTO Questoes (idEleicao, questao, numeroDeAlternativas) VALUES "
                + "("
                + idEleicao + ","
                + "'" + questao + "',"
                + nMax
                + ")";

        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Função que adiciona uma linha na tabela Alternativa
     * no banco de dados banco_eleicao.
     * @param idQuestao indica em qual questão a alternativa adicionada faz parte.
     * @param alternativa texto da alternativa.
     */
    public void criarAlternativa(int idQuestao, String alternativa){
        Connection conexao = ConnectionFactory.getConnection();

        String sql = "INSERT INTO Alternativa (idQuestao, alternativa, totalDeVotos) VALUES "
                + "("
                + idQuestao + ","
                + "'" + alternativa + "',"
                + 0
                + ")";

        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Atualiza coluna iniciada da tabela Eleicao
     * a fim de definir que a eleição está aberta.
     * @param idEleicao define qual eleição será aberta.
     */
    public void abrirEleicao(int idEleicao){
        Connection conexao = ConnectionFactory.getConnection();

        String sql = "UPDATE Eleicao SET iniciada = 1 WHERE idEleicao = " + idEleicao;

        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Atualiza coluna encerrada da tabela Eleicao
     * a fim de definir que a eleição não está aberta.
     * @param idEleicao define qual eleição será encerrada.
     */
    public void encerrarEleicao(int idEleicao){
        Connection conexao = ConnectionFactory.getConnection();

        String sql = "UPDATE Eleicao SET encerrada = 1 WHERE idEleicao = " + idEleicao;

        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Função que armazena um voto em determinada alternativa,
     * atualizando a coluna totalDeVotos da tabela Alternativa.
     * @param idAlternativa define qual alternativa recebeu um voto.
     */
    public void computarVoto(int idAlternativa){
        Connection conexao = ConnectionFactory.getConnection();

        String sql = "UPDATE Alternativa SET totalDeVotos = (totalDeVotos+1) WHERE idAlternativa = " + idAlternativa;

        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Função que atualiza coluna situacaoVoto da tabela Eleitor,
     * a fim de garantir que um eleitor não realize mais de um voto
     * na mesma eleição.
     * @param login chave estrangeira que define qual eleitor votou
     * @param idEleicao define em qual eleição ocorreu o voto
     */
    public void eleitorVotou(String login, int idEleicao){
        Connection conexao = ConnectionFactory.getConnection();

        String sql = "UPDATE Eleitor SET situacaoVoto = 1 WHERE (login = '" + login + "' and idEleicao = " + idEleicao + ")";

        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Função que carrega uma lista de logins de pessoas
     * para a tabela Eleitores em uma determinada eleição.
     * @param idEleicao identificador da eleição onde os eleitores serão carregados
     * @param arquivoEleitores nome do arquivo que contém logins de pessoas (futuros eleitores)
     */
    public void carregarEleitores(int idEleicao, String arquivoEleitores){
        File arquivo = new File(arquivoEleitores);

        String login;

        Connection conexao = ConnectionFactory.getConnection();

        try {
            Scanner leitor = new Scanner(arquivo);

            // varrendo o conteúdo do arquivo linha por linha
            while (leitor.hasNextLine()){
                login = leitor.nextLine();

                String sql = "INSERT INTO Eleitor (situacaoVoto, login, idEleicao) VALUES "
                        + "("
                        + 0 + ","
                        + "'" + login + "',"
                        + idEleicao
                        + ")";

                PreparedStatement stmt = null;
                try {
                    stmt = conexao.prepareStatement(sql);
                    stmt.executeUpdate();
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Erro ao tentar ler o arquivo: " + e.toString());
        }
    }
}
