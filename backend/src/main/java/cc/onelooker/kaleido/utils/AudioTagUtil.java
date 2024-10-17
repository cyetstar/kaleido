package cc.onelooker.kaleido.utils;

import cc.onelooker.kaleido.dto.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.MusicTrackDTO;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.ID3v23FieldKey;
import org.jaudiotagger.tag.id3.ID3v23Tag;

import java.util.Map;
import java.util.Objects;

/**
 * @Author xiadawei
 * @Date 2024-10-14 11:29:00
 * @Description TODO
 */
public class AudioTagUtil {

    private static final Map<FieldKey, ID3v23FieldKey> fieldKeyMap = Maps.newHashMap();

    static {
        fieldKeyMap.put(FieldKey.MUSICBRAINZ_TRACK_ID, ID3v23FieldKey.MUSICBRAINZ_TRACK_ID);
        fieldKeyMap.put(FieldKey.MUSICBRAINZ_RELEASEID, ID3v23FieldKey.MUSICBRAINZ_RELEASEID);
        fieldKeyMap.put(FieldKey.ALBUM_ARTIST, ID3v23FieldKey.ALBUM_ARTIST);
        fieldKeyMap.put(FieldKey.MUSICBRAINZ_RELEASE_TYPE, ID3v23FieldKey.MUSICBRAINZ_RELEASE_TYPE);
        fieldKeyMap.put(FieldKey.GENRE, ID3v23FieldKey.GENRE);
        fieldKeyMap.put(FieldKey.MUSICBRAINZ_RELEASE_COUNTRY, ID3v23FieldKey.MUSICBRAINZ_RELEASE_COUNTRY);
        fieldKeyMap.put(FieldKey.YEAR, ID3v23FieldKey.YEAR);
        fieldKeyMap.put(FieldKey.RECORD_LABEL, ID3v23FieldKey.RECORD_LABEL);
        fieldKeyMap.put(FieldKey.ORIGINALRELEASEDATE, ID3v23FieldKey.ORIGINALRELEASEDATE);
        fieldKeyMap.put(FieldKey.MEDIA, ID3v23FieldKey.MEDIA);
    }

    public static void toMusicAlbumDTO(Tag tag, MusicAlbumDTO musicAlbumDTO) {
        AudioTag audioTag = toAudioTag(tag);
        musicAlbumDTO.setNeteaseId(audioTag.getNeteaseReleaseId());
        musicAlbumDTO.setMusicbrainzId(audioTag.getMusicbrainzReleaseId());
        musicAlbumDTO.setArtists(audioTag.getAlbumArtist());
        musicAlbumDTO.setType(audioTag.getReleaseType());
        musicAlbumDTO.setGenre(audioTag.getGenre());
        musicAlbumDTO.setReleaseCountry(audioTag.getReleaseCountry());
        musicAlbumDTO.setOriginallyAvailableAt(audioTag.getReleaseDate());
        if (StringUtils.isNotEmpty(audioTag.getYear())) {
            musicAlbumDTO.setYear(StringUtils.substring(audioTag.getYear(), 0, 4));
        } else {
            musicAlbumDTO.setYear(StringUtils.substring(audioTag.getReleaseDate(), 0, 4));
        }
        musicAlbumDTO.setLabel(audioTag.getRecordLabel());
        musicAlbumDTO.setMedia(audioTag.getMedia());
        if (StringUtils.isNumeric(audioTag.getTotalDiscs())) {
            musicAlbumDTO.setTotalDiscs(Integer.parseInt(audioTag.getTotalDiscs()));
        }
        if (StringUtils.isNumeric(audioTag.getTotalTracks())) {
            musicAlbumDTO.setTotalTracks(Integer.parseInt(audioTag.getTotalTracks()));
        }
    }

    public static void toMusicTrackDTO(Tag tag, MusicTrackDTO musicTrackDTO) {
        AudioTag audioTag = toAudioTag(tag);
        musicTrackDTO.setNeteaseId(audioTag.getNeteaseId());
        musicTrackDTO.setMusicbrainzId(audioTag.getMusicbrainzId());
        musicTrackDTO.setArtists(audioTag.getArtist());
        if (StringUtils.isNumeric(audioTag.getDiscIndex())) {
            musicTrackDTO.setDiscIndex(Integer.parseInt(audioTag.getDiscIndex()));
        }
        if (StringUtils.isNumeric(audioTag.getTrackIndex())) {
            musicTrackDTO.setTrackIndex(Integer.parseInt(audioTag.getTrackIndex()));
        }
    }

