import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Date;

public class Test {
	public static void main(String[] arguments) throws Exception{
//        InetAddress ia = InetAddress.getLocalHost();//��ȡ����IP����
//        System.out.println("MAC ......... "+getMACAddress(ia));
//		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
//		System.out.println(sFormat.format(new Date()));
//		Map map = new HashMap();
//		try {
//			System.out.println(map.get("11"));
//			if(map.get("11")==null){
//				System.out.println(11);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		String str = "25000.00";
//		float f = Float.parseFloat(str);
//		int i = (int)f;
//		System.out.println(i);
		System.out.println(new Date().getTime());
		Date d = new Date();
		System.out.println(d);
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);
		double seconds = 6;
		System.out.println(seconds);
	    ca.add(Calendar.MINUTE, (int)seconds);
	    System.out.println(ca.getTime().getTime());

    }
    
    //��ȡMAC��ַ�ķ���
    private static String getMACAddress(InetAddress ia)throws Exception{
        //�������ӿڶ��󣨼������������õ�mac��ַ��mac��ַ������һ��byte�����С�
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        
        //��������ǰ�mac��ַƴװ��String
        StringBuffer sb = new StringBuffer();
        
        for(int i=0;i<mac.length;i++){
            if(i!=0){
                sb.append("-");
            }
            //mac[i] & 0xFF ��Ϊ�˰�byteת��Ϊ������
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length()==1?0+s:s);
        }
        
        //���ַ�������Сд��ĸ��Ϊ��д��Ϊ�����mac��ַ������
        return sb.toString().toUpperCase();
   }


}
