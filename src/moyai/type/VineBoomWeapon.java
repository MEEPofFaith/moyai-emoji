package moyai.type;

import arc.math.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;
import moyai.*;
import moyai.entities.units.*;

public class VineBoomWeapon extends Weapon{
    public float boomIntensity = 0.2f,
        boomDuration = 60f;

    public VineBoomWeapon(String name){
        super(name);
        mountType = SpamWeaponMount::new;
    }

    public VineBoomWeapon(){
        this("");
    }

    @Override
    public void update(Unit unit, WeaponMount mount){
        SpamWeaponMount m = (SpamWeaponMount)mount;
        if(unit.isPlayer() && unit.isShooting() && !m.wasShooting){ //SPAMMABLE
            float
                weaponRotation = unit.rotation - 90 + (rotate ? mount.rotation : baseRotation),
                mountX = unit.x + Angles.trnsx(unit.rotation - 90, x, y),
                mountY = unit.y + Angles.trnsy(unit.rotation - 90, x, y),
                bulletX = mountX + Angles.trnsx(weaponRotation, this.shootX, this.shootY),
                bulletY = mountY + Angles.trnsy(weaponRotation, this.shootX, this.shootY),
                shootAngle = bulletRotation(unit, mount, bulletX, bulletY);
            shoot(unit, mount, bulletX, bulletY, shootAngle);

            mount.reload = reload;

            if(useAmmo){
                unit.ammo--;
                if(unit.ammo < 0) unit.ammo = 0;
            }
        }else{
            super.update(unit, mount);
        }
        m.wasShooting = unit.isShooting();
    }

    @Override
    protected void bullet(Unit unit, WeaponMount mount, float xOffset, float yOffset, float angleOffset, Mover mover){
        if(!unit.isAdded()) return;

        super.bullet(unit, mount, xOffset, yOffset, angleOffset, mover);

        Moyai.mRenderer.boom(boomIntensity, boomDuration);
    }
}
