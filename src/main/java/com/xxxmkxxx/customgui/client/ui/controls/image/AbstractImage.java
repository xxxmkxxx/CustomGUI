package com.xxxmkxxx.customgui.client.ui.controls.image;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;
import net.minecraft.util.Identifier;

@Getter
public class AbstractImage extends AbstractNode implements Image {
    protected boolean isStandard;
    protected Identifier imageIdentifier;

    public AbstractImage(Pos startPos, int width, int height, Identifier imageIdentifier) {
        this.imageIdentifier = imageIdentifier;
        this.isStandard = defineImageStatus();
        this.frame = new SimpleFrame(startPos, width, height);
    }

    public boolean defineImageStatus() {
        return imageIdentifier.getNamespace().equals("minecraft");
    }
}
