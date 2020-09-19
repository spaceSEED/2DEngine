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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
        if(animate){
            frames=findFrames(spriteImage);
        }
    }
    public void setCurImage(String path, boolean animate){
        animated=animate;
        try {
            spriteImage= ImageIO.read(new File(path));
            spriteImage=adjustImage(spriteImage);
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
        if(animate){
            frames=findFrames(spriteImage);
        }
    }

    public void moveSpriteBy(int x, int y){
        startx+=x;
        starty+=y;
        endx+=x;
        endy+=y;
    }
    public void moveSpriteTo(int x, int y){//move sprite to position
        int w=endx-startx;
        int h=endy-starty;
        startx=x;
        starty=y;
        endx=x+w;
        endy=y+h;
    }
    public void moveSpriteToInScreen(int x, int y){//moves sprtie to a position within screen space offset
        int w=endx-startx;
        int h=endy-starty;
        startx=x+Display.viewBounds[0];
        starty=y+Display.viewBounds[1];
        endx=x+w;
        endy=y+h;
    }

    public void resizeSprite(int sx, int sy, int ex, int ey){
        startx=sx;
        starty=sy;
        endy=ey;
        endx=ex;
        spriteImage=adjustImage(spriteImage);
    }
    public void resizeSprite(int wid, int hei){
        endx=startx+wid;
        endy=starty+hei;
        spriteImage=adjustImage(spriteImage);
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
    public int getRGB(int x, int y){
        if(x>endx||y>endy||x<startx||y<starty){
            return 0;
        }
        int rgba=0;
        BufferedImage art=this.getSprite();
        if(art==null||y-starty>=art.getHeight()||x-startx>=art.getWidth()){
            rgba=0;
        }else {
            rgba=art.getRGB(x-startx, y-starty);
        }
        return rgba;
    }

    public void setFrames(BufferedImage[] bi){//recommended to maintain frame accuracy
        for(int i=0;i<bi.length;i++){
            bi[i]=adjustImage(bi[i]);
        }
        frames=bi;
    }
    public void setAnimated(boolean a){
        animated=a;
    }
    public boolean getAnimated(){
        return animated;
    }
    public long getAnimationQuantum() {
        return animationQuantum;
    }
    public void setAnimationQuantum(long animationQuantum) {
        this.animationQuantum = animationQuantum;
    }

    private BufferedImage[] findFrames(BufferedImage bi){//todo get this working
        Integer emptySpace=findEmptySpace(bi);
        ArrayList<BufferedImage> frames=new ArrayList<BufferedImage>();

        boolean hori=true;
        int last_hori=0;
        for(int i=0;i<bi.getHeight();i++){
            for(int j=0;j<bi.getWidth();j++){
                if(bi.getRGB(j,i)!=emptySpace){
                    hori=false;
                    break;
                }
            }
            if(hori){
                boolean vert=true;
                int last_vert=0;
                for(int a=0;a<bi.getWidth();a++){
                    for(int b=last_hori;b<i;b++){
                        if(bi.getRGB(a,b)!=emptySpace){
                            vert=false;
                            break;
                        }
                    }
                    if(vert){
                        if(i!=last_hori&&a!=last_vert) {
                            frames.add(adjustImage(bi.getSubimage(last_vert, last_hori, (a - last_vert), i)));
                        }
                        last_vert=a;
                    }
                    vert=true;
                }
                last_hori=i;
            }
            hori=true;
        }

        BufferedImage[] f=new BufferedImage[frames.size()];
        return frames.toArray(f);
    }
    private Integer findEmptySpace(BufferedImage bi){//returns the color of the background/spritesheet empty space
        Map<Integer, Integer>space =new HashMap<Integer,Integer>();//Key: color, val: count

        for(int x=0;x<bi.getWidth();x++){
            if(space.containsKey(bi.getRGB(x,0))){
                space.put(bi.getRGB(x,0),space.get(bi.getRGB(x,0))+1);
            }else{
                space.put(bi.getRGB(x,0),1);
            }
            if(space.containsKey(bi.getRGB(x,bi.getHeight()-1))){
                space.put(bi.getRGB(x,bi.getHeight()-1),space.get(bi.getRGB(x,bi.getHeight()-1))+1);
            }else{
                space.put(bi.getRGB(x,bi.getHeight()-1),1);
            }
        }
        for(int y=0;y<bi.getHeight();y++){
            if(space.containsKey(bi.getRGB(0,y))){
                space.put(bi.getRGB(0,y),space.get(bi.getRGB(0,y))+1);
            }else{
                space.put(bi.getRGB(0,y),1);
            }
            if(space.containsKey(bi.getRGB(bi.getWidth()-1,y))){
                space.put(bi.getRGB(bi.getWidth()-1,y),space.get(bi.getRGB(bi.getWidth()-1,y))+1);
            }else{
                space.put(bi.getRGB(bi.getWidth()-1,y),1);
            }
        }
        Iterator<Integer> it=space.keySet().iterator();
        int lgst=0;
        Integer empty=null;
        while(it.hasNext()){
            Integer temp=it.next();
            if(space.get(temp)>lgst){
                empty=temp;
                lgst=space.get(temp);
            }
        }
        return empty;
    }


}
