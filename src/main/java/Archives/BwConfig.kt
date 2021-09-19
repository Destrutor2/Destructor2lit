package Archives
//
//import kotlin.reflect.KProperty1
//
////created by: oscar at 18:21 of 20/08/2021
//
//open class SuperConfigClass{
//    open fun getProperty(path: String, config: BwConfig): Number {
//        return when {
//            path.startsWith("Fireball") -> readInstanceProperty<Number>(config.fireBallConfig, path.substringAfter('.'))
//            path.startsWith("Fireballjump") -> readInstanceProperty<Number>(config.fireballjumpConfig, path.substringAfter('.'))
//            else -> readInstanceProperty(config, path)
//        }
//    }
//}
//
//data class BwConfig(
//    var fireBallConfig: FireBallConfig,
//    var fireballjumpConfig: FireballjumpConfig,
//    var voidheight: Double,
//    var golemdamagemultiplier: Double,
//    var golemhealthmultiplier: Double,
//    var golemspeedmultiplier: Double,
//    var buildlimit: Int,
//    var builddownlimit: Int,
//    var attacktagstaytimemillis: Int,
//    var minbowcharge: Float,
//    var traptriggerradius: Double,
//    var popuptowerspeedmultiplier: Byte,
//    var teamGeneratorsConfig: TeamGeneratorsConfig
//):SuperConfigClass()
//
//data class FireBallConfig(
//    var knockbackradius: Double,
//    var knockbackmodifier: Double,
//    var explosionheightmodifier: Double,
//    var explosionpower: Double,
//    var fireradius: Int,
//    var fireprob: Float,
//    var speed: Double
//)
//
//data class FireballjumpConfig(
//    var knockbackradius: Double,
//    var knockbackmodifier: Double,
//    var explosionheightmodifier: Double,
//    var throwermultiplier: Double,
//    var velocitymodifier: Double
//)
//
//data class TeamGeneratorsConfig(
//    var mingolddelay: Int,
//    var maxgolddelay: Int,
//    var maxirondelay: Int,
//    var tickstotry: Int
//)
//
//
//public fun getProperty(path: String, config: BwConfig): Number {
//    return when {
//        path.startsWith("Fireball") -> readInstanceProperty<Number>(config.fireBallConfig, path.substringAfter('.'))
//        path.startsWith("Fireballjump") -> readInstanceProperty<Number>(config.fireballjumpConfig, path.substringAfter('.'))
//        else -> readInstanceProperty(config, path)
//    }
//}
//
//@Suppress("UNCHECKED_CAST")
//fun <R> readInstanceProperty(instance: Any, propertyName: String): R {
//    val property = instance::class.members
//        // don't cast here to <Any, R>, it would succeed silently
//        .first { it.name == propertyName } as KProperty1<Any, *>
//    // force a invalid cast exception if incorrect type here
//    return property.get(instance) as R
//}