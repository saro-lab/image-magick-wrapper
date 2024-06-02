package me.saro.imw.model

data class ImageInfo private constructor(
    private val map: MutableMap<String, Any> = mutableMapOf(),
) {
    companion object {
        fun create(verboseOutput: String, formats: List<String>): List<ImageInfo> {
            return verboseOutput.trim().lines().map {
                val data = it.split(" ")
                ImageInfo(formats.mapIndexed { index, format -> format to data[index] }.toMap().toMutableMap())
            }
        }
    }

    val frameNumber = map["frameIndex"] as String
    val format = map["format"] as String
    val size = map["size"] as String
    val width = size.split("x")[0].toInt()
    val height = size.split("x")[1].toInt()
    val depth = map["depth"] as String
}