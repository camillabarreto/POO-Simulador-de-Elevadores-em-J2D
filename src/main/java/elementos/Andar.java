package elementos;

import auxiliar.Pessoa;
import gui.Tela;

import java.util.ArrayList;

public class Andar extends Elemento{
    private ArrayList<Pessoa> fila;

    public Andar(Tela pai, String imagemNome, int posX, int posY) {
        super(pai, imagemNome, posX, posY);
        this.fila = new ArrayList<>();
    }

    @Override
    public void atualizar() {
        
    }
}
