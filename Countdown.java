import java.util.Random;
import java.util.Collections;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class Countdown {
    public static void main(String[] args) {
        int[][] targetSolved = new int[5][900];
        int[][] targetAttempted = new int[5][900];

        Random rand = new Random();

        for(int j=0; j<1000000000; j++){
            int nBigs = drawNBig(rand);
            int[] d = draw(rand, nBigs);
            int t = drawTarget(rand);
            
            if (j%10000==0) {
                checkpointTarget(targetSolved, targetAttempted, j, args[0]);
            }

            if (Solver.Solve(d, t, false)) {
                targetSolved[nBigs][t-100]+=1;
            }
            targetAttempted[nBigs][t-100]+=1;
        }
    }

    public static void checkpointTarget(int[][] targetSolved, int[][] targetAttempted , int step, String suffix){
        String targetFilename = String.format("target_%s_%s", suffix, step);

        try {
            FileWriter writer = new FileWriter(targetFilename);

            writer.write("target,nBig,nSolved,nAttempted\n");
            for(int i=0; i<5; i++){
                for(int j=0; j<900; j++){
                    writer.write(String.valueOf(j+100));
                    writer.write(",");
                    writer.write(String.valueOf(i));
                    writer.write(",");
                    writer.write(String.valueOf(targetSolved[i][j]));
                    writer.write(",");
                    writer.write(String.valueOf(targetAttempted[i][j]));
                    writer.write("\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int drawTarget(Random rand){
        return rand.nextInt(900)+100;
    }

    public static int drawNBig(Random rand){
        return rand.nextInt(5);
    }

    public static int[] draw(Random rand, int nBigs){
        int nSmalls = 6-nBigs;

        List<Integer> smalls = new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10));
        List<Integer> bigs = new ArrayList<>(List.of(25,50,75,100));

        Collections.shuffle(smalls);
        Collections.shuffle(bigs);


        List<Integer> numbers = new ArrayList<>(6);

        List<Integer> bigDraw = bigs.subList(0, nBigs);
        List<Integer> smallDraw = smalls.subList(0, nSmalls);
        numbers.addAll(bigDraw);
        numbers.addAll(smallDraw);
        // numbers.addAll(nSmalls.subList(0, nSmalls));

        return toIntArray(numbers);
    }

    public static int[] toIntArray(List<Integer> intList){
       return intList.stream().mapToInt(Integer::intValue).toArray();
}
}
