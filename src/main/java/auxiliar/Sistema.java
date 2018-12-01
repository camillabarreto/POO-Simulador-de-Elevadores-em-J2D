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

                    elevador.setAndarAtual(andar.getAndar());

                    //System.out.println("Elevador " + elevador.getVelocidade() + " - Passando pelo andar" + andar.getAndar());

                    //verificando se pessoas querem descer no andar atual
                    //se sim, será removido e porta fica aberta
                    elevador.removePessoas(andar.getAndar());

                    //se parou em andar que possui fila
                    if (elevador.isPortaAberta() && andar.fila()) {
                        elevador.addPessoas(andar.removePessoas(elevador.lugaresLivres()));
                    }

                    //se está descendo e tiver fila no andar
                    else if (elevador.isDescendo() && andar.fila()) {
                        elevador.addPessoas(andar.removePessoas(elevador.lugaresLivres()));
                    }

                    //se ta subindo e não está carregando ninguém
                    else if (!elevador.isDescendo() && !elevador.fila()) {
                        elevador.addPessoas(andar.removePessoas(elevador.lugaresLivres()));
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
                    //System.out.println(" - FilaELevador: " + elevador.filaTamanho());
                }
            });
            if(andar.getAndar() == 4) System.out.println("Andar " + andar.getAndar() + " : " + andar.filaTamanho());
            andar.carregarImagem();
        });

        //se tiver elevador livre, manda ele se deslocar para direção do andar que tem fila
        //se elevador ocupado
        andares.forEach(andar -> {
            if(andar.fila()){
                for (int i = 0; i < elevadores.size(); i++) {
                    Elevador e = elevadores.get(i);
                    if(e.isDisponivel()){
                        System.out.println("Elevador " + (i+1) + " disponivel");
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

}
