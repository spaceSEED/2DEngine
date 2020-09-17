import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Sprite {//todo
    int startx, starty, endx, endy;//screen space used

    boolean animated=false;
    long animationQuantum=0;//time til next frame
    BufferedImage curFrame;//current frame (or last animation frame)

    BufferedImage spriteImage;//full sprite image


    public Sprite(int sx, int sy, int ex, int ey){
        startx=sx;starty=sy;
        endx=ex;endy=ey;
    }

    public void setCurImage(BufferedImage i, boolean animate){
        animated=animate;
        spriteImage=i;
    }
    public void setCurImage(String path, boolean animate){
        animated=animate;
        try {
            spriteImage= ImageIO.read(new File(path));
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }

    public BufferedImage getSprite(){//return a rescaled image on the proper animation frame
        BufferedImage bi =new BufferedImage((endx-startx),(endy-starty),BufferedImage.TYPE_INT_ARGB);
        //todo get scaled true-current animation frame
        return bi;
    }

    private BufferedImage[] findFrames(BufferedImage bi){
        BufferedImage frames[];
        //todo return an array of all the animation frames for the sprite
        return null;
    }

}
