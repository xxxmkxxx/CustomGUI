package com.xxxmkxxx.customgui.client;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.common.inventory.AbstractInventory;
import com.xxxmkxxx.customgui.client.common.inventory.InventoryType;
import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
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
import com.xxxmkxxx.customgui.client.ui.controls.field.InputField;
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

    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            CustomGUI customGUI = CustomGUI.getInstance();

            Style style = new Style();
            style.setMargins(Margins.builder().left(1).bottom(1).right(1).top(1).build());
            style.setColor(new Color("2ad43b"));
            style.setBackground(Background.builder().opacity(new Opacity(20)).type(Background.Type.COLORED).color(Color.DefaultColor.BLUE.getColor()).build());

            customGUI.addGUIBlock("testButton", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);
                Window window = screenStage.getWindow();

                Pos pos = Pos.builder().relativeCoords(0.55, 2.63).build(window.getXPercentValue(), window.getYPercentValue());

                SimpleButton button = SimpleButton.builder()
                        .positions(SimpleFrame.builder().startPos(pos).widthPercent(4.59).heightPercent(3.07).build())
                        .text("MISSIONS")
                        .style(style)
                        .build();
                button.setLeftClickAction(() -> {
                    MinecraftClient.getInstance().player.sendMessage(Text.of("MISSIONS"), false);
                });
                button.getStyle().getOpacity().setPercent(50);

                SimpleImage image = SimpleImage.builder()
                        .identifier(new Identifier("customgui", "/textures/gui/empty_img.png"))
                        .positions(SimpleFrame.builder().startPos(pos).widthPercent(1.55).heightPercent(2.32).build())
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
                        .positions(SimpleFrame.builder().startPos(pos).widthPercent(4.59).heightPercent(5.07).build())
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

                InputField field = InputField.builder()
                        .height(Utils.getTextHeight())
                        .width(50)
                        .pos(Pos.builder().relativeCoords(5, 5).build(window.getXPercentValue(), window.getYPercentValue()))
                        .build();
                SimpleLabel label = SimpleLabel.builder()
                        .text("label")
                        .style(style)
                        .position(AbstractNode.Position.BOTTOM)
                        .build(field);

                scene.addElement(field);
                scene.addElement(label);

                return scene;
            });
            customGUI.addGUIBlock("testInputField", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);
                Window window = screenStage.getWindow();

                InputField inputField = InputField.builder()
                        .style(style)
                        .height(Utils.getTextHeight())
                        .width(50)
                        .pos(Pos.builder().relativeCoords(5, 5).build(window.getXPercentValue(), window.getYPercentValue()))
                        .build();
                inputField.getInputCursor().getStyle().setColor(Color.DefaultColor.ORANGE.getColor());

                scene.addElement(inputField);

                return scene;
            });
            customGUI.addGUIBlock("testSlotContainers", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);
                Window window = screenStage.getWindow();

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

                SimpleText text = SimpleText.builder()
                        .startPos(Pos.builder().coords(50, 50).build(window.getXPercentValue(), window.getYPercentValue()))
                        .stopPos(Pos.builder().coords(67, 57).build(window.getXPercentValue(), window.getYPercentValue()))
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
                System.out.println("button - " + simpleButton.getFrame());

                //scene.addElement(text);
                scene.addElement(simpleButton);

                return scene;
            });

            customGUI.addGUIBlock("RMC", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);
                Window window = screenStage.getWindow();

                Background moreElementsBackground = Background.builder()
                        .color(Color.builder().hexValue("655c55").build())
                        .opacity(new Opacity(40))
                        .build();

                Style standardElementStyle = new Style();
                standardElementStyle.setBackground(moreElementsBackground);

                //Missions button
                SimpleImage logoImage = SimpleImage.builder()
                        .positions(
                                Pos.builder().relativeCoords( 0.84, 2.87).build(window.getXPercentValue(), window.getYPercentValue()),
                                Pos.builder().relativeCoords(1.64, 4.78).build(window.getXPercentValue(), window.getYPercentValue())
                        )
                        .identifier(new Identifier("customgui", "textures/gui/acacia_door.png"))
                        .style(standardElementStyle)
                        .build();

                SimpleText missionsButtonText = SimpleText.builder()
                        .text("MISSIONS")
                        .startPos(Pos.builder().relativeCoords(  2.0, 3.34).build(window.getXPercentValue(), window.getYPercentValue()))
                        .stopPos(Pos.builder().relativeCoords(4.08, 4.2).build(window.getXPercentValue(), window.getYPercentValue()))
                        //.style(standardElementStyle)
                        .build();

                ImagedButton missionsButton = ImagedButton.builder()
                        .positions(
                                Pos.builder().relativeCoords( 0.56, 2.48).build(window.getXPercentValue(), window.getYPercentValue()),
                                Pos.builder().relativeCoords(4.6, 5.25).build(window.getXPercentValue(), window.getYPercentValue())
                        )
                        .image(logoImage)
                        .text(missionsButtonText)
                        .style(standardElementStyle)
                        .build();

                //Create team button
                SimpleText createTeamButtonText = SimpleText.builder()
                        .positions(
                                Pos.builder().relativeCoords(9.6, 92.55).build(window.getXPercentValue(), window.getYPercentValue()),
                                Pos.builder().relativeCoords(14.28, 93.89).build(window.getXPercentValue(), window.getYPercentValue())
                        )
                        .text("CREATE TEAM")
                        .build();

                SimpleButton createTeamButton = SimpleButton.builder()
                        .positions(
                                Pos.builder().relativeCoords(7.88, 91.5).build(window.getXPercentValue(), window.getYPercentValue()),
                                Pos.builder().relativeCoords(16.08, 95.32).build(window.getXPercentValue(), window.getYPercentValue())
                        )
                        .style(standardElementStyle)
                        .text(createTeamButtonText)
                        .build();

                //Armor slots
                UnmodifiableLinearSlotContainer.Builder<SimpleSlot> armorSlotsContainerBuilder = UnmodifiableLinearSlotContainer.builder();
                SimpleSlot.Factory armorSlotFactory = SimpleSlot.factoryBuilder()
                        .inventory(inventory)
                        .style(standardElementStyle)
                        .widthPercent(2.92).heightPercent(6.97)
                        .build();

                UnmodifiableLinearSlotContainer<SimpleSlot> armorSlotsContainer = armorSlotsContainerBuilder
                        .size(7)
                        .style(standardElementStyle)
                        .build(
                            Pos.builder().relativeCoords(15.76, 76.89).build(window.getXPercentValue(), window.getYPercentValue()),
                                armorSlotFactory,
                                0, 1, 2, 3, 4, 5, 6
                        );

                scene.addElement(missionsButton);
                scene.addElement(createTeamButton);
                scene.addElement(armorSlotsContainer);

                return scene;
            });

            customGUI.setActiveScene("RMC", RendererType.SCREEN);
        });
    }
}
