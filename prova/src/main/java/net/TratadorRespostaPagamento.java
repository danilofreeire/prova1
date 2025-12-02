package net;


import domain.PessoaPagamento;
import domain.PagamentoResultado;
import org.json.JSONObject;

/**
 * Equivalente ao trataJSON do professor.
 * Recebe a String JSON, extrai os campos e monta a PessoaPagamento,
 * depois embrulha em um PagamentoResultado.
 */
public class TratadorRespostaPagamento {

    public PagamentoResultado parse(String json) {
        // Mesmo comportamento do trataJSON do professor
        JSONObject myObj = new JSONObject(json);

        String id               = myObj.optString("id", null);
        String valorAutorizado  = myObj.optString("valor_autorizado", null);
        String modo             = myObj.optString("modo", null);
        String codRetorno       = myObj.optString("cod_retorno");
        String msg              = myObj.optString("msg", null);


        // Equivalente a:
        // p1 = new pessoa("","","",this.id,this.modo,this.msg,this.valor_autorizado);
        PessoaPagamento p = new PessoaPagamento(
                "",              // nome vazio (prof também não preenche aqui)
                "",              // fone vazio
                "",              // cpf vazio
                id,              // id
                modo,            // modo (PIX/BOLETO/CARTAO)
                msg,             // retmsg
                valorAutorizado  // valor
        );

        // Em vez de devolver pessoa direta, devolvemos PagamentoResultado
        return new PagamentoResultado(p);
    }
}
