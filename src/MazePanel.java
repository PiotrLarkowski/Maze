import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

public class MazePanel extends JPanel implements ActionListener{

    private Image img = new ImageIcon("C:\\Users\\Larko\\IdeaProjects\\Maze\\src\\greenSquare.png").getImage();

    private final JButton bExit, bStart, bNewMap, bChangeValueOfMaze;
    private int positionX, positionY, playerPositionY=10, playerPositionX=0, stepsCounter = 0;
    private int[][] mazeArray = new int[20][40];
    private String[] playerMove = new String[800];
    private final JTextField tfPositionX, tfPositionY;

    private boolean inThereMoveLeft, inThereMoveRight = false, inThereMoveUp = false, inThereMoveDown = false;
    boolean mazeHasBeenCreated=false;;
    public MazePanel() {
        setPreferredSize(new Dimension(1000, 600));
        setName("Maze");
        setLayout(null);

        playerMove[0]="START";

        bExit = new JButton("Exit");
        bExit.setBounds(850, 550, 80, 20);
        bExit.addActionListener(this);
        add(bExit);

        bStart = new JButton("Start");
        bStart.setBounds(850, 520, 80, 20);
        bStart.addActionListener(this);
        add(bStart);

        bNewMap = new JButton("New Map");
        bNewMap.setBounds(710, 520, 120, 20);
        bNewMap.addActionListener(this);
        add(bNewMap);

        tfPositionX = new JTextField();
        tfPositionX.setBounds(200,535,40,20);
        add(tfPositionX);

        tfPositionY = new JTextField();
        tfPositionY.setBounds(300,535,40,20);
        add(tfPositionY);

        bChangeValueOfMaze = new JButton("Change Value");
        bChangeValueOfMaze.setBounds(350, 535, 120, 20);
        bChangeValueOfMaze.addActionListener(this);
        add(bChangeValueOfMaze);
    }

    private int[][] creatingMaze(){
        int[][] intArray = new int[20][40];
        for(int i =0; i<20; i++) {
            for(int j=0;j<40;j++) {
                int randomValue, maxValue=10;
                Random r = new Random();
                randomValue = r.nextInt(maxValue);
                if(i==10 && j==0) {
                    intArray[i][j]=1;
                }
                else if(randomValue <= 3)
                {
                    intArray[i][j]=7;
                }else{
                    intArray[i][j]=6;
                }
                positionX+=20;
            }
            positionX = 0;
            positionY+=20;
        }
        return intArray;
    }

    public void repaintMap() {
        mazeArray = creatingMaze();
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        if(!mazeHasBeenCreated) {
            mazeArray = creatingMaze();
            mazeHasBeenCreated = true;
        }
        positionX =0;
        positionY = 0;

        g2d.drawString("Set the coordinate of Maze to change value of Maze",140,520);
        g2d.drawString("X value:                   Y value:",150,550);

        drawAxesValues(g2d);

        g2d.setColor(Color.white);
        g2d.fillRect(100, 100, 800, 400);
        for(int i =0; i<20; i++) {
            for(int j=0;j<40;j++) {
                if(mazeArray[i][j] == 1){
                    g2d.setColor(Color.red);
                    g2d.fillRect(100+positionX, 100+positionY, 20, 20);
                }else if(mazeArray[i][j] == 2 || mazeArray[i][j] == 3 || mazeArray[i][j] == 4 || mazeArray[i][j] == 5) {
                    if(mazeArray[i][j] == 2) {
                        img = new ImageIcon("src\\greenSquareUP.png").getImage();
                    }else if(mazeArray[i][j] == 3) {
                        img = new ImageIcon("src\\greenSquareRIGHT.png").getImage();
                    }else if(mazeArray[i][j] == 4) {
                        img = new ImageIcon("src\\greenSquareDOWN.png").getImage();
                    }else if(mazeArray[i][j] == 5) {
                        img = new ImageIcon("src\\greenSquareLEFT.png").getImage();
                    }
                    g2d.drawImage(img, 100 + positionX, 100 + positionY, this);
                }else if(mazeArray[i][j] == 6){
                    g2d.setColor(Color.black);
                    g2d.drawRect(100+positionX, 100+positionY, 20, 20);
                }else if(mazeArray[i][j] == 7)                {
                    g2d.setColor(Color.black);
                    g2d.fillRect(100+positionX, 100+positionY, 20, 20);
                }else if(mazeArray[i][j] == 8){
                    img = new ImageIcon("src\\greenSquare.png").getImage();
                    g2d.drawImage(img, 100 + positionX, 100 + positionY, this);
                }
                positionX+=20;
            }
            positionX = 0;
            positionY+=20;
        }
        positionX =0;
        positionY = 0;
        //printAvailableMovedPosition();
    }

