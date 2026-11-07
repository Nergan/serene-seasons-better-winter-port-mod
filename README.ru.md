# Serene Seasons Better Winter

Порт [Serene Seasons Better Winter](https://github.com/Maeiro/Serene-Seasons-Better-Winter) (Maeiro, GPL-3.0-only) на Fabric **1.21.1**. Оригинал — Forge 1.20.1.

Лиственные кроны пропадают поздней осенью и зимой. Ель и всё, в чьём имени есть jungle, остаётся. Снег на скрытом листе не рисуется. Мелочь, которая висит под скрытым листом, например яблоко или кокон, тоже не рисуется. При `pass_through_hidden_blocks` (по умолчанию true) у этих листьев и снега на них нет коллизии, и они перестают перекрывать свет.

`config/serenebetterwinter-common.toml` задаёт подсезоны. По умолчанию `LATE_AUTUMN, EARLY_WINTER, MID_WINTER, LATE_WINTER`. Файл читают и клиент, и сервер. Сетевого пакета нет: выделенный сервер и клиенты читают каждый свою копию, и умолчания совпадают.

У Embeddium нет сборки Fabric, поэтому этот путь рендера в порт не вошёл. Скрытие листьев в LOD Distant Horizons тоже не вошло. Для ванильного рендера листья невидимы, в том числе когда Sodium спрашивает форму отрисовки блока. Сезонные цвета LOD — это другой мод, Serene Seasons x Distant Horizons.

Нужны [Serene Seasons](https://modrinth.com/mod/serene-seasons) Fabric 1.21.1, Fabric API и Fabric Language Kotlin. Мод ставится и на клиент, и на сервер.

## Сборка

JDK 21 и Gradle 8.11 из этого каталога: `gradle build --stacktrace`.
