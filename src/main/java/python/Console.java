package python;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class Console extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
//        response.setCharacterEncoding("UTF-8");//设置将字符以"UTF-8"编码输出到客户端浏览器
        //通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
//        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("UTF-8");
        String question = request.getParameter("editor");

//        String text = "/javabigwork_war_exploded/answer.jsp?" + "question=" + c + "&answer=" + start(c);
//        System.out.println(text);

        response.sendRedirect("/javabigwork_war_exploded/answer.jsp?"
                + "question=" + java.net.URLEncoder.encode(question,"UTF-8")
                + "&answer=" + java.net.URLEncoder.encode(start(question), "UTF-8"));
    }

    private static String toChinese(String str) {
        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };
        String result = "";
        int n = str.length();
        for (int i = 0; i < n; i++) {
            int num = str.charAt(i) - '0';
            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
//            System.out.println("  "+result);
        }
//        System.out.println(result);
        return result;
    }
    public static String start(String text){
        String result = "";
        try {
            PythonCSV.covert4ML();
            String dataText = PythonCSV.predict(text);
            double data = Double.valueOf(dataText);

            String year = String.valueOf((int)data);
            String month = String.valueOf((int)((data - (int)data) * 12));
//            System.out.println(data);
//            System.out.println(year);
//            System.out.println(month);

            year = toChinese(year);
            month = toChinese(month);
//            System.out.println(toChinese(month));


            result = "这个人应该判" + year + "年" + month + "个月";
        }catch (Exception e){
            result = "请检查你的输入";
        }

        return result;
    }

    public static void main(String[] args) {
        String c = "小明在家非法制造冰毒，贩卖冰毒100g，贩卖大麻500克，持有冰毒200克，还制造枪支，并使用枪支对民警进行伤害，留容小强在家吸食大麻，罪大恶极";
        System.out.println(start(c));
    }



}