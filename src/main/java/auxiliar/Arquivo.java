package auxiliar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Arquivo {
    private HashMap<Integer, ArrayList<Pessoa>> instantes;

    public Arquivo(String nomeArquivo) {
        this.instantes = new HashMap<>();
        try {
            FileReader arq = new FileReader(nomeArquivo);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();
            while (linha != null) {
                armazenaInstante(lerArq.readLine());
            }
            arq.close();

        } catch (IOException e){
        }
    }

    private void armazenaInstante(String linha){
        ArrayList<Pessoa> valores = new ArrayList<>();

        int aux = linha.indexOf(":");
        String chave = linha.substring(aux+1, 1);

        while(aux != -1){

            aux = linha.indexOf(":",aux+1);
            int andarOrigem = Integer.parseInt(linha.substring(aux+1, 1));
            aux = linha.indexOf(":",aux+1);
            int andarDestino = Integer.parseInt(linha.substring(aux+1, 1));

            valores.add(new Pessoa(andarOrigem, andarDestino));
        }

        instantes.put(Integer.getInteger(chave), valores);
    }

    public ArrayList<Pessoa> proximoInstante(int chave){
        if(instantes.containsKey(chave)){
            return instantes.remove(chave);
        }
        return new ArrayList<>();
    }
}