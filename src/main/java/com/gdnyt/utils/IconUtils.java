package com.gdnyt.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class IconUtils {
	/** 
     * @param path 
     *            - the path used to create the buffered image. 
     * @return an BufferedImage object, or <code>null</code> if the given path 
     *         is not valid or an error occurs during reading. 
     */  
    public  BufferedImage createImage(String path) {  
        // TODO Auto-generated method stub  
        URL imageURL = getClass().getResource(path);  
        if (imageURL != null) {  
            try {  
                return ImageIO.read(imageURL);  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                System.err.println("an error occurs during reading.");  
                e.printStackTrace();  
                return null;  
            }  
        } else {  
            System.err.println("Couldn't find file: " + path);  
            return null;  
        }  
    }  
  
    /** 
     * It is for the image icon that needs a description for the visually 
     * impaired user. 
     * <p> 
     * Create an icon from an original image, which has normally a bigger size. 
     *  
     * @param image 
     *            - the original image to be converted to icon 
     * @param width 
     *            - the created icon width 
     * @param height 
     *            - the created icon height 
     * @param desc 
     *            - the description for created icon, which would allow 
     *            assistive technologies to help visually impaired user 
     *            understand what information the icon conveys. 
     * @return an Icon object 
     */  
    public Icon createIcon(Image image, int width, int height, String desc) {  
        // TODO Auto-generated method stub  
        BufferedImage iconImage = new BufferedImage(width, height,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics2D g2 = iconImage.createGraphics();  
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,  
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g2.drawImage(image, 0, 0, width, height, null);  
        g2.dispose();  
        return new ImageIcon(iconImage, desc);  
    }  
  
    /** 
     * It is for the image icon without the description. 
     * <p> 
     * Create an icon from an original image, which has normally a bigger size. 
     *  
     * @param image 
     *            - the original image to be converted to icon 
     * @param width 
     *            - the created icon width 
     * @param height 
     *            - the created icon height 
     * @return an Icon object 
     */  
    public Icon createIcon(Image image, int width, int height) {  
        // TODO Auto-generated method stub  
        BufferedImage iconImage = new BufferedImage(width, height,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics2D g2 = iconImage.createGraphics();  
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,  
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g2.drawImage(image, 0, 0, width, height, null);  
        g2.dispose();  
        return new ImageIcon(iconImage);  
    }  
    
    public Icon createIcon(String filename,int width,int height){
    	BufferedImage image = createImage(filename);  
        Icon buttonIcon = createIcon(image, width, height);  
        return buttonIcon;
    }
    
  
  
}
