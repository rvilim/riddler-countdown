import java.util.Random;
import java.util.Collections;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class One {
    public static void main(String[] args) {
        int[] input = new int[6];

        for(int i=0; i<6; i++){
            input[i]=Integer.valueOf(args[i]);
        }
        Solver.Solve(input, Integer.valueOf(args[6]), true);
    }
}
