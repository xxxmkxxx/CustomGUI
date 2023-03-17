package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.common.ParametrizedSelfDestructionMethod;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.common.inventory.AbstractInventory;
import com.xxxmkxxx.customgui.client.common.inventory.InventoryType;
import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Background;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.Window;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.image.AbstractImage;
import com.xxxmkxxx.customgui.client.ui.controls.image.SimpleImage;
import com.xxxmkxxx.customgui.client.ui.controls.image.StandardImage;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.entity.player.PlayerEntity;
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
    private AbstractImage image;
    protected Runnable leftClickAction;
    protected Runnable hoverAction;
    protected Runnable resetHoverAction;

    protected SimpleSlot(Pos startPos, Pos stopPos, AbstractImage image, int index, AbstractInventory inventory) {
        super(startPos, stopPos, index, inventory);
        this.leftClickAction = () -> {};
        this.hoverAction = () -> {};
        this.resetHoverAction = () -> {};
        this.amountItemsText = SimpleText.builder()
                .startPos(initAmountItemsTextStartPos(Text.of(String.valueOf(inventory.getStack(index).getCount()))))
                .build();
        this.image = image;

        updateAmountItemsText(inventory.getStack(index));
        updateIndents();
    }

    private Pos initAmountItemsTextStartPos(Text text) {
        return Pos.builder()
                .coords(
                        frame.getStopPos().getX() - Utils.getTextWidth(text),
                        frame.getStopPos().getY() - Utils.getTextHeight()
                )
                .build(frame.getLastXPercentValue(), frame.getLastYPercentValue());
    }

    private void updateAmountItemsText(ItemStack itemStack) {
        Text amount = Text.of(Integer.toString(itemStack.getCount()));

        Pos startPos = initAmountItemsTextStartPos(amount);

        Pos stopPos = Pos.builder()
                .coords(
                       startPos.getX() + amountItemsText.getFrame().getWidth(),
                        frame.getStopPos().getY() + amountItemsText.getFrame().getHeight()
                )
                .build(frame.getLastXPercentValue(), frame.getLastYPercentValue());

        amountItemsText.getFrame().moveStartPos(startPos);
        amountItemsText.getFrame().moveStopPos(stopPos);
        amountItemsText.setText(amount);
    }

    @Override
    public void scaling(Window window) {
        super.scaling(window);
        amountItemsText.scaling(window);
        image.scaling(window);
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        amountItemsText.initRenderer(type);
        image.initRenderer(type);
        this.renderer = new RendererFactory().create(type);
    }

    @Override
    public void update() {
        updateIndents();
    }

    private void updateIndents() {
        float leftSlotMargin = style.getMargins().getLeft();
        float topSlotMargin = style.getMargins().getTop();

        frame.moveStartPos(leftSlotMargin, topSlotMargin);
        frame.moveStopPos(leftSlotMargin, topSlotMargin);
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

    public static FactoryBuilder factoryBuilder() {
        return new FactoryBuilder();
    }

    @Setter
    @RequiredArgsConstructor
    public static class Factory implements SlotFactory<SimpleSlot> {
        private final float widthPercent;
        private final float heightPercent;
        private final AbstractInventory inventory;
        private Style style = Style.defaultStyle();
        private Consumer<ItemStack> leftClickAction = (itemStack) -> {};
        private Runnable hoverAction = () -> {};
        private Runnable resetHoverAction = () -> {};

        public void setStyle(Style style) {
            try {
                this.style = (Style) style.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public SimpleSlot create(int index, Pos pos) {
            Pos position = Pos.defaultPos();

            try {
                position = (Pos) pos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }

            Identifier identifier = Registry.ITEM.getId(inventory.getStack(index).getItem());
            AbstractImage image = identifier.getNamespace().equals("minecraft")
                    ? StandardImage.builder().startPos(position).itemStack(inventory.getStack(index)).widthPercent(widthPercent).heightPercent(heightPercent).build()
                    : SimpleImage.builder().startPos(position).identifier(identifier).widthPercent(widthPercent).heightPercent(heightPercent).build();

            SimpleSlot slot = new SimpleSlot(
                    position,
                    Pos.builder()
                            .relativeCoords(
                                    position.getXIndentPercent() + widthPercent,
                                    position.getYIndentPercent() + heightPercent
                            )
                            .build(pos.getXPercentValue(), position.getYPercentValue()),
                    image, index, inventory
            );
            slot.setLeftClickAction(() -> leftClickAction.accept(inventory.getStack(index)));
            slot.setHoverAction(hoverAction);
            slot.setResetHoverAction(resetHoverAction);
            slot.setStyle(style);

            return slot;
        }
    }

    public static class FactoryBuilder {
        private Identifier identifier;
        private float widthPercent;
        private float heightPercent;
        private AbstractInventory inventory;
        private AbstractImage image;
        private Style style;

        public FactoryBuilder() {
            this.widthPercent = 5;
            this.heightPercent = 5;
            this.style = Style.defaultStyle();
            this.inventory = new AbstractInventory(InventoryType.EMPTY) {
                @Override
                public int size() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public ItemStack getStack(int slot) {
                    return ItemStack.EMPTY;
                }

                @Override
                public ItemStack removeStack(int slot, int amount) {
                    return null;
                }

                @Override
                public ItemStack removeStack(int slot) {
                    return null;
                }

                @Override
                public void setStack(int slot, ItemStack stack) {

                }

                @Override
                public void markDirty() {

                }

                @Override
                public boolean canPlayerUse(PlayerEntity player) {
                    return false;
                }

                @Override
                public void clear() {

                }
            };
            this.image = SimpleImage.builder().build();
            this.identifier = new Identifier("customgui", "/textures/gui/empty_img.png");
        }

        public FactoryBuilder style(Style style) {
            try {
                this.style = (Style) style.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public FactoryBuilder image(SimpleImage image) {
            this.image = image;
            return this;
        }

        public FactoryBuilder image(Identifier identifier) {
            this.identifier = identifier;
            return this;
        }

        public FactoryBuilder widthPercent(float widthPercent) {
            this.widthPercent = widthPercent;
            return this;
        }

        public FactoryBuilder heightPercent(float heightPercent) {
            this.heightPercent = heightPercent;
            return this;
        }

        public FactoryBuilder inventory(AbstractInventory inventory) {
            this.inventory = inventory;
            return this;
        }

        public Factory build() {
            Factory factory = new Factory(widthPercent, heightPercent, inventory);
            factory.setStyle(style);

            return factory;
        }
    }

    public static class RendererFactory implements NodeRendererFactory<SimpleSlot> {
        private ParametrizedSelfDestructionMethod<SimpleSlot> initBackgroundMethod = new ParametrizedSelfDestructionMethod<>();
        private Consumer<SimpleSlot> backgroundRenderMethod = simpleSlot -> {};

        public RendererFactory() {
            initBackgroundMethod.setAction(simpleSlot -> {
                backgroundRenderMethod = Background.chooseBackground(simpleSlot.getStyle().getBackground().getType());
            });
        }

        @Override
        public NodeRenderer<SimpleSlot> create(RendererType type) {
            return this::render;
        }

        private void render(SimpleSlot slot) {
            initBackgroundMethod.execute(slot);

            backgroundRenderMethod.accept(slot);

            if (slot.getItemStack() == ItemStack.EMPTY) return;

            slot.getImage().getState().execute(slot.getImage(), slot.getImage().getRenderer());
            slot.getAmountItemsText().getState().execute(slot.getAmountItemsText(), slot.getAmountItemsText().getRenderer());
        }
    }
}
