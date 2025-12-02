package domain;

public class PessoaPagamento {

	private String nome;
    private String fone;
    private String cpf;
    private String id;      // id da transação / pagamento
    private String modo;    // PIX, BOLETO, CARTAO
    private String retmsg;  // mensagem de retorno da API
    private String valor;

    public PessoaPagamento() {
        // construtor vazio: útil para quando formos preencher por partes
    }

    public PessoaPagamento(String nome,
                           String fone,
                           String cpf,
                           String id,
                           String modo,
                           String retmsg,
                           String valor) {
        this.nome = nome;
        this.fone = fone;
        this.cpf = cpf;
        this.id = id;
        this.modo = modo;
        this.retmsg = retmsg;
        this.valor = valor;
    }

    public void atualizar(String nome,
                          String fone,
                          String cpf,
                          String id,
                          String modo,
                          String retmsg,
                          String valor) {
        this.nome = nome;
        this.fone = fone;
        this.cpf = cpf;
        this.id = id;
        this.modo = modo;
        this.retmsg = retmsg;
        this.valor = valor;
    }

    // getters e setters

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "PessoaPagamento{" +
                "nome='" + nome + '\'' +
                ", fone='" + fone + '\'' +
                ", cpf='" + cpf + '\'' +
                ", id='" + id + '\'' +
                ", modo='" + modo + '\'' +
                ", retmsg='" + retmsg + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}