package net.ornithemc.osl.config.api.serdes;

import net.ornithemc.osl.config.api.ConfigRegistries;
import net.ornithemc.osl.core.api.json.JsonFile;
import net.ornithemc.osl.core.api.registry.Registries;
import net.ornithemc.osl.core.api.registry.Registry;
import net.ornithemc.osl.core.api.registry.RegistryKey;

public final class SerializerTypes {

	private static final Registry<RegistryKey, SerializerType<?>> REGISTRY = Registries.register(ConfigRegistries.SERIALIZER_TYPE);

	public static final FileSerializerType<JsonFile> JSON = register("json", path -> new JsonFile(path));

	public static <T extends SerializerType<?>> T register(String name, T type) {
		return Registries.registerMapping(REGISTRY, RegistryKey.of(ConfigRegistries.SERIALIZER_TYPE, name), type);
	}

	public static <T extends SerializerType<?>> T get(String name) {
		return get(RegistryKey.of(ConfigRegistries.SERIALIZER_TYPE, name));
	}

	public static <T extends SerializerType<?>> T get(RegistryKey key) {
		return Registries.getMapping(REGISTRY, key);
	}

	public static RegistryKey getKey(SerializerType<?> type) {
		return Registries.getKey(REGISTRY, type);
	}
}
