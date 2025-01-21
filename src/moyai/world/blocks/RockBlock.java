package moyai.world.blocks;

import arc.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import moyai.*;

public class RockBlock extends Block{
    public UnitType spawnUnit;
    public float boomIntensity = 0.2f,
        boomDuration = 60f;

    public RockBlock(String name, UnitType spawnUnit){
        super(name);
        this.spawnUnit = spawnUnit;

        requirements(Category.units, BuildVisibility.sandboxOnly, ItemStack.with());
        alwaysUnlocked = true;
        update = true;
        destructible = true;
        createRubble = false;
        destroyEffect = Fx.none;
    }

    public RockBlock(String name){
        this(name, null);
    }

    public class RockBlockBuild extends Building{
        @Override
        public void placed(){
            super.placed();

            spawnUnit.spawn(team, this).rotation(90);
            Moyai.mRenderer.boom(boomIntensity, boomDuration);
            Call.buildDestroyed(this);
        }
    }
}
