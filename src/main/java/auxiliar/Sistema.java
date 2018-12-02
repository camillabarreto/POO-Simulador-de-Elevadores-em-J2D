package auxiliar;

import elementos.Andar;
import elementos.Elevador;

import javax.swing.*;
import java.util.ArrayList;

public class Sistema {
    private int maximoAndares;
    private Arquivo arq;
    private ArrayList<Elevador> elevadores;
    private ArrayList<Andar> andares;
    private int instante;
    private ArrayList<String> mensagens;
    private JTextArea console;

    public Sistema(int maximoAndares, String arquivoInstantes, ArrayList<Elevador> elevadores, ArrayList<Andar> andares, JTextArea console) {
        this.maximoAndares = maximoAndares;
        this.arq = new Arquivo(arquivoInstantes);
        this.elevadores = elevadores;
        this.andares = andares;
        this.instante = 0;
        this.mensagens = new ArrayList<>();
        this.console = console;
    }

    public ArrayList<String> gerenciar(){
        mensagens.clear();
        String mensagemElevador = "INSTANTE " + instante + "----------------\n";
        mensagens.add(mensagemElevador);

        System.out.println("----------------------------------------------------| Instante " + instante);
        //Adicionando Pessoas nos Andares
        addPessoasEmAndares();
        //disparo elevadores que estão parados
        gerenciarElevadores();

        instante++;

        mensagens.forEach(s -> {
            console.append(s);
        });

        return mensagens;
    }

    /**
     * Esse método recebe uma lista de Pessoas que chegaram na fila no instante atual.
     * Adiciona essas pessoas nas filas dos determinados Andares.
     */
    private void addPessoasEmAndares(){
        //Lista com Pessoas que entrarão na fila
        ArrayList<Pessoa> pessoas = arq.proximoInstante(instante);

        //Buscando os Andares
        andares.forEach(andar->{

            //Buscando Pessoas que estão no Andar apontado
            for (int i = 0; i < pessoas.size(); i++) {
                //Removendo Pessoa da lista Pessoas e adicionando no devido Andar
                if(pessoas.get(i).getAndarOrigem() == andar.getAndar()){
                    int index = pessoas.indexOf(pessoas.get(i));
                    andar.adicionarPessoas(pessoas.remove(index));
                    i--;
                }
            }
        });
    }

    /**
     * Esse método verifica se os Elevadores estão passando por algum Andar.
     * Caso o Elevador possua Pessoas que tem seu destino no Andar atual, então devem ser despachadas
     */
    private void gerenciarElevadores(){
        andares.forEach(andar -> {
            elevadores.forEach(elevador -> {


                //Verifica se elevador está passando ou parado em algum andar
                if(elevador.getPosY() == andar.getPosY()) {

                    int filaSaida = 0;
                    int filaEntrando = 0;

                    //verificando se pessoas querem descer no andar atual
                    //se sim, será removido e porta fica aberta

                    elevador.setAndarAtual(andar.getAndar());

                    filaSaida = elevador.removePessoas(andar.getAndar());

                    //se parou em andar que possui fila
                    if (elevador.isPortaAberta() && andar.fila()) {
                        filaEntrando= filaEntrando + elevador.addPessoas(andar.removePessoas(elevador.lugaresLivres()));
                    }

                    //se está descendo e tiver fila no andar
                    else if ((elevador.isDescendo() && andar.fila()) && (elevador.lugaresLivres() > 0)) {
                        filaEntrando= filaEntrando + elevador.addPessoas(andar.removePessoas(elevador.lugaresLivres()));
                    }

                    //se ta subindo e não está carregando ninguém
                    else if (!elevador.isDescendo() && !elevador.fila()) {
                        filaEntrando= filaEntrando + elevador.addPessoas(andar.removePessoas(elevador.lugaresLivres()));
                    }

                    System.out.println("Fila elevador: " + elevador.filaTamanho());

                    //se tem alguem no elevador
                    if(!elevador.fila()) {
                        elevador.viajar(0);
                    }else if(andar.getAndar() == 0){
                        elevador.viajar(1);
                    }else if(andar.getAndar() == maximoAndares-1){
                        elevador.viajar(-1);
                    }else{
                        elevador.viajar(2);
                    }

                    String mensagemElevador = "Elevador " + elevador.getNumero() + ": andar " + andar.getAndar() + ", entraram " + filaEntrando + ", sairam " + filaSaida + "\n";
                    mensagens.add(mensagemElevador);
                }

            });
            andar.carregarImagem();
        });

        //se tiver elevador livre, manda ele se deslocar para direção do andar que tem fila
        //se elevador ocupado
        andares.forEach(andar -> {
            if(andar.fila()){
                for (int i = 0; i < elevadores.size(); i++) {
                    Elevador e = elevadores.get(i);
                    if(e.isDisponivel()){
                        if(andar.getPosY() < e.getPosY()){
                            e.viajar(1);
                        }else{
                            e.viajar(-1);
                        }
                        break;
                    }
                }
            }
        });

    }

    public void relatorioFinal(){
        String mensagem = "Número de viagens";
        for (int i = 0; i < elevadores.size(); i++) {
            mensagem = "\nElevador " + i + ": " + elevadores.get(i).getNumeroViagens();
        }
        System.out.println(mensagem);
        this.console.append(mensagem);
    }

}
