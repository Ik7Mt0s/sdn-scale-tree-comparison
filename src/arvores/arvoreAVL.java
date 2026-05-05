package arvores;
import models.PacketRule;

public class arvoreAVL {
    
    private class No {
        PacketRule regra;
        No esquerda, direita;
        int altura;
        
        No(PacketRule regra) {
            this.regra = regra;
            this.altura = 1;
        }
    }
    
    private No raiz;
    private int contadorRotacoes;
    private int contadorNos;
    
    public arvoreAVL() {
        raiz = null;
        contadorRotacoes = 0;
        contadorNos = 0;
    }
}