package net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import domain.PagamentoRequisicao;

/**
 * Versão integrada do pagamentoHTTP do professor.
 * Faz a mesma coisa, mas usando PagamentoRequisicao.
 */
public class PagamentoHTTP {

    private final String url;
    private String urlParameters;
    private int codretorno;

    public PagamentoHTTP(String url) {
        this.url = url;
    }

    /**
     * Monta os parâmetros no mesmo formato que o professor usa:
     * nome, cpf, ncartao, valor, tipopag (x-www-form-urlencoded).
     */
    private void montarParametros(PagamentoRequisicao req) throws Exception {
        this.urlParameters =
                "nome="    + URLEncoder.encode(req.getNome(),       "UTF-8") +
                "&cpf="    + URLEncoder.encode(req.getCpf(),        "UTF-8") +
                "&ncartao="+ URLEncoder.encode(req.getDadosCartao(),"UTF-8") +
                "&valor="  + URLEncoder.encode(req.getValor(),      "UTF-8") +
                "&tipopag="+ URLEncoder.encode(req.getModo(),       "UTF-8");
    }

    /**
     * Envia a requisição para a API e devolve a resposta como String (JSON),
     * exatamente como o método conecta() do professor.
     */
    public String enviar(PagamentoRequisicao requisicao) throws Exception {
        montarParametros(requisicao);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Mesmo método e cabeçalhos do professor
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Envio do corpo (parâmetros)
        con.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }

        // Código HTTP de retorno
        this.codretorno = con.getResponseCode();

        // Ler corpo da resposta
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            return response.toString(); // JSON em String
        }
    }

    public int getCodretorno() {
        return codretorno;
    }
}