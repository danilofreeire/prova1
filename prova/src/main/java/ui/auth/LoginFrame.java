package ui.auth;

import net.ClienteHTTP;
import ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Login com dois modos:
 * - Sem criptografia (HTTP) -> envia senha pura para URL_SEM_CRIPTO
 * - Com criptografia (AES)  -> cifra a senha e envia para URL_COM_AES
 *
 * Mostra a resposta do servidor em um JOptionPane e,
 * em caso de sucesso (HTTP 200), abre o MainFrame.
 */
public class LoginFrame extends JFrame {

    // Campos de entrada
    private final JTextField tUsuario = new JTextField(18);
    private final JPasswordField tSenha = new JPasswordField(18);

    // Ações
    private final JButton bLogar = new JButton("Logar");
    private final JButton bLimpar = new JButton("Limpar");

    // Seletor de modo
    private final JComboBox<String> cbModo = new JComboBox<>(new String[]{
            "Sem criptografia (HTTP)",
            "Com criptografia (AES)"
    });

    // Endpoints 
    private static final String URL_SEM_CRIPTO = "http://www.datse.com.br/dev/syncjava.php";
    private static final String URL_COM_AES    = "http://www.datse.com.br/dev/syncjava2.php";

    // AES
    private static final String AES_KEY_16 = "1234567890123456"; // 16 chars => 128 bits
    private static final String AES_ALGO   = "AES";

    public LoginFrame() {
        super("Login");
        setLayout(new BorderLayout(8, 8));

        // ===== Topo: formulário =====
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 8, 4, 8);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Modo
        c.gridx = 0; c.gridy = row; form.add(new JLabel("Modo:"), c);
        c.gridx = 1; c.gridy = row++; form.add(cbModo, c);

        // Usuário
        c.gridx = 0; c.gridy = row; form.add(new JLabel("Usuário:"), c);
        c.gridx = 1; c.gridy = row++; form.add(tUsuario, c);

        // Senha
        c.gridx = 0; c.gridy = row; form.add(new JLabel("Senha:"), c);
        c.gridx = 1; c.gridy = row++; form.add(tSenha, c);

        // ===== Rodapé: botões =====
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(bLogar);
        botoes.add(bLimpar);

        add(form, BorderLayout.NORTH);
        add(botoes, BorderLayout.SOUTH);

        // Listeners
        bLogar.addActionListener(e -> onLogar());
        bLimpar.addActionListener(e -> onLimpar());
        cbModo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // nada a fazer por enquanto, mantido para futura customização
            }
        });

        setSize(520, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void onLogar() {
    	// Lê os valores dos campos:
        String usuario = tUsuario.getText().trim();
        String senhaPura = new String(tSenha.getPassword());
        String modo = (String) cbModo.getSelectedItem();

        // Decide URL por modo
        String url = "Sem criptografia (HTTP)".equals(modo) ? URL_SEM_CRIPTO : URL_COM_AES;

        try {
            // Decide a senha a enviar (pura ou cifrada)
            String senhaParaEnviar = senhaPura;
            if ("Com criptografia (AES)".equals(modo)) {
                senhaParaEnviar = encryptAES(senhaPura, AES_KEY_16);
            }

            // Faz a chamada HTTP 
            ClienteHTTP http = new ClienteHTTP(usuario, senhaParaEnviar, url);
            String resposta = http.conecta();
            int httpCode = http.codretorno;
            
            boolean ok = isLoginSucesso(httpCode, resposta);

            // Mostra resposta em diálogo
            showResponseDialog(
            	    ok ? "Login efetuado" : "Falha no login",
            	    "HTTP " + httpCode + "\n\n" + resposta,
            	    ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
            );
            
            // Se sucesso, abre o sistema principal
            if (ok) {
                new MainFrame().setVisible(true);
                dispose();
            }

        } catch (Exception ex) {
            showResponseDialog("Erro ao conectar", String.valueOf(ex.getMessage()), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLimpar() {
        tUsuario.setText("");
        tSenha.setText("");
    }

    /** AES Cipher.getInstance("AES") + Base64). */
    private static String encryptAES(String data, String key16) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key16.getBytes(), AES_ALGO);
        Cipher cipher = Cipher.getInstance(AES_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /** Diálogo com área de texto rolável para respostas longas. */
    private void showResponseDialog(String titulo, String conteudo, int messageType) {
        JTextArea ta = new JTextArea(conteudo, 12, 40);
        ta.setWrapStyleWord(true);
        ta.setLineWrap(true);
        ta.setEditable(false);

        JScrollPane sp = new JScrollPane(
                ta,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        JOptionPane.showMessageDialog(this, sp, titulo, messageType);
    }
    
    
    /** Decide se o login foi bem-sucedido combinando status e conteúdo. */
    private boolean isLoginSucesso(int httpCode, String body) {
        if (httpCode == 200) {
            // Heurísticas comuns para APIs que SEMPRE retornam 200
            if (body == null) return false;
            String b = body.trim().toLowerCase();

            // Ajuste esses sinais conforme a resposta da sua API
            if (b.contains("ok") || b.contains("sucesso") || b.contains("\"success\":true") || b.contains("\"status\":\"ok\"")) {
                return true;
            }
            if (b.contains("erro") || b.contains("invalid") || b.contains("inválid")
                    || b.contains("senha incorreta") || b.contains("\"success\":false")) {
                return false;
            }
            // Se não reconheceu, assuma sucesso apenas se você tiver um padrão claro.
            return false;
        }

        // HTTP semântico
        if (httpCode == 401 || httpCode == 403) return false;
        if (httpCode >= 400) return false;

        return true; // 2xx exceto 200 também pode ser sucesso
    }

}
