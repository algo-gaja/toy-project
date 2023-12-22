import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {
	static Stack<Character> stack = new Stack<>();
	static String num = "";
	static String postfix = "";
	static List<String> list = new ArrayList<>();
	
	public static void main(String[] args) {
		String str = "A*(B+C)";
		check(str);
		System.out.println(postfix);
		
		// String str2 = "235*+";
		// list.add("2");
		// list.add("3");
		// list.add("5");
		// list.add("*");
		// list.add("+");
		
		// for(String l : list) {
		// 	if(checkedNumber2(l)) {
		// 		stack.push(Double.parseDouble(l));
		// 	} else {
		// 		double o2 = Double.parseDouble(stack.pop().toString());
		// 		double o1 = Double.parseDouble(stack.pop().toString());
				
		// 		switch (l) {
		// 		case "+":
		// 			stack.push(o2 + o1);
		// 			break;
		// 		case "-":
		// 			stack.push(o2 - o1);
		// 			break;
		// 		case "*":
		// 			stack.push(o2 * o1);
		// 			break;
		// 		case "/":
		// 			stack.push(o2 / o1);
		// 			break;
		// 		}
		// 	}
		// }
		
		// System.out.println(stack.pop().toString());
	}
	
	public static boolean checkedNumber2(String num) {
		try {
			Double.parseDouble(num);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static void check(String str) {
        str = str.replace(" ", "");
        char[] chars = str.toCharArray();
		for(int i = 0 ; i < chars.length ; i++) {
            char c = chars[i];
            int idx = priority(c);
            
            if(idx > 0) {
                if(!"".equals(num)) {
                    postfix += num + " ";
                    num = "";
                }

                switch (c) {
                    case '*':
        			case '/':
        			case '+':
        			case '-':
                        while(!stack.isEmpty() && priority(stack.peek()) >= idx && stack.peek() != '(') {
                            postfix += stack.pop() + " ";
                        }
                        stack.push(c);
                        break;
                    case '(':
                        stack.push(c);
                        break;
                    case ')':
                        while(!stack.isEmpty() && stack.peek() != '(') {
                            postfix += stack.pop() + " ";
                        }
                        stack.pop();
                        break;
                }
            } else {
                num += c;
			}
		}
        postfix += num + " ";
        while(!stack.isEmpty()) {
            postfix += stack.pop() + " ";
        }
	}
	
	public static boolean checkedNumber(char c) {
		return '0' <= c && c <= '9';
	}

    // 연산자 우선순위 정하기
	public static int priority(char c) {
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
}
