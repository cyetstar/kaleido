package cc.onelooker.kaleido.enums;

/**
 * @Author cyetstar
 * @Date 2023-12-29 13:42:00
 * @Description TODO
 */
public enum ConfigKey {

    movieLibraryPath,
    tvshowLibraryPath,
    musicLibraryPath,
    comicLibraryPath,

    plexUrl,
    plexToken,
    plexMovieLibraryId,
    plexTvshowLibraryId,
    plexMusicLibraryId,
    plexMovieLibraryPath,
    plexTvshowLibraryPath,
    plexMusicLibraryPath,

    komgaUrl,
    komgaUsername,
    komgaPassword,
    komgaComicLibraryPath,

    tmmUrl,
    neteaseUrl,
    doubanApikey,
    doubanCookie,

    lastMovieAnalyzeTime,
    lastMovieMatchInfo,
    lastTvshowMatchInfo,
    lastMusicMatchInfo,
    lastComicMatchInfo,

    writeComicInfo,
    writeMovieNFO,
    writeAudioTag,
    writeTvshowNFO,
    refreshMetadata,

    videoExtension,
    comicZipExtension,
    audioExtension,

    plexRetries,
    matchInfoSleepSecond,
    downloadLyricSleepSecond,
}
