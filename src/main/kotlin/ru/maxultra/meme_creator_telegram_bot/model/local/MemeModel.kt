package ru.maxultra.meme_creator_telegram_bot.model.local

data class MemeModel(
    val topText: String? = null,
    val bottomText: String? = null,
    val imageUrl: String,
) {
    init {
        require(topText != null || bottomText != null)
    }
}
