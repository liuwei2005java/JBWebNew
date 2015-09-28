import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Date;

public class Test {
	public static void main(String[] arguments) throws Exception{
//        InetAddress ia = InetAddress.getLocalHost();//获取本地IP对象
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
    
    //获取MAC地址的方法
    private static String getMACAddress(InetAddress ia)throws Exception{
        //获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        
        //下面代码是把mac地址拼装成String
        StringBuffer sb = new StringBuffer();
        
        for(int i=0;i<mac.length;i++){
            if(i!=0){
                sb.append("-");
            }
            //mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length()==1?0+s:s);
        }
        
        //把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
   }


}
