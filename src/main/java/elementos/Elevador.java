package elementos;

import auxiliar.Pessoa;
import gui.Tela;

import java.util.ArrayList;

public class Elevador extends Elemento{
    private ArrayList<Pessoa> pessoas;
    private int velocidade; // instantes por andar
    private int capacidadeMaxima;
    private int capacidadeAtual;
    private boolean descendo;
    private int numeroViagens;

    public Elevador(Tela pai, String imagemNome, int posX, int posY, int velocidade, int capacidade) {
        super(pai, imagemNome, posX, posY);
        this.pessoas = new ArrayList<>();
        this.velocidade = velocidade;
        this.capacidadeMaxima = capacidade;
        this.capacidadeAtual = 0;
        this.descendo = true;
        this.numeroViagens = 0;
    }

    public boolean isDescendo(){
        return descendo;
    }

    public int lugaresLivres(){
        return capacidadeMaxima - capacidadeAtual;
    }

    public void addPessoas(ArrayList<Pessoa> pessoas){
        numeroViagens++;
        pessoas.addAll(pessoas);
    }

    public boolean removePessoas(int andarAtual) {
        ArrayList<Pessoa> saida = new ArrayList<>();
        pessoas.forEach(p ->{
            if(p.getAndarDestino() == andarAtual){
                saida.add(pessoas.remove(pessoas.indexOf(p)));
            }
        });
        return saida.size() > 0;
    }

    @Override
    public void atualizar() {
    }
}
