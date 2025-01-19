package moai;

import arc.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import moai.content.*;
import moai.gen.*;
import moai.graphics.*;

import static mindustry.Vars.headless;

public class Moai extends Mod{
    public static VineBoomRenderer mRenderer;

    public Moai(){
        Events.on(FileTreeInitEvent.class, e -> Core.app.post(MSounds::load));
    }

    @Override
    public void init(){
        mRenderer = new VineBoomRenderer();
    }

    @Override
    public void loadContent(){
        EntityRegistry.register();
        MUnitTypes.load();
    }
}
