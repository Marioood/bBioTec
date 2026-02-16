package net.marioood.bbiotec.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.marioood.bbiotec.BBioTec;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class IrradiatorScreen extends AbstractContainerScreen<IrradiatorMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BBioTec.MODID, "textures/gui/container/irradiator.png");

    private static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BBioTec.MODID, "textures/gui/container/irradiator_arrow.png");

    private static final ResourceLocation FX_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BBioTec.MODID, "textures/gui/container/irradiator_fx.png");

    public IrradiatorScreen(IrradiatorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        if(menu.isCrafting()) {
            RenderSystem.setShaderTexture(0, ARROW_TEXTURE);
            pGuiGraphics.blit(ARROW_TEXTURE, x + 89, y + 35, 0, 0, menu.getScaledArrowProgress(), 16, 24, 16);

            double fxProg = menu.getFxProgress(pPartialTick) * Math.PI * 2;
            float fxAlp1 = (float)(Math.cos(fxProg * Math.PI) / 2 + 0.5);
            float fxAlp2 = (float)(Math.cos((fxProg + 0.5) * Math.PI) / 2 + 0.5);
            int fxCol1 = 0x00BFEA52 | ((byte)(fxAlp1 * 255) << 24);
            int fxCol2 = 0x00BFEA52 | ((byte)(fxAlp2 * 255) << 24);
            //pGuiGraphics.setColor(1f, 1f, 1f, fxAlp1);
            //pGuiGraphics.blit(FX_TEXTURE, x + 25, y + 14, 0, 0, 62, 56, 62, 174);
            pGuiGraphics.fillGradient(x + 26, y + 15, x + 16 + 26, y + 16 + 15, 0x00000000, fxCol1);
            pGuiGraphics.fillGradient(x + 70, y + 15, x + 16 + 70, y + 16 + 15, 0x00000000, fxCol1);
            pGuiGraphics.fillGradient(x + 26, y + 55, x + 16 + 26, y + 16 + 55, 0x00000000, fxCol1);
            pGuiGraphics.fillGradient(x + 70, y + 55, x + 16 + 70, y + 16 + 55, 0x00000000, fxCol1);

            pGuiGraphics.fillGradient(x + 46, y + 33, x + 20 + 46, y + 20 + 33, 0x00000000, fxCol2);
            //RenderSystem.setShaderColor(1f, 1f, 1f, fxAlp2);
            //pGuiGraphics.blit(FX_TEXTURE, x + 25, y + 14, 0, 56, 62, 56, 62, 174);
            //RenderSystem.setShaderColor(1f, 1f, 1f, fxAlp3);
            //pGuiGraphics.blit(FX_TEXTURE, x + 25, y + 14, 0, 56 * 2, 62, 56, 62, 174);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
