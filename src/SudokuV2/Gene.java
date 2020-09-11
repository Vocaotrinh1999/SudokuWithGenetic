package SudokuV2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/*
 * Một đoạn gen đại diện cho 1 dòng hợp lệ trong sudoku
 * Các dòng này được tạo sẵn và lưu vào file gen3.txt
 */

public class Gene {

	Integer[] gen = new Integer[9];

	public Gene(Integer[] gen2) {
		for (int i = 0; i < gen2.length; i++) {
			gen[i] = gen2[i];
		}
	}

	public Gene() {

	}

	// Phương tạo các đoạn gene hợp lệ
	public List<Gene> creatPopGen() {
		List<Gene> listGen = new ArrayList<Gene>();
		String c = "";
		try {
			for (int k = 0; k < 362880; k++) {
				Integer[] arr = new Integer[9];
				for (int i = 0; i < arr.length; i++) {
					arr[i] = i + 1;
				}
				Collections.shuffle(Arrays.asList(arr));
				Gene gen2 = new Gene(arr);
				listGen.add(gen2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listGen;
	}

	// ghi các đoạn gene hợp lệ đã tạo vào file
	public void gen() {
		List<Gene> listGen = creatPopGen();
		String c = "";
		for (Gene gen2 : listGen) {
			Integer[] gen = gen2.gen;
			String gs = "";
			for (int i = 0; i < 9; i++) {
				gs += gen[i] + " ";
			}
			c += gs + "\n";
		}
		BufferedWriter output = null;
		try {
			File file = new File("image\\gen3.txt");
			output = new BufferedWriter(new FileWriter(file));
			output.write(c);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("write sucess");
	}

	public static void main(String[] args) {

		Gene gen2 = new Gene();
		gen2.gen();
	}
}
