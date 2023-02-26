package Game;

import javax.swing.*;
    import java.awt.*;
    import java.awt.event.KeyEvent;
    import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JComponent implements KeyListener {
        Hero hero = new Hero();
        int[][] wallCoordinates = new int[44][2];                   //pole pre koordinaty stien
        List<Skeleton> Skeletons = new ArrayList<>();               // list pre skeletonov
        int[][] skelyCoordinates;
        List<Boss> bossList = new ArrayList<>();                    // list pre bossa, prečo som to nedal ako list pre creatures, nikto nevie
        int moveCounter = 0;
        int funcCounter = 0;
        int arrCounter = 0;                                         // premenné používane pri tvorbe lvl atd.
        int monsterCounter = 0;
        int level = 1;
        boolean changed;

        String file;
        public Board() {
            hero.x = 0;
            hero.y = 0;
            file = "hero-right.png";                                    //pripravuje sa prostredie

            // set the size of your draw board
            setPreferredSize(new Dimension(720, 740));
            setVisible(true);
        }

        @Override
        public void paint(Graphics graphics) {                          //kreslia sa levely, vždy ked polia dosiahnu velkost 0(všetci su mrtvi) kreslí sa další, premenne sa setuju na 0
                doGame(level,graphics);
                if(Skeletons.size() == 0 && bossList.size() == 0){
                    if(!changed){
                        level++;
                        monsterCounter = 0;
                        moveCounter = 0;
                        funcCounter = 0;
                        arrCounter = 0;
                        changed = true;
                        hero.x = 0;
                        hero.y = 0;
                    }
                    doGame(level, graphics);
                    if(Skeletons.size() == 0 && bossList.size() == 0 && level <= 3){
                        if(changed){
                            level++;
                            monsterCounter = 0;
                            moveCounter = 0;
                            funcCounter = 0;
                            arrCounter = 0;
                            changed = false;
                            hero.x = 0;
                            hero.y = 0;
                        }
                        doGame(level, graphics);
                    }
                }
        }
        public void doGame(int lvl, Graphics graphics){                         //tvorenie jednotlivych levelov
            backgroundStuff(graphics,lvl);                                      // tvorba mapy(pozadie, steny)
            if(monsterCounter < 1){
                createEnemy(lvl);                                               //tvorba nepriatelov
                monsterCounter++;
            }
            if(moveCounter == 2){                                               // loop na pohyb monštier, každe dva pohyby hráča sa pohnu oni raz
                for(int j = 0; j < Skeletons.size(); j++){
                    Skeletons.get(j).move(Skeletons.get(j).x,Skeletons.get(j).y,wallCoordinates,Skeletons.get(j));
                    Skeletons.get(j).count = 0;
                }
                for(int j = 0; j<bossList.size();j++){
                    bossList.get(0).move(bossList.get(0).x,bossList.get(0).y,wallCoordinates,bossList.get(0));
                }
                moveCounter = 0;
            }
            for(int j = 0; j < Skeletons.size(); j++){                          // kreslenie skeletonov
                Game.PositionedImage skelly = new Game.PositionedImage(Skeletons.get(j).pic,Skeletons.get(j).x,Skeletons.get(j).y);
                skelly.draw(graphics);

            }
            graphics.setFont(new Font("TimesRoman", Font.PLAIN, 17));   // toto aj riadok nižšie kresba písma pre štatistiky o hrdinovi
            graphics.drawString("Hero level: " + hero.lvl + " | " + "Hero hp: " + hero.HP + " | " + "Hero max HP: " + hero.maxHP + " | " + "Hero strike damage: " + hero.SP + " | " + "Do you have key?" + hero.hasKey,0,735);
            for(int j = 0; j < bossList.size();j++){                                // kresba bossa
                Game.PositionedImage mf = new Game.PositionedImage(bossList.get(0).pic,bossList.get(0).x,bossList.get(0).y);
                mf.draw(graphics);
            }
            if(hero.alive){                                                       // kresba hrdinu
                Game.PositionedImage image = new Game.PositionedImage(file,hero.x,hero.y);
                image.draw(graphics);
            }
            if(Skeletons.size() == 0 && bossList.size() == 0 && level == 3){    // ak sa polia rovnaju nula(všetkých si pobil) a level je 3 zavrie program a vypíše víťaznu hlášku
                System.out.println("You Won");
                System.exit(0);
            }
        }
        public void backgroundStuff(Graphics graphics, int lvl){ //prostredie sa kreslí po riadkoch, táto funkcia otočí funkciu kresliacu jednotlivé riadky 10x keďže 10 riadkove prostredie

            int y = 0;
            int x = 0;
            for(int j = 0; j < 10; j++){
                showMeRow(graphics,j,x,y,lvl);
                y+= 72;
                x = 0;
            }
        }


        public static void main(String[] args) {
            // Here is how you set up a new window and adding our board to it

            JFrame frame = new JFrame("RPG Game");
            Board board = new Board();
            frame.add(board);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.pack();
            // Here is how you can add a key event listener
            // The board object will be notified when hitting any key
            // with the system calling one of the below 3 methods
            frame.addKeyListener(board);
            // Notice (at the top) that we can only do this
            // because this Game.Board class (the type of the board object) is also a KeyListener
        }

        // To be a KeyListener the class needs to have these 3 methods in it
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        // But actually we can use just this one for our goals here
        @Override
        public void keyReleased(KeyEvent e) {   //Key eventy, pohyb a utok
            // pri šípkach sa kontroluje či hrdina nevyjde z mapy alebo či sa nesnaží vojsť do steny.
            if (e.getKeyCode() == KeyEvent.VK_UP && hero.y - 72  >= 0 && hero.findCollision("y", 2, hero, wallCoordinates)) {
                file = "hero-up.png";
                hero.y -= 72;
                moveCounter++;
            } else if(e.getKeyCode() == KeyEvent.VK_DOWN && hero.y + 72 <= 648 && hero.findCollision("y", 1, hero, wallCoordinates)) {
                file = "hero-down.png";
                hero.y += 72;
                moveCounter++;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT && hero.x - 72  >= 0 && hero.findCollision("x", 2, hero, wallCoordinates)) {
                file = "hero-left.png";
                hero.x -= 72;
                moveCounter++;
            } else if(e.getKeyCode() == KeyEvent.VK_RIGHT && hero.x + 72  <= 648 && hero.findCollision("x", 1, hero, wallCoordinates)) {
                file = "hero-right.png";
                hero.x += 72;
                moveCounter++;
            }else if(e.getKeyCode() == KeyEvent.VK_SPACE) {     // pomocou funkcie kontroluje či sa hrdina nachádza na rovnakom poli ako je príšera/boss
                if(controlMobs() && cratureType().equals("Skeleton")){  // a v prípade že áno zaútočí na ňu a príšera, za predpokladu že prežije zaútočí naspäť
                    hero.attack(findEnemySkelly(),Skeletons);
                }else if (controlMobs() && cratureType().equals("Boss")) {
                    hero.attack(findBoss(),bossList);
                };
            }
            repaint();

        }
        public void addCoordinates(int x, int y){           //funkcia opakujuca sa pri tvorbe príšer a ich pozície na mape, pridáva X a Y pozície do poľa
            if(funcCounter <= 40){
                wallCoordinates[arrCounter][0] = x;
                wallCoordinates[arrCounter][1] = y;
                funcCounter++;
                arrCounter++;
            }
        }
        public void showMeRow(Graphics graphics, int j, int x, int y, int lvl){
            String mapBg;
            if(lvl == 1){
                if(j == 0){
                    for (int i = 0; i < 10; i++) {
                        if(i == 3 || i == 5){
                            addCoordinates(x,y);
                            mapBg = "wall.png";
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 1) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 3 || i == 5 || i == 7 || i == 8){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 2) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 1|| i == 2|| i == 3 || i == 5 || i == 7 || i == 8){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 3) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 5){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 4) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 0||i == 1|| i == 2|| i == 3 || i == 5 || i == 6 || i == 7 || i == 8){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 5) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 1 || i == 3 || i == 8){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 6) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 1|| i == 3|| i == 5 || i == 6 || i == 8){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 7) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 5 || i == 6 || i == 8){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 8) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 1 || i == 2 || i == 3|| i == 8){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 9) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 3 ||i == 5 || i == 6){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }
            }else if(lvl == 2) {
                if(j == 0){
                    for (int i = 0; i < 10; i++) {
                        if(i == 3){
                            addCoordinates(x,y);
                            mapBg = "wall.png";
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 1) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 6 || i == 7 || i == 8 || i == 9){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 2) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 1|| i == 2|| i == 3 || i == 0 || i == 9){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 3) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 3 || i == 9){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 4) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 3 || i == 9){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 5) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 1 || i == 3 || i == 8 || i == 9 || i == 7 || i == 6){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 6) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 1|| i == 3){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 7) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 1 || i == 2 || i == 3 || i == 7){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 8) {
                    for (int i = 0; i < 10; i++) {
                        if((i == 6 || i == 7 || i == 8 || i == 9)){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 9) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 1 || i == 2 || i == 6 || i == 7 || i == 8 || i == 9){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }
            }else if(lvl == 3) {
                if(j == 0){
                    for (int i = 0; i < 10; i++) {
                        if(i == 9 || i == 8 || i == 7 || i == 6 || i == 5){
                            addCoordinates(x,y);
                            mapBg = "wall.png";
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 1) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 3 || i == 1 || i == 0 || i == 9 || i == 5){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 2) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 9){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 3) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 5 || i == 1 || i == 3 || i == 9){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 4) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 1|| i == 2|| i == 3 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 5) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 2 || i == 6){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 6) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 6){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 7) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 0 || i == 1 || i == 2 || i == 3 || i == 8){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 8) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 8){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }else if (j == 9) {
                    for (int i = 0; i < 10; i++) {
                        if(i == 8 ||i == 5 || i == 6){
                            mapBg = "wall.png";
                            addCoordinates(x,y);
                        }else{
                            mapBg = "floor.png";
                        }
                        PositionedImage image = new PositionedImage(mapBg,x,y);
                        image.draw(graphics);
                        x+=72;
                    }
                }
            }
        }   // kreslí jednotlivé riadky podľa argumentu ktorý dostane pri volaní funkcie
        public void createEnemy(int lvl){                                              // tvorba skeletonov a bossa, generujú sa náhodne súradnice dovtedy dokým všetky súradnice príšer niesú iné ako súradnice stien
            Skeleton bob = new Skeleton(lvl);
            Skeleton rob = new Skeleton(lvl);
            Skeleton kyle = new Skeleton(lvl);
            Skeleton rudy = new Skeleton(lvl);
            Skeleton karen = new Skeleton(lvl);
            Skeletons.add(bob);
            Skeletons.add(rob);
            Skeletons.add(kyle);
            Skeletons.add(rudy);
            Skeletons.add(karen);
            Random random = new Random();
            int keyHolder = random.nextInt(5);
            for(int i = 0; i < Skeletons.size();i++){                   //náhodne sa generuje ktorý skeleton má kľúč potrebný na postup do ďalšieho levela
                if(i == keyHolder){ Skeletons.get(i).hasKey = true;}
            }
            skelyCoordinates = new int[Skeletons.size()+2][2];
            int randomX;
            int randomY;
            int skelyCounter = 0;
            for (int i = 0; i < Skeletons.size(); i++) {
                do{
                    do{
                        randomX = (int)(Math.random()*(648-1+1)+1);
                    }while(randomX % 72 != 0);
                    do{
                        randomY = (int)(Math.random()*(648-1+1)+1);
                    }while(randomY % 72 != 0);
                }while(!Skeletons.get(i).canCreateThere(wallCoordinates, randomX, randomY) && skeletonCreationP2(skelyCoordinates,Skeletons,randomX,randomY));
                Skeletons.get(i).x = randomX;
                Skeletons.get(i).y = randomY;
                skelyCoordinates[skelyCounter][0] = randomX;
                skelyCoordinates[skelyCounter][1] = randomY;
                skelyCounter++;
            }

            Boss boss = new Boss(lvl);
            do{
                do{
                    randomX = (int)(Math.random()*(648+1)+0);
                }while(randomX % 72 != 0 && randomX !=0);
                do{
                    randomY = (int)(Math.random()*(648+1)+0);
                }while(randomY % 72 !=0 && randomY != 0);
            }while(!boss.canCreateThere(wallCoordinates, randomX, randomY) && skeletonCreationP2(skelyCoordinates,Skeletons,randomX,randomY)); //točí sa dokým nieje mimo stien a iných príšer
            boss.x = randomX;
            boss.y = randomY;
            skelyCoordinates[skelyCounter][0] = randomX;
            skelyCoordinates[skelyCounter][1] = randomY;
            bossList.add(boss);
        }
        public boolean skeletonCreationP2(int[][] arr, List skeletons , int x, int y){ //zaručuje že sa príšery nespawnu na sebe
            for(int i = 0; i <= skeletons.size()+1;i++){
                if(arr[i][0] == x && arr[i][1] == y){
                    return false;
                }
            }
            return true;
        }
        public boolean controlMobs(){                                               //fukcia kontrolujúca pri stlačení medzerníka či sa nachádzate na tom istom políčku ako príšera/boss
            for (int i = 0; i < Skeletons.size(); i++) {
                if(hero.x == Skeletons.get(i).x && hero.y == Skeletons.get(i).y){
                    return true;
                }
            }
            for (Boss boss : bossList) {
                return hero.x == boss.x && hero.y == boss.y;
            }
            return false;
        }
        public String cratureType(){                                            //zistuje či je príšera na tvojich súradniciach skeleton alebo boss
            for (int i = 0; i < Skeletons.size(); i++) {
                if(hero.x == Skeletons.get(i).x && hero.y == Skeletons.get(i).y){
                    return "Skeleton";
                }
            }
            return "Boss";
        }
        public Skeleton findEnemySkelly(){                                      //v prípade že je to skeleton zistuje ktorý konkrétne skeleton to je
            Skeleton skeleton = null;
            for (int i = 0; i < Skeletons.size(); i++) {
                if(hero.x == Skeletons.get(i).x && hero.y == Skeletons.get(i).y){
                    skeleton = Skeletons.get(i);
                }
            }
            return skeleton;
        }
        public Boss findBoss(){                                                 //boss je len jeden takže ho nemusí hľadať
            return bossList.get(0);
        }



    }


