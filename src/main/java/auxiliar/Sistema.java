package auxiliar;

import java.util.ArrayList;

public class Sistema {
    private int maximoAndares;
    Arquivo arq;

    public Sistema(int maximoAndares, String nomeArquivo) {
        this.maximoAndares = maximoAndares;
        this.arq = new Arquivo(nomeArquivo);
    }

    public void gerenciar(){
        ArrayList<Pessoa> pessoas = arq.proximoIntante();
        //coloco pessoas nos devidos andares
        //disparo elevadores que estão parados
    }
}
