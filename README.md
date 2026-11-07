# Serene Seasons Better Winter

A Fabric **1.21.1** port of [Serene Seasons Better Winter](https://github.com/Maeiro/Serene-Seasons-Better-Winter) by Maeiro. License: GPL-3.0-only. The original is Forge 1.20.1.

Deciduous leaves disappear in late autumn and winter. Spruce and anything with jungle in the name stay. Snow sitting on a hidden leaf is not drawn. Small blocks hanging under a hidden leaf, such as an apple or a cocoon, are not drawn either. With `pass_through_hidden_blocks` (default true) those hidden leaves and the snow on them have no collision, and they stop blocking light.

`config/serenebetterwinter-common.toml` lists the sub-seasons. The default is `LATE_AUTUMN, EARLY_WINTER, MID_WINTER, LATE_WINTER`. The same file is read by the client and the server. There is no network packet: a dedicated server and its clients each read their own copy, and the defaults match.

Embeddium has no Fabric build, so that renderer path is not in this port. Distant Horizons LOD leaf hiding is not in this port either. The leaves are invisible to the vanilla renderer, including when Sodium asks for the block render shape. Seasonal LOD colors are a different mod, Serene Seasons x Distant Horizons.

Required: [Serene Seasons](https://modrinth.com/mod/serene-seasons) Fabric 1.21.1, Fabric API and Fabric Language Kotlin. Put the mod on the client and the server.

## Building

JDK 21 and Gradle 8.11, from this directory: `gradle build --stacktrace`.
