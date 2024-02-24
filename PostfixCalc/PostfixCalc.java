package toy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class PostfixCalc {
	public static void main(String[] args) throws IOException {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String formula = br.readLine();
			
			PostfixCalc calc = new PostfixCalc();
			String postFix = calc.convertPostfix(formula);
			Double result = calc.calcPostfix(postFix);
			
			System.out.println("|=======================================================|");
			System.out.printf("  계  산  결  과 > %.2f%n", result);
			System.out.println("  후 위 표 현 식 > " + postFix);
			System.out.println("|=======================================================|");
			
		} catch (Exception e) {
			System.err.println("계산식을 확인해주세요.");
		}
	}
	
	// 후위표현식으로 변환
	private String convertPostfix(String formula) {
		Stack<Character> stack = new Stack<>();
		
		StringBuilder num = new StringBuilder();
		StringBuilder postfix = new StringBuilder();

		char[] chars = formula.replace(" ", "").toCharArray();
        
		for(int i = 0 ; i < chars.length ; i++) {
            char c = chars[i];
            int priority = priority(c);
            
            if(priority > 0) {
                if(!num.isEmpty()) {
                    postfix.append(num).append(" ");
                    num.setLength(0);
                }
                
                switch (c) {
                    case '*':
        			case '/':
        			case '+':
        			case '-':
                        while(!stack.isEmpty() && stack.peek() != '(' && priority(stack.peek()) >= priority) {
                            postfix.append(stack.pop()).append(" ");
                        }
                        stack.push(c);
                        break;
                    case '(':
                        stack.push(c);
                        break;
                    case ')':
                        while(!stack.isEmpty() && stack.peek() != '(') {
                        	postfix.append(stack.pop()).append(" ");
                        }
                        stack.pop(); // '(' 제거
                        break;
                }
            } else {
                num.append(c);
			}
		}
		// 마지막 숫자
		if(!num.isEmpty()) {
            postfix.append(num).append(" ");
        }
		
		// stack에 있는 연산자
        while(!stack.isEmpty()) {
        	postfix.append(stack.pop()).append(" ");
        }
        
        return postfix.toString().trim();
	}
	
	// 연산자 우선순위 정하기
	private int priority(char c) {
		switch (c) {
            case '(':
            case ')': return 3;
            case '*':
			case '/': return 2;
			case '+':
			case '-': return 1;
			default : return 0;
        }
	}
	
	// 후위표현식 계산
	private double calcPostfix(String postFix) {
		Stack<Double> stack = new Stack<>();
		String[] formulas = postFix.split(" ");
		
		for(int i = 0 ; i < formulas.length ; i++) {
			String formula = formulas[i];
			
			if(checkedNumber(formula)) {
				stack.push(Double.parseDouble(formula));
			} else {
				double o2 = Double.parseDouble(stack.pop().toString());
				double o1 = Double.parseDouble(stack.pop().toString());
	            
				switch (formula) {
				case "+":
					stack.push(o1 + o2);
					break;
				case "-":
					stack.push(o1 - o2);
					break;
				case "*":
					stack.push(o1 * o2);
					break;
				case "/":
					stack.push(o1 / o2);
					break;
				}
			}
		}
		
		return stack.pop();
	}
	
	// 숫자인지 연산자인지 체크
	private boolean checkedNumber(String num) {
		try {
			Double.parseDouble(num);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
