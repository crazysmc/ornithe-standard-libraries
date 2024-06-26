package net.ornithemc.osl.config.api.serdes;

import io.netty.buffer.Unpooled;

import net.minecraft.nbt.NbtElement;
import net.minecraft.network.PacketByteBuf;

public class MinecraftSerializerTypes {

	public static final MemorySerializerType<NbtElement>    NBT     = SerializerTypes.register("nbt"    , () -> null);
	public static final MemorySerializerType<PacketByteBuf> NETWORK = SerializerTypes.register("network", () -> new PacketByteBuf(Unpooled.buffer()));

}
