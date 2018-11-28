package elementos;

import gui.Tela;

import java.util.ArrayList;

public class ThreadElevadores extends Thread {
    private Elevador elevador;
    private  boolean  executando;
    private Tela pai;

    public ThreadElevadores(Elevador elevador, Tela pai){
        this.elevador = elevador;
        this.executando = false;
        this.pai = pai;
    }

    @Override
    public void run() {
        pai.escreverNoConsole("Thread iniciada\n");
        this.executando = true;

        // Isso vai repetir até que o usuário mande parar a simulação
        while (executando) {

            // Atualize as coordenadas do elemento, que no caso, fará a troca de imagens do OutroCarro
            //elemento.atualizar();
            elevador.atualizar();

            try {
                // Durma por 1 segundo
                Thread.sleep(elevador.getVelocidade()*500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pai.escreverNoConsole("Thread finalizada \n");
    }

    /**
     * Usado basicamente para fazer a thread ser finalizada, uma vez que o uso do método Thread.stop() não é seguro e foi descontinuado
     * @param executando
     */
    public synchronized void setExecutando(boolean executando) {
        this.executando = executando;
    }
}
