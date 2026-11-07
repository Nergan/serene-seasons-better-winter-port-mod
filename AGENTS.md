# Serene Seasons Better Winter — инструкции для AI-агентов

Порт Fabric 1.21.1 мода Maeiro. Лицензия GPL-3.0-only, её не менять и не закрывать код. Общие правила — `../AGENTS.md`.

Идентификатор мода `serenebetterwinter`: в идентификаторе нет подчёркиваний. У оригинала был `serene_better_winter`.

Сезон читается отражением `SeasonHelper.getSeasonState`, без зависимости компиляции на jar Serene Seasons. Миксины Java: процессор Loom пишет intermediary-имена. В Kotlin их не переписывать без `patchKotlinRefmap`.

Embeddium и LOD Distant Horizons в этот порт не входят: у Embeddium нет Fabric, а миксин оригинала целится в Forge-классы Distant Horizons. Скрытие листа идёт через `getRenderShape`, снег и подвесы — через `ModelBlockRenderer.tesselateBlock`.

Сборка — Gradle 8.11, JDK 21. McnCDN нет.
