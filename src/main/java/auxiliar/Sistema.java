package auxiliar;

import elementos.Andar;
import elementos.Elevador;
import java.util.ArrayList;

public class Sistema {
    private int maximoAndares;
    private Arquivo arq;
    private ArrayList<Elevador> elevadores;
    private ArrayList<Andar> andares;
    private int instante;

    public Sistema(int maximoAndares, String arquivoInstantes, ArrayList<Elevador> elevadores, ArrayList<Andar> andares) {
        this.maximoAndares = maximoAndares;
        this.arq = new Arquivo(arquivoInstantes);
        this.elevadores = elevadores;
        this.andares = andares;
        this.instante = 0;
    }

    public void gerenciar(){
        //Adicionando Pessoas nos Andares
        addPessoasEmAndares();
        //disparo elevadores que estão parados
        gerenciarElevadores();

        instante++;
    }

    /**
     * Esse método recebe uma lista de Pessoas que chegaram na fila no instante atual.
     * Adiciona essas pessoas nas filas dos determinados Andares.
     */
    private void addPessoasEmAndares(){
        System.out.println("----------------------------------------------------| Instante " + instante);
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

                    System.out.println("Elevador " + elevador.getVelocidade() + " - Passando pelo andar" + andar.getAndar());
                    System.out.println("Antes: " + elevador.lugaresLivres());

                    //verificando se pessoas querem descer no andar atual
                    //se sim, será removido e porta fica aberta
                    elevador.removePessoas(andar.getAndar());

                    //se parou em andar que possui fila
                    if (elevador.isPortaAberta() && andar.fila()) {
                        System.out.println("SITUAÇÃO 1");
                        elevador.addPessoas(andar.removePessoas(elevador.lugaresLivres()));
                    }

                    //se está descendo e tiver fila no andar
                    else if (elevador.isDescendo() && andar.fila()) {
                        System.out.println("SITUAÇÃO 2");
                        elevador.addPessoas(andar.removePessoas(elevador.lugaresLivres()));
                    }

                    //se ta subindo e não está carregando ninguém
                    else if (!elevador.isDescendo() && !elevador.fila()) {
                        System.out.println("SITUAÇÃO 3");
                        elevador.addPessoas(andar.removePessoas(elevador.lugaresLivres()));
                    }

                    //se está no último andar
                    else if(andar.getAndar() == andares.size()-1){
                        System.out.println("SITUAÇÃO 4");
                        elevador.addPessoas(andar.removePessoas(elevador.lugaresLivres()));
                    }

                    System.out.println("Depois: " + elevador.lugaresLivres());

                    //se tem alguem no elevador
                    if(elevador.fila()){
                        elevador.viajar(-1);
                    }

                }
            });
        });

        //nesse ponto já saiu quem tinha que sair, ja entrou quem tinha que entrar
        //precisamos verificar se ainda existe fila nos andares
        //se existir, o elevador mais próximo deve ser chamado

        andares.forEach(andar -> {
            if(andar.fila()){
                int aux = -1;
                for (int i = 0; i < elevadores.size(); i++) {
                    if(elevadores.get(i).isDisponivel()){
                        aux = i;
                        break;
                    }
                }
                if(aux != -1){
                    elevadores.get(aux).viajar(andar.getPosY());
                }
            }
        });
    }

}
