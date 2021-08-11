package net.kunmc.lab.bugmod.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;

public class GlitchEffect extends StatusEffect {
    public GlitchEffect() {
        super(
                StatusEffectType.HARMFUL, // whether beneficial or harmful for entities
                0x98D982); // color in RGB
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (entity instanceof PlayerEntity) {
            ((PlayerEntity) entity).addExperience(1 << amplifier); // Higher amplifier gives you EXP faster
        }
    }
}
