import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;
import java.util.TreeSet;

public class MouseInput implements MouseListener {//todo implement
    boolean mouseInFrame=false;
     static Point mouseActionPos;
     static Set<Integer> mouseAction=new TreeSet<Integer>();

    public void mouseClicked(MouseEvent e) {
        if(mouseInFrame&&e.getButton()==MouseEvent.BUTTON1) {//edit to your discretion
            mouseActionPos = e.getPoint();
        }

    }

    public void mousePressed(MouseEvent e) {
        mouseAction.add(e.getButton());
    }

    public void mouseReleased(MouseEvent e) {
        mouseAction.remove(e.getButton());
    }

    public void mouseEntered(MouseEvent e) {
        mouseInFrame=true;
    }

    public void mouseExited(MouseEvent e) {
        mouseInFrame=false;
    }
}
