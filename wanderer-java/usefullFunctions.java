package Game;

import java.util.ArrayList;
import java.util.List;

public class usefullFunctions {
    int[][] wallCoordinates = new int[44][2];
    int max;
    int min;
    List<Skeleton> list = new ArrayList<>();
    public void random(){
        int b = (int)(Math.random()*(max-min+1)+min);
    }
    public void iterateThroughList(){
        for(int i = 0; i < list.size(); i++){
            //Skeletons.get(i) do something
        }
    }
    public void iterateThrough2Darr(){
        for(int i = 0; i < wallCoordinates.length; i++){
            System.out.println(wallCoordinates[i][0] + "  " + wallCoordinates[i][1]);
        }
    }


}

