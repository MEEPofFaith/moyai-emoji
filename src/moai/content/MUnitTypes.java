package moai.content;

import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import moai.gen.*;
import moai.type.*;

public class MUnitTypes{
    public static UnitType moyai;

    public static void load(){
        moyai = EntityRegistry.content("moyai", UnitEntity.class, name -> new UnitType(name){{
            weapons.add(new VineBoomWeapon(){{
                bullet = new ExplosionBulletType(100000, 50 * 8){{
                    killShooter = false;
                    splashDamagePierce = true;
                }};

                reload = 20;
                x = y = shootX = shootY = 0f;
                shootCone = 180f;
            }});

            flying = false;
            strafePenalty = 0f;
        }});
    }
}
