import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;        

public class JPanelOpenCV extends JPanel{

    BufferedImage image;

    public static void main (String args[]) throws InterruptedException{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        JPanelOpenCV t = new JPanelOpenCV();
        VideoCapture camera = new VideoCapture(0);
      

        Mat frame = new Mat();
        camera.read(frame); 
       

        if(!camera.isOpened()){
            System.out.println("Error");
        }
        else {    
            JFrame frame0=new JFrame();
            
            while(true){        

                if (camera.read(frame)){

                    BufferedImage image = t.MatToBufferedImage(frame);
                    

                    t.window(image, "Original Image", 0, 0,frame0);

                    

                    //t.window(t.loadImage("ImageName"), "Image loaded", 0, 0);

                    //break;
                }
               
                
            }   
        }
        camera.release();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
        g.drawImage(image,700,0,this);
    }

    public JPanelOpenCV() {
    }

    public JPanelOpenCV(BufferedImage img) {
        image = img;
        
    }   

    //Show image on window
    public void window(BufferedImage img, String text, int x, int y,JFrame frame0) {
        
        
        //frame0.getContentPane().add(new JPanelOpenCV(img));
        this.image=img;
        frame0.getContentPane().add(this);
        this.repaint();
        frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame0.setTitle(text);
        frame0.setSize(img.getWidth()+800, img.getHeight() + 300);
        frame0.setLocation(x, y);
        frame0.setVisible(true);
    }

    

    public BufferedImage MatToBufferedImage(Mat frame) {
        //Mat() to BufferedImage
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);

        return image;
    }

}