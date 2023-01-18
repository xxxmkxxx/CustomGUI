package com.xxxmkxxx.customgui.client.ui.controls.image;

import com.xxxmkxxx.customgui.client.geometry.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import lombok.Getter;
import net.minecraft.util.Identifier;

@Getter
public class AbstractImage extends AbstractNode implements Image {
    protected Identifier imageIdentifier;
    protected AbstractFrame frame;
}
