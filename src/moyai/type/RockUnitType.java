package moyai.type;

import mindustry.type.*;
import moyai.*;

public class RockUnitType extends UnitType{
    public RockUnitType(String name){
        super(name);

        strafePenalty = 0f;
        drag = 0.05f;
        flying = false;
        faceTarget = false;
        drawCell = false;
        outlines = false;
    }

    public void reloadRegions(){
        fullIcon = uiIcon = region = shadowRegion = Moyai.mRenderer.rockType.region();
    }
}
