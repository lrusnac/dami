package me.bedifferent.dami.dataframe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataFrame {
    private Attribute[][] df;

    private String[] header;
    private Attribute.Type[] types;
    private int[] interestedColumns;

    private String csvFile;

    public DataFrame(String[] header, Attribute.Type[] types, int[] columns, String csvFile) {
        if (header.length != types.length || types.length != columns.length) {
            System.err.println("the arrays must have the same length");
            return;
        }
        this.header = header;
        this.interestedColumns = columns;
        this.types = types;
        this.csvFile = csvFile;

        this.readFromCSV(";");
    }

    public void readFromCSV(String seperator) {
        List<Attribute[]> lines = new ArrayList<Attribute[]>();
        BufferedReader bufRdr = null;
        boolean header = true;
        try {
            bufRdr = new BufferedReader(new FileReader(new File(csvFile)));
        } catch (FileNotFoundException e) {
            System.out.println("No such file");
            e.printStackTrace();
        }

        String line;
        try {
            while ((line = bufRdr.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }

                String[] arr = line.split(seperator);
                Attribute[] arrAtt = new Attribute[interestedColumns.length];
                for (int i = 0; i < interestedColumns.length; i++) {
                    if (arr[interestedColumns[i]].equals("")) {
                        arr[interestedColumns[i]] = "";
                    }
                    arrAtt[i] = new Attribute(arr[interestedColumns[i]]);
                }

                lines.add(arrAtt);
            }

            Attribute[][] ret = new Attribute[lines.size()][];
            bufRdr.close();
            df = lines.toArray(ret);
        } catch (IOException e) {
            System.out.println("csv file parsing error");
            e.printStackTrace();
        }
    }

    public int getNumRows() {
        return df.length;
    }

    public int getNumCols() {
        return (this.getNumRows() > 0) ? df[0].length : 0;

    }

    public Attribute[] getColumn(String name) {
        int columNumber = headerNameToColNumber(name);
        return getColumn(columNumber);
    }

    public Attribute[] getColumn(int columNumber) {
        Attribute[] col = new Attribute[getNumRows()];

        for (int i = 0; i < col.length; i++) {
            col[i] = df[i][columNumber];
        }

        return col;
    }

    private int headerNameToColNumber(String name) {
        for (int i = 0; i < header.length; i++) {
            if (header[i].equals(name))
                return i;
        }
        return -1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Attribute[] line : df) {
            sb.append(Arrays.toString(line)).append("\n");
        }

        return sb.toString();
    }
}
