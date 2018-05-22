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
import java.util.ArrayList;
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

    public static BufferedImage histogramEqualization(BufferedImage src)
    {   
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Gray resolution of image -- hardcoded to 256 (2^8) here
        int grayResolution = 256;
        //2^8 array for histogram of grayscale values
        int[] histogram = new int[grayResolution];
        //2^8 array for CDF of histogram values
        int[] cdf = new int[grayResolution];
        //2^8 array for mapping of equalized histogram values
        int[] mappedHistogramValues = new int[grayResolution];
        //Iterate through every pixel in the image.
        for(int i=0;i<src.getWidth();i++){
            for(int j=0;j<src.getHeight();j++){
                //Get the grayscale value of the current pixel
                int pixelValue = wrSrc.getSample(i, j, 0);
                //We found another pixel of this value, increase our histogram array by 1 for that gray level!
                histogram[pixelValue] = histogram[pixelValue]+1;
            }
        }
        //The number of pixels used in the image
        int totalPixels = wrSrc.getHeight()*wrSrc.getWidth();
        for(int i=0; i<histogram.length;i++)
        {
            //First value in CDF array is just the 0th value in the histogram array
            if(i==0) cdf[i]=histogram[i];
            else
                //CDF is the accumlation of all histogram frequencies before and including this pixel gray level.
                cdf[i]=cdf[i-1]+histogram[i];
            //Calculate the mapped histogram equalized value for the pixel
            mappedHistogramValues[i] = Math.min(Math.round((float)cdf[i]/totalPixels*grayResolution), 255);
        }
        //Loop through all pixels and equalize the histogram values using the calculated mapping array.
        for(int i=0;i<src.getWidth();i++){
            for(int j=0;j<src.getHeight();j++){
                //Set the destination image with the values of the mapped histogram equalized values.
                wrDst.setSample(i, j, 0, mappedHistogramValues[wrSrc.getSample(i, j, 0)]);
            }
        }
        dst.setData(wrDst);
        return dst;
    }
    
    public static BufferedImage zeroPadImage(BufferedImage src, int filterX, int filterY)
    {
        //Get writable raster of the src image.
        WritableRaster wrSrc = src.getRaster();

        //Half of the filter size x&y which must be padded or skipped depending on paddingType
        int xPad = filterX/2;
        int yPad = filterY/2;
        ////Create a new padded image buffer which accounts for the necessary padding.
        BufferedImage padImg = new BufferedImage((2*xPad) + src.getWidth(),(2*yPad) + src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster padRaster = padImg.getRaster();
        
        //Initially fill the image with zeros
        for(int i=0; i<padImg.getWidth();i++)
            for(int j=0; j<padImg.getHeight();j++)
                padRaster.setSample(i, j, 0, 0);

        //Fill the non-pad pixels with the values from the src image
        for(int i=0;i<src.getWidth();i++)
            for(int j=0;j<src.getHeight();j++)
                padRaster.setSample(i+xPad, j+yPad, 0, wrSrc.getSample(i, j, 0));
        return padImg;
    }
    
    public static BufferedImage scaleImage(BufferedImage src)
    {
        //Get writable raster of the src image.
        WritableRaster wrSrc = src.getRaster();

        ////Create a new padded image buffer which accounts for the necessary padding.
        BufferedImage scaledImg = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster scaledRaster = scaledImg.getRaster();
        
        int lowestValue=0;
        int maxValue=0;
        //Find the lowest & max values for a given image
        for(int i=0; i<src.getWidth();i++)
            for(int j=0; j<src.getHeight();j++){
                lowestValue = Math.min(lowestValue, wrSrc.getSample(i, j, 0));
                maxValue = Math.max(maxValue,wrSrc.getSample(i, j, 0));
            }

        //Get the absolute value of the lowest value pixel
        lowestValue = Math.abs(lowestValue);
        //Scale the image according to the lowest and max values
        for(int i=0; i<src.getWidth();i++)
            for(int j=0; j<src.getHeight();j++){
                int sample = wrSrc.getSample(i, j, 0);
                //scale the sample
                sample = Math.round((((float)lowestValue+sample)/maxValue+lowestValue)*255);
                //set the value of the scaled buffered image with the scaled sample value.
                scaledRaster.setSample(i, j, 0, sample);
            }
        return scaledImg;
    }
    
    public static BufferedImage drawHistogram(BufferedImage src, int histogramWidth, int histogramHeight)
    {
        BufferedImage histogramImg = new BufferedImage(histogramWidth+1,histogramHeight+1,BufferedImage.TYPE_BYTE_GRAY);

        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrHist = histogramImg.getRaster();
        
        //Gray resolution of image -- hardcoded to 256 (2^8) here
        int grayResolution = 256;
        //2^8 array for histogram of grayscale values
        int[] histogram = new int[grayResolution];
        
        //Iterate through every pixel in the image.
        for(int i=0;i<src.getWidth();i++){
            for(int j=0;j<src.getHeight();j++){
                //Get the grayscale value of the current pixel
                int pixelValue = wrSrc.getSample(i, j, 0);
                //We found another pixel of this value, increase our histogram array by 1 for that gray level!
                histogram[pixelValue] = histogram[pixelValue]+1;
            }
        }
        
        int maxNumPixels = 0;
        //Find the maximum histogram value
        for(int i=0; i < histogram.length;i++)
            maxNumPixels = Math.max(maxNumPixels, histogram[i]);
        
        //System.out.println("MAX PIXELS: "+maxNumPixels);
        //The amount of pixel width to draw per gray resolution level
        int pixelDrawWidth = histogramWidth/256;
        //Scale factor so that max pixels can be the largest part of the histogram.
        float scaleFactor = (float)maxNumPixels/histogramHeight;
        for(int i=0; i < histogram.length;i++)
        {
            System.out.println("histogram["+i+"]=="+histogram[i]);
            int drawHeight = (int)Math.floor((float)histogram[i]/scaleFactor);
            for(int j=histogramHeight;j>histogramHeight-drawHeight;j--)
            {
                for(int x=i*pixelDrawWidth;x <=(i*pixelDrawWidth)+pixelDrawWidth;x++)
                {
                    wrHist.setSample(x, j, 0, 255);
                }
            }
        }
        histogramImg.setData(wrHist);
        return histogramImg;
    }
    
    public static BufferedImage localHistogramEqualization(BufferedImage src,int filterX,int filterY,String paddingType)
    {   
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Empty declarations here to be determined based on padding style chosen.
        BufferedImage padImg;
        WritableRaster padRaster;

        //Half of the filter size x&y which must be padded or skipped depending on paddingType
        int xPad = filterX/2;
        int yPad = filterY/2;
        
        
        //Do the setup for different padding types.
        //Currently only support 0 or no padding (IE skipping the edge pixels)
        if (paddingType=="zero"){
            //Create a new padded image buffer which accounts for the necessary padding.
            padImg = zeroPadImage(src,filterX,filterY);
            padRaster = padImg.getRaster();
        }
        else //Skip edge pixels, don't do padding to make them processable.
        {
            //Copy all pixels to destination image buffer (since we skip edge pixels, we need them)
            for(int i=0;i<src.getWidth();i++)
                for(int j=0;j<src.getHeight();j++)
                    wrDst.setSample(i, j, 0, wrSrc.getSample(i, j, 0));
            padImg = src;
            padRaster = padImg.getRaster();
        }

        //Gray resolution of image -- hardcoded to 256 (2^8) here
        int grayResolution = 256;
        //Iterate through every pixel in the image differently depending on the padding type
        for(int i=xPad;i<padImg.getWidth()-xPad;i++){
            for(int j=yPad;j<padImg.getHeight()-yPad;j++){
                //2^8 array for histogram of grayscale values
                int[] histogram = new int[grayResolution];
                //2^8 array for CDF of histogram values
                int[] cdf = new int[grayResolution];
                //2^8 array for mapping of equalized histogram values
                int[] mappedHistogramValues = new int[grayResolution];
                //Do local histogram equalization 
                for(int x = -xPad; x < xPad+1; x++)
                    for(int y = -yPad; y < yPad+1; y++)
                    {
                        //Get the grayscale value of the current pixel
                        int pixelValue = padRaster.getSample(i+x, j+y, 0);
                        //We found another pixel of this value, increase our histogram array by 1 for that gray level!
                        histogram[pixelValue] = histogram[pixelValue]+1;                        
                    }
                //The number of pixels in the filter
                int totalPixels = filterX*filterY;
                
                for(int x=0; x<histogram.length;x++)
                {
                    //First value in CDF array is just the 0th value in the histogram array
                    if(x==0) cdf[x]=histogram[x];
                    else
                        //CDF is the accumlation of all histogram frequencies before and including this pixel gray level.
                        cdf[x]=cdf[x-1]+histogram[x];
                    //Calculate the mapped histogram equalized value for the pixel
                    mappedHistogramValues[x] = Math.min(Math.round((float)cdf[x]/totalPixels*grayResolution), 255);
                }
                //Equalize the histogram value in the final transformed image using the calculated mapping array.
                if(paddingType=="zero")
                    wrDst.setSample(i-xPad, j-yPad, 0, mappedHistogramValues[padRaster.getSample(i,j,0)]);
                else//Skipping edge pixels so there is no padding
                    wrDst.setSample(i, j, 0, mappedHistogramValues[padRaster.getSample(i,j,0)]);
            }
        }
        dst.setData(wrDst);
        return dst;
    }
    public static BufferedImage medianFilter(BufferedImage src,int filterX,int filterY,String paddingType)
    {  
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Initialize Pad-relevant variables
        BufferedImage padImg;
        WritableRaster padRaster;
        int xPad = filterX/2;
        int yPad = filterY/2;
        
        //Do the setup for different padding types.
        //Currently only support 0 padding
        if (paddingType=="zero"){
            //Create a new padded image buffer which accounts for the necessary padding.
            padImg = zeroPadImage(src,filterX,filterY);
            padRaster = padImg.getRaster();
        }
        else {
            return src; //No other padding types are supported currently!
        }
        
        for(int i=xPad;i<padImg.getWidth()-xPad;i++)
            for(int j=yPad;j<padImg.getHeight()-yPad;j++){
                ArrayList<Integer> pixelValuesInFilter = new ArrayList<Integer>();
                //Loop through filter.
                for(int x = -xPad; x < xPad+1; x++)
                    for(int y = -yPad; y < yPad+1; y++){
                        pixelValuesInFilter.add(padRaster.getSample(i+x, y+j, 0));
                    }
                pixelValuesInFilter.sort(null);
                if(paddingType=="zero")
                    wrDst.setSample(i-xPad, j-yPad, 0, pixelValuesInFilter.get(pixelValuesInFilter.size()/2));
            }
        
        dst.setData(wrDst);
        return dst;
    }

    public static BufferedImage maxFilter(BufferedImage src,int filterX,int filterY,String paddingType)
    {  
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Initialize Pad-relevant variables
        BufferedImage padImg;
        WritableRaster padRaster;
        int xPad = filterX/2;
        int yPad = filterY/2;
        
        //Do the setup for different padding types.
        //Currently only support 0 padding
        if (paddingType=="zero"){
            //Create a new padded image buffer which accounts for the necessary padding.
            padImg = zeroPadImage(src,filterX,filterY);
            padRaster = padImg.getRaster();
        }
        else {
            return src; //No other padding types are supported currently!
        }
        
        for(int i=xPad;i<padImg.getWidth()-xPad;i++)
            for(int j=yPad;j<padImg.getHeight()-yPad;j++){
                ArrayList<Integer> pixelValuesInFilter = new ArrayList<Integer>();
                //Loop through filter.
                for(int x = -xPad; x < xPad+1; x++)
                    for(int y = -yPad; y < yPad+1; y++){
                        pixelValuesInFilter.add(padRaster.getSample(i+x, y+j, 0));
                    }
                pixelValuesInFilter.sort(null);
                if(paddingType=="zero")
                    wrDst.setSample(i-xPad, j-yPad, 0, pixelValuesInFilter.get(pixelValuesInFilter.size()-1));
            }
        
        dst.setData(wrDst);
        return dst;
    }

    public static BufferedImage minFilter(BufferedImage src,int filterX,int filterY,String paddingType)
    {  
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Initialize Pad-relevant variables
        BufferedImage padImg;
        WritableRaster padRaster;
        int xPad = filterX/2;
        int yPad = filterY/2;
        
        //Do the setup for different padding types.
        //Currently only support 0 padding
        if (paddingType=="zero"){
            //Create a new padded image buffer which accounts for the necessary padding.
            padImg = zeroPadImage(src,filterX,filterY);
            padRaster = padImg.getRaster();
        }
        else {
            return src; //No other padding types are supported currently!
        }
        
        for(int i=xPad;i<padImg.getWidth()-xPad;i++)
            for(int j=yPad;j<padImg.getHeight()-yPad;j++){
                ArrayList<Integer> pixelValuesInFilter = new ArrayList<Integer>();
                //Loop through filter.
                for(int x = -xPad; x < xPad+1; x++)
                    for(int y = -yPad; y < yPad+1; y++){
                        pixelValuesInFilter.add(padRaster.getSample(i+x, y+j, 0));
                    }
                pixelValuesInFilter.sort(null);
                if(paddingType=="zero")
                    wrDst.setSample(i-xPad, j-yPad, 0, pixelValuesInFilter.get(0));
            }
        
        dst.setData(wrDst);
        return dst;
    }

    public static BufferedImage midpointFilter(BufferedImage src,int filterX,int filterY,String paddingType)
    {  
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Initialize Pad-relevant variables
        BufferedImage padImg;
        WritableRaster padRaster;
        int xPad = filterX/2;
        int yPad = filterY/2;
        
        //Do the setup for different padding types.
        //Currently only support 0 padding
        if (paddingType=="zero"){
            //Create a new padded image buffer which accounts for the necessary padding.
            padImg = zeroPadImage(src,filterX,filterY);
            padRaster = padImg.getRaster();
        }
        else {
            return src; //No other padding types are supported currently!
        }
        
        for(int i=xPad;i<padImg.getWidth()-xPad;i++)
            for(int j=yPad;j<padImg.getHeight()-yPad;j++){
                ArrayList<Integer> pixelValuesInFilter = new ArrayList<Integer>();
                //Loop through filter.
                for(int x = -xPad; x < xPad+1; x++)
                    for(int y = -yPad; y < yPad+1; y++){
                        pixelValuesInFilter.add(padRaster.getSample(i+x, y+j, 0));
                    }
                pixelValuesInFilter.sort(null);
                if(paddingType=="zero")
                    wrDst.setSample(i-xPad, j-yPad, 0, Math.round((float)1/2 * (pixelValuesInFilter.get(0) + pixelValuesInFilter.get(pixelValuesInFilter.size()-1))));
            }
        
        dst.setData(wrDst);
        return dst;
    }

    public static BufferedImage alphaTrimmedMeanFilter(BufferedImage src,int filterX,int filterY,int d, String paddingType)
    {  
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Initialize Pad-relevant variables
        BufferedImage padImg;
        WritableRaster padRaster;
        int xPad = filterX/2;
        int yPad = filterY/2;
        
        //Do the setup for different padding types.
        //Currently only support 0 padding
        if (paddingType=="zero"){
            //Create a new padded image buffer which accounts for the necessary padding.
            padImg = zeroPadImage(src,filterX,filterY);
            padRaster = padImg.getRaster();
        }
        else {
            return src; //No other padding types are supported currently!
        }
        
        if(d%2==1) //Odd d's don't work, force it to be even!
            d += 1;
        
        int dHalf = d/2;
        int filterSize = filterX * filterY;        
        for(int i=xPad;i<padImg.getWidth()-xPad;i++)
            for(int j=yPad;j<padImg.getHeight()-yPad;j++){
                ArrayList<Integer> pixelValuesInFilter = new ArrayList<Integer>();
                //Loop through filter.
                for(int x = -xPad; x < xPad+1; x++)
                    for(int y = -yPad; y < yPad+1; y++){
                        pixelValuesInFilter.add(padRaster.getSample(i+x, y+j, 0));
                    }
                pixelValuesInFilter.sort(null);
                int summedAlphaTrimmedPixelValues = 0;
                for(int x=dHalf; x< pixelValuesInFilter.size()-dHalf;x++)
                    summedAlphaTrimmedPixelValues += pixelValuesInFilter.get(x);
                if(paddingType=="zero")
                    wrDst.setSample(i-xPad, j-yPad, 0, Math.round((float)1/(filterSize-d) * summedAlphaTrimmedPixelValues));
            }
        
        dst.setData(wrDst);
        return dst;
    }
    
    public static BufferedImage arithmeticMeanFilter(BufferedImage src,int filterX,int filterY,String paddingType)
    {  
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Initialize Pad-relevant variables
        BufferedImage padImg;
        WritableRaster padRaster;
        int xPad = filterX/2;
        int yPad = filterY/2;
        
        //Do the setup for different padding types.
        //Currently only support 0 padding
        if (paddingType=="zero"){
            //Create a new padded image buffer which accounts for the necessary padding.
            padImg = zeroPadImage(src,filterX,filterY);
            padRaster = padImg.getRaster();
        }
        else {
            return src; //No other padding types are supported currently!
        }
        
        int filterSize = filterX * filterY;
        for(int i=xPad;i<padImg.getWidth()-xPad;i++)
            for(int j=yPad;j<padImg.getHeight()-yPad;j++){
                int pixelSubimageSum = 0;
                for(int x = -xPad; x < xPad+1; x++)
                    for(int y = -yPad; y < yPad+1; y++){
                        pixelSubimageSum += padRaster.getSample(i+x, y+j, 0);
                    }
                if(paddingType=="zero")
                    wrDst.setSample(i-xPad, j-yPad, 0, Math.round((float)pixelSubimageSum/filterSize));
            }
        
        dst.setData(wrDst);
        return dst;
    }
    
    public static BufferedImage geometricMeanFilter(BufferedImage src,int filterX,int filterY,String paddingType)
    {  
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Initialize Pad-relevant variables
        BufferedImage padImg;
        WritableRaster padRaster;
        int xPad = filterX/2;
        int yPad = filterY/2;
        
        //Do the setup for different padding types.
        //Currently only support 0 padding
        if (paddingType=="zero"){
            //Create a new padded image buffer which accounts for the necessary padding.
            padImg = zeroPadImage(src,filterX,filterY);
            padRaster = padImg.getRaster();
        }
        else {
            return src; //No other padding types are supported currently!
        }
        
        int filterSize = filterX * filterY;
        for(int i=xPad;i<padImg.getWidth()-xPad;i++)
            for(int j=yPad;j<padImg.getHeight()-yPad;j++){
                double pixelSubimageProduct = 1;
                for(int x = -xPad; x < xPad+1; x++)
                    for(int y = -yPad; y < yPad+1; y++){
                        pixelSubimageProduct *= padRaster.getSample(i+x, y+j, 0);
                    }
                        
                if(paddingType=="zero")
                    wrDst.setSample(i-xPad, j-yPad, 0, (int)Math.round(Math.pow(pixelSubimageProduct,(double)1/filterSize)));
            }
        
        dst.setData(wrDst);
        return dst;
    }

    public static BufferedImage harmonicMeanFilter(BufferedImage src,int filterX,int filterY,String paddingType)
    {  
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Initialize Pad-relevant variables
        BufferedImage padImg;
        WritableRaster padRaster;
        int xPad = filterX/2;
        int yPad = filterY/2;
        
        //Do the setup for different padding types.
        //Currently only support 0 padding
        if (paddingType=="zero"){
            //Create a new padded image buffer which accounts for the necessary padding.
            padImg = zeroPadImage(src,filterX,filterY);
            padRaster = padImg.getRaster();
        }
        else {
            return src; //No other padding types are supported currently!
        }
        
        int filterSize = filterX * filterY;
        
        for(int i=xPad;i<padImg.getWidth()-xPad;i++)
            for(int j=yPad;j<padImg.getHeight()-yPad;j++){
                double pixelSubimageSum = 0;
                for(int x = -xPad; x < xPad+1; x++)
                    for(int y = -yPad; y < yPad+1; y++){
                        pixelSubimageSum += (double)1/padRaster.getSample(i+x, y+j, 0);
                    }
                if(paddingType=="zero")
                    wrDst.setSample(i-xPad, j-yPad, 0, Math.round((float)filterSize/pixelSubimageSum));
            }
        
        dst.setData(wrDst);
        return dst;
    }

    public static BufferedImage contraharmonicMeanFilter(BufferedImage src,int filterX,int filterY,double filterOrder,String paddingType)
    {  
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Initialize Pad-relevant variables
        BufferedImage padImg;
        WritableRaster padRaster;
        int xPad = filterX/2;
        int yPad = filterY/2;
        
        //Do the setup for different padding types.
        //Currently only support 0 padding
        if (paddingType=="zero"){
            //Create a new padded image buffer which accounts for the necessary padding.
            padImg = zeroPadImage(src,filterX,filterY);
            padRaster = padImg.getRaster();
        }
        else {
            return src; //No other padding types are supported currently!
        }
        
        for(int i=xPad;i<padImg.getWidth()-xPad;i++)
            for(int j=yPad;j<padImg.getHeight()-yPad;j++){
                double highOrderSum = 0;
                double normalOrderSum = 0;
                for(int x = -xPad; x < xPad+1; x++)
                    for(int y = -yPad; y < yPad+1; y++){
                        highOrderSum += Math.pow((double)padRaster.getSample(i+x, y+j, 0), filterOrder+1);
                        normalOrderSum += Math.pow((double)padRaster.getSample(i+x, y+j, 0), filterOrder);
                    }
                if(paddingType=="zero")
                    wrDst.setSample(i-xPad, j-yPad, 0, (int)Math.round(highOrderSum/normalOrderSum));
            }
        
        dst.setData(wrDst);
        return dst;
    }
    
    //Convolve a given filter with a src image pixel and output the result ot the destination image. (Note, src image should already be padded if convolving on edge pixels!)
    public static void convolvePixel(WritableRaster src, int[][] unscaledOutput, int[][] filter,int srcX,int srcY, int dstX, int dstY)
    {
        int w = getFilterW(filter);
        //Only odd sized filters are supported (EG: 3x3,3x5)
        int filterX = filter.length;
        int filterY = filter[0].length;
        int centerX = filterX/2;
        int centerY = filterY/2;
        int convolutionResult = 0;
        for(int i=-centerX; i<=centerX;i++)
            for(int j=-centerY; j<=centerY;j++)
            {
                convolutionResult = (src.getSample(i+srcX, j+srcY, 0)*filter[i+centerX][j+centerY])+convolutionResult;
            }
        if (w!=0)
            convolutionResult = Math.round(((float)1/w) * convolutionResult); 
        unscaledOutput[dstX][dstY]=convolutionResult;
        //dst.setSample(dstX, dstY, 0, convolutionResult);
    }
    
    public static BufferedImage convolveImage(BufferedImage src,int[][] filter,String paddingType)
    {  
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        int [][] unscaledOutput = new int[src.getWidth()][src.getHeight()];
        
        int filterX = filter.length;
        int filterY = filter[0].length;
        int xPad = filterX/2;
        int yPad = filterY/2;
        
        //Initialize Pad-relevant variables
        BufferedImage padImg = zeroPadImage(src,filterX,filterY);
        WritableRaster padRaster = padImg.getRaster();
        
        int maxPixelValue = 0;
        int minPixelValue = 255;
        for(int i=xPad;i<padImg.getWidth()-xPad;i++)
            for(int j=yPad;j<padImg.getHeight()-yPad;j++){
                convolvePixel(padRaster,unscaledOutput,filter,i,j,i-xPad,j-yPad);
                maxPixelValue = Math.max(maxPixelValue, unscaledOutput[i-xPad][j-yPad]);
                minPixelValue = Math.min(minPixelValue, unscaledOutput[i-xPad][j-yPad]);
            }
        minPixelValue = Math.abs(minPixelValue);
        
        //Scale the values in the image!
        for(int i=0; i<wrDst.getWidth();i++)
            for(int j=0;j<wrDst.getHeight();j++){
                //Scale the pixel value to be within 0-255!
                int scaledValue = Math.round((((float)unscaledOutput[i][j]+minPixelValue)/(maxPixelValue+minPixelValue))*255);
                wrDst.setSample(i, j, 0, scaledValue);
            }
        dst.setData(wrDst);
        return dst;
    }


    public static BufferedImage sharpenImage(BufferedImage src,int[][] filter,String paddingType)
    {  
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Image array to hold the convolved, unscaled values
        int [][] unscaledOutput = new int[src.getWidth()][src.getHeight()];
        int [][] sourceImageArray = get2DImageArray(wrSrc);
        int[][] scaledSourceImage = scaleImageArray(sourceImageArray);
        int filterX = filter.length;
        int filterY = filter[0].length;
        int xPad = filterX/2;
        int yPad = filterY/2;
        
        //Initialize Pad-relevant variables
        BufferedImage padImg = zeroPadImage(src,filterX,filterY);
        WritableRaster padRaster = padImg.getRaster();
        
        int maxPixelValue = 0;
        int minPixelValue = 255;
        for(int i=xPad;i<padImg.getWidth()-xPad;i++)
            for(int j=yPad;j<padImg.getHeight()-yPad;j++){
                convolvePixel(padRaster,unscaledOutput,filter,i,j,i-xPad,j-yPad);
            }
        
        int[][] scaledLaplacianArray = scaleImageArray(unscaledOutput);
        int[][] sharpenedImage = subtract2DArray(scaledSourceImage, scaledLaplacianArray);
        int[][] sharpenedScaledImage = scaleImageArray(sharpenedImage);
        setWritableRasterTo2DImageArray(sharpenedScaledImage,wrDst);
        
        dst.setData(wrDst);
        return dst;
    }
    
    //Simple function to scale an image array
    public static int[][] scaleImageArray(int[][] imageArray)
    {
        int[][] scaledArray = new int[imageArray.length][imageArray[0].length];
        //Get the minimum and maximum values from the image array
        int minValue=512;
        int maxValue=-512;
        for(int i=0; i<imageArray.length;i++)
            for(int j=0; j<imageArray[0].length;j++){
                minValue = Math.min(minValue, imageArray[i][j]);
                maxValue = Math.max(maxValue,imageArray[i][j]);
            }
        minValue = Math.abs(minValue);
        for(int i=0; i<imageArray.length;i++)
            for(int j=0; j<imageArray[0].length;j++){
                scaledArray[i][j] = Math.round((((float)imageArray[i][j]+minValue)/(maxValue+minValue))*255);
            }
        return scaledArray;
    }

    //Simple function to subtract 2 image arrays
    public static int[][] subtract2DArray(int [][] a,int [][] b)
    {
        int[][] dest  = new int[a.length][a[0].length];
        for(int i=0; i<a.length;i++)
            for(int j=0; j<a[0].length;j++){
                dest[i][j] = a[i][j] - b[i][j];
            }
        return dest;
    }
    
    //Simple function to subtract 2 image arrays
    public static int[][] scalarMultiply2DArray(int [][] a,float scalar)
    {
        int[][] dest  = new int[a.length][a[0].length];
        for(int i=0; i<a.length;i++)
            for(int j=0; j<a[0].length;j++){
                dest[i][j] = Math.round(scalar*a[i][j]);
            }
        return dest;
    }
    
    //Simple function to add 2 image arrays
    public static int[][] add2DArray(int [][] a,int [][] b)
    {
        int[][] dest  = new int[a.length][a[0].length];
        for(int i=0; i<a.length;i++)
            for(int j=0; j<a[0].length;j++){
                dest[i][j] = a[i][j] + b[i][j];
            }
        return dest;
    }
    //Simple function to get a 2d Image array from a writableRaster
    public static int[][] get2DImageArray(WritableRaster wr)
    {
        int[][] imageArray = new int[wr.getWidth()][wr.getHeight()];
        for(int i=0; i<imageArray.length;i++)
            for(int j=0; j<imageArray[0].length;j++){
                imageArray[i][j] = wr.getSample(i, j, 0);
            }
        return imageArray;
    }
    
    public static void setWritableRasterTo2DImageArray(int[][]imageArray,WritableRaster wrDst)
    {
        for(int i=0; i<imageArray.length;i++)
            for(int j=0; j<imageArray[0].length;j++){
                wrDst.setSample(i, j, 0, imageArray[i][j]);
            }   
    }
    
    public static BufferedImage highBoostFilter(BufferedImage src,int[][] filter, float highBoostK,String paddingType)
    {  
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Image array to hold the convolved, unscaled values
        int [][] blurredOutput = new int[src.getWidth()][src.getHeight()];
        int [][] sourceImageArray = get2DImageArray(wrSrc);
        int filterX = filter.length;
        int filterY = filter[0].length;
        int xPad = filterX/2;
        int yPad = filterY/2;
        
        //Initialize Pad-relevant variables
        BufferedImage padImg = zeroPadImage(src,filterX,filterY);
        WritableRaster padRaster = padImg.getRaster();
        
        int maxPixelValue = 0;
        int minPixelValue = 255;
        for(int i=xPad;i<padImg.getWidth()-xPad;i++)
            for(int j=yPad;j<padImg.getHeight()-yPad;j++){
                convolvePixel(padRaster,blurredOutput,filter,i,j,i-xPad,j-yPad);
            }
        
        //int[][] scaledLaplacianArray = scaleImageArray(unscaledOutput);
        int[][] unsharpMask = subtract2DArray(sourceImageArray,blurredOutput);
        int[][] weightedUnsharpMask = scalarMultiply2DArray(unsharpMask,highBoostK);
        int[][] imageWithSharpenedMask = add2DArray(sourceImageArray, weightedUnsharpMask);
        int[][] scaledSharpenedImage = scaleImageArray(imageWithSharpenedMask);
        setWritableRasterTo2DImageArray(scaledSharpenedImage,wrDst);
        
        dst.setData(wrDst);
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
    
    public static BufferedImage selectBitplanes(BufferedImage src,boolean bit8, boolean bit7, boolean bit6, boolean bit5, boolean bit4, boolean bit3, boolean bit2, boolean bit1)
    {
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //Calculate the bitplane value
        int bitmask = 0;
        
        if (bit8) bitmask += 128;
        if (bit7) bitmask += 64;
        if (bit6) bitmask += 32;
        if (bit5) bitmask += 16;
        if (bit4) bitmask += 8;
        if (bit3) bitmask += 4;
        if (bit2) bitmask += 2;
        if (bit1) bitmask += 1;

        //We don't do pixel shift in this method since we want to see what actually happens to the values.
        //To see a bitplane more clearly, use the selectBitplane function.

        for(int i=0;i<src.getWidth();i++){
            for(int j=0;j<src.getHeight();j++){
                // bitwise and the bitmask with the src pixel.
                wrDst.setSample(i, j, 0, (wrSrc.getSample(i, j, 0) & bitmask));
            }
        }
        dst.setData(wrDst);
 
        return dst;
    }    
    
    /*
    *   Change the bitdepth of an image.
    *   This is only a faux change since the actual bits of the image remain
    *   from 0-255 in the displayed/resulting image.
    */
    public static BufferedImage changeBitDepth(BufferedImage src,int bitDepth)
    {
        BufferedImage dst = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wrSrc = src.getRaster();
        WritableRaster wrDst = dst.getRaster();
        
        //We want the pixel shift so that we can display the pixels from a bitplane clearly.
        int pixelShift = Math.max(8-bitDepth,0);
        for(int i=0;i<src.getWidth();i++){
            for(int j=0;j<src.getHeight();j++){
                //Shift pixels to get new bit depth. Because we still display in 8 bit, shift back.
                wrDst.setSample(i, j, 0, (wrSrc.getSample(i, j, 0) >> pixelShift << pixelShift));
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
    
    public static int[][] generate2DSmoothingFilter1(int filterX, int filterY)
    {
        //Forcing odd-sized filters!
        if(filterX%2==0){
            filterX +=1;
            System.out.println("Forcing Odd Size Filter X:" + filterX);
        }
        if(filterY%2==0){
            filterY +=1;
            System.out.println("Forcing Odd Size Filter Y:" + filterY);
        }
            
        int[][] filter = new int [filterX][filterY];
        for(int i=0;i<filterX;i++)
            for(int j=0;j<filterY;j++)
                filter[i][j]=1;
        return filter;
    }
    
    public static int[][] generate2DLaPlacianFilter2(int filterX, int filterY)
    {
        int[][] filter = generate2DSmoothingFilter1(filterX,filterY);

        //n2-1 with support for differently sized filters
        filter[filter.length/2][filter[0].length/2] = -1*((filter.length * filter[0].length) - 1);

        return filter;
    }
    
    public static int[][] gaussian5x5Filter()
    {
        int[][] filter={ {1,4,7,4,1}, {4,16,26,16,4}, {7,26,41,26,7}, {4,16,26,16,4}, {1,4,7,4,1} };
        return filter;
    }
    public static int getFilterW(int[][] filter)
    {
        int w = 0;
        for(int i=0; i<filter.length;i++)
            for(int j=0;j<filter[0].length;j++)
                w += filter[i][j];
        return w;
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
            copy(ImageLibrary.class.getResourceAsStream("wdg4.gif"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"wdg4.gif");
            copy(ImageLibrary.class.getResourceAsStream("blurry_fce2.gif"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"blurry_fce2.gif");
            copy(ImageLibrary.class.getResourceAsStream("800px-Unequalized_Hawkes_Bay_NZ.jpg"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"800px-Unequalized_Hawkes_Bay_NZ.jpg");
            
            //Copy all the book images
            copy(ImageLibrary.class.getResourceAsStream("Fig0503 (original_pattern).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0503 (original_pattern).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0504(a)(gaussian-noise).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0504(a)(gaussian-noise).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0504(b)(rayleigh-noise).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0504(b)(rayleigh-noise).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0504(c)(gamma-noise).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0504(c)(gamma-noise).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0504(g)(neg-exp-noise).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0504(g)(neg-exp-noise).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0504(h)(uniform-noise).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0504(h)(uniform-noise).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0504(i)(salt-pepper-noise).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0504(i)(salt-pepper-noise).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0505(a)(applo17_boulder_noisy).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0505(a)(applo17_boulder_noisy).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0507(a)(ckt-board-orig).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0507(a)(ckt-board-orig).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0507(b)(ckt-board-gauss-var-400).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0507(b)(ckt-board-gauss-var-400).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0508(a)(circuit-board-pepper-prob-pt1).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0508(a)(circuit-board-pepper-prob-pt1).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0508(b)(circuit-board-salt-prob-pt1).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0508(b)(circuit-board-salt-prob-pt1).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0510(a)(ckt-board-saltpep-prob.pt05).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0510(a)(ckt-board-saltpep-prob.pt05).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0512(a)(ckt-uniform-var-800).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0512(a)(ckt-uniform-var-800).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0512(b)(ckt-uniform-plus-saltpepr-prob-pt1).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0512(b)(ckt-uniform-plus-saltpepr-prob-pt1).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0513(a)(ckt_gaussian_var_1000_mean_0).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0513(a)(ckt_gaussian_var_1000_mean_0).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0514(a)(ckt_saltpep_prob_pt25).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0514(a)(ckt_saltpep_prob_pt25).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0516(a)(applo17_boulder_noisy).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0516(a)(applo17_boulder_noisy).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0516(c)(BW_banreject_order4).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0516(c)(BW_banreject_order4).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0519(a)(florida_satellite_original).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0519(a)(florida_satellite_original).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0520(a)(NASA_Mariner6_Mars).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0520(a)(NASA_Mariner6_Mars).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0524(a)(impulse).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0524(a)(impulse).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0524(b)(blurred-impulse).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0524(b)(blurred-impulse).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0525(a)(aerial_view_no_turb).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0525(a)(aerial_view_no_turb).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0525(b)(aerial_view_turb_c_0pt0025).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0525(b)(aerial_view_turb_c_0pt0025).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0525(c)(aerial_view_turb_c_0pt001).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0525(c)(aerial_view_turb_c_0pt001).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0525(d)(aerial_view_turb_c_0pt00025).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0525(d)(aerial_view_turb_c_0pt00025).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0526(a)(original_DIP).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0526(a)(original_DIP).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0529(a)(noisiest_var_pt1).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0529(a)(noisiest_var_pt1).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0529(d)(medium_noise_var_pt01).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0529(d)(medium_noise_var_pt01).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0529(g)(least_noise_var_10minus37).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0529(g)(least_noise_var_10minus37).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0533(a)(circle).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0533(a)(circle).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0534(a)(ellipse_and_circle).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0534(a)(ellipse_and_circle).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0539(a)(vertical_rectangle).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0539(a)(vertical_rectangle).png");
            copy(ImageLibrary.class.getResourceAsStream("Fig0539(c)(shepp-logan_phantom).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"Fig0539(c)(shepp-logan_phantom).png");
            copy(ImageLibrary.class.getResourceAsStream("FigP0501(filtering).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"FigP0501(filtering).png");
            copy(ImageLibrary.class.getResourceAsStream("FigP0510(left).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"FigP0510(left).png");
            copy(ImageLibrary.class.getResourceAsStream("FigP0510(right).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"FigP0510(right).png");
            copy(ImageLibrary.class.getResourceAsStream("FigP0520(blurred-heart).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"FigP0520(blurred-heart).png");
            copy(ImageLibrary.class.getResourceAsStream("FigP0528(a)(single_dot).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"FigP0528(a)(single_dot).png");
            copy(ImageLibrary.class.getResourceAsStream("FigP0528(b)(two_dots).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"FigP0528(b)(two_dots).png");
            copy(ImageLibrary.class.getResourceAsStream("FigP0528(c)(doughnut).png"),java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString()+java.io.File.separator+"images"+java.io.File.separator+"book"+java.io.File.separator+"FigP0528(c)(doughnut).png");


            
        }
        catch(Exception e)
        {
            //Copying the resource didn't work. Guess you'll have to supply the image yourself!
        }
    }
}


