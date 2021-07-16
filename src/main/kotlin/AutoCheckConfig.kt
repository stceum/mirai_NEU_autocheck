package me.stceum.neu_autocheck

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value

object AutoCheckConfig : AutoSavePluginConfig("AutoCheckConfig") {
    var botQq: Long by value(1234567890L)
    var checkTime: String by value("6:00:00")
//    val IdPasswdLists: Map<String, String> by value(mapOf("20180000" to "abcdefg"))
    var targetGroups: MutableList<Long> by value(mutableListOf(-1L))
    var idPasswdLists: Map<String, String> by value()
}