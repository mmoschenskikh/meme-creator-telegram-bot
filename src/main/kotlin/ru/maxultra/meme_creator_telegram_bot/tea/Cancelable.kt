package ru.maxultra.meme_creator_telegram_bot.tea

import kotlinx.coroutines.Job

interface Cancelable {
    fun cancel()
}

fun Job.toCancelable() = object : Cancelable {
    override fun cancel() {
        this@toCancelable.cancel()
    }
}
