package main.java.com.olgadudina.labs;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletLab3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");

        int[] bounds;
        try {
            bounds = readParams(req);
        } catch (NumberFormatException ex) {
            resp.getWriter().print("Некорректный диапазон!");
            return;
        }

        boolean withNums = Boolean.parseBoolean(req.getParameter("withNums"));
        String table = getSymbolsTable(bounds[0], bounds[1], withNums);
        resp.getWriter().print(table);
    }

    private int[] readParams(HttpServletRequest req) {
        String var1 = req.getParameter("var1");
        int num1 = Integer.parseInt(var1);

        String var2 = req.getParameter("var2");
        int num2 = Integer.parseInt(var2);

        if (num1 > num2) {
            throw new NumberFormatException();
        }

        if (num1 < 0 || num1 > 65535) {
            throw new NumberFormatException();
        }

        if (num2 < 0 || num2 > 65535) {
            throw new NumberFormatException();
        }

        return new int[] {num1, num2};
    }

    private String getSymbolsTable(int from, int to, boolean withNums) {
        StringBuilder sb = new StringBuilder();
        if (withNums) {
            sb.append("<p>Наведите на символ, чтобы увидеть его числовое значение</p>");
        }
        sb.append("<table class=\"productsTable\">");

        int count = 0;
        boolean lineOpened = false;

        for (int i = from; i <= to; i++) {
            if (count == 0) {
                sb.append("<tr>");
                lineOpened = true;
            }

            if (withNums) {
                sb.append("<td title=\"" + i + " hex: " + Integer.toHexString(i) +"\">");
            } else {
                sb.append("<td>");
            }
            sb.append("&#" + i + ";");
            sb.append("</td>");

            count++;
            if (count == 10) {
                sb.append("</tr>");
                lineOpened = false;
                count = 0;
            }
        }

        if (lineOpened) {
            sb.append("</tr>");
        }

        sb.append("</table>");
        return sb.toString();
    }
}
