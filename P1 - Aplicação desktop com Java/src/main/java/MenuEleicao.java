
import entities.*;

import java.util.List;
import java.util.Scanner;

public class MenuEleicao {

    public static void main(String[] args) {

        Urna urna = new Urna();

        int op = 20;
        while (op != 0) {
            Scanner teclado = new Scanner(System.in);
            System.out.println("\n\n\n------------------------------------------------------------");
            System.out.println("----------------------- MENU PRINCIPAL ---------------------");
            System.out.println("------------------------------------------------------------");
            System.out.println("01 - Criar eleição");
            System.out.println("02 - Adicionar questões");
            System.out.println("03 - Adicionar repostas");
            System.out.println("04 - Adicionar eleitores");
            System.out.println("05 - Abrir eleicao");
            System.out.println("06 - Fechar eleição");
            System.out.println("07 - Apurar eleição");
            System.out.println("08 - Exibir resultado de uma eleição");
            System.out.println("09 - Votar em uma eleição");
            System.out.println("10 - Sair");
            System.out.println("Digite a opção desejada: \n");
            op = teclado.nextInt();
            System.out.println("\n");

            switch (op) {
                case 1:
                    System.out.println("\n\n\n------------------------------------------------------------");
                    System.out.println("----------------- MENU DE CRIAÇÃO DE ELEIÇÃO ---------------");
                    System.out.println("------------------------------------------------------------");

                    System.out.println("\nInforme o título desta eleição:");
                    Scanner teclado2 = new Scanner(System.in);
                    String tituloEleicao = teclado2.nextLine();

                    System.out.println("\nA eleição criada será: ");
                    System.out.println(tituloEleicao + "\n");
                    System.out.println("\nConfirmar?");
                    System.out.println("1 - Sim");
                    System.out.println("2 - Não");
                    int confirmacao = teclado2.nextInt();

                    if(confirmacao == 1){
                        urna.criarEleicao(tituloEleicao);
                        System.out.println("\nEleição criada");
                    }
                    else {
                        System.out.println("\nEleição não será criada");
                    }
                    break;

                case 2:
                    System.out.println("\n\n\n------------------------------------------------------------");
                    System.out.println("-------- MENU DE CRIAÇÃO DE QUESTÕES PARA UMA QUESTÃO ------");
                    System.out.println("------------------------------------------------------------");

                    System.out.println("\nSelecione para qual eleição será a sua questão: ");

                    List<Eleicao> eleicoes = EleicaoDAO.listarTodas();

                    int i = 0;
                    for (Eleicao eleicao : eleicoes) {
                        i++;
                        System.out.println(i + " - " + eleicao.getNome());
                    }

                    teclado2 = new Scanner(System.in);
                    int q = teclado2.nextInt();

                    int idLocalEleicao = (q-1);
                    System.out.println("\nA eleição selecionada foi: ");
                    System.out.println(eleicoes.get(idLocalEleicao).getNome());

                    System.out.println("\nInforme a questão: ");
                    teclado2 = new Scanner(System.in);
                    String questaoTxt = teclado2.nextLine();

                    System.out.println("\nInforme o número máximo de respostas nesta questão");
                    teclado2 = new Scanner(System.in);
                    int nMax = teclado2.nextInt();

                    int idEleicao = eleicoes.get(idLocalEleicao).getIdEleicao();

                    System.out.println("\nA questão criada será: ");
                    System.out.println(questaoTxt + "\n");
                    System.out.println("\nConfirmar?");
                    System.out.println("1 - Sim");
                    System.out.println("2 - Não");
                    confirmacao = teclado2.nextInt();

                    if(confirmacao == 1){
                        urna.criarQuestao(idEleicao,questaoTxt,nMax);
                        List<Questao> questoes = QuestaoDAO.listarTodas();
                        urna.criarAlternativa(questoes.size(), "Branco");   // Cria alternativa Branco
                        urna.criarAlternativa(questoes.size(), "Nulo");     // Cria alternativa Nulo
                        System.out.println("\nQuestão criada");
                    }
                    else {
                        System.out.println("\nQuestão não será criada");
                    }
                    break;

                case 3:
                    System.out.println("\n\n\n------------------------------------------------------------");
                    System.out.println("------- MENU DE CRIAÇÃO DE RESPOSTAS PARA UMA QUESTÃO ------");
                    System.out.println("------------------------------------------------------------");

                    System.out.println("\nSelecione para qual eleição será a sua questão");
                    eleicoes = EleicaoDAO.listarTodas();

                    i = 0;
                    for (Eleicao eleicao : eleicoes) {
                        i++;
                        System.out.println(i + " - " + eleicao.getNome());
                    }

                    teclado2 = new Scanner(System.in);
                    q = teclado2.nextInt();

                    idLocalEleicao = (q-1);
                    System.out.println("\nA eleição selecionada foi: ");
                    System.out.println(eleicoes.get(idLocalEleicao).getNome());

                    idEleicao = eleicoes.get(idLocalEleicao).getIdEleicao();

                    System.out.println("\nSelecione para qual questão será a sua alternativa");
                    List<Questao> questoes = QuestaoDAO.listarTodas();

                    i = 0;
                    int idQLocal[] = new int[questoes.size()];
                    int contadorLocal = 0;

                    for (Questao questao : questoes) {
                        i++;
                        if(idEleicao == questao.getIdEleicao()){
                            contadorLocal++;
                            idQLocal[contadorLocal-1] = i;
                            System.out.println((contadorLocal) + " - " + questao.getQuestao());
                        }
                    }

                    teclado2 = new Scanner(System.in);
                    q = teclado2.nextInt();

                    int j = idQLocal[q-1];
                    System.out.println("\nA questão selecionada foi: ");
                    System.out.println(questoes.get(j-1).getQuestao());

                    System.out.println("\nInsira a alternativa desejada para essa questão:");
                    teclado2 = new Scanner(System.in);
                    String alternativaTxt = teclado2.nextLine();

                    System.out.println("\nA alternativa criada será: ");
                    System.out.println(alternativaTxt + "\n");
                    System.out.println("\nConfirmar?");
                    System.out.println("1 - Sim");
                    System.out.println("2 - Não");
                    confirmacao = teclado2.nextInt();

                    if(confirmacao == 1){
                        int idLocalQuestao = questoes.get(j-1).getIdQuestao();
                        urna.criarAlternativa(idLocalQuestao, alternativaTxt);
                        System.out.println("Alternativa criada");
                    }
                    else {
                        System.out.println("Alternativa não será criada");
                    }
                    break;

                case 4:
                    System.out.println("\n\n\n------------------------------------------------------------");
                    System.out.println("---------------- MENU DE ADIÇÃO DE ELEITORES ---------------");
                    System.out.println("------------------------------------------------------------");
                    eleicoes = EleicaoDAO.listarTodas();
                    i = 0;
                    for (Eleicao eleicao : eleicoes) {
                        i++;
                        System.out.println(i + " - " + eleicao.getNome());
                    }
                    if(i == 0){
                        System.out.println("\nNão existem eleições criadas");
                    }
                    else {
                        System.out.println("\nInforme a eleição que receberá eleitores");
                        teclado2 = new Scanner(System.in);
                        q = teclado2.nextInt();

                        idLocalEleicao = (q - 1);
                        System.out.print("\nA eleição selecionada foi: ");
                        System.out.println(eleicoes.get(idLocalEleicao).getNome());

                        System.out.println("\nInforme o caminho do arquivo txt com os logins dos novos eleitores:");
                        teclado2 = new Scanner(System.in);
                        String arquivo = teclado2.nextLine();

                        idEleicao = eleicoes.get(idLocalEleicao).getIdEleicao();
                        urna.carregarEleitores(idEleicao, arquivo);
                    }
                    break;

                case 5:
                    System.out.println("\n\n\n------------------------------------------------------------");
                    System.out.println("------------- MENU DE ABERTURA DE UMA ELEIÇÃO --------------");
                    System.out.println("------------------------------------------------------------");

                    eleicoes = EleicaoDAO.listarTodas();

                    i = 0;
                    int idELocal[] = new int[eleicoes.size()];
                    contadorLocal = 0;

                    for (Eleicao eleicao : eleicoes) {
                        i++;
                        if(!eleicao.isEncerrada() && !eleicao.isIniciada()) {
                            contadorLocal++;
                            idELocal[contadorLocal - 1] = i;
                            System.out.println((contadorLocal) + " - " + eleicao.getNome());
                        }
                    }
                    if(contadorLocal == 0){
                        System.out.println("\nNão existem eleições para serem abertas");
                    }
                    else {
                        System.out.println("\nSelecione a eleição que será aberta:");
                        teclado2 = new Scanner(System.in);
                        q = teclado2.nextInt();

                        idLocalEleicao = idELocal[(q - 1)];
                        System.out.print("\nA eleição que será aberta é: ");
                        System.out.println(eleicoes.get(idLocalEleicao - 1).getNome());

                        idEleicao = eleicoes.get(idLocalEleicao - 1).getIdEleicao();
                        urna.abrirEleicao(idEleicao);
                    }
                    break;

                case 6:
                    System.out.println("\n\n\n------------------------------------------------------------");
                    System.out.println("------------ MENU DE ENCERRAMENTO DE UMA ELEIÇÃO -----------");
                    System.out.println("------------------------------------------------------------");

                    eleicoes = EleicaoDAO.listarTodas();

                    int idELocal2[] = new int[eleicoes.size()];
                    contadorLocal = 0;

                    i = 0;

                    for (Eleicao eleicao : eleicoes) {
                        i++;
                        if(eleicao.isIniciada() && !eleicao.isEncerrada()) {
                            contadorLocal++;
                            idELocal2[contadorLocal - 1] = i;
                            System.out.println((contadorLocal) + " - " + eleicao.getNome());
                        }
                    }
                    if(contadorLocal == 0){
                        System.out.println("\nNão existem eleições para serem encerradas");
                    }
                    else {
                        System.out.println("\nSelecione a eleição que será encerrada:");
                        teclado2 = new Scanner(System.in);
                        q = teclado2.nextInt();

                        idLocalEleicao = idELocal2[(q - 1)];
                        System.out.print("\nA eleição que será encerrada é: ");
                        System.out.println(eleicoes.get(idLocalEleicao-1).getNome());

                        idEleicao = eleicoes.get(idLocalEleicao-1).getIdEleicao();
                        urna.encerrarEleicao(idEleicao);
                    }
                    break;

                case 7:
                    System.out.println("\n\n\n------------------------------------------------------------");
                    System.out.println("--------- MENU DE APURAÇÃO DE ELEIÇÕES EM EXECUÇÃO ---------");
                    System.out.println("------------------------------------------------------------");

                    eleicoes = EleicaoDAO.listarTodas();

                    i = 0;
                    for (Eleicao eleicao : eleicoes) {
                        if(eleicao.isIniciada() && !eleicao.isEncerrada()) {
                            i++;
                            System.out.println(i + " - " + eleicao.getNome());
                        }
                    }

                    if(i == 0){
                        System.out.println("\nNão há eleições abertas para apuração!");
                        System.out.println("Por favor, abra alguma eleição.");
                    }
                    else {

                        System.out.println("\nSelecione a eleição que você deseja apurar:");
                        teclado2 = new Scanner(System.in);
                        q = teclado2.nextInt();

                        idLocalEleicao = (q - 1);


                        idEleicao = eleicoes.get(idLocalEleicao).getIdEleicao();
                        questoes = QuestaoDAO.listarTodas();


                        int idQuestao;
                        List<Alternativa> alternativas = AlternativaDAO.listarTodas();

                        System.out.println("--------------------------------------------------------------------------------------------------------");
                        System.out.println(eleicoes.get(idLocalEleicao).getNome());
                        System.out.println("--------------------------------------------------------------------------------------------------------");
                        System.out.println(String.format("|%-60s|%-25s|%-15s|", "Questão", "Alternativa", "Total de Votos"));
                        System.out.println("--------------------------------------------------------------------------------------------------------");
                        i = 0;

                        String questaoApurada;
                        String alternativaApurada;
                        int votosApurados;

                        for (Questao questao : questoes) {

                            if (idEleicao == questao.getIdEleicao()) {
                                questaoApurada = questao.getQuestao();

                                idQuestao = questoes.get(i).getIdQuestao();

                                for (Alternativa alternativa : alternativas) {

                                    if (idQuestao == alternativa.getIdQuestao()) {
                                        alternativaApurada = alternativa.getAlternativa();
                                        votosApurados = alternativa.getTotalDeVotos();
                                        System.out.println(String.format("|%-60s|%-25s|%-15s|", questaoApurada, alternativaApurada, votosApurados));
                                    }
                                }
                            }
                            i++;
                        }
                        System.out.println("--------------------------------------------------------------------------------------------------------");
                    }
                    break;

                case 8:
                    System.out.println("\n\n\n------------------------------------------------------------");
                    System.out.println("--------- MENU DE RESULTADO DE ELEIÇÕES ENCERRADAS ---------");
                    System.out.println("------------------------------------------------------------");

                    eleicoes = EleicaoDAO.listarTodas();

                    i = 0;
                    for (Eleicao eleicao : eleicoes) {
                        if(eleicao.isEncerrada()) {
                            i++;
                            System.out.println(i + " - " + eleicao.getNome());
                        }
                    }

                    if(i == 0){
                        System.out.println("\nNão há eleições encerradas!");
                        System.out.println("Por favor, encerre alguma eleição.");
                    }
                    else {

                        System.out.println("\nSelecione a eleição que você deseja obter resultados:");
                        teclado2 = new Scanner(System.in);
                        q = teclado2.nextInt();

                        idLocalEleicao = (q - 1);


                        idEleicao = eleicoes.get(idLocalEleicao).getIdEleicao();
                        questoes = QuestaoDAO.listarTodas();


                        int idQuestao;
                        List<Alternativa> alternativas = AlternativaDAO.listarTodas();

                        System.out.println("--------------------------------------------------------------------------------------------------------");
                        System.out.println(eleicoes.get(idLocalEleicao).getNome());
                        System.out.println("--------------------------------------------------------------------------------------------------------");
                        System.out.println(String.format("|%-60s|%-25s|%-15s|", "Questão", "Alternativa", "Total de Votos"));
                        System.out.println("--------------------------------------------------------------------------------------------------------");
                        i = 0;

                        String questaoApurada;
                        String alternativaApurada;
                        int votosApurados;

                        for (Questao questao : questoes) {

                            if (idEleicao == questao.getIdEleicao()) {
                                questaoApurada = questao.getQuestao();

                                idQuestao = questoes.get(i).getIdQuestao();

                                for (Alternativa alternativa : alternativas) {

                                    if (idQuestao == alternativa.getIdQuestao()) {
                                        alternativaApurada = alternativa.getAlternativa();
                                        votosApurados = alternativa.getTotalDeVotos();
                                        System.out.println(String.format("|%-60s|%-25s|%-15s|", questaoApurada, alternativaApurada, votosApurados));
                                    }
                                }
                            }
                            i++;
                        }
                        System.out.println("--------------------------------------------------------------------------------------------------------");
                    }
                    break;

                case 9:

                    System.out.println("\n\n\n------------------------------------------------------------");
                    System.out.println("--------------------- CABINE DE VOTAÇÃO --------------------");
                    System.out.println("------------------------------------------------------------");
                    System.out.println("\nPara qual eleição você deseja votar?");

                    eleicoes = EleicaoDAO.listarTodas();
                    i = 0;
                    for (Eleicao eleicao : eleicoes) {
                        if(eleicao.isIniciada() && !eleicao.isEncerrada()) {
                            i++;
                            System.out.println(i + " - " + eleicao.getNome());
                        }
                    }
                    if(i == 0){
                        System.out.println("\nNão há nenhuma eleição aberta no momento");
                    }
                    else {

                        teclado2 = new Scanner(System.in);
                        q = teclado2.nextInt();
                        idLocalEleicao = (q - 1);

                        System.out.println("\nInforme seu login");
                        teclado2 = new Scanner(System.in);
                        String login = teclado2.nextLine();
                        idEleicao = eleicoes.get(idLocalEleicao).getIdEleicao();
                        List<Eleitor> eleitores = EleitorDAO.listarTodas();
                        List<Pessoa> pessoas = PessoaDAO.listarTodas();

                        i = 0;
                        for (Eleitor eleitor : eleitores) {
                            if (idEleicao == eleitor.getIdEleicao() && login.equals(eleitor.getLogin())) {
                                i++;
                            }
                        }

                        j = 0;
                        for (Eleitor eleitor : eleitores) {
                            if (eleitor.isSituacaoVoto() && login.equals(eleitor.getLogin()) && idEleicao == eleitor.getIdEleicao()) {
                                j++;
                            }
                        }

                        if (i == 0) {
                            System.out.println("\nEste usuário não está autorizado a votar nessa eleição!");
                        } else if (j > 0) {
                            System.out.println("\nEste usuário já votou!");
                        } else {
                            System.out.println("\nInforme sua senha");
                            teclado2 = new Scanner(System.in);
                            String senha = teclado2.nextLine();

                            j = 0;
                            for (Eleitor eleitor : eleitores) {
                                for (Pessoa pessoa : pessoas) {
                                    if (senha.equals(pessoa.getSenha()) && login.equals(eleitor.getLogin())) {
                                        j++;
                                    }
                                }
                            }

                            if (j == 0) {
                                System.out.println("\nSenha incorreta!");
                            } else {
                                questoes = QuestaoDAO.listarTodas();
                                List<Alternativa> alternativas = AlternativaDAO.listarTodas();
                                i = 0;
                                for (Questao questao : questoes) {

                                    if (idEleicao == questao.getIdEleicao()) {
                                        System.out.println(questao.getQuestao());
                                        int quantidadeDeRepostas = questao.getNumeroDeAlternativas();
                                        int idQuestao = questao.getIdQuestao();

                                        int idAlternativaLocal[] = new int[alternativas.size()];
                                        contadorLocal = 0;

                                        j = 0;
                                        for (Alternativa alternativa : alternativas) {
                                            j++;
                                            if (idQuestao == alternativa.getIdQuestao()) {
                                                if (!alternativa.getAlternativa().equals("Nulo")) {
                                                    contadorLocal++;
                                                    idAlternativaLocal[contadorLocal - 1] = j;
                                                    System.out.println((contadorLocal) + " - " + alternativa.getAlternativa());
                                                }
                                            }
                                        }
                                        if (quantidadeDeRepostas > 1) {
                                            System.out.println("\nEsta questão suporta " + quantidadeDeRepostas + " repostas.");
                                        }

                                        while (quantidadeDeRepostas != 0) {

                                            System.out.println("\nInsira o seu voto");
                                            teclado2 = new Scanner(System.in);
                                            int voto = teclado2.nextInt();

                                            int idAlternativa = 0;

                                            if (voto > (contadorLocal)) {
                                                System.out.println("\nVoto Nulo");
                                                for (Alternativa alternativa : alternativas) {
                                                    if (alternativa.getAlternativa().equals("Nulo") && idQuestao == alternativa.getIdQuestao()) {
                                                        contadorLocal++;
                                                        idAlternativa = alternativa.getIdAlternativa();
                                                    }
                                                }
                                            } else {
                                                voto = idAlternativaLocal[voto - 1];
                                                System.out.println("\nA questão selecionada foi: ");
                                                System.out.println(alternativas.get(voto - 1).getAlternativa());
                                                idAlternativa = alternativas.get(voto - 1).getIdAlternativa();
                                            }

                                            urna.computarVoto(idAlternativa);
                                            quantidadeDeRepostas--;
                                            i++;
                                        }

                                    }

                                }
                                urna.eleitorVotou(login, idEleicao);
                            }
                        }
                    }
                    break;

                case 10:
                    System.out.println("\nSaindo...");
                    return;

                default:
                    System.out.println("\nOpção Inválida!");
                    break;
            }
        }
    }
}
