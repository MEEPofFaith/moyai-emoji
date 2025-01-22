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
            buffer.resize(graphics.getWidth(), graphics.getHeight());
            buffer.begin();
        });

        Draw.draw(Layer.max - 6, () -> {
            buffer.end();
            MShaders.vineBoomShader.intensity = boomIntensity;
            buffer.blit(MShaders.vineBoomShader);

            Draw.alpha(rockAlpha);
            TextureRegion region = rockType.splashRegion();
            float scl = rockType.width / Vars.renderer.getDisplayScale();
            Draw.rect(region, camera.position.x, camera.position.y, scl, scl / region.ratio());
            Draw.color();
        });
    }

    public enum RockType{
        moyai("vine-boom-moyai", "vine-boom-moyai-emoji", 400),
        boulder("boulder2", "vine-boom-animboulder", 450),
        maurice("vine-boom-maurice", "vine-boom-machine-i-have-taken-a-selfie-with-the-funny-rock", 600);

        private final String sprite, splash;
        public final int width;

        RockType(String sprite, String splash, int width){
            this.sprite = sprite;
            this.splash = splash;
            this.width = width;
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