    private static void drawAxesValues(Graphics2D g2d) {
        g2d.drawString("1    2    3     4    5    6    7     8    9    10",108,98);
        int xInterval = 20, yInterval=17;
        for(int i=0;i<30;i++){
            g2d.drawString("  " + (i+11),278+xInterval,98);
            xInterval+=20;
        }
        g2d.drawString("   X",278+xInterval,98);
        xInterval = 0;
        for(int i=1;i<21;i++){
            if (i<10)
                xInterval = 3;
            else
                xInterval=0;
            g2d.drawString(""+i,85+xInterval,98+yInterval);
            yInterval+=20;
        }
        g2d.drawString("Y",88,98+yInterval);
    }

    private void printArray(int[][] array) {
        for(int i = 0;i<20;i++)
        {
            for(int j=0; j<40;j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }

    private void isAvailableMove() {
        inThereMoveLeft = false;
        inThereMoveRight = false;
        inThereMoveUp = false;
        inThereMoveDown = false;
        if(playerPositionY>0 && mazeArray[playerPositionY-1][playerPositionX]==6)
            inThereMoveUp = true;
        if(playerPositionY<40 && mazeArray[playerPositionY+1][playerPositionX]==6)
            inThereMoveDown = true;
        if(playerPositionX>0 && mazeArray[playerPositionY][playerPositionX-1]==6)
            inThereMoveLeft = true;
        if(playerPositionX<20 && mazeArray[playerPositionY][playerPositionX+1]==6)
            inThereMoveRight = true;
    }

    private void printAvailableMovedPosition() {
        System.out.println("X:" + playerPositionX);
        System.out.println("Y:" + playerPositionY);
        System.out.println("Left: " + inThereMoveLeft);
        System.out.println("Right: " + inThereMoveRight);
        System.out.println("Up: " + inThereMoveUp);
        System.out.println("Down: " + inThereMoveDown);
    }
    public void movePlayer(int yFix, int xFix, int squareNumber, String moveLabel){
        mazeArray[playerPositionY+yFix][playerPositionX+xFix] = squareNumber;
        playerMove[stepsCounter]=moveLabel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();
        if(event == bExit) {
            System.exit(0);
        }else if(event == bStart) {
            stepsCounter++;
            do{
                isAvailableMove();
                if(inThereMoveUp) {
                    movePlayer(-1,0,2,"UP");
                    playerPositionY--;
                }else if(inThereMoveRight) {
                    movePlayer(0,1,3,"RIGHT");
                    playerPositionX++;
                }else if(inThereMoveDown) {
                    movePlayer(1,0,4,"DOWN");
                    playerPositionY++;
                }else if(inThereMoveLeft) {
                    movePlayer(0,-1,5,"LEFT");
                    playerPositionX--;
                }else if(!inThereMoveUp && !inThereMoveDown && !inThereMoveLeft && !inThereMoveRight) {
                    switch (playerMove[stepsCounter - 1]) {
                        case "START" -> {
                            JOptionPane.showMessageDialog(null, "There is no exit from maze - rebuild maze", "Alert", JOptionPane.INFORMATION_MESSAGE);
                            repaint();
                        }
                        case "UP" -> {
                            mazeArray[playerPositionY][playerPositionX] = 8;
                            playerPositionY++;
                        }
                        case "RIGHT" -> {
                            mazeArray[playerPositionY][playerPositionX] = 8;
                            playerPositionX--;
                        }
                        case "DOWN" -> {
                            mazeArray[playerPositionY][playerPositionX] = 8;
                            playerPositionY--;
                        }
                        case "LEFT" -> {
                            mazeArray[playerPositionY][playerPositionX] = 8;
                            playerPositionX++;
                        }
                    }
                    playerMove[stepsCounter]=null;
                    stepsCounter--;
                    break;
                }
                stepsCounter++;
            }while(true);
            repaint();
        }else if(event == bNewMap) {
            playerPositionY=10;
            playerPositionX=0;
            stepsCounter = 0;
            playerMove = new String[800];
            repaintMap();
        }else if(event == bChangeValueOfMaze){
            if(mazeArray[Integer.parseInt(tfPositionY.getText())-1][Integer.parseInt(tfPositionX.getText())-1]==6){
                mazeArray[Integer.parseInt(tfPositionY.getText())-1][Integer.parseInt(tfPositionX.getText())-1]=7;
            }else if(mazeArray[Integer.parseInt(tfPositionY.getText())-1][Integer.parseInt(tfPositionX.getText())-1]==7){
                mazeArray[Integer.parseInt(tfPositionY.getText())-1][Integer.parseInt(tfPositionX.getText())-1]=6;
            }
            repaint();
        }
    }
}
