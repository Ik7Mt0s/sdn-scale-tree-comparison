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

    private No rotacaoSimplesDireita(No y) {
        No x = y.esquerda;
        No filhoDireitoDeX = x.direita;

        x.direita = y;
        y.esquerda = filhoDireitoDeX;

        atualizarAltura(y);
        atualizarAltura(x);

        contadorRotacoes++;

        return x;
    }

    private No rotacaoSimplesEsquerda(No x) {
        No y = x.direita;
        No filhoEsquerdoDeY = y.esquerda;

        y.esquerda = x;
        x.direita = filhoEsquerdoDeY;

        atualizarAltura(x);
        atualizarAltura(y);

        contadorRotacoes++;

        return y;
    }

    private No rotacaoDuplaEsqDir(No no){
        no.esquerda = rotacaoSimplesEsquerda(no.esquerda);
        return rotacaoSimplesDireita(no);
    }

    private No rotacaoDuplaDirEsq(No no){
        no.direita = rotacaoSimplesDireita(no.direita);
        return rotacaoSimplesEsquerda(no);
    }

    private No balancear(No no){
        if (no == null) return null;

        int FB = fatorBalanceamento(no);

        if (FB > 1) {
            if (fatorBalanceamento(no.esquerda) >= 0) {
                return rotacaoSimplesDireita(no);
            }
            else {
                return rotacaoDuplaEsqDir(no);
            }
        }

        if (FB < -1) {
            if (fatorBalanceamento(no.direita) <= 0) {
                return rotacaoSimplesEsquerda(no);
            }
            else{
                return rotacaoDuplaDirEsq(no);
            }
        }

        return no;
    }

    public void inserir(PacketRule regra){
        raiz = inserirRecursivo(raiz, regra);
    }

    private No inserirRecursivo(No no, PacketRule regra){
        if (no == null) {
            contadorNos++;
            return new No(regra);
        }

        int cmp = regra.compareTo(no.regra);

        if(cmp < 0){
            no.esquerda = inserirRecursivo(no.esquerda, regra);
        } else if(cmp > 0){
            no.direita = inserirRecursivo(no.direita, regra);
        } else{
            return no;
        }

        atualizarAltura(no);

        return balancear(no);
    }

    public void remover(PacketRule regra){
        raiz = removerRecursivo(raiz, regra);
    }

    private No removerRecursivo(No no, PacketRule regra){
        if (no == null) return null;

        int cmp = regra.compareTo(no.regra);

        if (cmp < 0) {
            no.esquerda = removerRecursivo(no.esquerda, regra);
        } else if (cmp > 0) {
            no.direita = removerRecursivo(no.direita, regra);
        } else {
            if (no.esquerda == null || no.direita == null){
                contadorNos--;
                return (no.esquerda != null) ? no.esquerda : no.direita;
            } else {
                No sucessor = encontrarMinimo(no.direita);
                no.regra = sucessor.regra;
                no.direita = removerRecursivo(no.direita, sucessor.regra);
            }
        }

        atualizarAltura(no);
        return balancear(no);
    }

    private No encontrarMinimo(No no) {
        while (no.esquerda != null) {
            no = no.esquerda;
        }
        return no;
    }

    public PacketRule buscar(int prioridade) {
        No atual = raiz;
        while (atual != null) {
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