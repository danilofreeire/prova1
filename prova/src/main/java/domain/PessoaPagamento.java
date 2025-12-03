package domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PessoaPagamento {

    // ID da tabela no banco
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    // Campos que já usamos na integração com a API
    private String nome;
    private String fone;
    private String cpf;

    // este "id" é o ID/txid/payload que vem da API (usado no QRCode)
    private String id;
    private String modo;
    private String retmsg;
    private String valor;

    private LocalDateTime dataHora; // quando o pagamento foi feito

    public PessoaPagamento() {
    }

    public PessoaPagamento(String nome, String fone, String cpf,
                           String id, String modo, String retmsg, String valor) {
        this.nome = nome;
        this.fone = fone;
        this.cpf = cpf;
        this.id = id;
        this.modo = modo;
        this.retmsg = retmsg;
        this.valor = valor;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

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

    // continua igual: este id é o id do pagamento na API
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

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
