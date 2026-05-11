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
            if (z.regra.compareTo(x.regra) < 0){
                x = x.esquerda;
            }
            else{
                x = x.direita;
            }
        }

        z.pai = y;

        if (y == NIL){ 
            raiz = z;
        }else if (z.regra.compareTo(y.regra) < 0){
            y.esquerda = z;
        }
        else{
            y.direita = z;
        }
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

    public void remover(PacketRule regra) {
        No z = buscarNo(regra);
        if (z == null) return;

        No y = z;
        boolean yCorOriginal = y.cor;
        No x;

        if (z.esquerda == NIL) {
            x = z.direita;
            transplante(z, z.direita);
        } else if (z.direita == NIL) {
            x = z.esquerda;
            transplante(z, z.esquerda);
        } else {
            y = minimo(z.direita);
            yCorOriginal = y.cor;
            x = y.direita;
            if (y.pai == z) {
                if (x != NIL) {
                    x.pai = y;
                }
            } else {
                transplante(y, y.direita);
                y.direita = z.direita;
                y.direita.pai = y;
            }
            transplante(z, y);
            y.esquerda = z.esquerda;
            y.esquerda.pai = y;
            y.cor = z.cor;
        }

        contadorNos--;

        if (yCorOriginal == PRETO) {
            corrigirRemocao(x);
        }
    }

    private No buscarNo(PacketRule regra) {
        No atual = raiz;
        while (atual != NIL) {
            int cmp = regra.compareTo(atual.regra);
            if (cmp == 0)
                return atual;
            else if (cmp < 0)
                atual = atual.esquerda;
            else
                atual = atual.direita;
        }
        return null;
    }

    private No minimo(No no) {
        while (no.esquerda != NIL) {
            no = no.esquerda;
        }
        return no;
    }

    private void transplante(No u, No v){
        if (u.pai == NIL) {
            raiz = v;            
        } else if (u == u.pai.esquerda) {
            u.pai.esquerda = v;
        } else {
            u.pai.direita = v;
        }
        if (v != NIL) {
            v.pai = u.pai; 
        }
    }

    private void corrigirRemocao(No x){
        while (x != raiz && ehPreto(x)) {
            if (x == x.pai.esquerda) {
                No w = x.pai.direita;
                if (ehVermelho(w)) {
                    w.cor = PRETO;
                    x.pai.cor = VERMELHO;
                    rotacionarEsquerda(x.pai);
                    w = x.pai.direita;
                }
                if (ehPreto(w.esquerda) && ehPreto(w.direita)) {
                    w.cor = VERMELHO;
                    x = x.pai;
                } else {
                    if (ehPreto(w.direita)) {
                        w.esquerda.cor = PRETO;
                        w.cor = VERMELHO;
                        rotacionarDireita(w);
                        w = x.pai.direita;
                    }
                    w.cor = x.pai.cor;
                    x.pai.cor = PRETO;
                    w.direita.cor = PRETO;
                    rotacionarEsquerda(x.pai);
                    x = raiz;
                }
            } else {
                No w = x.pai.esquerda;
                if (ehVermelho(w)) {
                    w.cor = PRETO;
                    x.pai.cor = VERMELHO;
                    rotacionarDireita(x.pai);
                    w = x.pai.esquerda;
                }
                if (ehPreto(w.direita) && ehPreto(w.esquerda)) {
                        w.cor = VERMELHO;
                        x = x.pai;
                    } else {
                        if (ehPreto(w.esquerda)) {
                        w.direita.cor = PRETO;
                        w.cor = VERMELHO;
                        rotacionarEsquerda(w);
                        w = x.pai.esquerda;
                    }
                    w.cor = x.pai.cor;
                    x.pai.cor = PRETO;
                    w.esquerda.cor = PRETO;
                    rotacionarDireita(x.pai);
                    x = raiz;
                }
            }
        }
        x.cor = PRETO;
    }

    public PacketRule buscar(int prioridade) {
        No atual = raiz;
        while (atual != NIL) {
            int prioAtual = atual.regra.getPriority();
            if (prioridade == prioAtual) {
                return atual.regra;
            } else if (prioridade > prioAtual) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }
        return null;
    }

    public int size() {
        return contadorNos;
    }

    public int getRotationCount() {
        return contadorRotacoes;
    }
}
