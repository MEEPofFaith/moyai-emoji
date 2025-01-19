package moai.graphics;

import arc.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.graphics.*;
import moai.graphics.MShaders.*;

import static arc.Core.graphics;

public class VineBoomRenderer{
    private float boomIntensity, boomReduction, boomTime;

    private FrameBuffer buffer;

    public VineBoomRenderer(){
        MShaders.init();

        Events.run(Trigger.update, this::update);
        Events.run(Trigger.preDraw, this::draw);
    }

    public void boom(float intensity, float duration){
        boomIntensity = Math.max(boomIntensity, Mathf.clamp(intensity, 0, 100));
        boomTime = Math.max(boomTime, duration);
        boomReduction = boomIntensity / boomTime;
    }

    private void update(){
        if(boomTime > 0){
            boomIntensity -= boomReduction * Time.delta;
            boomTime -= Time.delta;
            boomIntensity = Mathf.clamp(boomIntensity, 0f, 100f);
        }
    }

    private void draw(){
        Draw.draw(Layer.background - 0.1f, () -> {
            buffer.resize(graphics.getWidth(), graphics.getHeight());
            buffer.begin();
        });

        Draw.draw(Layer.max - 6, () -> {
            buffer.end();
            MShaders.vineBoomShader.intensity = boomIntensity;
            buffer.blit(MShaders.vineBoomShader);
        });
    }
}
