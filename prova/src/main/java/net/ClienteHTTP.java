package net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Cliente HTTP simples (SEM CRIPTOGRAFIA)
 * POST com application/x-www-form-urlencoded: "usuario=<>&senha=<>"
 */
public class ClienteHTTP {

    private String url;
    private String urlParameters;
    public int codretorno; // código HTTP da resposta

    public ClienteHTTP(String usuario, String senha, String url) throws Exception {
        setUrl(url);
        setLogin(usuario, senha);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLogin(String usuario, String senha) throws Exception {
        this.urlParameters =
                "usuario=" + URLEncoder.encode(usuario, "UTF-8") +
                "&senha="   + URLEncoder.encode(senha, "UTF-8");
    }

    public String conecta() throws Exception {
    	// Cria a conexão || Constrói a URL e abre a conexão HTTP.
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Método e cabeçalhos
        con.setRequestMethod("POST");

        // Envio de corpo e escreve parametros(body x-www-form-urlencoded)
        con.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }

        // Código de retorno
        this.codretorno = con.getResponseCode();

        // Corpo da resposta
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }
}
