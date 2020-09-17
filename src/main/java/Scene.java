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
}
