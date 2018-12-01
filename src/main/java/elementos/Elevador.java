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
    private int andarAtual;
    private String IMAGEM_PORTA_ABERTA = "elevadorAberto.png";
    private String IMAGEM_PORTA_FECHADA = "elevadorFechado.png";
    private int numero;

    public Elevador(Tela pai, String imagemNome, int posX, int posY, int velocidade, int capacidade, int numero) {
        super(pai, imagemNome, posX, posY);
        this.pessoas = new ArrayList<>();
        this.velocidade = velocidade;
        this.capacidadeMaxima = capacidade;
        this.capacidadeAtual = 0;
        this.descendo = false;
        this.portaAberta = true;
        this.disponivel = true;
        this.numeroViagens = 0;
        this.andarAtual = 0;
        this.numero = numero;
    }

    public int getNumero() { return numero; }

    public void setAndarAtual(int andarAtual){ this.andarAtual = andarAtual; }

    public boolean isDisponivel(){ return disponivel; }

    public boolean isPortaAberta(){ return portaAberta; }

    public boolean isDescendo(){
        return descendo;
    }

    public boolean fila(){ return pessoas.size() > 0; }

    public int filaTamanho() {return pessoas.size();}

    public int getVelocidade() { return velocidade; }

    public int lugaresLivres(){ return capacidadeMaxima - capacidadeAtual; }

    public int addPessoas(ArrayList<Pessoa> entrando){
        System.out.println("Saindo do Andar: " + entrando.size());
        if(entrando.size() > 0) setPortaAberta(true);
        capacidadeAtual = capacidadeAtual+entrando.size();
        this.pessoas.addAll(entrando);
        return entrando.size();
    }

    public int removePessoas(int andarAtual) {
        ArrayList<Pessoa> saindo = new ArrayList<>();
        int quantidade = pessoas.size();
        int i = 0;
        while(quantidade > 0){
            Pessoa p = pessoas.get(i);
            if(p.getAndarDestino() == andarAtual){
                saindo.add(this.pessoas.remove(i));
            }else i++;
            quantidade--;
        }
        if(saindo.size() > 0){
            capacidadeAtual = capacidadeAtual-saindo.size();
            numeroViagens++;
            setPortaAberta(true);
        }
        return saindo.size();
    }

    /**
     * posiçãoAtual = -0, elevador deve ficar parado
     * @param direção: '0' elevador parado, '1' elevador sobe, '-1' elevador desce, '2' elevador verificar sua fila
     */
    public void viajar(int direção) {
        //verificar essa logica aqui porque ta dando conflito

        if(direção == 2){
            int aux = -1;
            for (int i = 0; i < pessoas.size(); i++) {
                if(pessoas.get(i).getAndarDestino() > andarAtual){
                    aux = 1;
                    break;
                }
            }viajar(aux);
        }else if(direção == 0){
            disponivel = true;
            setPortaAberta(true);
        }else{
            disponivel = false;
            setPortaAberta(false);
            if(direção == -1){
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
