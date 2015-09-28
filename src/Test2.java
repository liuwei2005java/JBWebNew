import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test2 {
	public static void main(String[] args) {
		String[] arrs = new String[]{"ddd","aaa","ccc","eee","fff"};
		List<String> list = new ArrayList<String>(Arrays.asList(arrs));
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			System.out.println(s);
			list.remove(s);
		}
		
	}
}
