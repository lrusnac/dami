package me.bedifferent.dami.kmeans;

public class KMeansRow {
	
	private Integer id;
	
    private double numberOfGames;
    private double hoursPlaying;
    
    public KMeansRow(double numberOfGames, double hoursPlaying) {
        this.numberOfGames = numberOfGames;
        this.hoursPlaying = hoursPlaying;
    }
    
    public KMeansRow(int id, double numberOfGames, double hoursPlaying) {
        this.id = id;
    	this.numberOfGames = numberOfGames;
        this.hoursPlaying = hoursPlaying;
    }
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getNumberOfGames() {
		return numberOfGames;
	}

	public void setNumberOfGames(double numberOfGames) {
		this.numberOfGames = numberOfGames;
	}

	public double getHoursPlaying() {
		return hoursPlaying;
	}

	public void setHoursPlaying(double hoursPlaying) {
		this.hoursPlaying = hoursPlaying;
	}

	public Double distance(KMeansRow m) {
        double dist = 0;

        dist += Math.pow(numberOfGames - m.numberOfGames, 2);
        dist += Math.pow(hoursPlaying - m.hoursPlaying, 2);

        dist = Math.sqrt(dist);

        return dist;
    }

	public boolean equals(Object obj) {
		if (null == obj) return false;
		if (this == obj) return true;
		if (!(obj instanceof KMeansRow)) return false;
		KMeansRow that = (KMeansRow) obj;
		
		return this.hoursPlaying == that.hoursPlaying && this.numberOfGames == that.numberOfGames;
    }
	
	public String toString() {
		return "" +numberOfGames + ":" + hoursPlaying + "";
	}
}
