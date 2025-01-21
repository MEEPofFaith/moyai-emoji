package moyai.content;

import mindustry.world.*;
import moyai.world.blocks.*;

public class MBlocks{
    public static Block rockBlock;

    public static void load(){
        rockBlock = new RockBlock("rock-block", MUnitTypes.moyai);
    }
}
