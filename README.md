# Meme Creator Telegram Bot

A bot for making up memes without leaving Telegram.

## What this bot can do?

This bot creates a meme from image and text sent by user.
Possible inputs are:

- `/start` — lets bot greet you:
  ![](img/greeting.png)
- sending image with caption — the bot will try to generate a meme:
  ![](img/sample.png)

Any other input will be regarded as incorrect.

## Getting started

### Setting up credentials

First, provide your own [Meme Generation API](https://memebuild.com/api) and [Telegram bot](https://t.me/BotFather)
credentials.
For Meme Generation API key, set `ru.maxultra.meme_creator_telegram_bot.api.config.MemeApiToken.MEME_API_TOKEN`:

```kotlin
internal const val MEME_API_TOKEN: String = "0123456789abcdefghijklmnopqrst"
```

For Telegram bot credentials, set `ru.maxultra.meme_creator_telegram_bot.tg.TelegramBotSettingsKt.TELEGRAM_BOT_TOKEN`
and `ru.maxultra.meme_creator_telegram_bot.tg.TelegramBotSettingsKt.TELEGRAM_BOT_USERNAME`:

```kotlin
internal const val TELEGRAM_BOT_TOKEN: String = "0123456789:abcdefghijklmnopqrstuvwxyzABCDEFGJI"
internal const val TELEGRAM_BOT_USERNAME: String = "SomeCoolBotUsername"
```

### Running with Docker

First, build the fat jar image:

```
docker build -t meme-creator-telegram-bot .
```

Then, run the application:

```
docker run meme-creator-telegram-bot
```

### Running without Docker

First, build the fat jar image with Gradle:

```
./gradlew fatJar
```

Then, run the application:

```
java -jar ./build/libs/meme-creator-telegram-bot-$VERSION.jar
```
