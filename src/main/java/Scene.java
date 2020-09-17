import com.sun.istack.internal.Nullable;

import java.awt.image.BufferedImage;

public class Scene {
    Layer BackgroundLayers[];//0 is top layer
    Layer ForegroundLayers[];//0 is top layer
    Sprite spriteList[];

    public Scene(int bgl, int fgl, int spriteN){
        BackgroundLayers = new Layer[bgl];
        ForegroundLayers = new Layer[fgl];
        spriteList = new Sprite[spriteN];

    }

    public void setBGLayer(int i,Layer l) {
        BackgroundLayers[i] = l;
    }
    public void setFGLayer(int i,Layer l) {
        ForegroundLayers[i] = l;
    }

    public Layer[] getBGLayers(){
        return BackgroundLayers;
    }
    public Layer[] getFGLayers(){
        return ForegroundLayers;
    }

    public void setSprite(int i, Sprite s){
        if(i<spriteList.length&&i>=0){
            spriteList[i]=s;
        }
    }
    public void setSpriteImage(int i, @Nullable String path, @Nullable BufferedImage bi, boolean animate){
        if(i>=0&&i<spriteList.length&&spriteList[i]!=null){
            if(bi==null) {
                spriteList[i].setCurImage(path, animate);
            }else if(path==null){
                spriteList[i].setCurImage(bi, animate);
            }
        }
    }

    public Sprite[] getSprites(){
        return spriteList;
    }

    public boolean checkSpriteCollision(int i, int j){//check 2 sprites against each other
        Sprite cur=spriteList[i];
        Sprite t = spriteList[j];
        if(t!=null&&cur!=null&&inside(cur.startx,cur.starty,cur.endx,cur.endy,t.startx,t.starty,t.endx,t.endy)){
            return true;
        }
        return false;

    }
    public boolean checkSpriteCollision(int i){//use location of sprite in list (1 vs all)
        Sprite cur=spriteList[i];
        for(int j=0;j<spriteList.length;j++){
            if(j!=i) {
                Sprite t = spriteList[j];
                if(t!=null&&cur!=null&&inside(cur.startx,cur.starty,cur.endx,cur.endy,t.startx,t.starty,t.endx,t.endy)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean checkSpriteCollision(){//use if needed. this is O(n^2), the others are faster and more focused
        for(int i=0;i<spriteList.length;i++){
            Sprite cur=spriteList[i];
            for(int j=0;j<spriteList.length;j++){
                if(j!=i) {
                    Sprite t = spriteList[j];
                    if(t!=null&&cur!=null&&inside(cur.startx,cur.starty,cur.endx,cur.endy,t.startx,t.starty,t.endx,t.endy)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean inside(int sx1, int sy1, int ex1, int ey1, int sx2, int sy2, int ex2, int ey2){
        if(sx2>=sx1&&sx2<=ex1&&sy2>=sy1&&sy2<=ey1){
            return true;
        }else if(ex2>=sx1&&ex2<=ex1&&ey2>=sy1&&ey2<=ey1) {
            return true;
        }else if(sx2>=sx1&&sx2<=ex1&&ey2>=sy1&&ey2<=ey1) {
            return true;
        }else if(ex2>=sx1&&ex2<=ex1&&sy2>=sy1&&sy2<=ey1) {
            return true;
        }
        return false;
    }
}
