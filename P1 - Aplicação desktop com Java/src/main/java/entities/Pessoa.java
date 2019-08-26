package entities;

public class Pessoa {
    private String login;
    private String nome;
    private String senha;

    public Pessoa(String login, String nome, String senha) {
        this.login = login;
        this.nome = nome;
        this.senha = senha;
    }

    public Pessoa(){}

    @Override
    public String toString() {
        return  String.format("|%-25s|%-25s|%-25s|",
                login, nome, senha);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
