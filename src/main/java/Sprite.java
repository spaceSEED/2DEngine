import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.io.File;
import java.nio.Buffer;

public class Sprite {//todo
    int startx, starty, endx, endy;//screen space used

    boolean animated=false;
    long animationQuantum=0;//time til next frame
    BufferedImage[] frames;//animation frames
    int curFrame=0;

    BufferedImage spriteImage;//full sprite image


    public Sprite(int sx, int sy, int ex, int ey){
        startx=sx;starty=sy;
        endx=ex;endy=ey;
    }

    public void setCurImage(BufferedImage i, boolean animate){//todo getFrames for animated sprites
        animated=animate;
        spriteImage=adjustImage(i);
    }
    public void setCurImage(String path, boolean animate){
        animated=animate;
        try {
            spriteImage= ImageIO.read(new File(path));
            spriteImage=adjustImage(spriteImage);
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }

    public void moveSpriteBy(int x, int y){
        startx+=x;
        starty+=y;
        endx+=x;
        endy+=y;
    }
    public void moveSpriteTo(int x, int y){
        int w=endx-startx;
        int h=endy-starty;
        startx=x;
        starty=y;
        endx=x+w;
        endy=y+h;
    }
    public void resizeSprite(int sx, int sy, int ex, int ey){
        startx=sx;
        starty=sy;
        endy=ey;
        endx=ex;
    }
    public void resizeSprite(int wid, int hei){
        endx=startx+wid;
        endy=starty+hei;
    }

    private BufferedImage adjustImage(BufferedImage im){
        BufferedImage bi=new BufferedImage((endx-startx),(endy-starty),BufferedImage.TYPE_INT_ARGB);
        Graphics2D tempG=bi.createGraphics();
        double wid_ratio=(double)im.getWidth()/(double)(endx-startx);
        double hei_ratio=(double)im.getHeight()/(double)(endy-starty);
        tempG.drawImage(im, new BIOP(wid_ratio,hei_ratio), 0, 0);
        return bi;
    }

    private static long called=0;
    public BufferedImage getSprite(){//return a rescaled image on the proper animation frame
        if(animated){
            if(called>=animationQuantum){
                called=0;
                if(++curFrame>=frames.length){
                    curFrame=0;
                }
            }
            return frames[curFrame];
        }else{
            return spriteImage;
        }
    }

    private BufferedImage[] findFrames(BufferedImage bi){
        BufferedImage frames[];
        //todo return an array of all the animation frames for the sprite
        return null;
    }

}
