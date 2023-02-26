package Game;
import java.util.Random;
public class Boss extends Creature{
    boolean alive;
    int x;
    int y;
    String lastMove = "";
    String pic = "boss.png";
    int lvl;
    Random random = new Random();
    int b = random.nextInt(7 - 1) + 1;
    int HP;
    int DP;
    int SP;
    public Boss(int lvl){
        this.lvl = lvl;
        this.HP = 2 * lvl * b + b;
        this.DP = lvl * b + b/2;
        this.SP = lvl * b + lvl;
    }

    @Override
    public void attack(Hero hero) {

    }


    @Override
    public String findCollision2(Boss boss, int[][] arr) {
        return super.findCollision2(boss, arr);
    }
    @Override
    public boolean canCreateThere(int[][] arr, int x, int y) {
        return super.canCreateThere(arr, x, y);
    }

    @Override
    public void move(int x, int y, int [][] arr, Boss boss) {
        String bannedDirections = findCollision2(boss, arr);
        if(boss.x + 72 == 720){bannedDirections += "right";}        // pre istotu ešte raz kontrolujeme či chcený pohyb nespôsobí únik z mapy
        if(boss.x - 72 < 0){bannedDirections += "left";}
        if(boss.y + 72 == 720){bannedDirections += "down";}
        if(boss.y - 72 < 0){bannedDirections += "top";}
        boolean moved = false;
        do{
            int b = (int)(Math.random()*(4-1+1)+1);                     //generujeme náhodné čísla a pýtame sa či smer do ktorého chceme ísť je povolený alebo zakázaný
            if(!bannedDirections.contains("left") && b == 1) {          //do while sa opakuje dovtedy dokým sa daný skeleton nepohne
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
