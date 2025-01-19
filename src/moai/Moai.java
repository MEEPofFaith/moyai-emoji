package moai;

import mindustry.mod.*;
import moai.gen.*;
import moai.graphics.*;

import static mindustry.Vars.headless;

public class Moai extends Mod{
    @Override
    public void init(){
        if(!headless){
            MShaders.init();
        }
    }

    @Override
    public void loadContent(){
        EntityRegistry.register();
    }
}
