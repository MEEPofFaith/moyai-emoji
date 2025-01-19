package moai.content;

import arc.audio.*;
import mindustry.*;

public class MSounds{
    public static Sound
        scrape = new Sound(),
        boom = new Sound();

    public static void load(){
        if(Vars.headless) return;

        scrape = Vars.tree.loadSound("scrape");
        boom = Vars.tree.loadSound("vine-boom");
    }
}
