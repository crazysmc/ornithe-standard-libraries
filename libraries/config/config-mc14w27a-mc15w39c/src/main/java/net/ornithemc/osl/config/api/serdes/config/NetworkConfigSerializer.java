package net.ornithemc.osl.config.api.serdes.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.network.PacketByteBuf;

import net.ornithemc.osl.config.api.config.Config;
import net.ornithemc.osl.config.api.config.option.Option;
import net.ornithemc.osl.config.api.config.option.group.OptionGroup;
import net.ornithemc.osl.config.api.serdes.SerializationSettings;
import net.ornithemc.osl.config.api.serdes.config.option.NetworkOptionSerializer;
import net.ornithemc.osl.config.api.serdes.config.option.NetworkOptionSerializers;

public class NetworkConfigSerializer implements ConfigSerializer<PacketByteBuf> {

	private static final int STRING_MAX_LENGTH = Short.MAX_VALUE;

	@Override
	public void serialize(Config config, SerializationSettings settings, PacketByteBuf buffer) throws IOException {
		if (!settings.skipConfigMetadata) {
			buffer.writeString(config.getName());
			buffer.writeInt(config.getVersion());
		}

		buffer.writeInt(config.getGroups().size());

		for (OptionGroup group : config.getGroups()) {
			buffer.writeString(group.getName());
			serializeGroup(group, settings, buffer);
		}
	}

	private void serializeGroup(OptionGroup group, SerializationSettings settings, PacketByteBuf buffer) throws IOException {
		List<Option> options = new ArrayList<>();

		for (Option option : group.getOptions()) {
			if (!settings.skipDefaultOptions || !option.isDefault()) {
				options.add(option);
			}
		}

		buffer.writeInt(options.size());

		for (Option option : options) {
			buffer.writeString(option.getName());
			serializeOption(option, settings, buffer);
		}
	}

	private <O extends Option> void serializeOption(O option, SerializationSettings settings, PacketByteBuf buffer) throws IOException {
		NetworkOptionSerializer<O> serializer = NetworkOptionSerializers.get(option.getClass());

		if (serializer == null) {
			throw new IOException("don't know how to serialize option " + option);
		} else {
			serializer.serialize(option, settings, buffer);
		}
	}

	@Override
	public void deserialize(Config config, SerializationSettings settings, PacketByteBuf buffer) throws IOException {
		if (!settings.skipConfigMetadata) {
			String name = buffer.readString(STRING_MAX_LENGTH);
			int version = buffer.readInt();
		}

		int groupCount = buffer.readInt();

		for (int i = 0; i < groupCount; i++) {
			String groupName = buffer.readString(STRING_MAX_LENGTH);
			OptionGroup group = config.getGroup(groupName);

			if (group == null) {
				throw new IOException("unknown group " + groupName);
			}

			deserializeGroup(group, settings, buffer);
		}
	}

	private void deserializeGroup(OptionGroup group, SerializationSettings settings, PacketByteBuf buffer) throws IOException {
		int optionCount = buffer.readInt();

		for (int i = 0; i < optionCount; i++) {
			String optionName = buffer.readString(STRING_MAX_LENGTH);
			Option option = group.getOption(optionName);

			if (option == null) {
				throw new IOException("unknown option " + optionName + " in group " + group.getName());
			}

			deserializeOption(option, settings, buffer);
		}
	}

	private <O extends Option> void deserializeOption(O option, SerializationSettings settings, PacketByteBuf buffer) throws IOException {
		NetworkOptionSerializer<O> serializer = NetworkOptionSerializers.get(option.getClass());

		if (serializer == null) {
			throw new IOException("don't know how to deserialize option " + option);
		} else {
			serializer.deserialize(option, settings, buffer);
		}
	}
}
