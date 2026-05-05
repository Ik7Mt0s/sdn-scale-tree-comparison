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

    private int altura(No no){
        return no == null ? 0 : no.altura;
    }

    private int fatorBalanceamento(No no) {
        return no == null ? 0 : altura(no.esquerda) - altura(no.direita);
    }

    private void atualizarAltura(No no) {
        if (no != null) {
            no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
        }
    }
}