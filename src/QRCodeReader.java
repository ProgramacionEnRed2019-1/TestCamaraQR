
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeReader {

    private static String decodeQRCode(File qrCodeimage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
    }

    public static void main(String[] args) {
    	
        try {
        	Webcam webcam = Webcam.getDefault();
        	WebcamPanel panel=new WebcamPanel(webcam);
        	JFrame window=new JFrame();
        	window.add(panel);
        	window.setVisible(true);
        	window.pack();
        	window.setSize(600, 400);
        	webcam.open();
        	boolean found=false;
        	while(!found&&window.isActive()) {
        		File outputfile = new File("image.jpg");
        		ImageIO.write(webcam.getImage(), "jpg", outputfile);
        		String codigo=decodeQRCode(outputfile);
        		if(codigo!=null) {
        			System.out.println(codigo);
        			found=true;
        			webcam.close();
        			window.dispose();
        		}
        	}
        	webcam.close();
        	window.dispose();
//            
        } catch (IOException e) {
        }
    }
}
