import javax.swing.*;
import java.awt.*;
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

            viewBounds[0]=0;viewBounds[1]=0;viewBounds[2]=wid;viewBounds[3]=hei;

            frame.setBounds(0,0,wid,hei);
            frame.setFocusable(true);
            frame.requestFocusInWindow();
            frame.addKeyListener(new Input());

            load(0);
            frame.setVisible(true);
            graphics =frame.getGraphics();
        }

        public void load(int scene){
            Scene cur=Main.levels[scene];
            Main.BackgroundLayers=cur.getBGLayers();
            Main.ForegroundLayers=cur.getFGLayers();
            Main.spriteSheet=cur.getSpriteSheet();

        }

        public void update(){
            sequentialParse();//todo replace with better method
            frame.update(graphics);
        }

        public void sequentialParse(){//Update by sequentially updating each pixel; slowest method
            for(int x=0;x<wid;x++){
                for(int y=0;y<hei;y++){
                    int r=-1,g=-1,b=-1,a=-1;//todo use the 'alpha' value properly
                    for(int i=0;i<Main.FGL;i++){
                        int rgba[]=Main.ForegroundLayers[i].getRGBA(x,y);
                        if(rgba[0]!=-1||rgba[3]!=0){
                            r=rgba[0];
                            g=rgba[1];
                            b=rgba[2];
                            a=rgba[3];
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
                    if(r==0&&g==0&&b==0&&a==0) {
                        for (int i = 0; i < Main.BGL; i++) {
                            int rgba[] = Main.BackgroundLayers[i].getRGBA(x, y);
                            if (rgba[0]!=-1|| rgba[3] != 0) {
                                r = rgba[0];
                                g = rgba[1];
                                b = rgba[2];
                                a = rgba[3];
                            }
                        }
                    }
                    if(r==-1){
                        r=0;g=0;b=0;a=0;
                    }
                    graphics.setColor(new Color(r,g,b,a));
                    graphics.fillRect(x,y,1,1);
                }
            }
        }
}
