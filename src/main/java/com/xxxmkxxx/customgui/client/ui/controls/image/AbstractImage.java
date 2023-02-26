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

    public AbstractImage(Pos startPos, Pos stopPos, Identifier imageIdentifier) {
        this.imageIdentifier = imageIdentifier;
        this.isStandard = defineImageStatus();
        this.frame = SimpleFrame.builder().positions(startPos, stopPos).build();
    }

    public boolean defineImageStatus() {
        return imageIdentifier.getNamespace().equals("minecraft");
    }
}
