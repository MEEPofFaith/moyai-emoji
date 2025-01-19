package moai.graphics;

import arc.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.graphics.*;
import moai.graphics.MShaders.*;

import static arc.Core.graphics;

public class VineBoomRenderer{
    private float boomRadius, boomIntensity,
        boomRadiusReduction, boomIntensityReduction,
        boomTime;

    private final FrameBuffer buffer;

    public VineBoomRenderer(){
        MShaders.init();
        buffer = new FrameBuffer();

        Events.run(Trigger.update, this::update);
        Events.run(Trigger.draw, this::draw);
    }

    public void boom(float radius, float intensity, float duration){
        boomRadius = Math.max(boomRadius, radius);
        boomIntensity = Math.max(boomIntensity, Mathf.clamp(intensity, 0, 1));
        boomTime = Math.max(boomTime, duration);
        boomRadiusReduction = boomRadius / boomTime;
        boomIntensityReduction = boomIntensity / boomTime;
    }

    private void update(){
        if(!Vars.state.isPaused() && boomTime > 0){
            boomRadius -= boomRadiusReduction * Time.delta;
            boomIntensity -= boomIntensityReduction * Time.delta;
            boomTime -= Time.delta;
            boomRadius = Math.max(boomRadius, 0f);
            boomIntensity = Mathf.clamp(boomIntensity, 0f, 1f);
        }
    }

    private void draw(){
        Draw.draw(Layer.background - 0.1f, () -> {
            buffer.resize(graphics.getWidth(), graphics.getHeight());
            buffer.begin();
        });

        Draw.draw(Layer.max - 6, () -> {
            buffer.end();
            MShaders.vineBoomShader.radius = boomRadius;
            MShaders.vineBoomShader.intensity = boomIntensity;
            buffer.blit(MShaders.vineBoomShader);
        });
    }
}
