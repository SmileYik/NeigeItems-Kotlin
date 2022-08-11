package pers.neige.neigeitems.section.impl

import org.bukkit.OfflinePlayer
import org.bukkit.configuration.ConfigurationSection
import pers.neige.neigeitems.section.SectionParser
import pers.neige.neigeitems.utils.SectionUtils.parseSection

/**
 * 字符串节点解析器
 */
object StringsParser : SectionParser() {
    override val id: String = "strings"

    override fun onRequest(
        data: ConfigurationSection,
        cache: HashMap<String, String>?,
        player: OfflinePlayer?,
        sections: ConfigurationSection?
    ): String? {
        return handler(cache, player, sections, true, data.getStringList("values"))
    }

    override fun onRequest(
        args: List<String>,
        cache: HashMap<String, String>?,
        player: OfflinePlayer?,
        sections: ConfigurationSection?
    ): String {
        return handler(cache, player, sections, false, args) ?: "<$id::${args.joinToString("_")}>"
    }

    private fun handler(cache: HashMap<String, String>?,
                        player: OfflinePlayer?,
                        sections: ConfigurationSection?,
                        parse: Boolean,
                        values: List<String>): String? {
        return when {
            values.isEmpty() -> null
            else -> values[(values.indices).random()].parseSection(parse, cache, player, sections)
        }
    }
}