package moyai.graphics;

import arc.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.graphics.*;

import static arc.Core.*;

public class VineBoomRenderer{
    private float boomIntensity,
        boomReduction,
        boomTime;
    private float rockAlpha,
        rockReduction;
    private final float rockBorder = 32;
    private final FrameBuffer buffer;
    public RockType rockType = RockType.all[settings.getInt("vine-boom-rocktype", 0)];

    public VineBoomRenderer(){
        if(Vars.headless){
            buffer = null;
            return;
        }

        MShaders.init();
        buffer = new FrameBuffer();

        Events.run(Trigger.update, this::update);
        Events.run(Trigger.draw, this::draw);
    }

    public void boom(float intensity, float duration){
        boomIntensity = Math.max(boomIntensity, Mathf.clamp(intensity, 0, 1));
        boomTime = Math.max(boomTime, duration);
        boomReduction = boomIntensity / boomTime;
        rockAlpha = 0.25f;
        rockReduction = rockAlpha / boomTime;
    }

    private void update(){
        if(!Vars.state.isPaused() && boomTime > 0){
            boomIntensity -= boomReduction * Time.delta;
            rockAlpha -= rockReduction * Time.delta;
            boomTime -= Time.delta;
            boomIntensity = Mathf.clamp(boomIntensity, 0f, 1f);
            rockAlpha = Mathf.clamp(rockAlpha, 0f, 1f);
        }
    }

    private void draw(){
        if(Vars.headless) return;

        Draw.draw(Layer.background - 0.1f, () -> {
            if(!blur()) return;
            buffer.resize(graphics.getWidth(), graphics.getHeight());
            buffer.begin();
        });

        Draw.draw(Layer.max - 6, () -> {
            if(blur()){
                buffer.end();
                MShaders.vineBoomShader.intensity = boomIntensity * intensity();
                buffer.blit(MShaders.vineBoomShader);
            }

            Draw.alpha(rockAlpha);
            TextureRegion region = rockType.splashRegion();
            float rWidth = region.width, rHeight = region.height;
            float sWidth = graphics.getWidth() - 2 * rockBorder,
                sHeight = graphics.getHeight() - 2 * rockBorder;
            float targetRatio = sHeight / sWidth;
            float sourceRatio = rHeight / rWidth;
            float scale = targetRatio > sourceRatio ? sWidth / rWidth : sHeight / rHeight;
            rWidth *= scale / Vars.renderer.getDisplayScale();
            rHeight *= scale / Vars.renderer.getDisplayScale();
            Draw.rect(region, camera.position.x, camera.position.y, rWidth, rHeight);
            Draw.color();
        });
    }

    public boolean blur(){
        return settings.getInt("vine-boom-intensity", 4) > 0;
    }

    public float intensity(){
        return settings.getInt("vine-boom-intensity", 4) / 4f;
    }

    public enum RockType{
        moyai("vine-boom-moyai", "vine-boom-moyai-emoji"),
        boulder("boulder2", "vine-boom-animboulder"),
        maurice("vine-boom-maurice", "vine-boom-machine-i-have-taken-a-selfie-with-the-funny-rock");

        private final String sprite, splash;

        RockType(String sprite, String splash){
            this.sprite = sprite;
            this.splash = splash;
        }

        private TextureRegion region, splashRegion;

        public static final RockType[] all = values();

        public TextureRegion region(){
            if(region == null) region = Core.atlas.find(sprite);
            return region;
        }

        public TextureRegion splashRegion(){
            if(splashRegion == null) splashRegion = Core.atlas.find(splash);
            return splashRegion;
        }

        public String localized(){
            return Core.bundle.get("rock.vine-boom-" + name());
        }
    }
}
