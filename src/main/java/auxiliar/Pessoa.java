package auxiliar;

public class Pessoa {
    private int andarOrigem;
    private int andarDestino;

    public Pessoa(int andarOrigem, int andarDestino) {
        this.andarOrigem = andarOrigem;
        this.andarDestino = andarDestino;
    }

    public int getAndarOrigem() {
        return andarOrigem;
    }

    public void setAndarOrigem(int andarOrigem) {
        this.andarOrigem = andarOrigem;
    }

    public int getAndarDestino() {
        return andarDestino;
    }

    public void setAndarDestino(int andarDestino) {
        this.andarDestino = andarDestino;
    }
}