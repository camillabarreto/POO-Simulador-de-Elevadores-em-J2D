package elementos;

import auxiliar.Pessoa;
import gui.Tela;
import java.util.ArrayList;

public class Andar extends Elemento{
    private ArrayList<Pessoa> fila;
    private int andar;
    private String imagemFilaVazia = "filaVazia.png";
    private String imagemFilaCheia = "filaCheia.png";

    public Andar(Tela pai, String imagemNome, int posX, int posY, int andar) {
        super(pai, imagemNome, posX, posY);
        this.fila = new ArrayList<>();
        this.andar = andar;
    }

    public boolean fila(){
        return fila.size() > 0;
    }

    public int filaTamanho(){ return fila.size(); }

    public void adicionarPessoas(Pessoa pessoa){
        fila.add(pessoa);
    }

    public ArrayList<Pessoa> removePessoas(int quantidade){
        ArrayList<Pessoa> saida = new ArrayList<>();
        while(quantidade > 0){
            if(fila.size() > 0){
                Pessoa p = fila.remove(0);
                saida.add(p);
            }else break;
            quantidade--;
        }
        return saida;
    }

    public int getAndar(){
        return andar;
    }

    public void carregarImagem(){
        if(fila.size() > 0){
            this.icone = this.carregarImagem(imagemFilaCheia);
        }else this.icone = this.carregarImagem(imagemFilaVazia);
    }

    @Override
    public void atualizar() {

    }
}
