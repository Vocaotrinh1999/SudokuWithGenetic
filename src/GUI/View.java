package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import SudokuV2.Board;
import SudokuV2.Game;

//nhận đề bài từ lớp board và check game nếu người chơi và tạo ra goal nếu dùng nút solver
public class View extends JFrame implements ActionListener {

	private JTextField[][] jtextFiled;
	int[][] debai = new int[9][9];
	int[][] filled = new int[9][9];
	int[][] result = new int[9][9];
	Board board = new Board();
	Game g = new Game();

	public View() {
		creatView();
	}

	public void creatView() {
		JFrame jframe = new JFrame();
		jframe.setTitle("Game Sudoku With AI");
		Container container = jframe.getContentPane();
		JPanel intro = new JPanel(new BorderLayout());
		ImageIcon imageIcon = new ImageIcon("image\\nonglam.jpg");
		JLabel imageLabel = new JLabel(imageIcon);
		JPanel panelButton = new JPanel(new GridLayout(3, 1, 10, 10));
		JButton player = new JButton("Check");
		player.setActionCommand("Check");
		player.addActionListener(this);

		JButton playComputer = new JButton("Solver");
		playComputer.setActionCommand("Solver");
		playComputer.addActionListener(this);
		JButton newGame = new JButton("New Game");
		panelButton.add(player);
		panelButton.add(playComputer);
		panelButton.add(newGame);
		intro.add(imageLabel, BorderLayout.NORTH);
		intro.add(panelButton, BorderLayout.SOUTH);
		intro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		JPanel panel = new JPanel(new GridLayout(9, 9));// panel for game

		debai = board.deBai().solution;
		jtextFiled = new JTextField[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				jtextFiled[i][j] = new JTextField();
				jtextFiled[i][j].setHorizontalAlignment(JTextField.CENTER);
				jtextFiled[i][j].setFont(new Font(Font.SERIF, Font.BOLD, 20));
				if (debai[i][j] != 0) {
					jtextFiled[i][j].setText(String.valueOf(debai[i][j]));
					jtextFiled[i][j].setEnabled(false);
				} else {
					jtextFiled[i][j].setText("");
				}
				panel.add(jtextFiled[i][j]);
			}
		}
		JPanel mainPainel = new JPanel(new BorderLayout());
		mainPainel.add(intro, BorderLayout.WEST);
		mainPainel.add(panel, BorderLayout.CENTER);
		container.add(mainPainel);
		// jframe.setBackground(Color.WHITE);
		jframe.setSize(800, 600);
		jframe.setVisible(true);

	}

	public void displayBoard(int[][] arr) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++)
				System.out.print(arr[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		View gd = new View();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String txt = e.getActionCommand();
		if (txt.equals("Check")) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					filled[i][j] = Integer.parseInt(jtextFiled[i][j].getText());
				}
			}
			displayBoard(filled);
			g = new Game(filled);
			if (g.isGoal(g)) {
				JOptionPane.showMessageDialog(null, "Chúc mừng bạn đã chiến thắng.");
			} else {
				JOptionPane.showMessageDialog(null, "Bạn chưa hoàn thành thử thách. Xin thử lại.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (txt.equals("Solver")) {
			Game goal = board.run();
			int[][] solution = goal.solution;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					jtextFiled[i][j].setText(String.valueOf(solution[i][j]));
				}
			}
		}
	}
}
