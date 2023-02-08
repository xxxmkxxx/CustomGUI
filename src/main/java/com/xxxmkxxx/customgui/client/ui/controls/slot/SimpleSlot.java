package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.common.ParametrizedSelfDestructionMethod;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.common.inventory.AbstractInventory;
import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Background;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.image.SimpleImage;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Consumer;

@Getter
@SuppressWarnings("unused")
public class SimpleSlot extends AbstractSlot implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    private SimpleText amountItemsText;
    private SimpleImage image;
    protected Runnable leftClickAction;
    protected Runnable hoverAction;
    protected Runnable resetHoverAction;

    protected SimpleSlot(Pos startPos, int width, int height, int index, AbstractInventory inventory) {
        super(startPos, width, height, index, inventory);
        this.leftClickAction = () -> {};
        this.hoverAction = () -> {};
        this.resetHoverAction = () -> {};
        this.amountItemsText = SimpleText.builder()
                .pos(initAmountItemsTextStartPos(Text.of(String.valueOf(inventory.getStack(index).getCount()))))
                .style(new Style())
                .build();
        this.image = SimpleImage.builder()
                .startPos(startPos)
                .width(width)
                .height(height)
                .style(new Style())
                .build();

        updateAmountItemsText(inventory.getStack(index));
    }

    private Pos initAmountItemsTextStartPos(Text text) {
        return new Pos(
                frame.getStopPos().getX() - Utils.getTextWidth(text),
                frame.getStopPos().getY() - Utils.getTextHeight()
        );
    }

    private void updateAmountItemsText(ItemStack itemStack) {
        Text amount = Text.of(Integer.toString(itemStack.getCount()));

        Pos startPos = initAmountItemsTextStartPos(amount);

        Pos stopPos = new Pos(
                startPos.getX() + amountItemsText.getTextWidth(),
                frame.getStopPos().getY() + amountItemsText.getTextHeight()
        );

        amountItemsText.getFrame().moveStartPos(startPos);
        amountItemsText.getFrame().moveStopPos(stopPos);
        amountItemsText.setText(amount);
    }

    @Override
    public void scaling(double widthScaleValue, double heightScaleValue) {
        super.scaling(widthScaleValue, heightScaleValue);
        amountItemsText.scaling(widthScaleValue, heightScaleValue);
        image.scaling(widthScaleValue, heightScaleValue);
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        amountItemsText.initRenderer(type);
        image.initRenderer(type);
        this.renderer = new RendererFactory().create(type);
    }

    @Override
    public void onLeftClick() {
        leftClickAction.run();
    }

    @Override
    public void onHover() {
        hoverAction.run();
    }

    @Override
    public void onResetHover() {
        resetHoverAction.run();
    }

    public void setLeftClickAction(Runnable leftClickAction) {
        this.leftClickAction = leftClickAction;
        EventBus.LEFT_CLICK_EVENT.addHandler(this, this);
    }

    public void setHoverAction(Runnable hoverAction) {
        this.hoverAction = hoverAction;
        EventBus.HOVER_EVENT.addHandler(this, this);
    }

    public void setResetHoverAction(Runnable resetHoverAction) {
        this.resetHoverAction = resetHoverAction;
        EventBus.RESET_HOVER_EVENT.addHandler(this, this);
    }

    public Identifier getItemAtlas(Item item) {
        Identifier itemIdentifier = Registry.ITEM.getId(item);

        return new Identifier(itemIdentifier.getNamespace(), "textures/gui/" + itemIdentifier.getPath());
    }

    public static FactoryBuilder factoryBuilder(int width, int height, AbstractInventory inventory) {
        return new FactoryBuilder(width, height, inventory);
    }

    public static Factory factory(Pos startPos, int width, int height, AbstractInventory inventory) {
        return new Factory(width, height, inventory);
    }

    @Setter
    @RequiredArgsConstructor
    public static class Factory implements SlotFactory<SimpleSlot> {
        private final int width;
        private final int height;
        private final AbstractInventory inventory;
        private Style style = Style.DEFAULT_STYLE;
        private Consumer<ItemStack> leftClickAction = (itemStack) -> {};
        private Runnable hoverAction = () -> {};
        private Runnable resetHoverAction = () -> {};

        @Override
        public SimpleSlot create(int index, Pos pos) {
            SimpleSlot slot = new SimpleSlot(pos, width, height, index, inventory);
            slot.setLeftClickAction(() -> leftClickAction.accept(inventory.getStack(index)));
            slot.setHoverAction(hoverAction);
            slot.setResetHoverAction(resetHoverAction);
            slot.setStyle(style);

            return slot;
        }
    }

    public static class FactoryBuilder {
        private Factory factory;

        public FactoryBuilder(int width, int height, AbstractInventory inventory) {
            this.factory = new Factory(width, height, inventory);
        }

        public FactoryBuilder leftClickAction(Consumer<ItemStack> leftClickAction) {
            factory.setLeftClickAction(leftClickAction);
            return this;
        }

        public FactoryBuilder hoverAction(Runnable hoverAction) {
            factory.setHoverAction(hoverAction);
            return this;
        }

        public FactoryBuilder resetHoverAction(Runnable resetHoverAction) {
            factory.setResetHoverAction(resetHoverAction);
            return this;
        }

        public FactoryBuilder style(Style style) {
            factory.setStyle(style);
            return this;
        }

        public Factory build() {
            return factory;
        }
    }

    public static class RendererFactory implements NodeRendererFactory<SimpleSlot> {
        private ParametrizedSelfDestructionMethod<SimpleSlot> initBackgroundMethod = new ParametrizedSelfDestructionMethod<>();
        private Consumer<SimpleSlot> backgroundRenderMethod = simpleSlot -> {};
        private Consumer<SimpleSlot> standardImageRenderMethod;

        public RendererFactory() {
            initBackgroundMethod.setAction(simpleSlot -> {
                    backgroundRenderMethod = Background.chooseBackground(simpleSlot.getStyle().getBackground().getType());
                });
            standardImageRenderMethod = simpleSlot -> {
                CustomGUI.NODE_DRAWABLE_HELPER.drawTexture(
                        simpleSlot.getItemStack(),
                        simpleSlot.getFrame()
                );
            };
        }

        @Override
        public NodeRenderer<SimpleSlot> create(RendererType type) {
            return this::render;
        }

        private void render(SimpleSlot slot) {
            initBackgroundMethod.execute(slot);

            backgroundRenderMethod.accept(slot);

            ItemStack itemStack = slot.getItemStack();
            if (itemStack == ItemStack.EMPTY) return;
            if (slot.getImage().isStandard()) standardImageRenderMethod.accept(slot);
            else slot.getImage().getState().execute(slot.getImage(), slot.getImage().getRenderer());

            slot.getAmountItemsText().getState().execute(slot.getAmountItemsText(), slot.getAmountItemsText().getRenderer());
        }
    }
}
