package service;

import config.APIConfig;
import domain.PagamentoRequisicao;
import domain.PagamentoResultado;
import net.PagamentoHTTP;
import net.TratadorRespostaPagamento;
import infra.QRCodeGenerator;
/**
 * Service que integra tudo:
 * - recebe PagamentoRequisicao (dados da tela)
 * - chama a API (PagamentoHttp)
 * - interpreta a resposta (TratadorRespostaPagamento)
 * - devolve PagamentoResultado para a UI
 *
 * É o equivalente "organizado" ao que a Tela01 do professor fazia.
 */
public class PagamentoService {

    private final PagamentoHTTP http;
    private final TratadorRespostaPagamento tratador;
    private final QRCodeGenerator qrCodeGenerator;

    public PagamentoService() {
        // Usa a mesma URL que o professor
        this.http = new PagamentoHTTP(APIConfig.PAGAMENTO_URL);
        this.tratador = new TratadorRespostaPagamento();
        this.qrCodeGenerator = new QRCodeGenerator();
    }

    /**
     * Processa um pagamento:
     * 1) envia os dados para a API
     * 2) interpreta a resposta JSON
     * 3) devolve um PagamentoResultado (com PessoaPagamento dentro)
     */
    public PagamentoResultado processar(PagamentoRequisicao requisicao) throws Exception {
    	if (requisicao.getNome() == null || requisicao.getNome().isBlank()) {
	        throw new IllegalArgumentException("Nome não pode ser vazio");
	    }
    	
        // 1. Chama a API e obtém a resposta JSON como String
        String jsonResposta = http.enviar(requisicao);

        // 2. Usa o tratador (equivalente ao trataJSON) para montar o resultado
        PagamentoResultado resultado = tratador.parse(jsonResposta);

        // 3. Se a modalidade for PIX, gera o QR Code (igual o professor faz na Tela01)
        if ("PIX".equalsIgnoreCase(resultado.getModo())) {
            // No código do professor: QRcode.gerarQRCode(p1.getId());
            qrCodeGenerator.gerarQRCode(resultado.getId());
            // Isso vai criar/atualizar o arquivo "QRCodePix.png"
        }

        // 4. Retorna o resultado para a UI
        return resultado;
    }

    public int getCodigoHttp() {
        return http.getCodretorno();
    }
}