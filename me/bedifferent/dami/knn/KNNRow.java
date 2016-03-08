package me.bedifferent.dami.knn;

public class KNNRow {

    private String degree;
    private int numberOfGames;
    private int hoursPlaying;
    private String courseInterest;

    public KNNRow(String degree, int numberOfGames, int hoursPlaying, String courseInterest) {
        this.degree = degree;
        this.numberOfGames = numberOfGames;
        this.hoursPlaying = hoursPlaying;
        this.courseInterest = courseInterest;
    }

    public String getDegree() {
        return this.degree;
    }

    public Double distance(KNNRow m) {
        double dist = 0;

        // dist += Math.pow(numberOfGames - m.numberOfGames, 2);
        // dist += Math.pow(hoursPlaying - m.hoursPlaying, 2);
        //
        // dist = Math.sqrt(dist);

        dist += Math.abs(numberOfGames - m.numberOfGames);
        dist += Math.abs(hoursPlaying - m.hoursPlaying);

        if (courseInterest.equals(m.courseInterest))
            dist++;

        return dist;
    }
}
