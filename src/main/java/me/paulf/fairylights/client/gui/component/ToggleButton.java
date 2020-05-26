package me.paulf.fairylights.client.gui.component;

import com.mojang.blaze3d.systems.*;
import me.paulf.fairylights.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.widget.button.*;

public class ToggleButton extends Button {
    private final int u;

    private final int v;

    private boolean value;

    private boolean pressed;

    public ToggleButton(final int x, final int y, final int u, final int v, final String msg, final Button.IPressable pressable) {
        super(x, y, 20, 20, msg, pressable);
        this.u = u;
        this.v = v;
    }

    public void setValue(final boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    @Override
    public void onPress() {
        this.value = !this.value;
        this.pressed = true;
        super.onPress();
    }

    @Override
    public void onRelease(final double mouseX, final double mouseY) {
        this.pressed = false;
    }

    @Override
    public void renderButton(final int mouseX, final int mouseY, final float delta) {
        if (this.visible) {
            Minecraft.getInstance().getTextureManager().bindTexture(EditLetteredConnectionScreen.WIDGETS_TEXTURE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            final int t;
            if (this.isHovered) {
                if (this.pressed) {
                    t = 2;
                } else {
                    t = 1;
                }
            } else {
                if (this.value) {
                    t = 2;
                } else {
                    t = 0;
                }
            }
            this.blit(this.x, this.y, this.u, this.v + this.height * t, this.width, this.height);
        }
    }
}
