package moyai.content;

import moyai.world.blocks.*;

public class MBlocks{
    public static RockBlock rockBlock;

    public static void load(){
        rockBlock = new RockBlock("rock-block", MUnitTypes.moyai);
    }
}
