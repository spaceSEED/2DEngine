import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class Display /*implements Runnable*/{

        public static JFrame frame;
        //public static JPanel panel;
        Graphics graphics;
        BufferedImage graphicsBuffer;//to prevent flickering
        Graphics2D gBg;//used to draw to the graphics buffer
        int viewBounds[];//top-left (x,y); bottom-right (x,y)
        int wid, hei;
        boolean fittedBG=false;
        boolean fittedFG=false;

        public Display(int wid, int hei){
            load(0);
            this.wid=wid;
            this.hei=hei;
            viewBounds=new int[4];
            frame=new JFrame(Main.GameName);
            //panel=new JPanel();
            //frame.getContentPane().add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            viewBounds[0]=Main.pos[0];viewBounds[1]=Main.pos[1];
            viewBounds[2]=Main.pos[0]+wid;viewBounds[3]=Main.pos[1]+hei;

            frame.setBounds(0,0,wid,hei);
            frame.setFocusable(true);
            frame.requestFocusInWindow();
            frame.addKeyListener(new Input());
            frame.addMouseListener(new MouseInput());


            frame.setVisible(true);
            graphics = frame.getGraphics();
            graphicsBuffer=new BufferedImage(frame.getWidth(),frame.getHeight(),BufferedImage.TYPE_INT_ARGB);
            gBg=graphicsBuffer.createGraphics();
        }

        public void load(int scene){
            Scene cur=Main.levels[scene];
            Main.BackgroundLayers=cur.getBGLayers();
            Main.ForegroundLayers=cur.getFGLayers();
            Main.spriteList=cur.getSprites();

        }

        public void update(){//pick a drawing method
                //sequentialParse();
                imageDraw();


                /*try {
                    //wait(2);
                } catch (Exception e) {
                }*/

                graphics.drawImage(graphicsBuffer,0,0,null);
        }

        public void sequentialParse(){//Update by sequentially updating each pixel; slowest method
            for(int x=0;x<wid;x++){//todo get static layers working
                for(int y=0;y<hei;y++){
                    Color curColor=new Color(0,0,0,0);
                    int rgba=0;//todo use the 'alpha' value properly
                    for(int i=Main.FGL-1;i>=0;i--){
                        int rgbat=0;
                        if(fittedFG){
                            Main.ForegroundLayers[i].getFittedImage(wid,hei);
                            rgbat=Main.ForegroundLayers[i].getFittedRGBA(x,y);
                        }else{
                            rgbat=Main.ForegroundLayers[i].getRGBA(x,y);
                        }

                        if(rgbat<0){//||rgba[3]!=0){
                            rgba=rgbat;
                        }
                    }
                    if(rgba==0){
                        for(int i=0;i<Main.spriteList.length;i++) {
                            if(Main.spriteList[i]!=null) {
                                int rgbat = Main.spriteList[i].getRGB(x, y);
                                if (rgbat < 0) {
                                    rgba = rgbat;
                                }
                            }
                        }
                    }
                    if(rgba==0) {
                        for (int i = Main.BGL-1; i >=0 ; i--) {
                            int rgbat=0;
                            if(fittedBG){
                                Main.BackgroundLayers[i].getFittedImage(wid,hei);
                                rgbat=Main.BackgroundLayers[i].getFittedRGBA(x,y);
                            }else{
                                rgbat=Main.BackgroundLayers[i].getRGBA(x,y);
                            }

                            if (rgbat<0) {
                                rgba=rgbat;
                            }
                        }
                    }
                    if(rgba!=0) {
                        curColor = new Color(rgba);
                    }
                    //graphics.setColor(curColor);
                    gBg.setColor(curColor);
                    //graphics.fillRect(x,y,1,1);
                    gBg.fillRect(x,y,1,1);
                }
            }
        }

        public void imageDraw(){//uses image draw methods
            for(int i=0;i<Main.BGL;i++){
                if(Main.BackgroundLayers[i].art!=null) {
                    //graphics.drawImage(Main.BackgroundLayers[i].art, 0, 0, null);
                    if(fittedBG){
                        BufferedImage temp=Main.BackgroundLayers[i].getFittedImage(wid,hei);
                        gBg.drawImage(temp, null, viewBounds[0], viewBounds[1]);
                    }else {
                        gBg.drawImage(Main.BackgroundLayers[i].art, null, Main.BackgroundLayers[i].getPos()[0], Main.BackgroundLayers[i].getPos()[1]);
                    }
                }
            }
            Sprite[] sl=Main.spriteList;
            for(int i=0;i<sl.length;i++){
                if(sl[i]!=null&&
                        ((sl[i].startx>=viewBounds[0]&&sl[i].startx<=viewBounds[2]&&sl[i].starty>=viewBounds[1]&&sl[i].starty<=viewBounds[3])
                ||(sl[i].endx>=viewBounds[0]&&sl[i].endx<=viewBounds[2]&&sl[i].endy>=viewBounds[1]&&sl[i].endy<=viewBounds[3]))){
                    gBg.drawImage(sl[i].getSprite(),null,sl[i].startx,sl[i].starty);
                }
            }
            for(int i=0;i<Main.FGL;i++){
                if(Main.ForegroundLayers[i].art!=null) {
                    //graphics.drawImage(Main.ForegroundLayers[i].art, 0, 0, null);
                    if(fittedFG){
                        BufferedImage temp=Main.ForegroundLayers[i].getFittedImage(wid,hei);
                        gBg.drawImage(temp,null,viewBounds[0],viewBounds[1]);
                    }else{
                        gBg.drawImage(Main.ForegroundLayers[i].art,null,Main.ForegroundLayers[i].getPos()[0],Main.ForegroundLayers[i].getPos()[1]);
                    }
                }
            }
        }


        public void scroll(int x, int y){
            viewBounds[0]+=x;
            viewBounds[1]+=y;
            viewBounds[2]+=x;
            viewBounds[3]+=y;

            for(int i=0;i<Main.BGL;i++){
                Main.BackgroundLayers[i].translateP(x,y);
            }
            for(int i=0;i<Main.FGL;i++){
                Main.ForegroundLayers[i].translateP(x,y);
            }

            gBg.translate(-x,-y);
        }

        public void setFittedLayers(boolean background, boolean foreground){
            fittedBG=background;
            fittedFG=foreground;
        }
}
