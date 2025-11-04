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
 * Tela de Login com dois modos:
 * - Sem criptografia (HTTP) -> senha em texto puro 
 * - Com criptografia (AES)  -> cifra a senha antes de enviar 
 *
 *  envio via ClienteHTTP (POST x-www-form-urlencoded).
 */
public class LoginFrame extends JFrame {

    private final JTextField tUsuario = new JTextField(18);
    private final JPasswordField tSenha = new JPasswordField(18);
    private final JTextField tUrl = new JTextField(28);

    private final JTextArea tRetorno = new JTextArea(6, 32);
    private final JButton bLogar = new JButton("Logar");
    private final JButton bLimpar = new JButton("Limpar");

    private final JComboBox<String> cbModo = new JComboBox<>(new String[]{
            "Sem criptografia (HTTP)",
            "Com criptografia (AES)"
    });

    // Defaults dos endpoints
    private static final String URL_SEM_CRIPTO = "http://www.datse.com.br/dev/syncjava.php";
    private static final String URL_COM_AES    = "http://www.datse.com.br/dev/syncjava2.php";

    // Chave fixa de exemplo
    private static final String AES_KEY_16 = "1234567890123456"; // 16 chars -> 128 bits
    private static final String AES_ALGO   = "AES"; // segue o exemplo da aula

    public LoginFrame() {
        super("Login");
        setLayout(new BorderLayout(8, 8));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        c.gridx = 0; c.gridy = row; form.add(new JLabel("Modo:"), c);
        c.gridx = 1; c.gridy = row++; form.add(cbModo, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("URL:"), c);
        c.gridx = 1; c.gridy = row++; form.add(tUrl, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Usuário:"), c);
        c.gridx = 1; c.gridy = row++; form.add(tUsuario, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Senha:"), c);
        c.gridx = 1; c.gridy = row++; form.add(tSenha, c);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(bLogar);
        botoes.add(bLimpar);

        tRetorno.setEditable(false);
        JScrollPane sp = new JScrollPane(tRetorno,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(form, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        // Defaults
        cbModo.setSelectedIndex(0);
        tUrl.setText(URL_SEM_CRIPTO);

        // Listeners
        bLogar.addActionListener(e -> onLogar());
        bLimpar.addActionListener(e -> onLimpar());
        cbModo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) onModoChange();
        });

        setSize(520, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void onModoChange() {
        String modo = (String) cbModo.getSelectedItem();
        if ("Com criptografia (AES)".equals(modo)) {
            // Se ainda está usando o default do modo anterior, troca para o default do AES
            if (URL_SEM_CRIPTO.equals(tUrl.getText().trim())) {
                tUrl.setText(URL_COM_AES);
            }
        } else {
            if (URL_COM_AES.equals(tUrl.getText().trim())) {
                tUrl.setText(URL_SEM_CRIPTO);
            }
        }
    }

    private void onLogar() {
        String usuario = tUsuario.getText().trim();
        String senhaPura = new String(tSenha.getPassword());
        String url = tUrl.getText().trim();
        String modo = (String) cbModo.getSelectedItem();

        try {
            String senhaParaEnviar = senhaPura;

            if ("Com criptografia (AES)".equals(modo)) {
                // Cifra a senha como na 3a aula
                senhaParaEnviar = encryptAES(senhaPura, AES_KEY_16);
            }

            ClienteHTTP http = new ClienteHTTP(usuario, senhaParaEnviar, url);
            String resposta = http.conecta();
            int httpCode = http.codretorno;

            tRetorno.setText("HTTP " + httpCode + "\n" + resposta);

            if (httpCode == 200) {
                // Se precisar, valide 'resposta' (ex.: "ok", token, etc.)
                new MainFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Falha no login. HTTP " + httpCode,
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            tRetorno.setText("Erro: " + ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Erro ao conectar: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLimpar() {
        tUsuario.setText("");
        tSenha.setText("");
        tRetorno.setText("");
    }

    // ====== AES ======
    private static String encryptAES(String data, String key16) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key16.getBytes(), AES_ALGO);
        Cipher cipher = Cipher.getInstance(AES_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }
}
