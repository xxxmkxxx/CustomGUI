package com.xxxmkxxx.customgui.client;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.common.Config;
import com.xxxmkxxx.customgui.client.common.Register;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.SimpleScene;
import com.xxxmkxxx.customgui.client.hierarchy.style.Background;
import com.xxxmkxxx.customgui.client.hierarchy.style.Color;
import com.xxxmkxxx.customgui.client.hierarchy.style.Opacity;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.button.ImagedButton;
import com.xxxmkxxx.customgui.client.ui.controls.button.SimpleButton;
import com.xxxmkxxx.customgui.client.ui.controls.image.SimpleImage;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SimpleSlot;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SlotFactory;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CustomGUIClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
            CustomGUI customGUI = new CustomGUI();
            Register.register(Config.getGuiName(), customGUI);

            customGUI.addGUIBlock("test", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);

                Style style = new Style();
                style.setColor(new Color("2ad43b"));
                style.setBackground(Background.builder().opacity(new Opacity(100)).type(Background.Type.COLORED).color(new Color("142615")).build());
                style.setOpacity(new Opacity(80));

                SimpleImage image = SimpleImage.builder()
                        .startPos(new Pos(150, 150))
                        .width(20).height(20)
                        .identifier(new Identifier("customgui", "textures/gui/img_1.png"))
                        .build();
                image.setLeftClickAction(() -> {
                    MinecraftClient.getInstance().player.sendMessage(Text.of("Iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiimage"), false);
                });

                SimpleButton button = SimpleButton.builder()
                        .startPos(new Pos(150, 150))
                        .text("test")
                        .build();
                button.setLeftClickAction(() -> {
                    MinecraftClient.getInstance().player.sendMessage(Text.of("teeeest"), false);
                });
                button.setStyle(style);

                ImagedButton imagedButton = ImagedButton.builder()
                        .image(image)
                        .text("aboba")
                        .pos(new Pos(150, 150))
                        .style(style)
                        .build();

                SimpleText simpleText = SimpleText.builder()
                        .pos(image.getFrame().getStopPos())
                        .style(style)
                        .build();

                /*EventBus.CHANGE_FRAME_EVENT.addHandler(button.getFrame(), (ChangeFrameEventHandler) () -> {
                    ((DynamicFrame)image.getFrame()).setStopPos(button.getFrame().getStopPos());
                });*/

                //scene.addElement(image);
                //scene.addElement(button);
                //scene.addElement(simpleText);
                scene.addElement(imagedButton);

                return scene;
            });
            customGUI.addGUIBlock("test2", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);

                SimpleSlot.Factory slotFactory = SimpleSlot.

                return scene;
            });

            customGUI.setActiveScene("test", customGUI.getScreenStage());
    }
}
