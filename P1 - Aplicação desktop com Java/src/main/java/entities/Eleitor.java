package entities;

public class Eleitor {
    private int idEleitor;
    private boolean situacaoVoto;
    private String login;
    private int idEleicao;

    public Eleitor(int idEleitor, boolean situacaoVoto, String login, int idEleicao) {
        this.idEleitor = idEleitor;
        this.situacaoVoto = situacaoVoto;
        this.login = login;
        this.idEleicao = idEleicao;
    }

    @Override
    public String toString() {
        return  String.format("|%-25s|%-25s|%-25s|%-25s|",
                idEleitor, situacaoVoto, login, idEleicao);
    }

    public int getIdEleitor() {
        return idEleitor;
    }

    public void setIdEleitor(int idEleitor) {
        this.idEleitor = idEleitor;
    }

    public boolean isSituacaoVoto() {
        return situacaoVoto;
    }

    public void setSituacaoVoto(boolean situacaoVoto) {
        this.situacaoVoto = situacaoVoto;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getIdEleicao() {
        return idEleicao;
    }

    public void setIdEleicao(int idEleicao) {
        this.idEleicao = idEleicao;
    }
}
