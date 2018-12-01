package gui;

import auxiliar.Sistema;
import elementos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Essa classe é responsável pela toda lógica de desenho de objetos na tela
 * Um objeto Timer fará a atualização periódica da tela, isto é, periodicamente os elementos
 * serão redesenhados por esse Timer.
 *
 * @author Emerson Ribeiro de Mello - 2018
 */
public class Tela extends JPanel implements ActionListener {

    // usado para redesenhar a tela periodicamente
    private Timer timer;
    private int taxaDeAtualizacao;

    // Para capturar as teclas pressionadas pelo usuário
    private JTextArea console;

    // Elementos que serão desenhados na tela
    private ArrayList<Elemento> elementos;

    //Sistema que vai controlar os Elevadores para buscar e deixar pessoas nos Andares
    private Sistema sistema;

    // Vetor de Threads que serão responsáveis por atualizar as coordenadas de um elemento específico
    private Thread[] atualizaElevador;

    // Threads responsável por atualizar as filas dos Andares
    private Thread atualizaSistema;

    private String ARQUIVO_DE_INSTANTES;

    private int QUANTIDADE_ELEVADORES;

    private int QUANTIDADE_ANDARES;



    /**
     * @param console para imprimir as mensagens para o usuário
     */
    public Tela(JTextArea console) {

        this.setLayout(new BorderLayout());
        this.setBackground(Color.gray);
        this.repaint();

        this.QUANTIDADE_ELEVADORES = 3;
        this.QUANTIDADE_ANDARES = 6;
        this.ARQUIVO_DE_INSTANTES = "/home/camilla/Área de Trabalho/projeto-pratico-02-camillabarreto/src/main/java/util/arquivoInstantes.txt";

        this.console = console;
        this.taxaDeAtualizacao = 250;

        this.elementos = new ArrayList<>();
        this.atualizaElevador = new Thread[QUANTIDADE_ELEVADORES];
    }


    /**
     *
     */
    public void criarElementos(){
        //Criando andares
        elementos.clear();
        ArrayList<Andar> andares = new ArrayList<>();
        for (int i = 0; i < QUANTIDADE_ANDARES; i++) {
            Andar a = new Andar(this, "filaVazia.png", 10, (i*120), 5-i);
            elementos.add(a);
            andares.add(a);
        }
        //Criando elevadores
        ArrayList<Elevador> elevadores = new ArrayList<>();
        for (int i = 0; i < QUANTIDADE_ELEVADORES; i++) { //carrov.png
            Elevador e = new Elevador(this, "elevadorAberto.png", 100*(i+1), 600, 1+i, 4+(i*2), i+1);
            elementos.add(e);
            elevadores.add(e);
            atualizaElevador[i] = new ThreadElevadores(e, this);
        }

        Sistema s = new Sistema(QUANTIDADE_ANDARES, ARQUIVO_DE_INSTANTES, elevadores, andares, console);
        this.atualizaSistema = new ThreadSistema(s, this);

    }

    /**
     * Quando a simulação é iniciada cria-se os elementos, dispara o Timer para redesenho da tela periodicamente e
     * dispara a Thread que será responsável por atualizar as coordenadas do OutroCarro
     */
    public void iniciaSimulacao(){
        this.setIgnoreRepaint(true);

        //disparando o timer para atualização da tela
        this.timer = new Timer(taxaDeAtualizacao, this);

        //constrói elementos que serão desenhados na tela
        this.criarElementos();

        // disparando timer que ficará redesenhando os elementos na tela
        this.timer.start();

        // disparando Thread que ficará atualizando atributos de um elemento específico
        //atualizaUmCarroViaThread.start();
        for (int i = 0; i < atualizaElevador.length; i++) {
            atualizaElevador[i].start();
        }
        atualizaSistema.start();

    }


    /**
     * Este método é invocado a cada taxaDeAtualizacao pelo Timer
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Atualiza as coordenadas dos elementos, respeitando a lógica de cada um
        //this.processarLogica();

        //sistema.gerenciar();
        //sistema.atualizarElevadores();

//        elementos.forEach(elemento-> {
//            elemento.atualizar();
//        });

        // desenha os elementos na tela novamente
        this.renderizar();
    }


    /**
     * Limpa a tela e desenha todos elementos novamente já com suas coordenadas atualizadas
     */
    public void renderizar(){
        Graphics2D g2 = (Graphics2D) this.getGraphics();

        //Limpando a tela, isto é, desenhando um retângulo preenchido com dimensões iguais a da Tela
        g2.setColor(Color.gray);
        g2.fillRect(0, 0, getWidth(), getHeight());


        // Desenhando o cenário de fundo
        this.renderizarCenario();


        // desenhando os elementos na tela
        //elementos.forEach(elemento -> elemento.desenhar(g2));
        elementos.forEach(elemento-> {
            elemento.desenhar(g2);
        });
//        sistema.getAndares().forEach(andar -> {
//            andar.desenhar(g2);
//        });

        //liberando os contextos gráficos
        g2.dispose();
    }


    /**
     * O cenário de fundo tem elementos estáticos. Aqui um pequeno auxiliar de como desenhar retângulo,
     * mas é possível desenhar imagens, elipse, string, etc.
     *
     */
    public void renderizarCenario(){
        Graphics2D g2 = (Graphics2D) this.getGraphics();

        // desenhando retângulos que poderiam ser janelas?
        g2.setColor(Color.darkGray);
        g2.drawLine(0, 90, getWidth(), 90);
        g2.drawLine(0, 210, getWidth(), 210);
        g2.drawLine(0, 330, getWidth(), 330);
        g2.drawLine(0, 450, getWidth(), 450);
        g2.drawLine(0, 570, getWidth(), 570);
        g2.drawLine(0, 690, getWidth(), 690);
        g2.dispose();
    }

    /**
     * Fim de jogo/rodada
     */
    public void pararTimer() {

        // Interrompe o timer que atualiza a tela
        this.timer.stop();

        for (int i = 0; i < 3; i++) {
            ((ThreadElevadores)this.atualizaElevador[i]).setExecutando(false);
        }

        ((ThreadSistema)this.atualizaSistema).setExecutando(false);


        this.setIgnoreRepaint(false);

        // Desenha a última posição dos elementos
        this.renderizar();

        // Interrompe a Thread que estava atualizando as coordenadas do OutroCarro
        //((ExemploDeThread)this.atualizaUmCarroViaThread).setExecutando(false);
    }

    /**
     * Console é um JTextArea do JPanel Principal. A ideia aqui é permitir escrever naquele componente
     *
     * @param mensagem mensagem que aparecerá no console
     */
    public void escreverNoConsole(String mensagem){
        this.console.append(mensagem);
    }

}
