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
    private TextureRegion rock = null;

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

            if(rock == null) rock = Core.atlas.find("vine-boom-moyai-emoji");

            Draw.alpha(rockAlpha);
            float scl = 400 / Vars.renderer.getDisplayScale();
            Draw.rect(rock, camera.position.x, camera.position.y, scl, scl);
            Draw.color();
        });
    }
}