    public static void toTag(MusicAlbumDTO musicAlbumDTO, MusicTrackDTO musicTrackDTO, Tag tag) {
        AudioTag audioTag = toAudioTag(musicAlbumDTO, musicTrackDTO);
        setTagValue(tag, FieldKey.CUSTOM1, audioTag.getNeteaseReleaseId());
        setTagValue(tag, FieldKey.MUSICBRAINZ_RELEASEID, audioTag.getMusicbrainzReleaseId());
        setTagValue(tag, FieldKey.ALBUM, audioTag.getAlbumTitle());
        setTagValue(tag, FieldKey.ALBUM_ARTIST, audioTag.getAlbumArtist());
        setTagValue(tag, FieldKey.MUSICBRAINZ_RELEASE_TYPE, audioTag.getReleaseType());
        setTagValue(tag, FieldKey.GENRE, audioTag.getGenre());
        setTagValue(tag, FieldKey.MUSICBRAINZ_RELEASE_COUNTRY, audioTag.getReleaseCountry());
        setTagValue(tag, FieldKey.RECORD_LABEL, audioTag.getRecordLabel());
        setTagValue(tag, FieldKey.MEDIA, audioTag.getMedia());
        setTagValue(tag, FieldKey.ORIGINALRELEASEDATE, audioTag.getReleaseDate());
        setTagValue(tag, FieldKey.YEAR, audioTag.getYear());
        setTagValue(tag, FieldKey.DISC_TOTAL, audioTag.releaseCountry);

        setTagValue(tag, FieldKey.CUSTOM2, audioTag.getNeteaseId());
        setTagValue(tag, FieldKey.MUSICBRAINZ_TRACK_ID, audioTag.getMusicbrainzId());
        setTagValue(tag, FieldKey.TITLE, audioTag.getTitle());
        setTagValue(tag, FieldKey.ARTIST, audioTag.getArtist());
        setTagValue(tag, FieldKey.DISC_NO, audioTag.getDiscIndex());
        setTagValue(tag, FieldKey.TRACK, audioTag.getTrackIndex());
    }

    public static boolean isChanged(Tag tag, MusicAlbumDTO musicAlbumDTO, MusicTrackDTO musicTrackDTO) {
        AudioTag audioTag1 = toAudioTag(tag);
        AudioTag audioTag2 = toAudioTag(musicAlbumDTO, musicTrackDTO);
        return !Objects.equals(audioTag1, audioTag2);
    }

    public static AudioTag toAudioTag(Tag tag) {
        AudioTag audioTag = new AudioTag();
        audioTag.setNeteaseReleaseId(getTagValue(tag, FieldKey.CUSTOM1));
        audioTag.setMusicbrainzReleaseId(getTagValue(tag, FieldKey.MUSICBRAINZ_RELEASEID));
        audioTag.setAlbumTitle(getTagValue(tag, FieldKey.ALBUM));
        audioTag.setAlbumArtist(getTagValue(tag, FieldKey.ALBUM_ARTIST));
        audioTag.setReleaseType(getTagValue(tag, FieldKey.MUSICBRAINZ_RELEASE_TYPE));
        audioTag.setGenre(getTagValue(tag, FieldKey.GENRE));
        audioTag.setReleaseCountry(getTagValue(tag, FieldKey.MUSICBRAINZ_RELEASE_COUNTRY));
        audioTag.setRecordLabel(getTagValue(tag, FieldKey.RECORD_LABEL));
        audioTag.setMedia(getTagValue(tag, FieldKey.MEDIA));
        audioTag.setReleaseDate(getTagValue(tag, FieldKey.ORIGINALRELEASEDATE));
        audioTag.setYear(getTagValue(tag, FieldKey.YEAR));
        audioTag.setTotalDiscs(getTagValue(tag, FieldKey.DISC_TOTAL));
        audioTag.setTotalTracks(getTagValue(tag, FieldKey.TRACK_TOTAL));
        audioTag.setNeteaseId(getTagValue(tag, FieldKey.CUSTOM2));
        audioTag.setMusicbrainzId(getTagValue(tag, FieldKey.MUSICBRAINZ_TRACK_ID));
        audioTag.setTitle(getTagValue(tag, FieldKey.TITLE));
        audioTag.setArtist(getTagValue(tag, FieldKey.ARTIST));
        audioTag.setDiscIndex(getTagValue(tag, FieldKey.DISC_NO));
        audioTag.setTrackIndex(getTagValue(tag, FieldKey.TRACK));
        return audioTag;
    }

