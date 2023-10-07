package bot.inker.bukkit.nbt.internal.ref;

import bot.inker.bukkit.nbt.internal.annotation.CbVersion;
import bot.inker.bukkit.nbt.internal.annotation.HandleBy;
import net.minecraft.server.v1_12_R1.PacketListener;

@HandleBy(version = CbVersion.v1_12_R1, reference = "net/minecraft/server/v1_12_R1/PacketListenerPlayOut")
@HandleBy(version = CbVersion.v1_17_R1, reference = "net/minecraft/network/protocol/game/ClientGamePacketListener")
public interface RefPacketListenerPlayOut extends RefPacketListener {
}
