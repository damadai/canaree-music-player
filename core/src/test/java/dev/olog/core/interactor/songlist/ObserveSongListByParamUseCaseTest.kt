package dev.olog.core.interactor.songlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import dev.olog.core.MediaId
import dev.olog.core.MediaIdCategory
import dev.olog.core.gateway.podcast.PodcastAlbumGateway
import dev.olog.core.gateway.podcast.PodcastArtistGateway
import dev.olog.core.gateway.podcast.PodcastGateway
import dev.olog.core.gateway.podcast.PodcastPlaylistGateway
import dev.olog.core.gateway.track.*
import org.junit.Test

class ObserveSongListByParamUseCaseTest {

    private val folderGateway = mock<FolderGateway>()
    private val playlistGateway = mock<PlaylistGateway>()
    private val songGateway = mock<SongGateway>()
    private val albumGateway = mock<AlbumGateway>()
    private val artistGateway = mock<ArtistGateway>()
    private val genreGateway = mock<GenreGateway>()

    private val podcastPlaylistGateway = mock<PodcastPlaylistGateway>()
    private val podcastGateway = mock<PodcastGateway>()
    private val podcastAlbumGateway = mock<PodcastAlbumGateway>()
    private val podcastArtistGateway = mock<PodcastArtistGateway>()

    private val sut = ObserveSongListByParamUseCase(
        folderGateway, playlistGateway, songGateway, albumGateway, artistGateway,
        genreGateway, podcastPlaylistGateway, podcastGateway,
        podcastAlbumGateway, podcastArtistGateway
    )

    @Test
    fun testFolders(){
        // given
        val path = "path"
        val mediaId = MediaId.createCategoryValue(MediaIdCategory.FOLDERS, path)

        // when
        sut(mediaId)

        verify(folderGateway).observeTrackListByParam(path)
        verifyNoMoreInteractions(folderGateway)
        verifyZeroInteractions(playlistGateway)
        verifyZeroInteractions(songGateway)
        verifyZeroInteractions(albumGateway)
        verifyZeroInteractions(artistGateway)
        verifyZeroInteractions(genreGateway)

        verifyZeroInteractions(podcastPlaylistGateway)
        verifyZeroInteractions(podcastGateway)
        verifyZeroInteractions(podcastAlbumGateway)
        verifyZeroInteractions(podcastArtistGateway)
    }

    @Test
    fun testPlaylists(){
        // given
        val id = 1L
        val mediaId = MediaId.createCategoryValue(MediaIdCategory.PLAYLISTS, id.toString())

        // when
        sut(mediaId)

        verify(playlistGateway).observeTrackListByParam(id)
        verifyZeroInteractions(folderGateway)
        verifyNoMoreInteractions(playlistGateway)
        verifyZeroInteractions(songGateway)
        verifyZeroInteractions(albumGateway)
        verifyZeroInteractions(artistGateway)
        verifyZeroInteractions(genreGateway)

        verifyZeroInteractions(podcastPlaylistGateway)
        verifyZeroInteractions(podcastGateway)
        verifyZeroInteractions(podcastAlbumGateway)
        verifyZeroInteractions(podcastArtistGateway)
    }

    @Test
    fun testSongs(){
        // given
        val id = 1L
        val mediaId = MediaId.createCategoryValue(MediaIdCategory.SONGS, id.toString())

        // when
        sut(mediaId)

        verify(songGateway).observeAll()
        verifyZeroInteractions(folderGateway)
        verifyZeroInteractions(playlistGateway)
        verifyNoMoreInteractions(songGateway)
        verifyZeroInteractions(albumGateway)
        verifyZeroInteractions(artistGateway)
        verifyZeroInteractions(genreGateway)

        verifyZeroInteractions(podcastPlaylistGateway)
        verifyZeroInteractions(podcastGateway)
        verifyZeroInteractions(podcastAlbumGateway)
        verifyZeroInteractions(podcastArtistGateway)
    }

    @Test
    fun testAlbums(){
        // given
        val id = 1L
        val mediaId = MediaId.createCategoryValue(MediaIdCategory.ALBUMS, id.toString())

        // when
        sut(mediaId)

        verify(albumGateway).observeTrackListByParam(id)
        verifyZeroInteractions(folderGateway)
        verifyZeroInteractions(playlistGateway)
        verifyZeroInteractions(songGateway)
        verifyNoMoreInteractions(albumGateway)
        verifyZeroInteractions(artistGateway)
        verifyZeroInteractions(genreGateway)

        verifyZeroInteractions(podcastPlaylistGateway)
        verifyZeroInteractions(podcastGateway)
        verifyZeroInteractions(podcastAlbumGateway)
        verifyZeroInteractions(podcastArtistGateway)
    }

