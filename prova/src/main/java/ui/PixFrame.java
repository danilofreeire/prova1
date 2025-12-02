package ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.net.MalformedURLException;

public class PixFrame extends JFrame {

    public PixFrame() {
        super("Pagamento em PIX");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);

        String imagePath = "QRCodePix.png";

        try {
            File imageFile = new File(imagePath);
            String imageUri = imageFile.toURI().toURL().toString();

            String htmlContent =
                    "<html><head><title>Pagamento em PIX</title></head>"
                            + "<body><center><h1>Pagamento por PIX</h1>"
                            + "<p>Efetue a leitura do QR Code</p>"
                            + "<img src=\"" + imageUri + "\" alt=\"QR Code\" width=\"200\" height=\"200\">"
                            + "</center></body></html>";

            editorPane.setText(htmlContent);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            editorPane.setText("<html><body>Erro ao carregar a imagem.</body></html>");
        }

        JScrollPane scrollPane = new JScrollPane(editorPane);
        add(scrollPane, BorderLayout.CENTER);

        JButton bImprimir = new JButton("Imprimir");
        add(bImprimir, BorderLayout.SOUTH);

        bImprimir.addActionListener((ActionEvent ae) -> {
            try {
                editorPane.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Erro ao imprimir: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        setSize(400, 400);
        setLocationRelativeTo(null);
    }
}