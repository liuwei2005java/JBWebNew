import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class TestJson {

	public static void main(String[] args) {
		JSONArray jsonObjectArrs = new JSONArray();
		JSONArray objArray = new JSONArray();
		JSONObject attributes = new JSONObject();
		JSONObject obj = new JSONObject();
		obj.put("text", "����������");
		obj.put("state", "open");
		JSONObject child = new JSONObject();
		child.put("id", "jb1");
		child.put("text", "Key��Ϣά��");
		attributes.put("url", "keyInfo_list.action");
		child.put("attributes", attributes);
		objArray.add(child);
		
		child = new JSONObject();
		child.put("id", "jb2");
		child.put("text", "��ѡ��Ʒ");
		attributes = new JSONObject();
		attributes.put("url", "oca_list.action");
		child.put("attributes", attributes);
		objArray.add(child);
		
		child = new JSONObject();
		child.put("id", "jb3");
		child.put("text", "ϵͳ����");
		attributes = new JSONObject();
		attributes.put("url", "systemInfo_list.action");
		child.put("attributes", attributes);
		objArray.add(child);
		
		child = new JSONObject();
		child.put("id", "jb4");
		child.put("text", "������־");
		attributes = new JSONObject();
		attributes.put("url", "jbLog_list.action");
		child.put("attributes", attributes);
		objArray.add(child);
		
		obj.put("children", objArray);
		
		jsonObjectArrs.add(obj);
		
		System.out.println(jsonObjectArrs.toString());
	}
}
