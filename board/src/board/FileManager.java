package board;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	private File file;
	private String fileName;

	private FileWriter fw;
	private FileReader fr;
	private BufferedReader br;

	public FileManager() {
		fileName = "board.txt";
		file = new File(fileName);
	}

	public void save(String info) {
		try {
			fw = new FileWriter(file);
			fw.write(info);
			fw.close();

			System.out.println("파일저장 성공");
		} catch (IOException e) {
			System.err.println("파일저장 실패");
		}
	}
	
	public String load() {
		String info = "";
		if (file.exists()) {
			try {
				fr = new FileReader(file);
				br = new BufferedReader(fr);

				while (br.ready()) {
					info += br.readLine();
					info += "\n";
				}

				fr.close();
				br.close();

				System.out.println("파일로드 성공");
			} catch (IOException e) {
				System.err.println("파일로드 실패");
			}
		}

		return info;
	}
}
