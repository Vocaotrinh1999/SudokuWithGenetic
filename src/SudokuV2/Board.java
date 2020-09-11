package SudokuV2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

	int quantity = 10;// so luong quan the ban dau
	Random random = new Random();
	int generation = 1950; // số thế hệ tạo ra

	/*
	 * Đọc các đoạn gene đã được tạo sẵn
	 */
	public List<Gene> creatPopGen() {
		List<Gene> listGen = new ArrayList<Gene>();
		try {
			BufferedReader bf = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File("image/gen3.txt"))));
			String line = null;
			Gene gen2 = null;
			while ((line = bf.readLine()) != null) {
				String[] arrGen = line.split(" ");
				Integer[] cgen = new Integer[9];
				for (int i = 0; i < 9; i++) {
					cgen[i] = Integer.parseInt(arrGen[i]);
					gen2 = new Gene(cgen);
				}
				listGen.add(gen2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listGen;
	}

	// đọc đề bài được tạo sẵn và lưu vào file
	public List<Gene> docDeBai() {
		List<Gene> listGen = new ArrayList<Gene>();
		try {
			BufferedReader bf = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File("image/sudokueasy2.txt"))));
			String line = null;
			Gene gen2 = null;
			while ((line = bf.readLine()) != null) {
				// line = bf.readLine();
				String[] arrGen = line.split(" ");
				Integer[] cgen = new Integer[9];
				for (int i = 0; i < 9; i++) {
					cgen[i] = Integer.parseInt(arrGen[i]);
					gen2 = new Gene(cgen);
				}
				listGen.add(gen2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listGen;
	}

	// từ các đoạn gene từ đề bài ghép lai tạo thành 1 sudoku đề bài cần giải
	public Game deBai() {
		List<Gene> list = docDeBai();
		int[][] c = new int[9][9];
		Game game = null;
		for (int i = 0; i < 9; i++) {
			Gene gen = list.get(i);
			for (int j = 0; j < 9; j++) {
				c[i][j] = gen.gen[j];
			}
		}
		game = new Game(c);
		return game;
	}

	// tìm các đoạn gene có đặc điểm giống với 1 đoạn gene cho trước
	public List<Gene> selectGen(Gene gen) {
		List<Gene> listSelect = new ArrayList<Gene>();
		List<Gene> listGenPop = creatPopGen();
		for (Gene gen2 : listGenPop) {
			Integer[] arrG1 = gen.gen;
			Integer[] arrG2 = gen2.gen;
			List<Integer> l1 = new ArrayList<Integer>();
			List<Integer> l2 = new ArrayList<Integer>();
			for (int i = 0; i < arrG2.length; i++) {
				l1.add(arrG1[i]);
				l2.add(arrG2[i]);
			}
			int index;
			while ((index = l1.indexOf(0)) >= 0) {
				l1.remove(index);
				l2.remove(index);
			}
			if (l1.equals(l2)) {
				listSelect.add(gen2);
			}
		}
		return listSelect;
	}

	// lấy các đoạn gene giống đề
	public List<Gene> selectGenRow2() {
		List<Gene> list = docDeBai();
		List<Gene> result = new ArrayList<Gene>();
		for (int i = 0; i < list.size(); i++) {
			List<Gene> list2 = selectGen(list.get(i));
			Gene g = list2.get(random.nextInt(list2.size()));
			result.add(g);
		}
		return result;
	}

	// ghép các đoạn gene giống đề tìm được tạo thành 1 sudoku
	public Game popGame2() {
		List<Gene> list = selectGenRow2();
		int[][] c = new int[9][9];
		Game game = null;
		for (int i = 0; i < 9; i++) {
			Gene gen = list.get(i);
			for (int j = 0; j < 9; j++) {
				c[i][j] = gen.gen[j];
			}
		}
		game = new Game(c);
		return game;
	}

	// chạy vòng lặp tạo ra quần thể ban đầu
	public List<Game> population() {
		List<Game> listGame = new ArrayList<Game>();
		List<Gene> listGen = creatPopGen();
		int qg = listGen.size();
		for (int k = 0; k < quantity; k++) {
			Game game = popGame2();
			listGame.add(game);
		}
		return listGame;
	}

	/*
	 * Lai tao 2 game bố mẹ cho ra các thế hệ con Random 1 vị trí bất kì rồi lấy lần
	 * lượt các đoạn gene của cha và mẹ ghép lại với nhau tạo thành game mới
	 */
	public List<Game> crossover(Game father, Game mother) {
		List<Game> list = new ArrayList<Game>();
		int[][] sFather = father.solution;
		int[][] sMother = mother.solution;
		int[][] child1 = new int[9][9];
		int[][] child2 = new int[9][9];
		int indexCross = random.nextInt(9);
		System.out.println("index cross : " + indexCross);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (i != indexCross) {
					child1[i][j] = sFather[i][j];
					child2[i][j] = sMother[i][j];
				} else {
					child1[i][j] = sMother[i][j];
					child2[i][j] = sFather[i][j];
				}
			}
		}
		Game game1 = new Game(child1);
		Game game2 = new Game(child2);
		list.add(game1);
		list.add(game2);
		return list;
	}

	/*
	 * Tạo đột biến trên 1 đoạn gene Random ra 1 vị trí ngẫu nhiên lấy 1 đoạn gene
	 * đem ra tạ đột biến Trên đoạn gene đó lấy ra 2 vị trí ngẫu nhiên hợp lệ so với
	 * đề bài đem đổi chổ cho nhau tạo ra 1 game mới
	 */
	public Game mutation(Game g) {
		int[][] solution = g.solution;
		int index = random.nextInt(9);
		List<Integer> l1 = new ArrayList<Integer>();
		Gene gen = docDeBai().get(index);
		Integer[] arrGen = gen.gen;
		for (int i = 0; i < arrGen.length; i++) {
			if (arrGen[i] == 0) {
				l1.add(i);
			}
		}
		int i1 = l1.get(random.nextInt(l1.size()));
		int i2 = l1.get(random.nextInt(l1.size()));
		System.out.println("Index : " + index + " i1 : " + i1 + " i2 : " + i2);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (i == index) {
					int temp = solution[i][i1];
					solution[i][i1] = solution[i][i2];
					solution[i][i2] = temp;
				}
			}
		}
		Game result = new Game(solution);
		return result;
	}

	// hàm đánh giá độ xung đột trung bình của quần thể
	public int fitness(List<Game> list) {
		int fitness = 0;
		int sum = 0;
		for (Game game : list) {
			sum += game.eval(game);
			fitness = sum / list.size();
		}
		return fitness;
	}

	// hàm run chạy game
	public Game run() {
		Game game = null;
		// khởi tạo quần thể
		List<Game> population = population();
		List<Game> initPopulation = new ArrayList<Game>();
		// lưu quần thể vào list
		initPopulation.addAll(population);
		// tiến hành đánh giá và loại bớt các phần tử xấu
		for (int i = 0; i < initPopulation.size() - 1; i++) {
			if (initPopulation.get(i).eval(initPopulation.get(i)) > fitness(initPopulation)) {
				initPopulation.remove(i);
			}
		}
		// chạy vòng lặp tìm ra goal
		outer: for (int j = 0; j < generation; j++) {
			int selectFather = random.nextInt(initPopulation.size());
			int selectMother = random.nextInt(initPopulation.size());
			// chọn ngẫu nhiên game cha và mẹ để lai tạo
			Game father = initPopulation.get(selectFather);
			Game mother = initPopulation.get(selectMother);
			// tiến hành lai tạo
			List<Game> child = crossover(father, mother);
			// thêm các phần tử sau lai vào quần thể
			initPopulation.addAll(child);
			// chọn ngẫu nhiên 1 phần tử đem ra tạo đột biến
			int selectMutation = random.nextInt(child.size());
			Game gameMutation = child.get(selectMutation);
			Game mutationResult = mutation(gameMutation);
			// lưu game đã đột biến vào quần thể
			initPopulation.add(mutationResult);
			// tiến hành đánh giá
			for (int i = 0; i < initPopulation.size() - 1; i++) {
				// Nếu chạy ra được goal thì dừng lại và đưa ra kết quả
				if (initPopulation.get(i).eval(initPopulation.get(i)) == 0) {
					System.out.println("This is goal");
					game = initPopulation.get(i);
					break outer;
					// loại bỏ các phần tử xấu trong quần thể
				} else if (initPopulation.get(i).eval(initPopulation.get(i)) > fitness(initPopulation)) {
					initPopulation.remove(i);
				}
			}
			System.out.println("init size : " + initPopulation.size());
			System.out.println("fitness : " + fitness(initPopulation));
		}
		return game;
	}

	// hiển thị console 1 sudoku
	public void displayGame(Game g) {
		int[][] solution = g.solution;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(solution[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Board b = new Board();
		Game game = b.run();
		b.displayGame(game);
	}

}
