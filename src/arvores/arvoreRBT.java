package arvores;
import models.PacketRule;

public class arvoreRBT {
    private static final boolean VERMELHO = true;
    private static final boolean PRETO = false;
    private static final No NIL = new No(true);

    private static class No {
        PacketRule regra;
        No esquerda, direita, pai;
        boolean cor;

        No(PacketRule regra) {
            this.regra = regra;
            this.cor = VERMELHO;
            this.esquerda = NIL;
            this.direita = NIL;
            this.pai = NIL;
        }

        private No(boolean isNil) {
            this.regra = null;
            this.cor = PRETO;
            this.esquerda = this.direita = this.pai = this;
        }
    }

    private No raiz;
    private int contadorRotacoes;
    private int contadorNos;

    public arvoreRBT() {
        raiz = NIL;
        contadorRotacoes = 0;
        contadorNos = 0;
    }
}
