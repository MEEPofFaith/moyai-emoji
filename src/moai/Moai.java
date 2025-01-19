package moai;

import mindustry.mod.*;
import moai.content.*;
import moai.gen.*;
import moai.graphics.*;

import static mindustry.Vars.headless;

public class Moai extends Mod{
    public static VineBoomRenderer mRenderer;

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
