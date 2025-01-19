package moai.graphics;

import arc.*;
import arc.graphics.gl.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class MShaders{
    public static VineBoomShader vineBoomShader;

    public static void init(){
        vineBoomShader = new VineBoomShader();
    }

    public static class VineBoomShader extends MLoadShader{
        public float radius;
        public float intensity;

        public VineBoomShader(){
            super("screenspace", "vineboom");
        }

        @Override
        public void apply(){
            setUniformf("u_resolution", graphics.getWidth(), graphics.getHeight());
            setUniformf("u_radius", radius * renderer.getDisplayScale());
            setUniformf("u_intensity", intensity * renderer.getDisplayScale());
        }
    }

    public static class MLoadShader extends Shader{
        public MLoadShader(String vert, String frag){
            super(
                files.internal("shaders/" + vert + ".vert"),
                tree.get("shaders/" + frag + ".frag")
            );
        }

        public MLoadShader(String frag){
            this("default", frag);
        }
    }
}
