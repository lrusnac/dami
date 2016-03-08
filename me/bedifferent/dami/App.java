package me.bedifferent.dami;

import java.util.Arrays;

import me.bedifferent.dami.dataframe.Attribute;
import me.bedifferent.dami.dataframe.Attribute.Type;
import me.bedifferent.dami.dataframe.DataFrame;

public class App {

    public static void main(String args[]) {
        int[] colums = { 1, 2, 3, 4, 5, 6, 7, 20, 21, 44, 45 };
        Attribute.Type[] types = { Type.NUMERIC, Type.LITERAL, Type.LITERAL, Type.NUMERIC, Type.LITERAL, Type.LITERAL, Type.LITERAL,
                Type.LITERAL, Type.LITERAL, Type.LITERAL, Type.LITERAL };
        String[] header = { "age", "gender", "shoesize", "height", "course", "interess", "languages", "playing", "games", "row",
                "seat" };
        DataFrame df = new DataFrame(header, types, colums, "DataMining 2016 (Responses).csv");
        System.out.println(df);

        System.out.println("ages: " + Arrays.toString(df.getColumn("age")));

        System.out.println("num rows: " + df.getNumRows());
        System.out.println("num cols: " + df.getNumCols());
    }
}
