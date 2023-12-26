package toy;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileFinder {
	static Scanner sc = new Scanner(System.in);
	static long count = 0;
	
	public static void main(String[] args) throws IOException {
		FileFinder fileFinder = new FileFinder();
		fileFinder.findFile();
	}
	
	private void findFile() {
		String drive = nextLine("탐색할 드라이브를 입력해주세요. > ");
		String keyword = nextLine("검색할 파일명을 입력해주세요. > ");
		String condition = nextLine("정확히 일치하는 파일만 찾겠습니까? [Y/N] > ");
		boolean flag = "Y".equals(condition.toUpperCase());
		
		if(!checkedDriveReg(drive)) {
			System.err.println("드라이브를 확인해주세요. > " + drive);
		} else if(keyword.isEmpty()) {
			System.err.println("검색할 파일명을 입력해주세요.");
		} else {
			scanDir(drive.toUpperCase() + ":\\", keyword, flag);
			System.out.printf("총 %d개의 파일을 찾았습니다.", count);
		}
	}
	
	private void scanDir(String path, String keyword, boolean condition) {
		File file = new File(path);
	
		if(file.isDirectory() && !file.getName().equals("System Volume Information")) {
			File[] files = file.listFiles();
			
			for(File f : files) {
				if(condition) {
					if(f.getName().equals(keyword)) {
						System.out.println(f.getAbsolutePath());
						count++;
					}
				} else {
					if(f.getName().contains(keyword)) {
						System.out.println(f.getAbsolutePath());
						count++;
					}
				}
				
				if(f.isDirectory()) {
					scanDir(f.getAbsolutePath(), keyword, condition);
				}
			}
		}
	}
	
	private boolean checkedDriveReg(String driveName) {
		return driveName.matches("^[a-zA-Z]");
	}
	
	private String nextLine(String str) {
		System.out.print(str);
		return sc.nextLine();
	}
} 