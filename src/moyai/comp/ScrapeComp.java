package moyai.comp;

import arc.math.geom.*;
import arc.util.*;
import ent.anno.Annotations.*;
import mindustry.audio.*;
import mindustry.entities.*;
import mindustry.entities.EntityCollisions.*;
import mindustry.gen.*;
import mindustry.type.*;
import moyai.content.*;
import moyai.gen.*;

import static mindustry.Vars.headless;


@EntityComponent
@EntityDef({ScrapeUnitc.class, Unitc.class})
abstract class ScrapeUnitComp implements Unitc{
    @Import float x, y;
    @Import Vec2 vel;
    @Import UnitType type;

    private transient @Nullable SoundLoop sound;

    @Override
    public void add(){
        if(sound == null) sound = new SoundLoop(MSounds.scrape, 1f);
    }

    @Override
    public void remove(){
        if(sound != null) sound.stop();
    }

    @Override
    public void update(){
        if(!headless && sound != null){
            sound.update(x, y, true, vel.len() / type.speed);
        }
    }

    @Override
    @Replace
    public SolidPred solidity(){
        return EntityCollisions::solid;
    }
}
