package moyai.util;

import arc.*;
import arc.func.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.SettingsMenuDialog.*;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable.*;
import moyai.*;
import moyai.content.*;
import moyai.graphics.VineBoomRenderer.*;

public class MSettings{
    public static void init(){
        Vars.ui.settings.graphics.pref(new RockSelection("vine-boom-rocktype", RockType.moyai));
        Vars.ui.settings.graphics.sliderPref("vine-boom-intensity", 4, 0, 40, s -> s * 25 + "%");
    }

    private static class RockSelection extends Setting{
        int value;

        public RockSelection(String name, RockType def){
            super(name);
            value = Core.settings.getInt(name, def.ordinal());
        }

        @Override
        public void add(SettingsTable table){
            Table content = new Table();
            content.add(title, Styles.outlineLabel).left().growX().wrap();

            content.button(Icon.left, () -> {
                value--;
                if(value < 0) value = RockType.all.length - 1;
                reloadRegion();
            }).right();

            content.table(Tex.pane, l -> {
                //Not wrapping makes it funnier
                l.label(() -> RockType.all[value].localized()).right().width(120).wrapLabel(false);
            });

            content.button(Icon.right, () -> {
                value++;
                if(value >= RockType.all.length) value = 0;
                reloadRegion();
            }).right();

            int count = content.getChildren().size;
            content.getChildren().swap(count - 1, count - 2);

            addDesc(table.add(content).width(Math.min(Core.graphics.getWidth() / 1.2f, 460f)).left().padTop(4f).get());
            table.row();

            reloadRegion();
        }

        private void reloadRegion(){
            Core.settings.put(name, value);
            Moyai.mRenderer.rockType = RockType.all[value];
            MUnitTypes.moyai.reloadRegions();
            MBlocks.rockBlock.reloadIcons();
        }
    }
}
