package moyai.content;

import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import moyai.gen.*;
import moyai.type.*;

public class MUnitTypes{
    public static UnitType moyai;

    public static void load(){
        moyai = EntityRegistry.content("moyai", UnitEntity.class, name -> new UnitType(name){{
            weapons.add(new VineBoomWeapon(){{
                bullet = new ExplosionBulletType(100000, 50 * 8){{
                    killShooter = false;
                    splashDamagePierce = true;
                    collidesAir = true;

                    shootEffect = smokeEffect = Fx.none;
                }};

                reload = 20;
                x = y = shootX = shootY = 0f;
                shootCone = 180f;

                shootSound = MSounds.boom;
            }});

            flying = false;
            strafePenalty = 0f;
        }});
    }
}
