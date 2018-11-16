import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;


public class NotifyServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String appkey = "WEPQgf22dGk7376tF0VFzgs5TDCo11DH";

        String appid = request.getParameter("appid");
        String amount = request.getParameter("amount");
        String itemname = request.getParameter("itemname");
        String ordersn = request.getParameter("ordersn");
        String orderdesc = request.getParameter("orderdesc");
        String serialno = request.getParameter("serialno");

        String sign = request.getParameter("sign");

        String[] param = new String[6];
        param[0] = "appid=" + appid;
        param[1] = "amount=" + amount;
        param[2] = "itemname=" + itemname;
        param[3] = "ordersn=" + ordersn;
        param[4] = "orderdesc=" + orderdesc;
        param[5] = "serialno=" + serialno;

        Arrays.sort(param);

        String str = "";
        boolean flag = false;
        for (int i = 0; i < param.length; i++) {
            System.out.println(param[i] + "  ");

            if (!"".equals(param[i])) {
                if (!flag) {
                    str = param[i].split("=")[1];
                    flag = true;
                } else {
                    str += "|" + param[i].split("=")[1];
                }
            }

        }

        String signstr = str + "|" + appkey;
        if (getMD5(signstr).equals(sign)) {
            ServletOutputStream out = response.getOutputStream();
            out.write("success".getBytes());//String:
        } else {
            ServletOutputStream out = response.getOutputStream();
            out.write("invalid sign".getBytes());//String:
        }

    }

    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}