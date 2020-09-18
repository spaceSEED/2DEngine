import java.awt.event.*;
import java.util.*;

public class Input implements KeyListener {//todo multi-input
    static Set<Integer> checkPushFlag=new TreeSet<Integer>();
    static Queue<Character> typedChar=new LinkedList<Character>();//queue of typed characters for use where needed

    public void keyTyped(KeyEvent e){//for use with info input
        if(Main.typing) {
            typedChar.add(e.getKeyChar());
        }
    }
    public void keyPressed(KeyEvent e){
        checkPushFlag.add(e.getKeyCode());
    }
    public void keyReleased(KeyEvent e){
        checkPushFlag.remove(e.getKeyCode());
    }
}
