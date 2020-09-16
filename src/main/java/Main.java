import java.awt.event.KeyEvent;

public class Main {
    static String GameName="Title";
    static int BGL=5;
    static int FGL=3;
    static int scenes=1;
    static String assetPath="assets\\";



    public static Sprite spriteSheet[][];//grid of sprites
    public static Layer BackgroundLayers[];//0 is top layer
    public static Layer ForegroundLayers[];//0 is top layer
    public static Scene levels[];
    public static int pos[];//top left window corner position
    static Display window;

    public static void main(String[] args){
        pos=new int[2];
        pos[0]=0;pos[1]=0;
        if(args.length>0) {
            BackgroundLayers = new Layer[Integer.parseInt(args[0])];
            ForegroundLayers = new Layer[Integer.parseInt(args[1])];
        }else{
            BackgroundLayers = new Layer[BGL];
            ForegroundLayers = new Layer[FGL];
        }
        levels=new Scene[scenes];
        for(int i=0;i<scenes;i++){//Fill all preloaded scenes
            levels[i] = new Scene(BGL,FGL,100,50);
            for(int j=0;j<BGL;j++){
                levels[i].setBGLayer(j,new Layer(/*filepath?*/));
            }
            for(int j=0;j<FGL;j++){
                levels[i].setFGLayer(j,new Layer(/*filepath?*/));
            }
        }
        levels[0].setBGLayer(0,new Layer(assetPath+"foxtrot.jpg"));//test
        levels[0].setFGLayer(0,new Layer(assetPath+"strawberry.jpg"));//test



        window=new Display(640,480);




        while(true){
            run();
            window.update();
        }
    }

    public static void run(){//gamelogic here
        if(Input.checkHitFlag!=-1){//action if a given key is hit (use KeyEvent.VK_?)

        }
        if(Input.checkPushFlag!=-1){//action if a given key is pushed (use KeyEvent.VK_?)
            if(Input.checkPushFlag==KeyEvent.VK_RIGHT){
                window.scroll(10,0);
            }
        }
        if(Input.checkRelFlag!=-1){//action if a given key is released (use KeyEvent.VK_?)

        }



        Input.checkPushFlag=-1;
        Input.checkRelFlag=-1;
        Input.checkHitFlag=-1;
    }



}
