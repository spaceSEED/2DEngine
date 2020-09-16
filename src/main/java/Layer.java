import java.awt.*;

public class Layer {
    public boolean isStatic=true;//set ability to scroll/image image scaling
    public Image art;
    public double scroll_v=0.0;//scroll velocity relative to the screen

    public Layer(){

    }
    public Layer(Image a){
        art=a;
    }
    public Layer(String s){

    }

    public void setStatic(boolean a){
        isStatic=a;
    }
    public boolean getStatic(){
        return isStatic;
    }

    public void setImage(Image a){
        art=a;
    }
    public void setScroll(double a){
        scroll_v=a;
    }
    public int[] getRGBA(int x, int y){
        //TODO get RGBA from image
        return new int[4];
    }


}
