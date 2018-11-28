package elementos;

import auxiliar.Pessoa;
import gui.Tela;
import java.util.ArrayList;

public class Andar extends Elemento{
    private ArrayList<Pessoa> fila;
    private int andar;

    public Andar(Tela pai, String imagemNome, int posX, int posY, int andar) {
        super(pai, imagemNome, posX, posY);
        this.fila = new ArrayList<>();
        this.andar = andar;
    }

    public void adicionarPessoas(Pessoa pessoa){
        fila.add(pessoa);
    }

    public ArrayList<Pessoa> removePessoas(int quantidade){
        ArrayList<Pessoa> saida = new ArrayList<>();
        for (int i = 0; i < quantidade; quantidade--) {
            saida.add(fila.remove(fila.indexOf(i)));
        }
        return saida;
    }

    public int getAndar(){
        return andar;
    }

    @Override
    public void atualizar() {

    }
}
