import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;

public class Rotate implements BufferedImageOp {
    double dif=0;
    double cx, cy;
    public Rotate(double dif){
        this.dif=dif;
    }

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if(dest==null){
            //int diag=(int)Math.sqrt(Math.pow(src.getWidth(),2)+Math.pow(src.getHeight(),2));
            //dest=new BufferedImage(diag,diag,src.getType());
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
        Point2D c=new Point2D() {
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
        cx=src.getWidth()/2;
        cy=src.getHeight()/2;
        for(int x=0;x<src.getWidth();x++){
            for(int y = 0; y<src.getHeight(); y++){
                s.setLocation(x,y);
                Point2D d=getPoint2D(s,null);
                g2d.setColor(new Color(src.getRGB(x,y)));
                g2d.fillRect((int)d.getX(),(int)d.getY(),1,1);
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
        double x = srcPt.getX()-cx;
        double y = srcPt.getY()-cy;
        double cos=Math.cos(dif);
        double sin=Math.sin(dif);
            double a=x*cos-y*-sin;
            double b=x*sin-y*cos;
            x=a+cx;
            y=b+cy;
        dstPt.setLocation(x,y);
        return dstPt;
    }

    @Override
    public RenderingHints getRenderingHints() {
        return null;
    }
}
