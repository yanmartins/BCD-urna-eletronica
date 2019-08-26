package entities;

public class Questao {
    private int idQuestao;
    private int idEleicao;
    private String questao;
    private int numeroDeAlternativas;

    public Questao(int idQuestao, int idEleicao, String questao, int numeroDeAlternativas) {
        this.idQuestao = idQuestao;
        this.idEleicao = idEleicao;
        this.questao = questao;
        this.numeroDeAlternativas = numeroDeAlternativas;
    }

    @Override
    public String toString() {
        return  String.format("|%-25s|%-25s|%-25s|%-25s|",
                idQuestao, idEleicao, questao, numeroDeAlternativas);
    }

    public int getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(int idQuestao) {
        this.idQuestao = idQuestao;
    }

    public int getIdEleicao() {
        return idEleicao;
    }

    public void setIdEleicao(int idEleicao) {
        this.idEleicao = idEleicao;
    }

    public String getQuestao() {
        return questao;
    }

    public void setQuestao(String questao) {
        this.questao = questao;
    }

    public int getNumeroDeAlternativas() {
        return numeroDeAlternativas;
    }

    public void setNumeroDeAlternativas(int numeroDeAlternativas) {
        this.numeroDeAlternativas = numeroDeAlternativas;
    }
}
