package moyai;

import arc.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import moyai.content.*;
import moyai.gen.*;
import moyai.graphics.*;
import moyai.util.*;

public class Moyai extends Mod{
    public static VineBoomRenderer mRenderer;

    public Moyai(){
        Events.on(FileTreeInitEvent.class, e -> Core.app.post(MSounds::load));
    }

    @Override
    public void init(){
        mRenderer = new VineBoomRenderer();

        if(!Vars.headless){
            MSettings.init();
        }
    }

    @Override
    public void loadContent(){
        EntityRegistry.register();
        MUnitTypes.load();
        MBlocks.load();
    }
}
