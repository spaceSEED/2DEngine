import javax.imageio.*;
import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.awt.*;
import java.awt.image.*;

public class Layer {

    public boolean wrapAround=false;//set ability to wrap back around to its beginning
    private BufferedImage fittedArt;
    public BufferedImage art;
    int[] pos=new int[2];
    int scroll_vx=0;//scroll velocity relative to the screen 0==static
    int scroll_vy=0;

    public Layer(){
        pos[0]=0;pos[1]=0;
    }
    public Layer(BufferedImage a){
        pos[0]=0;pos[1]=0;
        art=a;
    }
    public Layer(String filepath) {
        pos[0]=0;pos[1]=0;
        try {
            art= ImageIO.read(new File(filepath));
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void setWrappable(boolean a){
        wrapAround=a;
    }
    public boolean getWrappable(){
        return wrapAround;
    }

    public BufferedImage getFittedImage(int wid, int hei){
        if(art==null){return null;}
        if(fittedArt==null){
            fittedArt=new BufferedImage(wid,hei,BufferedImage.TYPE_INT_ARGB);
            Graphics2D tempG=fittedArt.createGraphics();
            double wid_ratio=(double)art.getWidth()/(double)wid;
            double hei_ratio=(double)art.getHeight()/(double)hei;
            tempG.drawImage(art, new BIOP(wid_ratio,hei_ratio), 0, 0);
        }else if(fittedArt.getWidth()!=wid||fittedArt.getHeight()!=hei){
            fittedArt=new BufferedImage(wid,hei,BufferedImage.TYPE_INT_ARGB);
            Graphics2D tempG=fittedArt.createGraphics();
            double wid_ratio=(double)art.getWidth()/(double)wid;
            double hei_ratio=(double)art.getHeight()/(double)hei;
            tempG.drawImage(art, new BIOP(wid_ratio,hei_ratio), 0, 0);
        }
        return fittedArt;
    }

    public void setImage(BufferedImage a){
        art=a;
    }
    public void setImage(String filepath){
        try {
            art= ImageIO.read(new File(filepath));
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }

    public void setScroll(int x, int y){
        scroll_vx=x;
        scroll_vy=y;
    }

    public int getRGBA(int x, int y){//return 0 if pixel doesn't exist
        int rgba=0;
        if(art==null||y>=art.getHeight()||x>=art.getWidth()){
            rgba=0;
        }else {
            rgba=art.getRGB(x, y);
        }
        return rgba;
    }
    public int getFittedRGBA(int x, int y){
        int rgba=0;
        if(fittedArt==null||y>=fittedArt.getHeight()||x>=fittedArt.getWidth()){
            rgba=0;
        }else {
            rgba=fittedArt.getRGB(x, y);
        }
        return rgba;
    }

    public void setPos(int x, int y){
        pos[0]=x;pos[1]=y;
    }
    public void setPos(int[] coor){
        pos[0]=coor[0];pos[1]=coor[1];
    }
    public int[] getPos(){
        return pos;
    }
    public void translate(int x, int y){
        pos[0]+=x;
        pos[1]+=y;
    }
    public void translateP(int x, int y){
        if(x!=0) {
            pos[0] += (x - scroll_vx);
        }
        if(y!=0) {
            pos[1] += (y - scroll_vy);
        }
    }
}
