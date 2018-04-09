/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagelibrary;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import javax.imageio.ImageIO;
import java.io.File;
import java.lang.Math;
/**
 *
 * @author Ibraheem Saleh
 */
public class ImageLibrary {

    public static BufferedImage loadGrayscaleImage(File file) throws Exception
    {
        BufferedImage src = ImageIO.read(file);
        BufferedImage destGray = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDestGray = destGray.getRaster();
        
        for(int i=0;i<wrSrc.getWidth();i++){
            for(int j=0;j<wrSrc.getHeight();j++){
                wrDestGray.setSample(i, j, 0, wrSrc.getSample(i, j, 0));
            }
        }
        destGray.setData(wrDestGray);
        return destGray;
    }

    public static BufferedImage nearestNeighborInterpolation(BufferedImage src,int newWidth, int newHeight)
    {
        float widthRatio = (float) src.getWidth()/newWidth;
        float heightRatio = (float) src.getHeight()/newHeight;
        
        BufferedImage dst = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Set the pixel values using nearest neighbor and rounding to the closest value in the src image for the dest image.
        for(int i=0;i<newWidth;i++){
            for(int j=0;j<newHeight;j++){
                wrDst.setSample(i, j, 0, wrSrc.getSample(Math.max(Math.min(Math.round(i*widthRatio),src.getWidth()-1),0),
                                                              Math.max(Math.min(Math.round(j*heightRatio),src.getHeight()-1),0),
                                                              0));
            }
        }
        dst.setData(wrDst);
        System.out.println(src);
        System.out.println(dst);
        return dst;
    }
    
    public static BufferedImage setPixelDepth(BufferedImage src,int newPixelDepth)
    {
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Set the pixel values using nearest neighbor and rounding to the closest value in the src image for the dest image.
        for(int i=0;i<src.getWidth();i++){
            for(int j=0;j<src.getHeight();j++){
                int displayPixelDepth = 8; //No matter what we change the pixel depth to, the displayed/drawn depth of 8 is what we must change our results for.
                int pixelShift = Math.max(displayPixelDepth-newPixelDepth,0);
                wrDst.setSample(i, j, 0, wrSrc.getSample(i, j, 0)>>pixelShift<<pixelShift);
            }
        }
        dst.setData(wrDst);
 
        return dst;
    }
    
    public static BufferedImage deepCopy(BufferedImage src)
    {
        ColorModel cm = src.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = src.copyData(src.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        ImageLibraryUI userInterface = new ImageLibraryUI();
        userInterface.main(args);
    }
    
}


