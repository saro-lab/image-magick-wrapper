package me.saro.imw.comm

class ImageMagickException(cause: Throwable) : RuntimeException(cause) {
    constructor(message: String) : this(RuntimeException(message))
    constructor(message: String, cause: Throwable) : this(RuntimeException(message, cause))
}