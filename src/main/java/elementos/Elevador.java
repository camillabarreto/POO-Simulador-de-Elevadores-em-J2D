package elementos;

import gui.Tela;

import java.util.ArrayList;

public class Elevador extends Elemento{
    private ArrayList<Integer> passageiros;
    private int velocidade;
    private int capacidade;

    public Elevador(Tela pai, String imagemNome, int posX, int posY, ArrayList<Integer> passageiros, int velocidade, int capacidade) {
        super(pai, imagemNome, posX, posY);
        this.passageiros = new ArrayList<>();
        this.velocidade = velocidade;
        this.capacidade = capacidade;
    }

    @Override
    public void atualizar() {

    }
}
