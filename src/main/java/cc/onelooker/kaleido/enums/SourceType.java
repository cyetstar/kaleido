package cc.onelooker.kaleido.enums;

/**
 * @Author cyetstar
 * @Date 2023-12-02 22:11:00
 * @Description TODO
 */
public enum SourceType {
    imdb(0, 1),

    douban(1, 0),

    tmdb(2, 2),

    themoviedb(2, 2),

    tmdbSet(3, 3),

    moviemeter(4, 4),

    trakt(5, 5),

    dmm(10, 10);

    private final int score;

    private final int ratingScore;

    SourceType(int score, int ratingScore) {
        this.score = score;
        this.ratingScore = ratingScore;
    }

    public int getScore() {
        return score;
    }

    public int getRatingScore() {
        return ratingScore;
    }

    public boolean eq(String type) {
        return this.equals(valueOf(type));
    }
}
