package test;

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
	
	public void findFile() {
		String drive = nextLine("탐색할 드라이브를 입력해주세요. > ");
		String keyword = nextLine("검색할 파일명을 입력해주세요. > ");
		String condition = nextLine("정확히 일치하는 파일만 찾겠습니까? [Y/N] > ");
		boolean flag = "Y".equals(condition.toUpperCase());
		
		if(!checkedDriveReg(drive)) {
			System.err.println("드라이브를 확인해주세요. > " + drive);
		} else {
			scanDir(drive + ":", keyword, flag);
		}
		
		System.out.printf("총 %d개의 파일을 찾았습니다.", count);
	}
	
	private void scanDir(String path, String keyword, boolean contain) {
			System.out.println("gd");
			File file = new File(path);
		
			if(file.isDirectory() && !file.getName().equals("System Volume Information")) {
				File[] files = file.listFiles();
			
			for(File f : files) {
				if(contain && f.getName().contains(keyword)) {
					System.out.println(f.getAbsolutePath());
				}
				if(f.isDirectory()) {
					scanDir(f.getAbsolutePath(), keyword, contain);
				}
			}
		}
	}
	
	private static boolean checkedDriveReg(String driveName) {
		return driveName.matches("^[a-zA-Z]");
	}
	
	private boolean checkedDrive(String driveName) {
		return driveName.length() == 1 && validateAlphabet(driveName.charAt(0));
	}
	
	private boolean validateAlphabet(char alphabet) {
		return ('A' <= alphabet && alphabet <= 'Z') || ('a' <= alphabet && alphabet <= 'z');
	}
	
	private static String nextLine(String str) {
		System.out.print(str);
		return sc.nextLine();
	}
} 
