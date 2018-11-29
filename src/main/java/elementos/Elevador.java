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
    private boolean portaAberta;
    private boolean disponivel;
    private int numeroViagens;

    public Elevador(Tela pai, String imagemNome, int posX, int posY, int velocidade, int capacidade) {
        super(pai, imagemNome, posX, posY);
        this.pessoas = new ArrayList<>();
        this.velocidade = velocidade;
        this.capacidadeMaxima = capacidade;
        this.capacidadeAtual = 0;
        this.descendo = false;
        this.portaAberta = true;
        this.disponivel = true;
        this.numeroViagens = 0;
    }

    public boolean isDisponivel(){ return disponivel; }

    public boolean isPortaAberta(){ return portaAberta; }

    public boolean isDescendo(){
        return descendo;
    }

    public boolean fila(){ return pessoas.size() > 0; }

    public int getVelocidade() { return velocidade; }

    public int lugaresLivres(){
        return capacidadeMaxima - capacidadeAtual;
    }

    public void addPessoas(ArrayList<Pessoa> pessoas){
        System.out.println("Adicionando pessoas: " + pessoas.size());
        if(pessoas.size() > 0) portaAberta = true;
        capacidadeAtual = capacidadeAtual+pessoas.size();
        pessoas.addAll(pessoas);
    }

    public boolean removePessoas(int andarAtual) {
        ArrayList<Pessoa> saida = new ArrayList<>();
        pessoas.forEach(p ->{
            if(p.getAndarDestino() == andarAtual){
                saida.add(pessoas.remove(pessoas.indexOf(p)));
            }
        });
        System.out.println("Removendo pessoas: " + saida.size());
        if(saida.size() > 0){
            numeroViagens++;
            portaAberta = true;
            return true;
        }
        return false;
    }

    public void viajar(int posiçãoAndar) {
        disponivel = false;
        portaAberta = false;
        if(posiçãoAndar < this.posY){
            this.descendo = false;
        }else this.descendo = true;

    }

    @Override
    public void atualizar() {
        if(disponivel) return;

        if(!descendo){
            this.posY = this.posY - 20;
        }else if(descendo){
            this.posY = this.posY + 20;
        }
    }
}
