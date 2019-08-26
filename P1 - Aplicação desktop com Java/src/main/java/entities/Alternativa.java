package entities;

public class Alternativa {
    private int idAlternativa;
    private int idQuestao;
    private String alternativa;
    private int totalDeVotos;

    public Alternativa(int idAlternativa, int idQuestao, String alternativa, int totalDeVotos) {
        this.idAlternativa = idAlternativa;
        this.idQuestao = idQuestao;
        this.alternativa = alternativa;
        this.totalDeVotos = totalDeVotos;
    }

    @Override
    public String toString() {
        return  String.format("|%-25s|%-25s|%-25s|%-25s|",
                idAlternativa, idQuestao, alternativa, totalDeVotos);
    }

    public int getIdAlternativa() {
        return idAlternativa;
    }

    public void setIdAlternativa(int idAlternativa) {
        this.idAlternativa = idAlternativa;
    }

    public int getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(int idQuestao) {
        this.idQuestao = idQuestao;
    }

    public String getAlternativa() {
        return alternativa;
    }

    public void setAlternativa(String alternativa) {
        this.alternativa = alternativa;
    }

    public int getTotalDeVotos() {
        return totalDeVotos;
    }

    public void setTotalDeVotos(int totalDeVotos) {
        this.totalDeVotos = totalDeVotos;
    }
}
