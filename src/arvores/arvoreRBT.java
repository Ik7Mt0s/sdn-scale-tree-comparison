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

        private No(boolean ehNil) {
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

    private void rotacionarEsquerda(No x){
        No y = x.direita;
        x.direita = y.esquerda;

        if (y.esquerda !=NIL) y.esquerda.pai = x;

        y.pai = x.pai;

        if (x.pai == NIL) {
            raiz = y;
        } else if (x == x.pai.esquerda) {
            x.pai.esquerda = y;
        } else {
            x.pai.direita = y;
        }

        y.esquerda = x;
        x.pai = y;

        contadorRotacoes++;
    }

    private void rotacionarDireita(No y) {
        No x = y.esquerda;
        y.esquerda = x.direita;

        if (x.direita != NIL) x.direita.pai = y;

        x.pai = y.pai;

        if (y.pai == NIL) {
            raiz = x;
        } else if (y == y.pai.esquerda) {
            y.pai.esquerda = x;
        } else {
            y.pai.direita = x;
        }

        x.direita = y;
        y.pai = x;

        contadorRotacoes++;
    }

    private boolean ehVermelho(No no) { return no.cor == VERMELHO; }
    private boolean ehPreto(No no)   { return no.cor == PRETO; }
}
