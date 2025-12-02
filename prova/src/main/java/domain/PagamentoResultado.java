package domain;

/**
 * Representa o resultado da chamada à API de pagamento.
 * É, na prática, o "p1" do professor (classe pessoa) organizado
 * dentro de um objeto próprio.
 */
public class PagamentoResultado {

    // Equivalente ao p1 do professor
    private PessoaPagamento pessoa;

    public PagamentoResultado() {
    }

    public PagamentoResultado(PessoaPagamento pessoa) {
        this.pessoa = pessoa;
    }

    public PessoaPagamento getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaPagamento pessoa) {
        this.pessoa = pessoa;
    }

    // Métodos de conveniência que espelham o que o professor usa em tela:

    // p1.getRetmsg()
    public String getMensagem() {
        return pessoa != null ? pessoa.getRetmsg() : null;
    }

    // p1.getModo()
    public String getModo() {
        return pessoa != null ? pessoa.getModo() : null;
    }

    // p1.getId()
    public String getId() {
        return pessoa != null ? pessoa.getId() : null;
    }

    // p1.getValor()
    public String getValor() {
        return pessoa != null ? pessoa.getValor() : null;
    }

    // p1.getNome()
    public String getNome() {
        return pessoa != null ? pessoa.getNome() : null;
    }

    // p1.getCpf()
    public String getCpf() {
        return pessoa != null ? pessoa.getCpf() : null;
    }

    // p1.getFone()
    public String getFone() {
        return pessoa != null ? pessoa.getFone() : null;
    }
}
