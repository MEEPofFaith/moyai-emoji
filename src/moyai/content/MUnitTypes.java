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
        moyai = EntityRegistry.content("moyai", ScrapeUnit.class, name -> new UnitType(name){{
            weapons.add(new VineBoomWeapon(){{
                bullet = new ExplosionBulletType(100000000, 50 * 8){{
                    killShooter = false;
                    splashDamagePierce = true;
                    collidesAir = true;

                    shootEffect = smokeEffect = hitEffect = despawnEffect = Fx.none;
                }};

                reload = 20;
                x = y = shootX = shootY = 0f;
                shootCone = 180f;

                shootSound = MSounds.boom;
            }});

            health = 1000000;
            flying = false;
            strafePenalty = 0f;
            drag = 0.05f;
            faceTarget = false;
            drawCell = false;
            outlines = false;
        }});
    }
}
