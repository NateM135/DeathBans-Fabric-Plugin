package com.natem135.deathbans.mixin;

import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.mojang.authlib.GameProfile;
import java.net.SocketAddress;

import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "checkCanJoin", at = @At("HEAD"))
    private void checkCanJoin(SocketAddress address, GameProfile profile, CallbackInfoReturnable<Text> cir) {
        PlayerManager thisObject = (PlayerManager)(Object)this;
        BannedPlayerEntry entry = thisObject.getUserBanList().get(profile);
    }
}
