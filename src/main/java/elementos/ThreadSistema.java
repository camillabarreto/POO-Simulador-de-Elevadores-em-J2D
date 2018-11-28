package elementos;

import auxiliar.Sistema;
import gui.Tela;

public class ThreadSistema extends Thread {
    private Sistema sistema;
    private  boolean  executando;
    private Tela pai;

    public ThreadSistema(Sistema sistema, Tela pai){
        this.pai = pai;
        this.sistema = sistema;
        this.executando = false;
    }

    @Override
    public void run() {
        pai.escreverNoConsole("Thread iniciada\n");
        this.executando = true;

        // Isso vai repetir até que o usuário mande parar a simulação
        while (executando) {

            // Atualize as coordenadas do elemento, que no caso, fará a troca de imagens do OutroCarro
            //elemento.atualizar();
            sistema.gerenciar();

            try {
                // Durma por 1 segundo
                Thread.sleep(3000);

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
