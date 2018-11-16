package http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HttpTest {
	public static void main(String[] args) {
		String preorderApi="http://api.daduci.com/";
		String payway="weixin";
		String paytype="weixin.app";
		String appkey="13e541320037895f572da58c6be47a73";
		String appId="20000";
		String amount="1";
		String itemname="shangpmingc";
		String ordersn="20000_DDH1529566992330";
		String orderdesc="miaoshu";
		String notifyurl= "http://api.shanLide.cn/appdemo/paycb.php";
		String[] param=new String[6];
		String[] param2=new String[11];
		param[0]="appid="+appId;
		param[1]="amount="+amount;
		param[2]="itemname="+itemname;
		param[3]="ordersn="+ordersn;
		param[4]="orderdesc="+orderdesc;
		param[5]="notifyurl="+notifyurl;
		
        Arrays.sort(param);
        
        String str = "";
        boolean flag= false;
        for(int i=0;i<param.length;i++){
        	System.out.println(param[i]+"  ");
        	
        	if(!"".equals(param[i])){
        		if(!flag){
        			str=param[i].split("=")[1];
        			flag=true;
        		}else{
        			str+="|"+param[i].split("=")[1];
        		}
        	}
            
        }
        
        String signstr =str+"|"+appkey;
        String sign= getMD5(signstr);
        System.out.println("ƴ��ǩ���ַ�����"+signstr);
        System.out.println("����õ���MD5ֵ"+sign);
        
        param2[0]=param[0];
        param2[1]=param[1];
        param2[2]=param[2];
        param2[3]=param[3];
        param2[4]=param[4];
        param2[5]=param[5];
        param2[6] = "sign=" +sign;
        param2[7] = "returnurl=https://www.so.com";
        param2[8] = "payway=" +payway;
        param2[9] = "ext=" ;
        param2[10] = "paytype=" +paytype;
        
        String urlParam=preorderApi;
        
        flag= false;
        for (int i = 0; i < param2.length; i++) {
        	if(!"".equals(param2[i])){
        		if(!flag){
        			urlParam+="?"+param2[i];
        			flag=true;
        		}else{
        			urlParam+="&"+param2[i];
        		}
        	}
		}
        String res = sendGet(urlParam);
        
        System.out.println(urlParam);
        System.out.println(res);
    }
	
	public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url ;
            URL realUrl = new URL(urlNameString);
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����ʵ�ʵ�����
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر�������
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }	
	
	public static String getMD5(String str) { 
		try {  
        // ����һ��MD5���ܼ���ժҪ  
        MessageDigest md = MessageDigest.getInstance("MD5");  
        // ����md5����  
        md.update(str.getBytes());  
        // digest()���ȷ������md5 hashֵ������ֵΪ8Ϊ�ַ�������Ϊmd5 hashֵ��16λ��hexֵ��ʵ���Ͼ���8λ���ַ�  
        // BigInteger������8λ���ַ���ת����16λhexֵ�����ַ�������ʾ���õ��ַ�����ʽ��hashֵ  
        return new BigInteger(1, md.digest()).toString(16);  
    } catch (Exception e) {  
       e.printStackTrace();  
       return null;  
    }  }  
}
