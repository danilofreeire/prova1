package ui;

import domain.PagamentoRequisicao;
import domain.PagamentoResultado;
import service.PagamentoService;

import javax.swing.*;
import java.awt.*;

public class PagamentoFrame extends JFrame {

    private final JTextField tNome      = new JTextField(30);
    private final JTextField tTelefone  = new JTextField(30);
    private final JTextField tCpf       = new JTextField(30);
    private final JTextField tDadosCartao = new JTextField(30);
    private final JTextField tValor     = new JTextField(8);

    private final JComboBox<String> comboTipo =
            new JComboBox<>(new String[]{"PIX", "BOLETO", "CARTAO"});

    private final JButton bEmitir   = new JButton("Emitir");
    private final JButton bImprimir = new JButton("Imprimir");
    private final JButton bLimpar   = new JButton("Limpar");

    private final JTextArea area = new JTextArea(6, 30);
    private final JScrollPane scrollArea = new JScrollPane(area);

    private final PagamentoService service = new PagamentoService();
    private PagamentoResultado ultimoResultado;

    public PagamentoFrame() {
        super("Pagamento");

        setLayout(new FlowLayout());

        add(new JLabel("Digite o Nome:"));
        add(tNome);

        add(new JLabel("Digite o Telefone:"));
        add(tTelefone);

        add(new JLabel("Digite o CPF:"));
        add(tCpf);

        add(new JLabel("Dados do Cartão de Crédito:"));
        add(tDadosCartao);

        add(new JLabel("Digite o Valor:"));
        add(tValor);

        add(comboTipo);

        // Botões
        add(bEmitir);
        add(bImprimir);
        add(bLimpar);

        // Área de texto (resultado)
        area.setEditable(false);
        add(scrollArea);

        configurarListeners();

        setSize(420, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private boolean validarFormulario() {
        String nome  = tNome.getText().trim();
        String cpf   = tCpf.getText().trim();
        String valor = tValor.getText().trim();
        String modo  = (String) comboTipo.getSelectedItem();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome.", "Aviso", JOptionPane.WARNING_MESSAGE);
            tNome.requestFocus();
            return false;
        }

        if (cpf.isEmpty() || !cpf.matches("\\d{11}")) { // 11 dígitos numéricos
            JOptionPane.showMessageDialog(this, "CPF deve ter 11 números.", "Aviso", JOptionPane.WARNING_MESSAGE);
            tCpf.requestFocus();
            return false;
        }

        if (valor.isEmpty() || !valor.matches("\\d+(\\.\\d{1,2})?")) {
            JOptionPane.showMessageDialog(this, "Informe um valor numérico válido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            tValor.requestFocus();
            return false;
        }

        if ("CARTAO".equalsIgnoreCase(modo)) {
            String cartao = tDadosCartao.getText().trim();
            if (cartao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe os dados do cartão para pagamento no cartão.", "Aviso", JOptionPane.WARNING_MESSAGE);
                tDadosCartao.requestFocus();
                return false;
            }
        }

        return true;
    }

    
    

    private void configurarListeners() {
        // Emitir = chamar API do professor via PagamentoService
        bEmitir.addActionListener(e -> emitirPagamento());

        // Limpar = zerar campos
        bLimpar.addActionListener(e -> limpar());

        // Imprimir = abrir janela PIX se for PIX
        bImprimir.addActionListener(e -> imprimir());
    }
    private String safe(String s) {
        if (s == null) return "";
        if ("NULL".equalsIgnoreCase(s)) return "";
        return s;
    }
    private void emitirPagamento() {
        try {
        	if (!validarFormulario()) {
                return; // não chama a API se os dados já estão errados aqui
            }

        	
            // Monta a requisição com base na tela
            PagamentoRequisicao req = new PagamentoRequisicao();
            req.setNome(tNome.getText().trim());
            req.setCpf(tCpf.getText().trim());
            req.setDadosCartao(tDadosCartao.getText().trim());
            req.setValor(tValor.getText().trim());
            req.setModo((String) comboTipo.getSelectedItem());

            // Chama o serviço (equivalente ao pagamentoHTTP + trataJSON)
            PagamentoResultado resultado = service.processar(req);
            this.ultimoResultado = resultado;

            // Mostra o retorno na área de texto (igual ao prof faz com p1)
            area.setText(""); // limpa
            area.append("Dados do Processamento\n");
            area.append("Mensagem: "  + safe(resultado.getMensagem()) + "\n");
            area.append("Modalidade: " + safe(resultado.getModo()) + "\n");
            area.append("ID: "        + safe(resultado.getId()) + "\n");
            area.append("Valor: "     + safe(resultado.getValor()) + "\n");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erro ao processar pagamento:\n" + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpar() {
        tNome.setText("");
        tTelefone.setText("");
        tCpf.setText("");
        tDadosCartao.setText("");
        tValor.setText("");
        area.setText("");
        ultimoResultado = null;
    }

    private void imprimir() {
        if (ultimoResultado == null) {
            JOptionPane.showMessageDialog(this,
                    "Nenhum pagamento processado ainda.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!"PIX".equalsIgnoreCase(ultimoResultado.getModo())) {
            JOptionPane.showMessageDialog(this,
                    "Somente pagamentos PIX geram QR Code.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Aqui, o service já gerou o QRCodePix.png quando era PIX.
        // Agora abrimos a tela que mostra essa imagem (equivalente à TelaPIX).
        new PixFrame().setVisible(true);
    }
}