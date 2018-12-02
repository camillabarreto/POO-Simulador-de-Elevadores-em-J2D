package gui;

import elementos.Carro;
import elementos.Elemento;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;


/**
 * Esse é um esqueleto (feito com IntelliJ 2018.3 + gradle) para indicar como fazer uma aplicação gráfica com
 * uma lógica de atualização semelhante a um jogo em J2D.
 *
 * Nesse projeto também um auxiliar de como criar uma Thread específica para atualizar as coordenadas de um elemento
 * gráfico que depois é redesenhado periodicamente pelo Timer que está associado a área de desenho (Tela)
 *
 * @author Emerson Ribeiro de Mello - 2018
 */
public class Principal {
    private JPanel painelPrincipal;
    private JPanel painelEsquerdo;
    private JButton iniciarButton;
    private JPanel painelDireito;
    private JTextArea console;
    private JPanel baixo;
    private JPanel cima;
    private JButton carregarArquivoButton;
    private JButton desceButton;

    public Principal() {

        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (iniciarButton.getText().equals("Iniciar")) {
                    ((Tela) painelEsquerdo).iniciaSimulacao();
                    iniciarButton.setText("Parar");

                    carregarArquivoButton.setEnabled(false);
                    desceButton.setEnabled(true);

                    console.append("<-Iniciou\n");


                }else {
                    ((Tela) painelEsquerdo).pararTimer();
                    iniciarButton.setText("Iniciar");
                    console.append("->Parou\n");
                }

                // devolvendo o foco para o painel Tela capturar as teclas
                ((Tela)painelEsquerdo).requestFocus();

            }
        });
    }

    private void createUIComponents() {

        painelPrincipal = new JPanel();
        console = new JTextArea();

        painelEsquerdo = new Tela(console);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Simulador J2D");
        frame.setContentPane(new Principal().painelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setLocationRelativeTo(null); // centralizando o JFrame na tela
        frame.setResizable(false); // não permitindo que o JFrame seja redimensionado
        frame.setVisible(true);
    }
}
