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
        System.out.println("----------------------| Instante " + instante);
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
                if(elevador.getPosY() == andar.getPosY()){
                    System.out.println("Elevador " + elevador.getVelocidade() + " - Passando pelo andar" + andar.getAndar());

                    if(!elevador.isDescendo() && andar.fila()){
                        System.out.println("Antes de entrar: " + elevador.lugaresLivres());
                        elevador.addPessoas(andar.removePessoas(elevador.lugaresLivres()));
                        System.out.println("Depois de entrar: "+elevador.lugaresLivres());
                    }
//                    //Se Elevador abriu para despachar Pessoas ou se está descendo
//                    if(elevador.removePessoas(andar.getAndar()) || !elevador.isDescendo()){
//                        System.out.println("Antes de entrar: " + elevador.lugaresLivres());
//                        elevador.addPessoas(andar.removePessoas(elevador.lugaresLivres()));
//                        System.out.println("Depois de entrar: "+elevador.lugaresLivres());
//                    }
                }
            });
        });

    }

    public ArrayList<Elevador> getElevadores() {
        return elevadores;
    }

    public ArrayList<Andar> getAndares() {
        return andares;
    }
}
