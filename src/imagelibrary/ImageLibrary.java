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
        //Create a placedholder BufferedImage object which operates in the grayscale domain.
        BufferedImage destGray = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDestGray = destGray.getRaster();
        
        //Copy each sample from the original image into the grayscale domain BufferedImage.
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
    
    public static BufferedImage bilinearInterpolation(BufferedImage src,int newWidth, int newHeight)
    {
        float widthRatio = (float) src.getWidth()/newWidth;
        float heightRatio = (float) src.getHeight()/newHeight;
        
        BufferedImage dst = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        for(int i=0;i<newWidth;i++){
            for(int j=0;j<newHeight;j++){
                float x = i*widthRatio;
                float y = j*heightRatio;
                //Select the pixel locations for comparison. The src.getWidth()-2 Minimum and 1 Maximum is because we don't deal with edge pixels!
                int x1 = (int) Math.max(Math.min(Math.round(Math.ceil(x))-1,src.getWidth()-2),0);
                int x2 = (int) Math.max(Math.min(Math.round(Math.ceil(x)),src.getWidth()-1),1);
                int y1 = (int) Math.max(Math.min(Math.round(Math.ceil(y))-1,src.getHeight()-2),0);
                int y2 = (int) Math.max(Math.min(Math.round(Math.ceil(y)),src.getHeight()-1),1);

                //Retrieve the 4-diagonal pixels
                int pixUL = wrSrc.getSample(x1, y1, 0);
                int pixUR = wrSrc.getSample(x2, y1, 0);
                int pixBL = wrSrc.getSample(x1, y2, 0);
                int pixBR = wrSrc.getSample(x2, y2, 0);
                
                //Calculate the interpolated pixel values
                float xInterpValU = ((x2-x)*pixUL) + ((x-x1)*pixUR);
                float xInterpValB = ((x2-x)*pixBL) + ((x-x1)*pixBR);
                float yInterpVal = ((y2-y)*xInterpValU) + ((y-y1)*xInterpValB);
                
                //Set the resulting pixel value
                wrDst.setSample(i, j, 0, (int)yInterpVal);
            }
        }
        dst.setData(wrDst);
        System.out.println(src);
        System.out.println(dst);
        return dst;
    }
    
    public static BufferedImage linearInterpolationX(BufferedImage src,int newWidth, int newHeight)
    {
        float widthRatio = (float) src.getWidth()/newWidth;
        float heightRatio = (float) src.getHeight()/newHeight;
        
        BufferedImage dst = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        for(int i=0;i<newWidth;i++){
            for(int j=0;j<newHeight;j++){
                float x = i*widthRatio;
                float y = j*heightRatio;
                //Select the pixel locations for comparison. The src.getWidth()-2 Minimum and 1 Maximum is because we don't deal with edge pixels!
                int x1 = (int) Math.max(Math.min(Math.round(Math.ceil(x))-1,src.getWidth()-2),0);
                int x2 = (int) Math.max(Math.min(Math.round(Math.ceil(x)),src.getWidth()-1),0);
                int y1 = (int) Math.max(Math.min(Math.round(y),src.getHeight()-1),0);

                //retrieve the pixels along the x-axis
                int pixL = wrSrc.getSample(x1, y1, 0);
                int pixR = wrSrc.getSample(x2, y1, 0);
                
                //Calculate and set the interpolated pixel value
                float xInterpValU = ((x2-x)*pixL) + ((x-x1)*pixR);
                wrDst.setSample(i, j, 0, (int)xInterpValU);
            }
        }
        dst.setData(wrDst);
        System.out.println(src);
        System.out.println(dst);
        return dst;
    }
    
    public static BufferedImage linearInterpolationY(BufferedImage src,int newWidth, int newHeight)
    {
        float widthRatio = (float) src.getWidth()/newWidth;
        float heightRatio = (float) src.getHeight()/newHeight;
        
        BufferedImage dst = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        for(int i=0;i<newWidth;i++){
            for(int j=0;j<newHeight;j++){
                float x = i*widthRatio;
                float y = j*heightRatio;
                //Select the pixel locations for comparison. The src.getWidth()-2 Minimum and 1 Maximum is because we don't deal with edge pixels!
                int x1 = (int) Math.max(Math.min(Math.round(x),src.getWidth()-1),0);
                int y1 = (int) Math.max(Math.min(Math.round(Math.ceil(y))-1,src.getHeight()-2),0);
                int y2 = (int) Math.max(Math.min(Math.round(Math.ceil(y)),src.getHeight()-1),0);

                //retrieve the pixels along the y-axis
                int pixU = wrSrc.getSample(x1, y1, 0);
                int pixB = wrSrc.getSample(x1, y2, 0);

                //Calculate and set the interpolated pixel value
                float yInterpVal = ((y2-y)*pixU) + ((y-y1)*pixB);
                wrDst.setSample(i, j, 0, (int)yInterpVal);
            }
        }
        dst.setData(wrDst);
        System.out.println(src);
        System.out.println(dst);
        return dst;
    }
    
    public static BufferedImage selectBitplane(BufferedImage src,int bitplane)
    {
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Calculate the bitplane value
        int bitmask = (int)Math.pow(2, bitplane);
        //We want the pixel shift so that we can display the pixels from a bitplane clearly.
        int pixelShift = Math.max(7-bitplane,0);
        System.out.println(bitmask);
        for(int i=0;i<src.getWidth();i++){
            for(int j=0;j<src.getHeight();j++){
                // bitwise and the bitmask with the src pixel. Shift it to 8th bit position for visibility in the display.
                wrDst.setSample(i, j, 0, (wrSrc.getSample(i, j, 0) & bitmask)<<pixelShift);
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
     * Copy a file from source to destination. (Used for copying resources embedded in the Jar file)
     *
     * Code Taken from: https://stackoverflow.com/questions/10308221/how-to-copy-file-inside-jar-to-outside-the-jar
     */
    public static boolean copy(java.io.InputStream source , String destination) {
        boolean success = true;

        System.out.println("Copying ->" + source + "\n\tto ->" + destination);

        try {
            java.io.File file = new java.io.File(destination);
            file.mkdirs();
            java.nio.file.Files.copy(source, java.nio.file.Paths.get(destination), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (java.io.IOException ex) {
            success = false;
        }
        return success;

    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        ImageLibraryUI userInterface = new ImageLibraryUI();
        userInterface.main(args);
        
        //Copy the demo lena image into the images directory for convenience
        try{
            copy(ImageLibrary.class.getResourceAsStream("lena.gif"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"lena.gif");
            
        }
        catch(Exception e)
        {
            //Copying the resource didn't work. Guess you'll have to supply the image yourself!
        }
    }
}


