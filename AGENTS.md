# Serene Seasons Better Winter — инструкции для AI-агентов

Порт Fabric 1.21.1 мода Maeiro. Лицензия GPL-3.0-only, её не менять и не закрывать код. Общие правила — `../../AGENTS.md`.

Идентификатор мода `serenebetterwinter`: в идентификаторе нет подчёркиваний. У оригинала был `serene_better_winter`.

Сезон читается отражением `SeasonHelper.getSeasonState`, без зависимости компиляции на jar Serene Seasons. Миксины Java: процессор Loom пишет intermediary-имена. В Kotlin их не переписывать без `patchKotlinRefmap`.

Embeddium и LOD Distant Horizons в этот порт не входят: у Embeddium нет Fabric, а миксин оригинала целится в Forge-классы Distant Horizons. Скрытие листа идёт через `getRenderShape`, снег и подвесы — через `ModelBlockRenderer.tesselateBlock`.

Сборка — Gradle 8.11, JDK 21. McnCDN нет.

## Поиск готового

Прежде чем писать своё, портировать или декомпилировать, найди готовый мод, порт или форк. Веб-поиск (Google, Perplexity) малоизвестные репозитории GitHub не видит: репозиторий без звёзд и без ссылки с уже проиндексированной страницы в их индекс не попадает. Ищи каталог GitHub. Перед поиском прочитай https://github.com/aaddrick/gh-cli-search/blob/main/skills/gh-search-repos/SKILL.md и для исходников https://github.com/aaddrick/gh-cli-search/blob/main/skills/gh-search-code/SKILL.md. Нижний порог звёзд не ставь: примеры `stars:>N` в скилле применяй только если владелец просил известные проекты. Скилл https://github.com/py-sit/github-search-skill отбрасывает репозитории без лицензии, без свежих коммитов и учебные, поэтому для порта и малоизвестного мода его не открывай. Сделай несколько запросов: `gh search repos` по имени, `--match readme` и `gh search code` по mod id; для порта добавь `--include-forks true` и список форков исходного репозитория. Дефис в имени совпадает со словами запроса. Минус в начале квалификатора — исключение: такой запрос отделяй `--`, в PowerShell ещё `gh --%`. Пустое описание, ноль звёзд и пустые topics результат не отменяют. Опубликованные моды дополнительно смотри в поиске Modrinth: исходник порта часто есть только на GitHub. Свою реализацию не начинай, пока этот поиск не сделан.

## Modrinth

На Modrinth у каждой пары версии Minecraft и загрузчика одна версия: самая новая. Релиз снимает прежние версии этого слота и ставит на их место текущий jar.
