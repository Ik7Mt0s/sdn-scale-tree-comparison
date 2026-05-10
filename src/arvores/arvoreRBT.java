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

    public void inserir(PacketRule regra) {
        No z = new No(regra);
        No y = NIL;
        No x = raiz;

        while (x != NIL) {
            y = x;
            if (z.regra.compareTo(x.regra) < 0)
                x = x.esquerda;
            else
                x = x.direita;
        }

        z.pai = y;

        if (y == NIL)
            raiz = z;
        else if (z.regra.compareTo(y.regra) < 0)
            y.esquerda = z;
        else
            y.direita = z;

        z.esquerda = NIL;
        z.direita = NIL;
        z.cor = VERMELHO;

        contadorNos++;

        corrigirInsercao(z);
        raiz.cor = PRETO;
    }

    private void corrigirInsercao(No z) {
        while (ehVermelho(z.pai)) {
            No avo = z.pai.pai;

            if (z.pai == avo.esquerda) {
                No tio = avo.direita;

                if (ehVermelho(tio)) {
                    z.pai.cor = PRETO;
                    tio.cor = PRETO;
                    avo.cor = VERMELHO;
                    z = avo;
                } 
                else {
                    if (z == z.pai.direita) {
                        z = z.pai;
                        rotacionarEsquerda(z);
                    }
                    z.pai.cor = PRETO;
                    avo.cor = VERMELHO;
                    rotacionarDireita(avo);
                }
            } 
            else {
                No tio = avo.esquerda;

                if (ehVermelho(tio)) {
                    z.pai.cor = PRETO;
                    tio.cor = PRETO;
                    avo.cor = VERMELHO;
                    z = avo;
                } 
                else {
                    if (z == z.pai.esquerda) {
                        z = z.pai;
                        rotacionarDireita(z);
                    }
                    z.pai.cor = PRETO;
                    avo.cor = VERMELHO;
                    rotacionarEsquerda(avo);
                }
            }
        }
    }
}
