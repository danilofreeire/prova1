package infra;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Versão integrada do QRCodeGenerator do     .
 * Gera sempre um arquivo "QRCodePix.png" na pasta do projeto,
 * a partir de um payload (no nosso caso, o id retornado pela API).
 */
public class QRCodeGenerator {

    public QRCodeGenerator() {
    }

    private static BufferedImage generateQRCodeImage(String payload, int width, int height)
            throws WriterException {

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); // mesmo nível do prof

        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                payload,
                BarcodeFormat.QR_CODE,
                width,
                height,
                hints
        );

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    /**
     * Gera um QRCode a partir do ID (payload) e salva como "QRCodePix.png".
     * Mesmo efeito visual do código do     .
     */
    public void gerarQRCode(String id) {
        try {
            String pixPayload = id; //      também usa o id direto

            BufferedImage qrCodeImage = generateQRCodeImage(pixPayload, 300, 300);

            // Salva sempre com o mesmo nome (igual ao prof)
            ImageIO.write(qrCodeImage, "PNG", new File("QRCodePix.png"));

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
}