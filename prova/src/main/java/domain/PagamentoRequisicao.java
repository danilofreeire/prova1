package domain;
/**
 * Representa os dados que serão enviados para a API de pagamento,
 * baseado nos parâmetros que o professor passa para pagamentoHTTP.
 */
public class PagamentoRequisicao {

    // Equivalentes aos campos da Tela01 do professor
    private String nome;        // caixa1
    private String fone;        // caixa2 (apesar de não ser passado no construtor original, existe na pessoa)
    private String cpf;         // caixa4
    private String dadosCartao; // caixa6
    private String valor;       // caixa5
    private String modo;        // PIX, BOLETO, CARTAO (comboLista1)

    public PagamentoRequisicao() {
    }

    public PagamentoRequisicao(String nome,
                               String fone,
                               String cpf,
                               String dadosCartao,
                               String valor,
                               String modo) {
        this.nome = nome;
        this.fone = fone;
        this.cpf = cpf;
        this.dadosCartao = dadosCartao;
        this.valor = valor;
        this.modo = modo;
    }

    // Getters e setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDadosCartao() {
        return dadosCartao;
    }

    public void setDadosCartao(String dadosCartao) {
        this.dadosCartao = dadosCartao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }
}
