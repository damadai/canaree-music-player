package dev.olog.navigation.screens

private const val PREFIX = "dev.olog"

enum class FragmentScreen(val tag: String) {
    HOME("$PREFIX.home"),
    LIBRARY("$PREFIX.library"),
    SEARCH("$PREFIX.search"),
    PLAYLISTS("$PREFIX.playlists"),
    QUEUE("$PREFIX.queue"),

    DETAIL("$PREFIX.detail"),
    PLAYER("$PREFIX.player"),
    PLAYER_MINI("$PREFIX.player.mini"),
    SETTINGS("$PREFIX.settings"),
    ABOUT("$PREFIX.about"),
    ONBOARDING("$PREFIX.onboarding"),
    EQUALIZER("$PREFIX.equalizer"),
    EDIT_TRACK("$PREFIX.edit.track"),
    EDIT_ALBUM("$PREFIX.edit.album"),
    EDIT_ARTIST("$PREFIX.edit.artist");

    companion object {
        const val OWNERSHIP = PREFIX
    }

}