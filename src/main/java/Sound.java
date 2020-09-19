import javax.sound.sampled.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class Sound {

    private static Queue<Clip> clipQ = new LinkedList<Clip>();

    public static void playTrack(String filepath, boolean infinite_loop, int numloops){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(filepath));
            Clip c=AudioSystem.getClip();
            c.open(ais);
            if(infinite_loop){
                c.loop(Clip.LOOP_CONTINUOUSLY);
            }else{
                c.loop(numloops);
            }
            clipQ.add(c);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void stopPlayback(){
        clipQ.remove().stop();
    }
    public static void stopAllPlayback(){
        while(!clipQ.isEmpty()){
            clipQ.remove().stop();
        }
    }

    //todo implement realtime playback

}