    private static AudioTag toAudioTag(MusicAlbumDTO musicAlbumDTO, MusicTrackDTO musicTrackDTO) {
        AudioTag audioTag = new AudioTag();
        audioTag.setNeteaseReleaseId(musicAlbumDTO.getNeteaseId());
        audioTag.setMusicbrainzReleaseId(musicAlbumDTO.getMusicbrainzId());
        audioTag.setAlbumTitle(musicAlbumDTO.getTitle());
        audioTag.setAlbumArtist(musicAlbumDTO.getArtists());
        audioTag.setReleaseType(musicAlbumDTO.getType());
        audioTag.setReleaseCountry(musicAlbumDTO.getReleaseCountry());
        audioTag.setReleaseDate(StringUtils.defaultIfEmpty(musicAlbumDTO.getOriginallyAvailableAt(), musicAlbumDTO.getYear()));
        audioTag.setRecordLabel(musicAlbumDTO.getLabel());
        audioTag.setGenre(musicAlbumDTO.getGenre());
        audioTag.setMedia(musicAlbumDTO.getMedia());
        audioTag.setYear(musicAlbumDTO.getYear());
        if (musicAlbumDTO.getTotalDiscs() != null) {
            audioTag.setTotalDiscs(String.valueOf(musicAlbumDTO.getTotalDiscs()));
        }
        if (musicAlbumDTO.getTotalTracks() != null) {
            audioTag.setTotalTracks(String.valueOf(musicAlbumDTO.getTotalTracks()));
        }
        audioTag.setNeteaseId(musicTrackDTO.getNeteaseId());
        audioTag.setMusicbrainzId(musicTrackDTO.getMusicbrainzId());
        audioTag.setTitle(musicTrackDTO.getTitle());
        audioTag.setArtist(musicTrackDTO.getArtists());
        if (musicTrackDTO.getDiscIndex() != null) {
            audioTag.setDiscIndex(String.valueOf(musicTrackDTO.getDiscIndex()));
        }
        if (musicTrackDTO.getTrackIndex() != null) {
            audioTag.setTrackIndex(String.valueOf(musicTrackDTO.getTrackIndex()));
        }
        return audioTag;
    }

    private static String getTagValue(Tag tag, FieldKey fieldKey) {
        if (tag instanceof ID3v23Tag) {
            ID3v23Tag id3v2Tag = (ID3v23Tag) tag;
            ID3v23FieldKey id3v23FieldKey = MapUtils.getObject(fieldKeyMap, fieldKey);
            if (id3v23FieldKey != null) {
                return id3v2Tag.getFirst(id3v23FieldKey);
            } else {
                return id3v2Tag.getFirst(fieldKey);
            }
        } else {
            return tag.getFirst(fieldKey);
        }
    }

    private static void setTagValue(Tag tag, FieldKey fieldKey, String fieldValue) {
        try {
            if (StringUtils.isNotEmpty(fieldValue)) {
                tag.setField(fieldKey, fieldValue);
            }
        } catch (FieldDataInvalidException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    @Data
    public static class AudioTag {
        private String neteaseReleaseId;
        private String musicbrainzReleaseId;
        private String albumTitle;
        private String albumArtist;
        private String releaseType;
        private String releaseCountry;
        private String releaseDate;
        private String year;
        private String genre;
        private String recordLabel;
        private String media;
        private String neteaseId;
        private String musicbrainzId;
        private String title;
        private String artist;
        private String discIndex;
        private String trackIndex;
        private String totalDiscs;
        private String totalTracks;

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            AudioTag audioTag = (AudioTag) object;
            return Objects.equals(neteaseReleaseId, audioTag.neteaseReleaseId) && Objects.equals(musicbrainzReleaseId, audioTag.musicbrainzReleaseId) && Objects.equals(albumTitle, audioTag.albumTitle) && Objects.equals(albumArtist, audioTag.albumArtist) && Objects.equals(releaseType, audioTag.releaseType) && Objects.equals(releaseCountry, audioTag.releaseCountry) && Objects.equals(releaseDate, audioTag.releaseDate) && Objects.equals(year, audioTag.year) && Objects.equals(genre, audioTag.genre) && Objects.equals(recordLabel, audioTag.recordLabel) && Objects.equals(media, audioTag.media) && Objects.equals(neteaseId, audioTag.neteaseId) && Objects.equals(musicbrainzId, audioTag.musicbrainzId) && Objects.equals(title, audioTag.title) && Objects.equals(artist, audioTag.artist) && Objects.equals(discIndex, audioTag.discIndex) && Objects.equals(trackIndex, audioTag.trackIndex) && Objects.equals(totalDiscs, audioTag.totalDiscs) && Objects.equals(totalTracks, audioTag.totalTracks);
        }

        @Override
        public int hashCode() {
            return Objects.hash(neteaseReleaseId, musicbrainzReleaseId, albumTitle, albumArtist, releaseType, releaseCountry, releaseDate, year, genre, recordLabel, media, neteaseId, musicbrainzId, title, artist, discIndex, trackIndex, totalDiscs, totalTracks);
        }
    }
}
