package com.xxxmkxxx.customgui.client.hierarchy.stage;

import com.xxxmkxxx.customgui.client.hierarchy.scene.SimpleScene;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

@Getter @Setter
public class Stage<H extends ScreenHandler> extends Screen implements ScreenHandlerProvider<H> {
    private SimpleScene root;
    private H handler;

    protected Stage(Text title, H handler) {
        super(title);
        this.handler = handler;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public H getScreenHandler() {
        return handler;
    }
}
