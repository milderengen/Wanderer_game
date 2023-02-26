package Game;

import java.util.List;

public abstract class Creature {
    public boolean canCreateThere(int[][] arr, int x, int y){   //pozoruje či sa koordinácie z argumentu nezhodujú s koordináciami ktoré už máme v poli, podľa toho vyhodnocuje či môžu alebo nemôžu byť
        for(int i = 0; i < 42; i++){
            if(arr[i][0] == x && arr[i][1] == y){
                return false;
            }
        }
        return true;
    }
    public void move(int x, int y, int[][] arr, Skeleton skeleton){

    };
    public void move(int x, int y, int[][] arr, Boss boss){

    };
    public boolean findCollision(String who, int num, Hero hero, int[][] arr){                      //kontroluje pohyb hrdinu, či sa snaží ísť von z mapy alebo do steny
        for (int row = 0; row < 42; row++) {                                                        //ak áno, nepustí ho
            if(who.equals("x")){
                if(num == 1 && (hero.x + 72 == arr[row][0] && hero.y == arr[row][1])){
                    return false;
                }else if (num == 2 && (hero.x - 72 == arr[row][0] && hero.y == arr[row][1])) {
                    return false;
                }
            }else{
                if(num == 1 && (hero.y + 72 == arr[row][1] && hero.x == arr[row][0])) {
                    return false;
                }else if(num == 2 && (hero.y - 72 == arr[row][1] && hero.x == arr[row][0])) {
                    if(hero.y - 72 == 0 && hero.x == 0){
                        return true;
                    }
                    return false;
                }
            }
        }
        return true;
    }
    public String findCollision(Skeleton skeleton, int[][] arr){                        //ten istý princíp len skelettoni
        String directions =  "";
        for (int row = 0; row < 42; row++) {

            if((skeleton.x + 72 == arr[row][0] && skeleton.y == arr[row][1])){  //v ifoch kontrolujeme súradnice skeletonov + stranu do ktorej by sa chceli pohnúť a pýtame sa či by sa možná budúca súradnica nezhodovala so súradnicou steny
                directions += "right";
            }else if ((skeleton.x - 72 == arr[row][0] && skeleton.y == arr[row][1])) {
                directions += "left";
            }
            if((skeleton.y + 72 == arr[row][1] && skeleton.x == arr[row][0])) {
                directions += "down";

            }else if((skeleton.y - 72 == arr[row][1] && skeleton.x == arr[row][0])) {
                if(skeleton.y - 72 == 0 && skeleton.x == 0){
                    continue;
                }
                directions += "up";
            }
        }
        if(skeleton.x + 72  > 648 && !directions.contains("right")){                // následne sa pýtame či po chcenom pohybe by sme neskončili mimo mapu
            directions += "right";
        }else if(skeleton.x - 72  <= 0 && !directions.contains("left")) {
            directions += "left";
        }else if(skeleton.y + 72 > 648 && !directions.contains("down")) {
            directions += "down";
        }else if(skeleton.y - 72  <= 0 && !directions.contains("up")) {
            directions += "up";
        }
        return directions;                                                          //funkcia vracia string so všetkými smermi ktoré sú znemožnené
    }
    public String findCollision2(Boss boss, int[][] arr){ //ten istý princíp len boss
        String directions =  "";
        for (int row = 0; row < 42; row++) {

            if((boss.x + 72 == arr[row][0] && boss.y == arr[row][1])){
                directions += "right";
            }else if ((boss.x - 72 == arr[row][0] && boss.y == arr[row][1])) {
                directions += "left";
            }
            if((boss.y + 72 == arr[row][1] && boss.x == arr[row][0])) {
                directions += "down";

            }else if((boss.y - 72 == arr[row][1] && boss.x == arr[row][0])) {
                if(boss.y - 72 == 0 && boss.x == 0){
                    continue;
                }
                directions += "up";
            }
        }
        if(boss.x + 72  > 648 && !directions.contains("right")){
            directions += "right";
        }else if(boss.x - 72  <= 0 && !directions.contains("left")) {
            directions += "left";
        }else if(boss.y + 72 > 648 && !directions.contains("down")) {
            directions += "down";
        }else if(boss.y - 72  <= 0 && !directions.contains("up")) {
            directions += "up";
        }
        return directions;
    }


    public void attack(Hero hero){

    }
    public void attack(Skeleton skeleton, List<Skeleton> skeletons){

    }
    public void attack(Boss boss, List<Boss> bossList){

    }

}
