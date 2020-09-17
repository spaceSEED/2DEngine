import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.util.Map;
import java.util.TreeMap;

public class BIOP implements BufferedImageOp {
    double wid_ratio,hei_ratio;
    int draw_wid,draw_hei;
    public BIOP(double wid_ratio, double hei_ratio){
        this.wid_ratio=wid_ratio;
        this.hei_ratio=hei_ratio;
        if(wid_ratio>=1){
            draw_wid=1;
        }else{
            draw_wid=(int)(1/wid_ratio);
            if((1/wid_ratio)>draw_wid){
                draw_wid+=1;
            }

        }
        if(hei_ratio>=1){
            draw_hei=1;
        }else{
            draw_hei=(int)(1/hei_ratio);
            if((1/hei_ratio)>draw_hei){
                draw_hei+=1;
            }
        }
    }


    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest){
        if(dest==null){
            dest=new BufferedImage((int)(src.getWidth()),(int)(src.getHeight()),src.getType());
        }else if(src.getType()!=dest.getType()){
            dest=new BufferedImage(dest.getWidth(), dest.getHeight(),src.getType());
        }
        Graphics2D g2d=dest.createGraphics();
        Point2D s=new Point2D() {
            double x,y;
            @Override
            public double getX() {
                return x;
            }

            @Override
            public double getY() {
                return y;
            }

            @Override
            public void setLocation(double x, double y) {
                this.x=x;this.y=y;
            }
        };
        for(int x=0;x<src.getWidth();x++){
            for(int y = 0; y<src.getHeight(); y++){
                s.setLocation(x,y);
                Point2D d=getPoint2D(s,null);
                g2d.setColor(new Color(src.getRGB(x,y)));
                g2d.fillRect((int)d.getX(),(int)d.getY(),draw_wid,draw_hei);
            }
        }

        return dest;
    }

    @Override
    public Rectangle2D getBounds2D(BufferedImage src) {
        return src.getGraphics().getClipBounds();
    }

    @Override
    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
        if(destCM==null){
            destCM=src.getColorModel();
        }
        return new BufferedImage(src.getWidth(),src.getHeight(),src.getType(),(IndexColorModel)destCM);
    }

    @Override
    public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        if(dstPt==null){
            dstPt=new Point2D() {
                double x,y;
                @Override
                public double getX() {
                    return x;
                }

                @Override
                public double getY() {
                    return y;
                }

                @Override
                public void setLocation(double x, double y) {
                    this.x=x;
                    this.y=y;
                }
            };
        }
        double x=srcPt.getX()/wid_ratio;
        double y=srcPt.getY()/hei_ratio;
        dstPt.setLocation(x,y);
        return dstPt;
    }

    @Override
    public RenderingHints getRenderingHints() {//todo maybe find a way to manipulate these settings with commands
       /* Map<RenderingHints.Key,Object> map=new TreeMap<RenderingHints.Key,Object>();
        map.put(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_DEFAULT);
        map.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_DEFAULT);
        return new RenderingHints(map);*/
       return null;
    }
}
