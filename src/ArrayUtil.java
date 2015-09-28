import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ArrayUtil {
	public static void main(String[] args) {
		// ����union
		String[] arr1 = { "abc", "df", "abc", "ee" };
		String[] arr2 = { "abc", "cc", "df", "dd", "abc" };
		String[] result_union = union(arr1, arr2);
		System.out.println("�󲢼��Ľ�����£�");
		for (String str : result_union) {
			System.out.println(str);
		}
		System.out
				.println("---------------------�ɰ��ķָ���------------------------");

		// ����insect
		List result_insect = intersect(arr1, arr2);
		System.out.println("�󽻼��Ľ�����£�");
		for (Object str : result_insect) {
			System.out.println(str);
		}

		System.out
				.println("---------------------���ķָ���------------------------");
		// ����minus
		List<String> result_minus = minus(arr1, arr2);
		System.out.println("���Ľ�����£�");
		for (String str : result_minus) {
			System.out.println(str);
		}
	}

	// �������ַ�������Ĳ���������set��Ԫ��Ψһ��
	public static String[] union(Object[] arr1, Object[] arr2) {
		Set<String> set = new HashSet<String>();
		for (Object str : arr1) {
			set.add(str.toString());
		}
		for (Object str : arr2) {
			set.add(str.toString());
		}
		String[] result = {};
		return set.toArray(result);
	}

	// ����������Ľ���
	public static List intersect(Object[] arr1, Object[] arr2) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		LinkedList<String> list = new LinkedList<String>();
		for (Object str : arr1) {
			if (!map.containsKey(str)) {
				map.put(str.toString(), Boolean.FALSE);
			}
		}
		for (Object str : arr2) {
			if (map.containsKey(str)) {
				map.put(str.toString(), Boolean.TRUE);
			}
		}

		for (Entry<String, Boolean> e : map.entrySet()) {
			if (e.getValue().equals(Boolean.TRUE)) {
				list.add(e.getKey());
			}
		}

		return list;
	}

	// ����������Ĳ
	public static List<String> minus(String[] arr1, String[] arr2) {
		LinkedList<String> list = new LinkedList<String>();
		LinkedList<String> history = new LinkedList<String>();
		List<String> retList = new ArrayList<String>();
		String[] longerArr = arr1;
		String[] shorterArr = arr2;
		// �ҳ��ϳ������������϶̵�����
		if (arr1.length > arr2.length) {
			longerArr = arr2;
			shorterArr = arr1;
		}
		for (String str : longerArr) {
			if (!list.contains(str)) {
				list.add(str);
			}
		}
		for (String str : shorterArr) {
			if (list.contains(str)) {
				history.add(str);
				list.remove(str);
			} else {
				if (!history.contains(str)) {
					list.add(str);
				}
			}
		}
		
		for(String s : list){
			if(!flagArray(arr2, s)){
				retList.add(s);
			}
		}
		
		return retList;
	}

	/**
	 * �ж�Ԫ���Ƿ�����ڸ�������
	 * @param arr
	 * @param targetValue
	 * @return
	 */
	public static boolean flagArray(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}
}