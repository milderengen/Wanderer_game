package Game;
import java.util.Random;
import java.lang.reflect.Array;

public class Skeleton extends Creature{
    boolean hasKey;
    String[] directions = new String[4];
    boolean alive = true;
    String lastMove = "";
    int count = 0;
    int x;
    int y;
    String pic = "skeleton.png";
    int lvl;
    Random random = new Random();
    int b = random.nextInt(7 - 1) + 1;;
    int HP;
    int DP;
    int SP;
    public Skeleton(int lvl){
        this.lvl = lvl;
        this.HP = 2 * lvl * b;
        this.DP = lvl *b;
        this.SP = lvl * b + lvl;
    }


    @Override
    public void attack(Hero hero) {

    }

    @Override
    public String findCollision(Skeleton skeleton, int[][] arr) {
        return super.findCollision(skeleton, arr);
    }
    @Override
    public boolean canCreateThere(int[][] arr, int x, int y) {
        return super.canCreateThere(arr, x, y);
    }

    @Override
    public void move(int x, int y, int [][] arr, Skeleton skeleton) {
        String bannedDirections = findCollision(skeleton, arr);
        boolean moved = false;
        if(skeleton.x + 72 == 720){bannedDirections += "right";}            // pre istotu ešte raz kontrolujeme či chcený pohyb nespôsobí únik z mapy
        if(skeleton.x - 72 < 0){bannedDirections += "left";}
        if(skeleton.y + 72 == 720){bannedDirections += "down";}
        if(skeleton.y - 72 < 0){bannedDirections += "top";}
        do{                                                                 //generujeme náhodné čísla a pýtame sa či smer do ktorého chceme ísť je povolený alebo zakázaný
            int b = (int)(Math.random()*(4-1+1)+1);                         //do while sa opakuje dovtedy dokým sa daný skeleton nepohne
            if(!bannedDirections.contains("left") && b == 1) {
                moveLeft();
                lastMove = "left";
                moved = true;
            }else if(!bannedDirections.contains("down") && b == 2) {
                moveDown();
                lastMove = "down";
                moved = true;
            }else if(!bannedDirections.contains("up") && b == 3) {
                moveUp();
                lastMove = "up";
                moved = true;
            }
            else if(!bannedDirections.contains("right") && b == 4){
                moveRight();
                lastMove = "right";
                moved = true;
            }
        }while(!moved);


    }

    public void moveRight(){
        this.x += 72;
    }
    public void moveLeft(){
        this.x -= 72;
    }
    public void moveDown(){
        this.y += 72;
    }
    public void moveUp(){
        this.y -= 72;
    }


}
