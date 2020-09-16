public class Scene {
    Layer BackgroundLayers[];//0 is top layer
    Layer ForegroundLayers[];//0 is top layer
    Sprite spriteSheet[][];

    public Scene(int bgl, int fgl, int gridw, int gridh){
        BackgroundLayers = new Layer[bgl];
        ForegroundLayers = new Layer[fgl];
        spriteSheet = new Sprite[gridw][gridh];

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

    public void setSprite(int x, int y, Sprite s){
        spriteSheet[x][y]=s;
    }

    public Sprite[][] getSpriteSheet(){
        return spriteSheet;
    }
}