    @Test
    fun testArtists(){
        // given
        val id = 1L
        val mediaId = MediaId.createCategoryValue(MediaIdCategory.ARTISTS, id.toString())

        // when
        sut(mediaId)

        verify(artistGateway).observeTrackListByParam(id)
        verifyZeroInteractions(folderGateway)
        verifyZeroInteractions(playlistGateway)
        verifyZeroInteractions(songGateway)
        verifyZeroInteractions(albumGateway)
        verifyNoMoreInteractions(artistGateway)
        verifyZeroInteractions(genreGateway)

        verifyZeroInteractions(podcastPlaylistGateway)
        verifyZeroInteractions(podcastGateway)
        verifyZeroInteractions(podcastAlbumGateway)
        verifyZeroInteractions(podcastArtistGateway)
    }

    @Test
    fun testGenre(){
        // given
        val id = 1L
        val mediaId = MediaId.createCategoryValue(MediaIdCategory.GENRES, id.toString())

        // when
        sut(mediaId)

        verify(genreGateway).observeTrackListByParam(id)
        verifyZeroInteractions(folderGateway)
        verifyZeroInteractions(playlistGateway)
        verifyZeroInteractions(songGateway)
        verifyZeroInteractions(albumGateway)
        verifyZeroInteractions(artistGateway)
        verifyNoMoreInteractions(genreGateway)

        verifyZeroInteractions(podcastPlaylistGateway)
        verifyZeroInteractions(podcastGateway)
        verifyZeroInteractions(podcastAlbumGateway)
        verifyZeroInteractions(podcastArtistGateway)
    }

    @Test
    fun testPodcastPlaylists(){
        // given
        val id = 1L
        val mediaId = MediaId.createCategoryValue(MediaIdCategory.PODCASTS_PLAYLIST, id.toString())

        // when
        sut(mediaId)

        verify(podcastPlaylistGateway).observeTrackListByParam(id)
        verifyZeroInteractions(folderGateway)
        verifyZeroInteractions(playlistGateway)
        verifyZeroInteractions(songGateway)
        verifyZeroInteractions(albumGateway)
        verifyZeroInteractions(artistGateway)
        verifyZeroInteractions(genreGateway)

        verifyNoMoreInteractions(podcastPlaylistGateway)
        verifyZeroInteractions(podcastGateway)
        verifyZeroInteractions(podcastAlbumGateway)
        verifyZeroInteractions(podcastArtistGateway)
    }

    @Test
    fun testPodcasts(){
        // given
        val mediaId = MediaId.createCategoryValue(MediaIdCategory.PODCASTS, "")

        // when
        sut(mediaId)

        verify(podcastGateway).observeAll()
        verifyZeroInteractions(folderGateway)
        verifyZeroInteractions(playlistGateway)
        verifyZeroInteractions(songGateway)
        verifyZeroInteractions(albumGateway)
        verifyZeroInteractions(artistGateway)
        verifyZeroInteractions(genreGateway)

        verifyZeroInteractions(podcastPlaylistGateway)
        verifyNoMoreInteractions(podcastGateway)
        verifyZeroInteractions(podcastAlbumGateway)
        verifyZeroInteractions(podcastArtistGateway)
    }

    @Test
    fun testPodcastAlbum(){
        // given
        val id = 1L
        val mediaId = MediaId.createCategoryValue(MediaIdCategory.PODCASTS_ALBUMS, id.toString())

        // when
        sut(mediaId)

        verify(podcastAlbumGateway).observeTrackListByParam(id)
        verifyZeroInteractions(folderGateway)
        verifyZeroInteractions(playlistGateway)
        verifyZeroInteractions(songGateway)
        verifyZeroInteractions(albumGateway)
        verifyZeroInteractions(artistGateway)
        verifyZeroInteractions(genreGateway)

        verifyZeroInteractions(podcastPlaylistGateway)
        verifyZeroInteractions(podcastGateway)
        verifyNoMoreInteractions(podcastAlbumGateway)
        verifyZeroInteractions(podcastArtistGateway)
    }

    @Test
    fun testPodcastArtist(){
        // given
        val id = 1L
        val mediaId = MediaId.createCategoryValue(MediaIdCategory.PODCASTS_ARTISTS, id.toString())

        // when
        sut(mediaId)

        verify(podcastArtistGateway).observeTrackListByParam(id)
        verifyZeroInteractions(folderGateway)
        verifyZeroInteractions(playlistGateway)
        verifyZeroInteractions(songGateway)
        verifyZeroInteractions(albumGateway)
        verifyZeroInteractions(artistGateway)
        verifyZeroInteractions(genreGateway)

        verifyZeroInteractions(podcastPlaylistGateway)
        verifyZeroInteractions(podcastGateway)
        verifyZeroInteractions(podcastAlbumGateway)
        verifyNoMoreInteractions(podcastArtistGateway)
    }

}