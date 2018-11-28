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
        System.out.println("Arquivo");
        this.instantes = new HashMap<>();
        try {
            FileReader arq = new FileReader(nomeArquivo);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();
            System.out.println("Linha:" + linha);

            while (linha != null) {
                armazenaInstante(linha);
                linha = lerArq.readLine();
            }
            arq.close();

        } catch (IOException e){
        }
        System.out.println("instante:" + instantes.get(0));
    }

    private void armazenaInstante(String linha){
        ArrayList<Pessoa> valores = new ArrayList<>();

        int aux = linha.indexOf(":");
        int aux1 = linha.indexOf(",", aux+1);

        String chave = linha.substring(aux+1, aux1);

        aux = aux1+1;
        aux = linha.indexOf(":", aux);
        while(aux != -1){
            aux1 = linha.indexOf(":", aux+1);

            int andarOrigem = Integer.parseInt(linha.substring(aux+1, aux1));

            aux = aux1;
            aux1 = linha.indexOf(",", aux1);
            if(aux1 == -1){
                aux1 = linha.length();
            }

            int andarDestino= Integer.parseInt(linha.substring(aux+1, aux1));

            valores.add(new Pessoa(andarOrigem, andarDestino));

            aux = aux1+1;
            aux = linha.indexOf(":", aux);
        }

        instantes.put(Integer.getInteger(chave), valores);

//        instantes.get("0").forEach(p -> {
//            System.out.println(p.getAndarOrigem() + " " + p.getAndarDestino());
//        });
    }

    public ArrayList<Pessoa> proximoInstante(int chave){
        if(instantes.containsKey(chave)){
            return instantes.remove(chave);
        }
        return new ArrayList<>();
    }
}