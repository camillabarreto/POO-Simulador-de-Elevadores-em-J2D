package elementos;

import auxiliar.Pessoa;
import gui.Tela;

import java.util.ArrayList;

public class Elevador extends Elemento{
    private ArrayList<Pessoa> pessoas;
    private int velocidade;
    private int capacidadeMaxima;
    private int capacidadeAtual;
    private boolean descendo;
    private boolean portaAberta;
    private boolean disponivel;
    private int numeroViagens;
    private String IMAGEM_PORTA_ABERTA = "elevadorAberto.png";
    private String IMAGEM_PORTA_FECHADA = "elevadorFechado.png";

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

    public int filaTamanho() {return pessoas.size();}

    public int getVelocidade() { return velocidade; }

    public int lugaresLivres(){ return capacidadeMaxima - capacidadeAtual; }

    public void addPessoas(ArrayList<Pessoa> pessoas){
        if(pessoas.size() > 0) setPortaAberta(true);
        capacidadeAtual = capacidadeAtual+pessoas.size();
        this.pessoas.addAll(pessoas);
    }

    public boolean removePessoas(int andarAtual) {
        ArrayList<Pessoa> saida = new ArrayList<>();
        for (int i = 0; i < pessoas.size(); i++) {
            Pessoa p = pessoas.get(i);
            if(p.getAndarDestino() == andarAtual){
                saida.add(this.pessoas.remove(i));
            }
        }

        if(saida.size() > 0){
            capacidadeAtual = capacidadeAtual-saida.size();
            numeroViagens++;
            setPortaAberta(true);
        }
        return false;
    }

    /**
     * posiçãoAtual = -0, elevador deve ficar parado
     * @param posiçãoAndar: '0' elevador parado, '1' elevador sobe, '-1' elevador desce
     */
    public void viajar(int posiçãoAndar) {

        //verificar essa logica aqui porque ta dando conflito
        if(posiçãoAndar == 0){
            disponivel = true;
            setPortaAberta(true);
        }else{
            disponivel = false;
            setPortaAberta(false);
            if(posiçãoAndar == -1){
                this.descendo = true;
            }else{
                this.descendo = false;
            }
        }
    }

    private void setPortaAberta(boolean modo){
        portaAberta = modo;
        if(portaAberta){
            this.icone = this.carregarImagem(IMAGEM_PORTA_ABERTA);
        }else this.icone = this.carregarImagem(IMAGEM_PORTA_FECHADA);
    }

    @Override
    public void atualizar() {
        if(!disponivel){
            if(!descendo){
                this.posY = this.posY - 20;
            }else if(descendo){
                this.posY = this.posY + 20;
            }
        }
    }
}
