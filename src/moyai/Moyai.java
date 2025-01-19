package moyai;

import arc.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import moyai.content.*;
import moyai.gen.*;
import moyai.graphics.*;

public class Moyai extends Mod{
    public static VineBoomRenderer mRenderer;

    public Moyai(){
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
