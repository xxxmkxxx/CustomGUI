package com.xxxmkxxx.customgui.client.hierarchy.node.events;

import com.xxxmkxxx.customgui.client.common.event.Event;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

public interface NodeEvent<H extends NodeEventHandler> extends Event<AbstractNode, H> {}
