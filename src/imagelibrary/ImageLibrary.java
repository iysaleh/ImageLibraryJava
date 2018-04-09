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
    
    public static BufferedImage linearInterpolation(BufferedImage src,int newWidth, int newHeight)
    {
        //Great reference for linear interpolation:
        // http://www.di.univr.it/documenti/OccorrenzaIns/matdid/matdid358544.pdf
        float widthRatio = (float) src.getWidth()/newWidth;
        float heightRatio = (float) src.getHeight()/newHeight;
        
        //For X-Interpolation
        BufferedImage dst_x = new BufferedImage(newWidth,src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        //For Y-Interpolation after X-Interpolation
        BufferedImage dst = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst_x = dst_x.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Linear Interpolate the image along the x-axis
        for(int j=0;j<src.getHeight();j++){
            for(int i=0;i<newWidth;i++){
                float pixelLoc = i*widthRatio;
                int pixelMinusLoc = (int) Math.ceil(i*widthRatio)-1;
                int pixelPlusLoc = (int) Math.ceil(i*widthRatio);
                float pixelMinusRatio = pixelLoc - pixelMinusLoc;
                float pixelPlusRatio = pixelPlusLoc - pixelLoc;
                int pixelMinus = wrSrc.getSample(Math.max(Math.min(pixelMinusLoc,src.getWidth()-1),0)
                                                ,j,0);
                int pixelPlus = wrSrc.getSample(Math.max(Math.min(pixelPlusLoc,src.getWidth()-1),0)
                                                ,j,0);
                wrDst_x.setSample(i, j, 0, Math.round((pixelMinus*pixelPlusRatio + pixelPlus*pixelMinusRatio)));
            }
        }
        //wrSrc = wrDst_x.createCompatibleWritableRaster();
        //wrSrc.setRect(wrDst_x);
        //Linear Interpolate the image along the y-axis
        for(int i=0;i<newWidth;i++){
            for(int j=0;j<newHeight;j++){
                float pixelLoc = j*heightRatio;
                int pixelMinusLoc = (int) Math.ceil(j*heightRatio)-1;
                int pixelPlusLoc = (int) Math.ceil(j*heightRatio);
                float pixelMinusRatio = pixelLoc - pixelMinusLoc;
                float pixelPlusRatio = pixelPlusLoc - pixelLoc;
                int pixelMinus = wrDst_x.getSample(i,Math.max(Math.min(pixelMinusLoc,src.getHeight()-1),0),0);
                int pixelPlus = wrDst_x.getSample(i,Math.max(Math.min(pixelPlusLoc,src.getHeight()-1),0),0);
                wrDst.setSample(i, j, 0, Math.round((pixelMinus*pixelMinusRatio + pixelPlus*pixelPlusRatio)));
            }
        }
        
        dst.setData(wrDst);
        System.out.println(src);
        System.out.println(dst);
        return dst;
    }
    
    public static BufferedImage bilinearInterpolation(BufferedImage src,int newWidth, int newHeight)
    {
        float widthRatio = (float) src.getWidth()/newWidth;
        float heightRatio = (float) src.getHeight()/newHeight;
        
        BufferedImage dst = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Set the pixel values using nearest neighbor and rounding to the closest value in the src image for the dest image.
        for(int i=0;i<newWidth;i++){
            for(int j=0;j<newHeight;j++){
                float x = i*widthRatio;
                float y = j*heightRatio;
                int x1 = (int) Math.max(Math.min(Math.round(Math.ceil(x))-1,src.getWidth()-1),0);
                int x2 = (int) Math.max(Math.min(Math.round(Math.ceil(x)),src.getWidth()-1),0);
                int y1 = (int) Math.max(Math.min(Math.round(Math.ceil(y))-1,src.getHeight()-1),0);
                int y2 = (int) Math.max(Math.min(Math.round(Math.ceil(y)),src.getHeight()-1),0);

                int pixUL = wrSrc.getSample(x1, y1, 0);
                int pixUR = wrSrc.getSample(x2, y1, 0);
                int pixBL = wrSrc.getSample(x1, y2, 0);
                int pixBR = wrSrc.getSample(x2, y2, 0);
                
                float xInterpValU = ((x2-x)*pixUL) + ((x-x1)*pixUR);
                float xInterpValB = ((x2-x)*pixBL) + ((x-x1)*pixBR);
                float yInterpVal = ((y2-y)*xInterpValU) + ((y-y1)*xInterpValB);
                wrDst.setSample(i, j, 0, (int)yInterpVal);
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


