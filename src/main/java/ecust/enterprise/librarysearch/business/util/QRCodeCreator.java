package ecust.enterprise.librarysearch.business.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeCreator
{
  private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    
    //!!!此处解决中文乱码问题
    HashMap<EncodeHintType, Object> hints = new HashMap<>();
    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
    //保存信息为二维码图片
    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
    
    //生成图片存放在路径上
    MatrixToImageWriter.writeToPath(bitMatrix, "PNG", new File(filePath).toPath());
}

  /**
   * Given an object, this method will create a QRCode with defined path 
   * <p> The QRCode contains info:{@code object.toString()}
   * @param object
   * @param path example: qrcode/ISBN.png
   * @throws WriterException
   * @throws IOException
   */
  public static void createQRCodeForObject(Object object, String path) throws WriterException, IOException
  {
    String data = "Default String";
    if (object != null)
    {
      data = object.toString();
    }
    
    Map<String, String> map = new HashMap<String, String>();
    map.put("data", data);
    JSONObject jsonObject = new JSONObject(map);
    String json = jsonObject.toString();

    generateQRCodeImage(json, 200, 200, path);
  }
}