/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagelibrary;
import static imagelibrary.ImageLibrary.copy;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
/**
 *
 * @author Ibraheem Saleh
 */
public class HW3Main {
    public static void main(String[] args) throws Exception {
        //Copy the demo lena image into the images directory for convenience
        try{
            //Copy all the book images as files
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
        
        File folder = new File("images/book");
        File[] listOfImages = folder.listFiles();
        
        for(int i=0; i<listOfImages.length;i++)
        {
            if(listOfImages[i].isFile())
            {
                String inputImagePath = "images"+ java.io.File.separator + "book" + java.io.File.separator + listOfImages[i].getName();
                String[] tokens = inputImagePath.split("\\.(?=[^\\.]+$)");
                String extension = tokens[1];
                String outputImageBasePath = tokens[0];
                BufferedImage bookImage = ImageLibrary.loadGrayscaleImage(new File(inputImagePath));
                //System.out.println(outputImageBasePath+"-ArithmeticMeanFilter."+extension);
                
                //Perform all of the filters on each image.
                ImageIO.write(ImageLibrary.arithmeticMeanFilter(bookImage, 3, 3, "zero"), extension, new File(outputImageBasePath+"-2ArithmeticMeanFilter."+extension));
                ImageIO.write(ImageLibrary.geometricMeanFilter(bookImage, 3, 3, "zero"), extension, new File(outputImageBasePath+"-3GeometricMeanFilter."+extension));
                ImageIO.write(ImageLibrary.harmonicMeanFilter(bookImage, 3, 3, "zero"), extension, new File(outputImageBasePath+"-4HarmonicMeanFilter."+extension));
                ImageIO.write(ImageLibrary.contraharmonicMeanFilter(bookImage, 3, 3, 3, "zero"), extension, new File(outputImageBasePath+"-5ContraharmonicMeanFilter."+extension));
                ImageIO.write(ImageLibrary.maxFilter(bookImage, 3, 3, "zero"), extension, new File(outputImageBasePath+"-6MaxFilter."+extension));
                ImageIO.write(ImageLibrary.minFilter(bookImage, 3, 3, "zero"), extension, new File(outputImageBasePath+"-7MinFilter."+extension));
                ImageIO.write(ImageLibrary.midpointFilter(bookImage, 3, 3, "zero"), extension, new File(outputImageBasePath+"-8MidpointFilter."+extension));
                ImageIO.write(ImageLibrary.alphaTrimmedMeanFilter(bookImage, 3,3, 4, "zero"), extension, new File(outputImageBasePath+"-9AlphaTrimmedMeanFilter."+extension));
            }
        }
    }
}
