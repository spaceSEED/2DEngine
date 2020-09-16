import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Display {

        public static JFrame frame;
        //public static JPanel panel;
        Graphics graphics;
        int viewBounds[];//top-left (x,y); bottom-right (x,y)
        int wid, hei;

        public Display(int wid, int hei){
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

            load(0);
            frame.setVisible(true);
            graphics = frame.getGraphics();
        }

        public void load(int scene){
            Scene cur=Main.levels[scene];
            Main.BackgroundLayers=cur.getBGLayers();
            Main.ForegroundLayers=cur.getFGLayers();
            Main.spriteSheet=cur.getSpriteSheet();

        }

        public void update(){
            sequentialParse();//todo replace with better method
            //imageDraw();

            frame.update(graphics);
        }

        public void sequentialParse(){//Update by sequentially updating each pixel; slowest method
            for(int x=0;x<wid;x++){
                for(int y=0;y<hei;y++){
                    Color curColor=new Color(0,0,0,0);
                    int rgba=0;//todo use the 'alpha' value properly
                    for(int i=Main.FGL-1;i>=0;i--){
                        int rgbat=Main.ForegroundLayers[i].getRGBA(x,y);
                        if(rgbat<0){//||rgba[3]!=0){
                            rgba=rgbat;
                        }
                    }
                    //todo check for sprite + color
                    /*if(r==0&&g==0&&b==0&&a==0) {
                            int rgba[] = Main.spriteSheet
                            if (rgba[0] != 0 || rgba[1] != 0 || rgba[2] != 0 || rgba[3] != 0) {
                                r = rgba[0];
                                g = rgba[1];
                                b = rgba[2];
                                a = rgba[3];
                            }
                    }*/
                    if(rgba==0) {
                        for (int i = Main.BGL-1; i >=0 ; i--) {
                            int rgbat = Main.BackgroundLayers[i].getRGBA(x, y);
                            if (rgbat<0) {
                                rgba=rgbat;
                            }
                        }
                    }
                    if(rgba!=0) {
                        curColor = new Color(rgba);
                    }
                    graphics.setColor(curColor);
                    graphics.fillRect(x,y,1,1);
                }
            }
        }

        public void imageDraw(){//uses image draw methods
            for(int i=0;i<Main.BGL;i++){
                if(Main.BackgroundLayers[i].art!=null) {
                    graphics.drawImage(Main.BackgroundLayers[i].art, 0, 0, null);
                }
            }
            //todo handle sprites
            for(int i=0;i<Main.FGL;i++){
                if(Main.ForegroundLayers[i].art!=null) {
                    graphics.drawImage(Main.ForegroundLayers[i].art, 0, 0, null);
                }
            }
        }

        public void scroll(int x, int y){
            viewBounds[0]+=x;
            viewBounds[1]+=y;
            viewBounds[2]+=x;
            viewBounds[3]+=y;
            //todo handle layer scrolling (parallax)
            graphics=graphics.create(-x,-y,wid,hei);
        }
}
