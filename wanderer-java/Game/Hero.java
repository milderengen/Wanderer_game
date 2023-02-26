package Game;

import java.util.List;
import java.util.Random;

public class Hero extends Creature{
    boolean alive = true;
    boolean hasKey;
    String pic = "hero-right.png";
    int lvl = 1;
    int x = 0;                                      // súradnice
    int y = 0;
    int b = (int)(Math.random()*(6-1+1)+1);


    int HP = 20 + (3*b);
    int DP = 2*b;
    int SP = 5 + b;
    int maxHP = HP;


    @Override
    public void attack(Skeleton skeleton, List<Skeleton> skeletons) {

        Random random = new Random();
        int randomNum = random.nextInt(7 - 1) + 1;
        int attackDamage = 2*randomNum+ this.SP;                            //generuje sa attack damage
        if(attackDamage > skeleton.DP){                                     // ak je vygenerované čislo vyššie ako defense daneho skeletona tak útok prejde
            skeleton.HP -= (attackDamage - skeleton.DP);                    // skeleton dostáva dmg rovný attack damge - jeho defense
        }
        if(skeleton.HP < 1){                                                //ak po útoku zomrie a má kľúč, hrdina ho zoberie
            if(skeleton.hasKey){
                this.hasKey = true;
            }
            skeleton.alive = false;
            for (int i = 0; i < skeletons.size(); i++) {                    //Skeleton sa hľadá v liste skeletonov podľa súradníc a z listu sa maže
                if(skeleton.x == skeletons.get(i).x && skeleton.y == skeletons.get(i).y){
                    skeletons.remove(i);
                    this.levelUp();                                         // hrdina dostáva lvl Up ktorý mu dáva životy útok aj obranu
                    return;
                }
            }
        }
        int attackDamageSkelly = 2*randomNum+ skeleton.SP;                  // Ak skeleton prežije útok tak útočí nazad, ak hrdina zomrie, hra sa vypne a dostaneme hlášku do konzoly
        if(attackDamageSkelly > this.DP){
            this.HP -= (attackDamageSkelly - this.DP);
        }
        if(this.HP < 1){
            alive = false;
            System.out.println("You lost");
            System.exit(0);
        }
    }

    @Override
    public void attack(Boss boss, List<Boss> bossList) {                    //princíp ten istý len s bossom
        Random random = new Random();
        int randomNum = random.nextInt(7 - 1) + 1;
        int attackDamage = 2*randomNum+ this.SP;
        if(attackDamage > boss.DP){
            boss.HP -= (attackDamage - boss.DP);
        }
        if(boss.HP < 1){
            boss.alive = false;
            if(boss.x == bossList.get(0).x && boss.y == bossList.get(0).y){
                bossList.remove(0);
                this.levelUp();
                return;
            }
        }
        int attackDamageBoss = 2*randomNum+ boss.SP;
        if(attackDamageBoss > this.DP){
            this.HP -= (attackDamageBoss - this.DP);
        }
        if(this.HP < 1){
            alive = false;
            System.out.println("You Lost");
            System.exit(0);
        }
    }

    @Override
    public boolean canCreateThere(int[][] arr, int x, int y) {
        return super.canCreateThere(arr, x, y);
    }
    @Override
    public boolean findCollision(String who, int num, Hero hero, int[][] arr) {
        return super.findCollision(who, num, hero, arr);
    }
    public void levelUp(){          // pridávajú sa stats po vyhratom súboji
        Random random = new Random();
        int hpThingy = random.nextInt(7 - 1) + 1;
        this.HP += hpThingy;
        this.DP += random.nextInt(7 - 1) + 1;
        this.SP += random.nextInt(7 - 1) + 1;
        this.maxHP += hpThingy;
        this.lvl++;
    }
}
