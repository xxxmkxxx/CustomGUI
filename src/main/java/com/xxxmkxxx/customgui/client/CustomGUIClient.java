package com.xxxmkxxx.customgui.client;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.common.inventory.AbstractInventory;
import com.xxxmkxxx.customgui.client.common.inventory.InventoryType;
import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.SimpleScene;
import com.xxxmkxxx.customgui.client.hierarchy.style.Background;
import com.xxxmkxxx.customgui.client.hierarchy.style.Color;
import com.xxxmkxxx.customgui.client.hierarchy.style.Opacity;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.containers.pane.SimplePane;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.LinearSlotContainer;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.RectangularSlotContainer;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.SquareSlotContainer;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.UnmodifiableLinearSlotContainer;
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
            style.setColor(new Color("2ad43b"));
            style.setBackground(Background.builder().opacity(new Opacity(70)).type(Background.Type.COLORED).color(Color.DefaultColor.BLUE.getColor()).build());

            customGUI.addGUIBlock("testButton", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);

                SimpleButton button = SimpleButton.builder()
                        .pos(new Pos(150, 150))
                        .text("test")
                        .build();
                button.setLeftClickAction(() -> {
                    MinecraftClient.getInstance().player.sendMessage(Text.of("teeeest"), false);
                });
                button.setStyle(style);

                //scene.addElement(image);
                //scene.addElement(button);
                scene.addElement(button);

                return scene;
            });
            customGUI.addGUIBlock("testSlot", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);

                SimpleSlot.Factory slotFactory = SimpleSlot.factory(20, 20, inventory);
                slotFactory.setLeftClickAction(itemStack -> {
                    MinecraftClient.getInstance().player.sendMessage(itemStack.getItem().getName(), false);
                });

                scene.addElement(slotFactory.create(2, new Pos(50, 50)));

                return scene;
            });
            customGUI.addGUIBlock("testLabel", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);

                InputField field = InputField.builder()
                        .height(Utils.getTextHeight())
                        .width(50)
                        .pos(new Pos(50, 50))
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

                InputField inputField = InputField.builder()
                        .style(style)
                        .height(Utils.getTextHeight())
                        .width(50)
                        .pos(new Pos(50, 50))
                        .build();
                inputField.getInputCursor().getStyle().setColor(Color.DefaultColor.ORANGE.getColor());

                scene.addElement(inputField);

                return scene;
            });
            customGUI.addGUIBlock("testSlotContainers", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);

                SlotFactory<SimpleSlot> slotFactory = SimpleSlot.factory(18, 18, inventory);

                LinearSlotContainer.Builder<SimpleSlot> u_builder = LinearSlotContainer.builder();
                LinearSlotContainer<SimpleSlot> linearSlotContainer = u_builder
                        .pos(new Pos(50, 50))
                        .build(slotFactory, 0, 1, 2, 3);

                RectangularSlotContainer.Builder<SimpleSlot> r_builder = RectangularSlotContainer.builder();
                RectangularSlotContainer<SimpleSlot> rectangularSlotContainer = r_builder
                        .pos(new Pos(50, 50))
                        .amountRows(2)
                        .rowSize(2)
                        .build(slotFactory, new int[][]{{0, 1}, {2, 3}});

                SquareSlotContainer.Builder<SimpleSlot> sq_builder = new SquareSlotContainer.Builder<>();
                SquareSlotContainer<SimpleSlot> squareSlotContainer = (SquareSlotContainer<SimpleSlot>) sq_builder
                        .size(2)
                        .pos(new Pos(50, 50))
                        .build(slotFactory, new int[][]{{0, 1}, {2, 3}});

                scene.addElement(squareSlotContainer);

                return scene;
            });
            customGUI.addGUIBlock("testPane", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);

                SimplePane pane = SimplePane.builder()
                        .pos(new Pos(50, 50))
                        .build();

                SimpleButton button = SimpleButton.builder()
                        .pos(new Pos(50, 50))
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

                SimpleText text = SimpleText.builder().pos(new Pos(50, 50)).style(style).text("text").build();
                text.setLeftClickAction(() -> {
                    MinecraftClient.getInstance().player.sendMessage(Text.of("text"), false);
                });

                SimpleButton simpleButton = SimpleButton.builder().pos(new Pos(50, 50)).style(style).build();
                simpleButton.setLeftClickAction(() -> {
                    MinecraftClient.getInstance().player.sendMessage(Text.of("asdas"), false);
                });

                scene.addElement(text);
                //scene.addElement(simpleButton);

                return scene;
            });

            customGUI.addGUIBlock("RMC", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);

                Background moreElementsBackground = Background.builder()
                        .color(Color.builder().hexValue("655c55").build())
                        .opacity(new Opacity(20))
                        .build();

                Style standardElementStyle = new Style();
                standardElementStyle.setBackground(moreElementsBackground);

                //Missions element
                SimplePane missionsPane = SimplePane.builder()
                        .pos(new Pos(6, 12))
                        .width(50)
                        .height(14)
                        .style(standardElementStyle)
                        .build();

                SimpleImage missionsLogo = SimpleImage.builder()
                        .pos(missionsPane.getFrame().getStartPos())
                        .width(12)
                        .height(12)
                        .identifier(new Identifier("customgui", "textures/gui/empty_img.png"))
                        .build();

                SimpleLabel missionsLogoLabel = SimpleLabel.builder()
                        .position(AbstractNode.Position.RIGHT)
                        .text("MISSIONS")
                        .build(missionsLogo);

                missionsPane.addNode(missionsLogo);
                missionsPane.addNode(missionsLogoLabel);

                //Create teams element
                SimplePane createTeamsPane = SimplePane.builder()
                        .pos(new Pos(96, 467))
                        .width(100)
                        .height(20)
                        .style(standardElementStyle)
                        .build();

                SimpleText createTeamsText = SimpleText.builder()
                        .text("CREATE TEAM")
                        .build();

                createTeamsPane.addNode(createTeamsText);

                //Armor slots element
                SimpleSlot.Factory slotFactory = SimpleSlot.factory(36, 36, inventory);
                UnmodifiableLinearSlotContainer.Builder<SimpleSlot> armorSlotContainerBuilder = UnmodifiableLinearSlotContainer.builder();

                UnmodifiableLinearSlotContainer<SimpleSlot> armorSlotContainer = armorSlotContainerBuilder
                        .size(7)
                        .build(new Pos(192, 392), slotFactory, 0, 0, 0, 0, 0, 0, 0);

                scene.addElement(missionsPane);
                /*scene.addElement(createTeamsPane);
                scene.addElement(armorSlotContainer);*/

                return scene;
            });

            customGUI.setActiveScene("RMC", RendererType.SCREEN);
        });
    }
}
