package auxiliar;

import java.io.BufferedReader;
import java.util.ArrayList;

public class Arquivo {
    private BufferedReader br;
    private String nomeArquivo;

    public Arquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public ArrayList<Pessoa> proximoIntante(){
        ArrayList<Pessoa> pessoas = new ArrayList<>();
        return pessoas;
    }
}
