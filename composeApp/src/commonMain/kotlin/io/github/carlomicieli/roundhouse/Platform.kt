package io.github.carlomicieli.roundhouse

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform