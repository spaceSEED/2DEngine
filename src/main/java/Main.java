import java.awt.event.KeyEvent;

public class Main {
    static String GameName="Title";
    static int BGL=5;
    static int FGL=3;
    static int scenes=1;
    static String assetPath="assets\\";

    static boolean typing=false;



    public static Scene levels[];
    public static int pos[];//top left window corner position
    static Display window;

    public static void main(String[] args){
        pos=new int[2];
        pos[0]=0;pos[1]=0;
        levels=new Scene[scenes];
        for(int i=0;i<scenes;i++){//Fill all preloaded scenes
            levels[i] = new Scene(BGL,FGL,50);
            for(int j=0;j<BGL;j++){
                levels[i].setBGLayer(j,new Layer(/*filepath?*/));
            }
            for(int j=0;j<FGL;j++){
                levels[i].setFGLayer(j,new Layer(/*filepath?*/));
            }
        }

        ///////////test code//////////////////
        levels[0].setBGLayer(0,new Layer(assetPath+"foxtrot.jpg"));//test
        levels[0].setFGLayer(0,new Layer(assetPath+"strawberry.jpg"));//
        levels[0].getFGLayers()[0].setPos(25,25);
        //levels[0].getBGLayers()[0].setScroll(2,1);
        levels[0].setSprite(0,new Sprite(100,100,140,140));//test
        levels[0].setSpriteImage(0,assetPath+"strawberry.jpg",null,false);//test

        /////////////test code/////////////////


        window=new Display(640,480);
        //window.setFittedLayers(true,false);//sets background and foreground layers to readjust to fit resolution
        window.displayText("Hello World!",100,200);

        while(true){
            window.update();
            run();
        }
    }


static private long actionCntr=0;//for use if action updates are too frequent/fast

    public static void run(){//gamelogic here
        if(actionCntr>=10&&((!Input.checkPushFlag.isEmpty())||MouseInput.mouseActionPos!=null||(!MouseInput.mouseAction.isEmpty()))){
            //action if a given key is pushed (use KeyEvent.VK_?) or mouse event (MouseEvent.?)
            if(Input.checkPushFlag.contains(KeyEvent.VK_RIGHT)){//movement example
                window.scroll(1,0);
                levels[0].getSprites()[0].moveSpriteBy(2,0);
            }
            if(Input.checkPushFlag.contains(KeyEvent.VK_DOWN)){//movement example
                window.scroll(0,1);
                levels[0].getSprites()[0].moveSpriteBy(0,2);
            }
            if(Input.checkPushFlag.contains(KeyEvent.VK_SPACE)){
                window.clearText();
            }
            if(MouseInput.mouseActionPos!=null){//mouse position use example
                levels[0].getSprites()[0].moveSpriteToInScreen(MouseInput.mouseActionPos.x,MouseInput.mouseActionPos.y);
                MouseInput.mouseActionPos=null;
            }

            actionCntr=0;
        }


    actionCntr++;
    }



}
