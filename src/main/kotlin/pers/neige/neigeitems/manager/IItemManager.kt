package pers.neige.neigeitems.manager

import org.bukkit.OfflinePlayer
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import pers.neige.neigeitems.item.ItemGenerator
import pers.neige.neigeitems.item.ItemInfo
import java.io.File

interface IItemManager {

    fun files(): ArrayList<File>

    fun itemIds(): List<String>

    fun itemIdsRaw(): List<String>

    fun itemAmount(): Int

    /**
     * 重载物品管理器
     */
    fun reload()

    /**
     * 获取物品原始Config的克隆
     *
     * @param id 物品ID
     * @return 物品原始Config的克隆
     */
    fun getOriginConfig(id: String): ConfigurationSection?

    /**
     * 获取物品原始Config
     *
     * @param id 物品ID
     * @return 物品原始Config
     */
    fun getRealOriginConfig(id: String): ConfigurationSection?

    /**
     * 获取物品生成器
     *
     * @param id 物品ID
     * @return 物品生成器
     */
    fun getItem(id: String): ItemGenerator?

    /**
     * 获取物品
     *
     * @param id 物品ID
     * @return 物品
     */
    fun getItemStack(id: String): ItemStack?

    /**
     * 获取物品
     *
     * @param id 物品ID
     * @param player 用于解析物品的玩家
     * @return 物品
     */
    fun getItemStack(id: String, player: OfflinePlayer?): ItemStack?

    /**
     * 获取物品
     *
     * @param id 物品ID
     * @param data 用于解析物品的指向数据
     * @return 物品
     */
    fun getItemStack(id: String, data: String?): ItemStack?

    /**
     * 获取物品
     *
     * @param id 物品ID
     * @param data 用于解析物品的指向数据
     * @return 物品
     */
    fun getItemStack(id: String, data: MutableMap<String, String>?): ItemStack?

    /**
     * 获取物品
     *
     * @param id 物品ID
     * @param player 用于解析物品的玩家
     * @param data 用于解析物品的指向数据
     * @return 物品
     */
    fun getItemStack(id: String, player: OfflinePlayer?, data: String?): ItemStack?

    /**
     * 获取物品
     *
     * @param id 物品ID
     * @param player 用于解析物品的玩家
     * @param data 用于解析物品的指向数据
     * @return 物品
     */
    fun getItemStack(id: String, player: OfflinePlayer?, data: MutableMap<String, String>?): ItemStack?

    /**
     * 是否存在对应ID的物品
     *
     * @param id 物品ID
     * @return 是否存在对应ID的物品
     */
    fun hasItem(id: String): Boolean

    /**
     * 保存物品
     *
     * @param itemStack 保存物品
     * @param id 物品ID
     * @param file 保存文件
     * @param config 文件转换来的YamlConfiguration
     * @param cover 是否覆盖
     * @return 保存结果
     */
    fun saveItem(
        itemStack: ItemStack?,
        id: String,
        file: File,
        config: YamlConfiguration,
        cover: Boolean
    ): SaveResult

    /**
     * 保存物品
     *
     * @param itemStack 保存物品
     * @param id 物品ID
     * @param path 保存路径
     * @param cover 是否覆盖
     * @return 保存结果
     */
    fun saveItem(itemStack: ItemStack, id: String, path: String = "$id.yml", cover: Boolean): SaveResult

    /**
     * 判断ItemStack是否为NI物品并返回NI物品信息
     *
     * @return NI物品信息, 非NI物品返回null
     */
    fun isNiItem(itemStack: ItemStack?): ItemInfo?

    /**
     * 判断ItemStack是否为NI物品并返回NI物品信息
     *
     * @param parseData 是否将data解析为HashMap
     * @return NI物品信息, 非NI物品返回null
     */
    fun isNiItem(itemStack: ItemStack?, parseData: Boolean): ItemInfo?

    /**
     * 根据ItemStack获取对应的NI物品ID
     *
     * @return NI物品ID, 非NI物品返回null
     */
    fun getItemId(itemStack: ItemStack?): String?

    /**
     * 设置物品使用次数
     *
     * @param amount 使用次数
     */
    fun ItemStack.setCharge(amount: Int)

    /**
     * 添加物品使用次数
     *
     * @param amount 添加量(可为负)
     */
    fun ItemStack.addCharge(amount: Int)

    /**
     * 设置物品最大使用次数
     *
     * @param amount 最大使用次数
     */
    fun ItemStack.setMaxCharge(amount: Int)

    /**
     * 添加物品最大使用次数
     *
     * @param amount 添加量(可为负)
     */
    fun ItemStack.addMaxCharge(amount: Int)

    /**
     * 设置物品耐久值
     *
     * @param amount 耐久值
     */
    fun ItemStack.setCustomDurability(amount: Int)

    /**
     * 添加物品耐久值
     *
     * @param amount 添加量(可为负)
     */
    fun ItemStack.addCustomDurability(amount: Int)

    /**
     * 设置物品最大耐久值
     *
     * @param amount 最大耐久值
     */
    fun ItemStack.setMaxCustomDurability(amount: Int)

    /**
     * 添加物品最大耐久值
     *
     * @param amount 添加量(可为负)
     */
    fun ItemStack.addMaxCustomDurability(amount: Int)

    /**
     * 根据自定义耐久计算原版损伤值
     *
     * @param current 当前自定义耐久
     * @param max 当前最大自定义耐久
     * @return 对应的原版损伤值
     */
    fun ItemStack.checkDurability(current: Int, max: Int): Short

    /**
     * 根据自定义耐久设置原版损伤值
     *
     * @param current 当前自定义耐久
     * @param max 当前最大自定义耐久
     */
    fun ItemStack.refreshDurability(current: Int, max: Int)

    /**
     * 重构物品
     *
     * @param player 用于重构物品的玩家
     * @param sections 重构节点(值为null代表刷新该节点)
     */
    fun ItemStack.rebuild(player: OfflinePlayer, sections: MutableMap<String, String?>): Boolean

    /**
     * 重构物品
     *
     * @param player 用于重构物品的玩家
     * @param sections 重构节点(值为null代表刷新该节点)
     * @param protectNBT 需要保护的NBT(重构后不刷新), 可以填null
     * @return 物品是空气时返回false, 其余情况返回true
     */
    fun ItemStack.rebuild(
        player: OfflinePlayer, sections: MutableMap<String, String?>, protectNBT: List<String>?
    ): Boolean

    /**
     * 重构物品
     *
     * @param player 用于重构物品的玩家
     * @param protectSections 保留的节点ID
     * @param protectNBT 需要保护的NBT(重构后不刷新), 可以填null
     * @return 物品是空气时返回false, 其余情况返回true
     */
    fun ItemStack.rebuild(
        player: OfflinePlayer, protectSections: List<String>, protectNBT: List<String>?
    ): Boolean

    /**
     * 根据物品内的 options.update 配置进行物品更新
     *
     * @param player 用于重构物品的玩家
     * @param itemStack 物品本身
     * @param forceUpdate 是否忽略检测, 强制更新物品
     * @param sendMessage 更新后是否向玩家发送更新提示文本
     */
    fun update(
        player: Player, itemStack: ItemStack, forceUpdate: Boolean = false, sendMessage: Boolean = false
    )

    enum class SaveResult {
        SUCCESS,
        AIR,
        CONFLICT
    }
}