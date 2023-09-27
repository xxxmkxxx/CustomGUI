package com.xxxmkxxx.customgui.client;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.common.inventory.AbstractInventory;
import com.xxxmkxxx.customgui.client.common.inventory.InventoryType;
import com.xxxmkxxx.customgui.client.hierarchy.node.layout.LayoutManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.layout.Position;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.SimpleScene;
import com.xxxmkxxx.customgui.client.hierarchy.style.*;
import com.xxxmkxxx.customgui.client.hierarchy.window.Window;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.containers.pane.SimplePane;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.LinearSlotContainer;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.RectangularSlotContainer;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.SquareSlotContainer;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.UnmodifiableLinearSlotContainer;
import com.xxxmkxxx.customgui.client.ui.controls.button.ImagedButton;
import com.xxxmkxxx.customgui.client.ui.controls.button.SimpleButton;
import com.xxxmkxxx.customgui.client.ui.controls.field.NoneExpandableInputField;
import com.xxxmkxxx.customgui.client.ui.controls.image.SimpleImage;
import com.xxxmkxxx.customgui.client.ui.controls.label.SimpleLabel;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SimpleSlot;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SlotFactory;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public class CustomGUIClient implements ClientModInitializer {
    private static AbstractInventory inventory = new AbstractInventory(InventoryType.ENTITY) {
        private final List<ItemStack> items = Arrays.asList(
                ItemStack.EMPTY, new ItemStack(Items.BARREL,5), new ItemStack(Items.ACACIA_PLANKS, 10),
                new ItemStack(Items.BLUE_GLAZED_TERRACOTTA, 8), new ItemStack(Items.END_STONE_BRICKS, 1),
                new ItemStack(Items.BLUE_GLAZED_TERRACOTTA, 8), new ItemStack(Items.END_STONE_BRICKS, 1),
                new ItemStack(Items.BLUE_GLAZED_TERRACOTTA, 8), new ItemStack(Items.END_STONE_BRICKS, 1)
        );

        @Override
        public int size() {
            return items.size();
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ItemStack getStack(int slot) {
            return items.get(slot);
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            return ItemStack.EMPTY;
        }

        @Override
        public ItemStack removeStack(int slot) {
            return ItemStack.EMPTY;
        }

        @Override
        public void setStack(int slot, ItemStack stack) {

        }

        @Override
        public void markDirty() {

        }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return true;
        }

        @Override
        public void clear() {

        }
    };
    private static AbstractInventory emptyInventory = new AbstractInventory(InventoryType.ENTITY) {
        @Override
        public int size() {
            return 1000;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ItemStack getStack(int slot) {
            return new ItemStack(Items.BAMBOO, 20);
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

    @Override
    public void onInitializeClient() {
        /*ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            CustomGUI customGUI = CustomGUI.getInstance();

            customGUI.addGUIBlock("testButton", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);
                Window window = screenStage.getWindow();

                Style style = new Style();
                style.setMargins(Margins.builder().left(0).bottom(0).right(0).top(0).build());
                style.setPaddings(Paddings.builder().left(0).right(0).top(0).bottom(0).build());
                style.setColor(new Color("2ad43b"));
                style.setBackground(Background.builder().opacity(new Opacity(20)).type(Background.Type.COLORED).color(Color.DefaultColor.BLUE.getColor()).build());
                style.setFont(Font.builder().size(3).build(window.getXPercentValue(), window.getYPercentValue()));

                Pos pos = Pos.builder().relativeCoords(0.55f, 2.63f).build(window.getXPercentValue(), window.getYPercentValue());

                SimpleButton button = SimpleButton.builder()
                        .positions(SimpleFrame.builder().startPos(pos).widthPercent(4.59f).heightPercent(3.07f).build())
                        .text("MISSIONS")
                        .style(style)
                        .build();
                button.setLeftClickAction(() -> {
                    MinecraftClient.getInstance().player.sendMessage(Text.of("MISSIONS"), false);
                });
                button.getStyle().getOpacity().setPercent(50);

                SimpleImage image = SimpleImage.builder()
                        .identifier(new Identifier("customgui", "/textures/gui/empty_img.png"))
                        .positions(SimpleFrame.builder().startPos(pos).widthPercent(1.55f).heightPercent(2.32f).build())
                        .build();

                SimpleText text = SimpleText.builder()
                        .startPos(
                                Pos.builder()
                                    .coords(image.getFrame().getStopPos().getY(), image.getFrame().getStartPos().getY())
                                    .build(window.getXPercentValue(), window.getYPercentValue())
                        )
                        .text("")
                        .style(style)
                        .build();

                ImagedButton imagedButton = ImagedButton.builder()
                        .positions(SimpleFrame.builder().startPos(pos).widthPercent(4.59f).heightPercent(5.07f).build())
                        .image(image)
                        .text(text)
                        .build();

                //scene.addElement(image);
                //scene.addElement(button);
                scene.addElement(imagedButton);

                return scene;
            });
            customGUI.addGUIBlock("testSlot", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);
                Window window = screenStage.getWindow();

                Style style = new Style();
                style.setMargins(Margins.builder().left(0).bottom(0).right(0).top(0).build());
                style.setPaddings(Paddings.builder().left(0).right(0).top(0).bottom(0).build());
                style.setColor(new Color("2ad43b"));
                style.setBackground(Background.builder().opacity(new Opacity(20)).type(Background.Type.COLORED).color(Color.DefaultColor.BLUE.getColor()).build());
                style.setFont(Font.builder().size(3).build(window.getXPercentValue(), window.getYPercentValue()));

                SimpleSlot.Factory slotFactory = SimpleSlot.factoryBuilder()
                        .widthPercent(3).heightPercent(5)
                        .inventory(inventory)
                        .style(style)
                        .build();
                slotFactory.setLeftClickAction(itemStack -> {
                    MinecraftClient.getInstance().player.sendMessage(itemStack.getItem().getName(), false);
                });

                SimpleSlot slot = slotFactory.create(
                        2,
                        Pos.builder()
                                .relativeCoords(5, 5)
                                .build(window.getXPercentValue(), window.getYPercentValue())
                );

                System.out.println(slot.getFrame());

                scene.addElement(slot);

                return scene;
            });
            customGUI.addGUIBlock("testLabel", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);
                Window window = screenStage.getWindow();

                Style style = new Style();
                style.setMargins(Margins.builder().left(0).bottom(0).right(0).top(0).build());
                style.setPaddings(Paddings.builder().left(0).right(0).top(0).bottom(0).build());
                style.setColor(new Color("2ad43b"));
                style.setBackground(Background.builder().opacity(new Opacity(20)).type(Background.Type.COLORED).color(Color.DefaultColor.BLUE.getColor()).build());
                style.setFont(Font.builder().size(3).build(window.getXPercentValue(), window.getYPercentValue()));

                SimpleButton simpleButton = SimpleButton.builder()
                        .text("button")
                        .style(style)
                        .startPos(Pos.builder().coords(50, 50).build(window.getXPercentValue(), window.getYPercentValue()))
                        .build();

                SimpleLabel label = SimpleLabel.builder()
                        .text("label")
                        .style(style)
                        .relativePosition(Position.LEFT)
                        .build(simpleButton);

                scene.addElement(simpleButton);
                scene.addElement(label);

                return scene;
            });
            customGUI.addGUIBlock("testInputField", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);
                Window window = screenStage.getWindow();

                Style style = new Style();
                style.setMargins(Margins.builder().left(0).bottom(0).right(0).top(0).build());
                style.setPaddings(Paddings.builder().left(0).right(0).top(0).bottom(0).build());
                style.setColor(new Color("2ad43b"));
                style.setBackground(Background.builder().opacity(new Opacity(20)).type(Background.Type.COLORED).color(Color.DefaultColor.BLUE.getColor()).build());
                style.setFont(Font.builder().color(Color.DefaultColor.ORANGE.getColor()).size(3).build(window.getXPercentValue(), window.getYPercentValue()));

                NoneExpandableInputField noneExpandableInputField = NoneExpandableInputField.builder()
                        .style(style)
                        .heightPercent(style.getFont().getYSizePercent())
                        .widthPercent(style.getFont().getXSizePercent() * 9)
                        .startPos(Pos.builder().relativeCoords(50, 50).build(window.getXPercentValue(), window.getYPercentValue()))
                        .build();
                noneExpandableInputField.getInputCursor().getStyle().setColor(Color.DefaultColor.ORANGE.getColor());
                noneExpandableInputField.setSendAction(System.out::println);

                scene.addElement(noneExpandableInputField);

                return scene;
            });
            customGUI.addGUIBlock("testSlotContainers", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);
                Window window = screenStage.getWindow();

                Style style = new Style();
                style.setMargins(Margins.builder().left(0).bottom(0).right(0).top(0).build());
                style.setPaddings(Paddings.builder().left(0).right(0).top(0).bottom(0).build());
                style.setColor(new Color("2ad43b"));
                style.setBackground(Background.builder().opacity(new Opacity(20)).type(Background.Type.COLORED).color(Color.DefaultColor.BLUE.getColor()).build());
                style.setFont(Font.builder().size(3).build(window.getXPercentValue(), window.getYPercentValue()));

                SlotFactory<SimpleSlot> slotFactory = SimpleSlot.factoryBuilder().inventory(inventory).style(style).heightPercent(5).widthPercent(3).build();

                LinearSlotContainer.Builder<SimpleSlot> u_builder = LinearSlotContainer.builder();
                LinearSlotContainer<SimpleSlot> linearSlotContainer = u_builder
                        .pos(Pos.builder().relativeCoords(5, 5).build(window.getXPercentValue(), window.getYPercentValue()))
                        .build(slotFactory, 0, 1, 2, 3);

                RectangularSlotContainer.Builder<SimpleSlot> r_builder = RectangularSlotContainer.builder();
                RectangularSlotContainer<SimpleSlot> rectangularSlotContainer = r_builder
                        .pos(Pos.builder().relativeCoords(5, 5).build(window.getXPercentValue(), window.getYPercentValue()))
                        .amountRows(2).rowSize(2)
                        .build(slotFactory, new int[][]{{0, 1}, {2, 3}});

                SquareSlotContainer.Builder<SimpleSlot> sq_builder = new SquareSlotContainer.Builder<>();
                SquareSlotContainer<SimpleSlot> squareSlotContainer = (SquareSlotContainer<SimpleSlot>) sq_builder
                        .size(2)
                        .pos(Pos.builder().relativeCoords(5, 5).build(window.getXPercentValue(), window.getYPercentValue()))
                        .build(slotFactory, new int[][]{{0, 1}, {2, 3}});

                scene.addElement(linearSlotContainer);

                return scene;
            });
            customGUI.addGUIBlock("testPane", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);
                Window window = screenStage.getWindow();

                Style style = new Style();
                style.setMargins(Margins.builder().left(0).bottom(0).right(0).top(0).build());
                style.setPaddings(Paddings.builder().left(0).right(0).top(0).bottom(0).build());
                style.setColor(new Color("2ad43b"));
                style.setBackground(Background.builder().opacity(new Opacity(20)).type(Background.Type.COLORED).color(Color.DefaultColor.BLUE.getColor()).build());
                style.setFont(Font.builder().size(3).build(window.getXPercentValue(), window.getYPercentValue()));

                SimplePane pane = SimplePane.builder()
                        .startPos(Pos.builder().relativeCoords(5, 5).build(window.getXPercentValue(), window.getYPercentValue()))
                        .build();

                SimpleButton button = SimpleButton.builder()
                        .startPos(Pos.builder().relativeCoords(5, 5).build(window.getXPercentValue(), window.getYPercentValue()))
                        .style(style)
                        .text("test")
                        .build();
                button.setLeftClickAction(() -> {
                    MinecraftClient.getInstance().player.sendMessage(Text.of("teeeest"), false);
                });

                pane.addNode(button);
                scene.addElement(pane);

                return scene;
            });
            customGUI.addGUIBlock("testText", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);
                Window window = screenStage.getWindow();

                Style style = new Style();
                style.setMargins(Margins.builder().left(0).bottom(0).right(0).top(0).build());
                style.setPaddings(Paddings.builder().left(0).right(0).top(0).bottom(0).build());
                style.setColor(new Color("2ad43b"));
                style.setBackground(Background.builder().opacity(new Opacity(20)).type(Background.Type.COLORED).color(Color.DefaultColor.BLUE.getColor()).build());
                style.setFont(Font.builder().size(3).build(window.getXPercentValue(), window.getYPercentValue()));

                SimpleText text = SimpleText.builder()
                        .startPos(Pos.builder().coords(50, 50).build(window.getXPercentValue(), window.getYPercentValue()))
                        .style(style)
                        .text("text")
                        .build();
                text.setLeftClickAction(() -> {
                    MinecraftClient.getInstance().player.sendMessage(Text.of("text"), false);
                });

                SimpleButton simpleButton = SimpleButton.builder()
                        .startPos(Pos.builder().coords(50, 50).build(window.getXPercentValue(), window.getYPercentValue()))
                        .text(text)
                        .style(style)
                        .build();
                simpleButton.setLeftClickAction(() -> {
                    MinecraftClient.getInstance().player.sendMessage(Text.of("asdas"), false);
                });
                simpleButton.getStyle().getOpacity().setPercent(70);
                System.out.println("text - " + text.getFrame());
                //System.out.println("button - " + simpleButton.getFrame());

                scene.addElement(text);
                //scene.addElement(simpleButton);

                return scene;
            });
            customGUI.addGUIBlock("testLayout", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);
                Window window = screenStage.getWindow();

                Style style = new Style();
                style.setMargins(Margins.builder().left(0).bottom(0).right(0).top(0).build());
                style.setPaddings(Paddings.builder().left(0).right(0).top(0).bottom(0).build());
                style.setColor(new Color("2ad43b"));
                style.setBackground(Background.builder().opacity(new Opacity(20)).type(Background.Type.COLORED).color(Color.DefaultColor.BLUE.getColor()).build());
                style.setFont(Font.builder().size(3).build(window.getXPercentValue(), window.getYPercentValue()));

                SimpleText firstText = SimpleText.builder()
                        .style(style)
                        .text("first")
                        .startPos(Pos.builder().coords(50, 50).build(window.getXPercentValue(), window.getYPercentValue()))
                        .build();

                SimpleText secondText = SimpleText.builder()
                        .style(style)
                        .text("second")
                        .startPos(Pos.builder().coords(50, 50).build(window.getXPercentValue(), window.getYPercentValue()))
                        .build();

                System.out.println("first - " + firstText.getFrame() + " " + firstText.getFrame().getWidth());
                System.out.println("second - " + secondText.getFrame() + " " + secondText.getFrame().getWidth());
                System.out.println("--------------------------------");

                LayoutManager.positionNodeRelativeTargetNode(firstText, secondText, Position.RIGHT);

                System.out.println("first - " + firstText.getFrame() + " " + firstText.getFrame().getWidth());
                System.out.println("second - " + secondText.getFrame() + " " + secondText.getFrame().getWidth());
                System.out.println("--------------------------------");

                scene.addElement(firstText);
                scene.addElement(secondText);

                return scene;
            });

            customGUI.setActiveScene("testInputField", RendererType.SCREEN);
        });*/
    }
}
