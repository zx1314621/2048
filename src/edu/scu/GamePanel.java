package edu.scu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private class Color{
		int fontColor;
		int backGroundColor;
		Color(int f, int b){
			fontColor=f;
			backGroundColor=b;
		}
	}
	
	private final int WIN=1;
	private final int LOSE=2;
	private final int PLAYING=0;
	
	int frameWidth=800;//???????????????
	int frameHeight=600;//???????????????????
	int blockBorderWidth=6;
	
	
	Game game;
	Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
	JPanel gameMapPanel = new JPanel();
	JPanel scoreAndButtonPanel = new JPanel();
	JPanel scorePanel = new JPanel();
	JPanel buttonsPanel = new JPanel();
	JLabel blocks[][];
	JLabel score;
	
	GamePanel(){
		game = new Game();
		initGUI();
		refresh();
	}
	GamePanel(int[][] map){
		game = new Game(map);
		initGUI();
		refresh();
	}
	//要不要加上一个对胜利条件的设置？????????????????????????????????
	
	private void initGUI() {
		colorMap.put(0, new Color(0x776e65, 0xCDC1B4));
        colorMap.put(2, new Color(0x776e65, 0xeee4da));
        colorMap.put(4, new Color(0x776e65, 0xede0c8));
        colorMap.put(8, new Color(0xf9f6f2, 0xf2b179));
        colorMap.put(16, new Color(0xf9f6f2, 0xf59563));
        colorMap.put(32, new Color(0xf9f6f2, 0xf67c5f));
        colorMap.put(64, new Color(0xf9f6f2, 0xf65e3b));
        colorMap.put(128, new Color(0xf9f6f2, 0xedcf72));
        colorMap.put(256, new Color(0xf9f6f2, 0xedcc61));
        colorMap.put(512, new Color(0xf9f6f2, 0xe4c02a));
        colorMap.put(1024, new Color(0xf9f6f2, 0xe2ba13));
        colorMap.put(2048, new Color(0xf9f6f2, 0xecc400));
		
		//set layout, add in content. add listener
		this.setBackground(new java.awt.Color(0xCDC1B4)); // background color
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		gameMapPanel.setLayout(new GridLayout(4,4));// 这里怎么搞个尺寸，最好是比例的。
		gameMapPanel.setPreferredSize(new Dimension(frameWidth*2/3,frameHeight));
		blocks = new JLabel[4][4];
		JLabel block;
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				blocks[i][j]=new JLabel("0",JLabel.CENTER);
				blocks[i][j].setOpaque(true);//?????????????????????????
				blocks[i][j].setBorder(BorderFactory.createMatteBorder(blockBorderWidth, blockBorderWidth, blockBorderWidth, blockBorderWidth, new java.awt.Color(0xBBADA0)));
				blocks[i][j].setFont(new java.awt.Font("Dialog", 1, 52));
				gameMapPanel.add(blocks[i][j]);
			}
		}
		
		scoreAndButtonPanel.setLayout(new GridLayout(2,1));
		
		scorePanel.setLayout(new GridLayout(2,1));//怎么使得上面的“Score”小，下面的分数大？
		scorePanel.setPreferredSize(new Dimension(frameWidth/3,frameHeight/2));
		JLabel scoreTitle = new JLabel("Score: ", JLabel.CENTER);
		score = new JLabel("0", JLabel.CENTER);
		scoreTitle.setOpaque(true);//这有啥用？？？？？？
		score.setOpaque(true);//????????????
		scoreTitle.setFont(new java.awt.Font("Dialog", 1, 52));//字体大小不一样有用吗？
		score.setFont(new java.awt.Font("Dialog", 1, 52));
		scorePanel.add(scoreTitle);
		scorePanel.add(score);
		
		buttonsPanel.setLayout(new BorderLayout()); // 想让四个按钮向中间靠拢，也就是center尽可能小。
		JButton up = new JButton("up");
		JButton down = new JButton("down");
		JButton left = new JButton("left");
		JButton right = new JButton("right");
		buttonsPanel.add(up,BorderLayout.NORTH);
		buttonsPanel.add(down, BorderLayout.SOUTH);
		buttonsPanel.add(left, BorderLayout.WEST);
		buttonsPanel.add(right,BorderLayout.EAST);
		
		//需要添加按钮监听  和 键盘监听 //???????????????????????????????????????????
		
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				
				// TODO Auto-generated method stub
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
	                    game.moveUp();
	                    refresh();
	                    checkResult();
	                    break;
	
	                case KeyEvent.VK_DOWN:
	                    game.moveDown();
	                    refresh();
	                    checkResult();
	                    break;
	
	                case KeyEvent.VK_LEFT:
	                    game.moveLeft();
	                    refresh();
	                    checkResult();
	                    break;
	
	                case KeyEvent.VK_RIGHT:
	                	game.moveRight();
	                	refresh();
	                	checkResult();
	                    break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		
		scoreAndButtonPanel.add(scorePanel);
		scoreAndButtonPanel.add(buttonsPanel);
		this.add(gameMapPanel);
		this.add(scoreAndButtonPanel);
		
		//记得在frame那边requestfocus！！！frame.getcontentPane().requestFocus(); 否则无法监听
		//??????????????????????????????????????????????????????????不知道下面这三行要不要加
		this.setVisible(true);
		//this.setEnabled(true);
		this.setFocusable(true);
	}
	
	private void refresh() {
		//update map and score
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				blocks[i][j].setText(""+game.map[i][j]);
			}
		}
		game.updateScore();
		score.setText(game.score+"");
	}
	
	private int checkResult() {
		//check if the game has ended
		if(game.isGameWin()) 
			return WIN;
		else if(game.isGameLose())
			return LOSE;
		else return PLAYING;
	}
	
}
