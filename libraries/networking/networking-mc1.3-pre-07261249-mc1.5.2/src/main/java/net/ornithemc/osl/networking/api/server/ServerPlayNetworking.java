package net.ornithemc.osl.networking.api.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;

import net.ornithemc.osl.core.api.util.function.IOConsumer;
import net.ornithemc.osl.networking.impl.server.ServerPlayNetworkingImpl;

public final class ServerPlayNetworking {

	/**
	 * Register a listener to receive data from the server through the given channel.
	 * This listener will only be called from the main thread.
	 */
	public static void registerListener(String channel, StreamListener listener) {
		ServerPlayNetworkingImpl.registerListener(channel, listener);
	}

	/**
	 * Register a listener to receive data from the server through the given channel.
	 * This listener will only be called from the main thread.
	 */
	public static void registerListenerRaw(String channel, ByteArrayListener listener) {
		ServerPlayNetworkingImpl.registerListener(channel, listener);
	}

	/**
	 * Remove the listener registered to the given channel.
	 */
	public static void unregisterListener(String channel) {
		ServerPlayNetworkingImpl.unregisterListener(channel);
	}

	/**
	 * Check whether the given channel is open for data to be sent through it.
	 * This method will return {@code false} if the client has no listeners for
	 * the given channel.
	 */
	public static boolean canSend(ServerPlayerEntity player, String channel) {
		return ServerPlayNetworkingImpl.canSend(player, channel);
	}

	/**
	 * Send a packet to the given player through the given channel. The writer
	 * will only be called if the channel is open.
	 */
	public static void send(ServerPlayerEntity player, String channel, IOConsumer<DataOutputStream> writer) {
		ServerPlayNetworkingImpl.send(player, channel, writer);
	}

	/**
	 * Send a packet to the given player through the given channel.
	 */
	public static void send(ServerPlayerEntity player, String channel, byte[] data) {
		ServerPlayNetworkingImpl.send(player, channel, data);
	}

	/**
	 * Send a packet to the given players through the given channel. The writer
	 * will only be called if the channel is open for at least one player.
	 */
	public static void send(Iterable<ServerPlayerEntity> players, String channel, IOConsumer<DataOutputStream> writer) {
		ServerPlayNetworkingImpl.send(players, channel, writer);
	}

	/**
	 * Send a packet to the given players through the given channel.
	 */
	public static void send(Iterable<ServerPlayerEntity> players, String channel, byte[] data) {
		ServerPlayNetworkingImpl.send(players, channel, data);
	}

	/**
	 * Send a packet to the players in the given dimension through the given
	 * channel. The writer will only be called if the channel is open for at
	 * least one player.
	 */
	public static void send(int dimension, String channel, IOConsumer<DataOutputStream> writer) {
		ServerPlayNetworkingImpl.send(dimension, channel, writer);
	}

	/**
	 * Send a packet to the players in the given dimension through the given
	 * channel.
	 */
	public static void send(int dimension, String channel, byte[] data) {
		ServerPlayNetworkingImpl.send(dimension, channel, data);
	}

	/**
	 * Send a packet to all players through the given channel. The writer will
	 * only be called if the channel is open for at least one player.
	 */
	public static void send(String channel, IOConsumer<DataOutputStream> writer) {
		ServerPlayNetworkingImpl.send(channel, writer);
	}

	/**
	 * Send a packet to all players through the given channel.
	 */
	public static void send(String channel, byte[] data) {
		ServerPlayNetworkingImpl.send(channel, data);
	}

	/**
	 * Send a packet to the given player through the given channel, without
	 * checking whether it is open.
	 * USE WITH CAUTION. Careless use of this method could lead to packet and log
	 * spam on the client.
	 */
	public static void doSend(ServerPlayerEntity player, String channel, IOConsumer<DataOutputStream> writer) {
		ServerPlayNetworkingImpl.doSend(player, channel, writer);
	}

	/**
	 * Send a packet to the given player through the given channel, without
	 * checking whether it is open.
	 * USE WITH CAUTION. Careless use of this method could lead to packet and log
	 * spam on the client.
	 */
	public static void doSend(ServerPlayerEntity player, String channel, byte[] data) {
		ServerPlayNetworkingImpl.doSend(player, channel, data);
	}

	/**
	 * Send a packet to the given players through the given channel, without
	 * checking whether it is open.
	 * USE WITH CAUTION. Careless use of this method could lead to packet and log
	 * spam on the client.
	 */
	public static void doSend(Iterable<ServerPlayerEntity> players, String channel, IOConsumer<DataOutputStream> writer) {
		ServerPlayNetworkingImpl.doSend(players, channel, writer);
	}

	/**
	 * Send a packet to the given players through the given channel, without
	 * checking whether it is open.
	 * USE WITH CAUTION. Careless use of this method could lead to packet and log
	 * spam on the client.
	 */
	public static void doSend(Iterable<ServerPlayerEntity> players, String channel, byte[] data) {
		ServerPlayNetworkingImpl.doSend(players, channel, data);
	}

	/**
	 * Send a packet to the players in the given dimension through the given
	 * channel, without checking whether it is open.
	 * USE WITH CAUTION. Careless use of this method could lead to packet and log
	 * spam on the client.
	 */
	public static void doSend(int dimension, String channel, IOConsumer<DataOutputStream> writer) {
		ServerPlayNetworkingImpl.doSend(dimension, channel, writer);
	}

	/**
	 * Send a packet to the players in the given dimension through the given
	 * channel, without checking whether it is open.
	 * USE WITH CAUTION. Careless use of this method could lead to packet and log
	 * spam on the client.
	 */
	public static void doSend(int dimension, String channel, byte[] data) {
		ServerPlayNetworkingImpl.doSend(dimension, channel, data);
	}

	/**
	 * Send a packet to all players through the given channel, without
	 * checking whether it is open.
	 * USE WITH CAUTION. Careless use of this method could lead to packet and log
	 * spam on the client.
	 */
	public static void doSend(String channel, IOConsumer<DataOutputStream> writer) {
		ServerPlayNetworkingImpl.doSend(channel, writer);
	}

	/**
	 * Send a packet to all players through the given channel, without
	 * checking whether it is open.
	 * USE WITH CAUTION. Careless use of this method could lead to packet and log
	 * spam on the client.
	 */
	public static void doSend(String channel, byte[] data) {
		ServerPlayNetworkingImpl.doSend(channel, data);
	}

	public interface StreamListener {

		/**
		 * Receive incoming data from the client.
		 *  
		 * @return 
		 *  Whether the data is consumed. Should only return {@code false} if the
		 *  data is completely ignored.
		 */
		boolean handle(MinecraftServer server, ServerPlayNetworkHandler handler, ServerPlayerEntity player, DataInputStream data) throws IOException;

	}

	public interface ByteArrayListener {

		/**
		 * Receive incoming data from the client.
		 *  
		 * @return 
		 *  Whether the data is consumed. Should only return {@code false} if the
		 *  data is completely ignored.
		 */
		boolean handle(MinecraftServer server, ServerPlayNetworkHandler handler, ServerPlayerEntity player, byte[] data);

	}
}
