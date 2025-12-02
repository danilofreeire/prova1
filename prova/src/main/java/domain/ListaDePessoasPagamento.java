package domain;

import java.util.ArrayList;
import java.util.List;

public class ListaDePessoasPagamento {

    private final List<PessoaPagamento> lista;

    public ListaDePessoasPagamento() {
        this.lista = new ArrayList<>();
    }

    // Adiciona no final
    public void adicionar(PessoaPagamento p) {
        lista.add(p);
    }

    // Adiciona em posição específica
    public void adicionar(int pos, PessoaPagamento p) {
        lista.add(pos, p);
    }

    // Substitui elemento em um índice
    public void atualizar(int indice, PessoaPagamento p) {
        lista.set(indice, p);
    }

    // Retorna um array de Strings formatadas (igual ao professor)
    public String[] retornaListaFormatada() {
        String[] nomes = new String[lista.size()];
        int cont = 0;
        for (PessoaPagamento pp : lista) {
            nomes[cont] = pp.getNome() + " || " + pp.getFone() + " || " + pp.getCpf();
            cont++;
        }
        return nomes;
    }

    // Apenas imprime um item no console (útil pra debug)
    public void imprimeElemento(int indice) {
        System.out.print("Imprimindo Elemento da Posicao [");
        System.out.print(indice);
        System.out.print("]: ");
        System.out.println(lista.get(indice).getNome());
        System.out.println();
    }

    // Busca por nome e retorna array de Strings formatadas
    public String[] buscaPorNome(String busca) {
        List<String> encontrados = new ArrayList<>();
        for (PessoaPagamento pp : lista) {
            if (busca.equals(pp.getNome())) {
                encontrados.add(pp.getNome() + " || " + pp.getFone() + " || " + pp.getCpf());
            }
        }
        return encontrados.toArray(new String[0]);
    }

    // Se precisar acessar a lista "crua"
    public List<PessoaPagamento> getLista() {
        return lista;
    }
}
