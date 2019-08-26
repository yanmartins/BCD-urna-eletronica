package entities;

public class Eleicao {
    private int idEleicao;
    private String nome;
    private boolean iniciada;
    private boolean encerrada;

    public Eleicao(int idEleicao, String nome, boolean iniciada, boolean encerrada) {
        this.idEleicao = idEleicao;
        this.nome = nome;
        this.iniciada = iniciada;
        this.encerrada = encerrada;
    }

    @Override
    public String toString() {
        return  String.format("|%-25s|%-25s|%-25s|%-25s|",
                idEleicao, nome, iniciada, encerrada);
    }

    public int getIdEleicao() {
        return idEleicao;
    }

    public void setIdEleicao(int idEleicao) {
        this.idEleicao = idEleicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isIniciada() {
        return iniciada;
    }

    public void setIniciada(boolean iniciada) {
        this.iniciada = iniciada;
    }

    public boolean isEncerrada() {
        return encerrada;
    }

    public void setEncerrada(boolean encerrada) {
        this.encerrada = encerrada;
    }
}
