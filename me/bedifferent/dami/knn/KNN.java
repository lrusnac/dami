package me.bedifferent.dami.knn;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javafx.util.Pair;
import me.bedifferent.dami.dataframe.Attribute;
import me.bedifferent.dami.dataframe.DataFrame;

public class KNN {
    /* is the degree program games? based on the the interest in the course, number of games and number of programming language */
    private static List<KNNRow> getKNN(int k, KNNRow m, List<KNNRow> trainingSet) {

        List<Pair<KNNRow, Double>> rowDistances = new LinkedList<>();

        for (int i = 0; i < k; i++) {
            rowDistances.add(i, new Pair<>(trainingSet.get(i), m.distance(trainingSet.get(i))));
        }

        for (int i = k; i < trainingSet.size(); i++) {
            // find the max distance in the list
            int maxDistPos = 0;
            for (int j = 1; j < k; j++) {
                if (rowDistances.get(j).getValue() > rowDistances.get(maxDistPos).getValue()) {
                    maxDistPos = j;
                }
            }

            if (rowDistances.get(maxDistPos).getValue() > trainingSet.get(i).distance(m)) {
                rowDistances.set(maxDistPos, new Pair<>(trainingSet.get(i), m.distance(trainingSet.get(i))));
            }
        }

        List<KNNRow> resultKNNRows = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            resultKNNRows.add(rowDistances.get(i).getKey());
        }

        return resultKNNRows;
    }

    public static boolean isStudingGames(int k, KNNRow m, List<KNNRow> trainingSet) {
        if (k >= trainingSet.size()) {
            System.err.println("the k cannot be bigger then the training set");
            return false;
        }
        int games = 0;
        for (KNNRow row : getKNN(k, m, trainingSet)) {
            if (row.getDegree().equals("games-t"))
                games++;
        }

        return games >= (k / 2);
    }

    public static void runKNN(DataFrame df) {
        List<KNNRow> rows = new LinkedList<>(); // DataManager.LoadData//DataManager.LoadData();
        // from df to KNNRows
        for (Attribute[] att : df) {
            int hoursPlaying;
            switch (att[7].getLiteral()) {
            case "never":
                hoursPlaying = 0;
                break;
            case "< 5 hours a week":
                hoursPlaying = 1;
                break;
            case "< 10 hours a week":
                hoursPlaying = 2;
                break;
            case "< 20 hours a week":
                hoursPlaying = 3;
                break;
            case "> 20 hours a week":
                hoursPlaying = 4;
                break;
            default:
                hoursPlaying = 0;
            }

            int numberOfGames = att[8].getLiteral().equals("i have not played any of these games") ? 0
                    : att[8].getLiteral().split(",").length;
            rows.add(new KNNRow(att[4].getLiteral(), numberOfGames, hoursPlaying, att[5].getLiteral()));
        }

        Collections.shuffle(rows);
        List<KNNRow> trainingSet = rows.subList(0, rows.size() / 3 * 2);
        List<KNNRow> testSet = rows.subList(rows.size() / 3 * 2, rows.size());

        for (int k = 2; k <= 10; k++) {
            int errors = 0;
            for (KNNRow test : testSet) {
                if (KNN.isStudingGames(k, test, trainingSet)) {
                    if (!test.getDegree().equals("games-t"))
                        errors++;
                } else {
                    if (test.getDegree().equals("games-t"))
                        errors++;
                }
            }

            System.out.println("k: " + k + " errors:" + errors + " tests: " + testSet.size());
        }
    }
}
