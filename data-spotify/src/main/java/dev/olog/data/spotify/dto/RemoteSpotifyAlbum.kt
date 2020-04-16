package dev.olog.data.spotify.dto

internal data class RemoteSpotifyAlbum(
    val album_type: String,
    val id: String,
    val images: List<RemoteSpotifyImage>,
    val name: String,
    val release_date: String,
    val total_tracks: Int,
    val uri: String
)