package net.ornithemc.osl.networking.impl.mixin.common;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.Connection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;

import net.ornithemc.osl.networking.api.server.ServerConnectionEvents;
import net.ornithemc.osl.networking.impl.CommonChannels;
import net.ornithemc.osl.networking.impl.NetworkingInitializer;
import net.ornithemc.osl.networking.impl.interfaces.mixin.IPlayerManager;
import net.ornithemc.osl.networking.impl.server.ServerPlayNetworkingImpl;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin implements IPlayerManager {

	@Shadow @Final private MinecraftServer server;
	@Shadow @Final private List<ServerPlayerEntity> players;

	@Inject(
		method = "onLogin",
		at = @At(
			value = "NEW",
			target = "Lnet/minecraft/network/packet/s2c/play/LoginS2CPacket;"
		)
	)
	private void osl$networking$registerChannels(Connection connection, ServerPlayerEntity player, CallbackInfo ci) {
		ServerPlayNetworkingImpl.doSend(player, CommonChannels.CHANNELS, data -> {
			NetworkingInitializer.writeChannels(data, ServerPlayNetworkingImpl.LISTENERS.keySet());
		});
	}

	@Inject(
		method = "onLogin",
		at = @At(
			value = "TAIL"
		)
	)
	private void osl$networking$handleLogin(Connection connection, ServerPlayerEntity player, CallbackInfo ci) {
		ServerConnectionEvents.LOGIN.invoker().accept(server, player);
	}

	@Override
	public List<ServerPlayerEntity> osl$networking$getAll() {
		return players;
	}
}
