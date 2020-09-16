import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Input implements KeyListener {
    static int checkPushFlag=-1;
    static int checkRelFlag=-1;
    static int checkHitFlag=-1;

    public void keyTyped(KeyEvent e){
        checkHitFlag=e.getKeyCode();
    }
    public void keyPressed(KeyEvent e){
        checkPushFlag=e.getKeyCode();
    }
    public void keyReleased(KeyEvent e){
        checkRelFlag=e.getKeyCode();
    }
}
