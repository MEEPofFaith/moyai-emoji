package moai.type;

import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;
import moai.*;

public class VineBoomWeapon extends Weapon{
    public float boomIntensity = 0.2f,
        boomDuration = 60f;

    public VineBoomWeapon(String name){
        super(name);
    }

    public VineBoomWeapon(){
        this("");
    }

    @Override
    protected void bullet(Unit unit, WeaponMount mount, float xOffset, float yOffset, float angleOffset, Mover mover){
        if(!unit.isAdded()) return;

        super.bullet(unit, mount, xOffset, yOffset, angleOffset, mover);

        Moai.mRenderer.boom(boomIntensity, boomDuration);
    }
}
