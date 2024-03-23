package com.et.qrcode.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;

/**
 * QRCodeGenerator
 *
 * @author zhouzhaodong
 */
public class QRCodeGenerator {

    /**
     * generateQRCodeImage
     * @param text
     * @param width
     * @param height
     * @param filePath
     * @throws WriterException
     * @throws IOException
     */
    public static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    /**
     * writeToStream
     * @param text
     * @param width
     * @param height
     * @return
     */
    public static String writeToStream(String text, int width, int height) {
        String message = "";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            Base64.Encoder encoder = Base64.getEncoder();
            message = encoder.encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

}