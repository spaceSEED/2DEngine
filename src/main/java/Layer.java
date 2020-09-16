import javax.imageio.*;
import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.awt.*;
import java.awt.image.*;

public class Layer {
    public boolean isStatic=true;//set ability to scroll/image image scaling
    public BufferedImage art;
    public double scroll_v=0.0;//scroll velocity relative to the screen

    public Layer(){

    }
    public Layer(BufferedImage a){
        art=a;
    }
    public Layer(String filepath) {
        try {
            art= ImageIO.read(new File(filepath));
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void setStatic(boolean a){
        isStatic=a;
    }
    public boolean getStatic(){
        return isStatic;
    }

    public void setImage(BufferedImage a){
        art=a;
    }
    public void setImageFromFile(String filepath){
        try {
            art= ImageIO.read(new File(filepath));
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }

    public void setScroll(double a){
        scroll_v=a;
    }
    public int[] getRGBA(int x, int y){
        int rgbArray[]=new int[4];
        if(art==null){
            rgbArray[0]=-1;
        }else {
            art.getRGB(x, y, 1, 1, rgbArray, 0, 1);
        }
        return rgbArray;
    }


}
