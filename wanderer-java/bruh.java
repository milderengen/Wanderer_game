import java.util.*;
public class bruh {
    public static void main(String[] args) {
        int[][] battleField =  {{0,0,0,0,1,1,0,0,0,1},
                                {1,0,0,0,0,0,0,0,0,1},
                                {0,0,0,0,0,0,0,0,0,1},
                                {0,0,0,1,1,1,0,0,0,0},
                                {1,0,0,0,0,0,0,1,0,0},
                                {0,0,0,0,0,0,0,0,0,0},
                                {0,0,1,1,0,1,1,1,1,0},
                                {0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,1},
                                {0,0,0,0,0,0,1,1,0,0}};

        int[][] battleField2 ={{1,0,0,0,0,0,0,0,0,1,},
                {0,0,0,0,1,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,1},
                {1,0,1,0,0,0,0,0,0,0},
                {1,0,1,0,0,0,0,0,1,1},
                {1,0,0,0,0,1,0,0,0,0},
                {1,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,1,0,0,0,0},
                {0,0,0,0,0,1,0,1,0,0},
                {0,1,1,0,0,1,0,0,0,1}};

        int[][] battleField3 = {{1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                                {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                                {1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
                                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                                {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        int[][] battleField4 ={{0,0,0,0,0,1,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,1,0,0,0,0,1,1,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,1,1,1,0,1,1,1,1},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,1,0,0},
        {0,0,1,0,1,1,0,1,0,1},
        {0,0,0,0,0,0,0,1,0,0},
        {1,1,0,0,0,0,0,0,0,0}};
        System.out.println(fieldValidator(battleField));
    }
    public static boolean fieldValidator(int[][] field) {
        int submarine =0;
        int doubles = 0;
        int triples = 0;
        int quadra = 0;
        for(int i = 0;i<field.length;i++){
            for(int j = 0;j<field[i].length;j++){
                if(i != 0 && i != field.length-1 && j!=0 && j!=field.length-1 && field[i][j] == 1){
                    if(aroundFieldVal(field,i,j,"InsideSquare")){return false;}
                }
                if(i==0 && j!=0 && j!=field.length-1 && field[i][j] == 1){
                    if(aroundFieldVal(field,i,j,"DownSide")){return false;}
                }
                if(i==field.length-1 && j!=0 && j!=field.length-1 && field[i][j] == 1){
                    if(aroundFieldVal(field,i,j,"TopSide")){return false;}
                }
                if(i!=0 && i!= field.length-1 && j==0 && field[i][j] == 1){
                    if(aroundFieldVal(field,i,j,"LeftSide")){return false;}
                }
                if(i!=0 && i!= field.length-1 && j== field.length-1 && field[i][j] == 1){
                    if(aroundFieldVal(field,i,j,"RightSide")){return false;}
                }
            }
        }
        for(int i = 0;i<field.length;i++){
            for(int j = 0;j<field[i].length;j++){
                if(field[i][j] == 1){
                    int ship = isShip(field,i,j);
                    System.out.println(ship);
                    System.out.println();
                    if(ship==1){submarine++;}
                    if(ship==2){doubles++;}
                    if(ship==3){triples++;}
                    if(ship==4){quadra++;}
                    if(ship==5){return false;}
                }
            }
        }
        /*System.out.println(submarine);
        System.out.println(doubles);
        System.out.println(triples);
        System.out.println(quadra);*/
        if(submarine!=4 || doubles!=3 ||triples!=2 || quadra!=1){return false;}
        return true;
    }
    public static boolean aroundFieldVal(int[][] field, int i,int j, String version){
        if(version == "InsideSquare"){
            if((field[i+1][j-1]==1 || field[i-1][j-1]==1 || field[i-1][j+1]==1 || field[i+1][j+1]==1)){
                return true;
            }
        }
        if(version=="LeftSide"){
            if(field[i-1][j+1]==1 || field[i+1][j+1]==1){
                return true;
            }
        }
        if(version=="RightSide"){
            if(field[i-1][j-1]==1 || field[i+1][j-1]==1){
                return true;
            }
        }
        if(version=="TopSide"){
            if(field[i-1][j-1]==1 || field[i-1][j+1]==1){
                return true;
            }
        }
        if(version=="DownSide"){
            if(field[i+1][j-1]==1 || field[i+1][j+1]==1){
                return true;
            }
        }
        return false;
    }
    public static int isShip(int[][] field, int i,int j){
        System.out.println();
        int UpSpace = field.length-1-i;
        int rightSpace = field.length-1-j;
        /*System.out.println(i);
        System.out.println(j);*/
        //System.out.println("/////////////");
        System.out.println(UpSpace);
        System.out.println(rightSpace);

        if((UpSpace == field.length-1 || rightSpace==field.length-1) && (UpSpace!=0 && rightSpace!=0)){
            if (rightSpace == field.length-1) {if (field[i + 1][j] == 0 && field[i][j + 1] == 0) {field[i][j] = 0;return 1;}}
            if (UpSpace == field.length-1) {if (field[i][j + 1] == 0 && field[i + 1][j] == 0) {field[i][j] = 0;return 1;}}
        }
        if(UpSpace == field.length-1 || rightSpace==field.length-1){
            if(UpSpace == field.length-1 && rightSpace == 0){if(field[i+1][j]==0){return 1;}}
            if(rightSpace == field.length-1 && UpSpace == 0){if(field[i][j+1] == 0){return 1;}}
        }
        if(UpSpace == 0 || rightSpace==0){
            if(UpSpace == 0 && rightSpace == 0){return 1;}
            if (UpSpace == 0 && field[i][j + 1] == 0) {return 1;}
            if (rightSpace == 0 && field[i + 1][j] == 0) {return 1;}
            if (rightSpace == 0) {if (field[i + 1][j] == 0) {field[i][j] = 0;return 1;}}
            if (UpSpace == 0) {if (field[i][j + 1] == 0) {field[i][j] = 0;return 1;}}
        }
        if(UpSpace < field.length-1 && rightSpace < field.length-1 && rightSpace > 0  && UpSpace > 0&& field[i+1][j]==0 && field[i][j+1]==0){return 1;}
        if(UpSpace>=1){
            if(field[i+1][j] == 1 && i+2>field.length-1){
                field[i][j] = 0;field[i+1][j] = 0;
                return 2;}
            if(field[i+1][j] == 1 && field[i+2][j]!=1){
                field[i][j] = 0;field[i+1][j] = 0;
                return 2;}
        }
        if(UpSpace>=2){
            if(field[i+1][j] == 1 && field[i+2][j] == 1 && i+3>field.length-1){
                field[i][j] = 0;field[i+1][j] = 0;field[i+2][j] = 0;
                return 3;}
            if(field[i+1][j] == 1 && field[i+2][j] == 1 && field[i+3][j]!=1){
                field[i][j] = 0;field[i+1][j] = 0;field[i+2][j] = 0;
                return 3;}
        }
        if(UpSpace>=3){
            if(field[i+1][j] == 1 && field[i+2][j] == 1 && field[i+3][j] == 1 &&  i+4>field.length-1){
                field[i][j] = 0;field[i+1][j] = 0;field[i+2][j] = 0;field[i+3][j] = 0;
                return 4;}
            if(field[i+1][j] == 1 && field[i+2][j] == 1 && field[i+3][j] == 1 &&  field[i+4][j]==0){
                field[i][j] = 0;field[i+1][j] = 0;field[i+2][j] = 0;field[i+3][j] = 0;
                return 4;}
            if(field[i+1][j] == 1 && field[i+2][j] == 1 && field[i+3][j] == 1 &&  field[i+4][j]==1){
                field[i][j] = 0;field[i+1][j] = 0;field[i+2][j] = 0;field[i+3][j] = 0;
                return 4;}
        }
        if(rightSpace>=1){
            if(field[i][j+1] == 1 && j+2>field.length-1){
                field[i][j] = 0;field[i][j+1] = 0;
                return 2;}
            if(field[i][j+1] == 1 && field[i][j+2]!=1){
                field[i][j] = 0;field[i][j+1] = 0;
                return 2;}
        }
        if(rightSpace>=2){
            if(field[i][j+1] == 1 && field[i][j+2] == 1 && j+3>field.length-1){
                field[i][j] = 0;field[i][j+1] = 0; field[i][j+2] = 0;
                return 3;}
            if(field[i][j+1] == 1 && field[i][j+2] == 1 && field[i][j+3]!=1){
                field[i][j] = 0;field[i][j+1] = 0; field[i][j+2] = 0;
                return 3;}
        }
        if(rightSpace>=3){
            if(field[i][j+1] == 1 && field[i][j+2] == 1 && field[i][j+3] == 1){
                field[i][j] = 0;field[i][j+1] = 0; field[i][j+2] = 0;field[i][j+3] = 0;
                return 4;}
            if(field[i][j + 1] == 1 && field[i][j + 2] == 1 && field[i][j + 3] == 1 && j+4>9){return 5;}
            if(field[i][j + 1] == 1 && field[i][j + 2] == 1 && field[i][j + 3] == 1 && field[i][j + 4]==1){return 5;}
        }
        return 0;
    }
}
