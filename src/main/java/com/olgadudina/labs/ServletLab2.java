package main.java.com.olgadudina.labs;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class ServletLab2 extends HttpServlet {
    private static final String QUERY_TYPE_NEAREST_TO_TEN = "nearest_to_ten";
    private static final String QUERY_TYPE_SQUARE_EQ = "square_eq";
    private static final String QUERY_TYPE_MATRIX_8_5 = "matrix_8_5";
    private static final String QUERY_TYPE_MATRIX_5_8 = "matrix_5_8";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");

        String queryType = req.getParameter("queryType");
        switch (queryType) {
            case QUERY_TYPE_NEAREST_TO_TEN:
                handleNearestToTen(req, resp);
                break;
            case QUERY_TYPE_SQUARE_EQ:
                handleSquareEq(req, resp);
                break;
            case QUERY_TYPE_MATRIX_8_5:
                handleMatrix8to5(req, resp);
                break;
            case QUERY_TYPE_MATRIX_5_8:
                handleMatrix5to8(req, resp);
                break;
        }
    }

    private void handleNearestToTen(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final double PIVOT = 10d;

        String var1 = req.getParameter("var1");
        var1 = var1.replace(',', '.');
        double num1 = Double.parseDouble(var1);

        String var2 = req.getParameter("var2");
        var2 = var2.replace(',', '.');
        double num2 = Double.parseDouble(var2);

        double res = Math.abs(num1 - PIVOT) <= Math.abs(num2 - PIVOT) ? num1 : num2;
        resp.getWriter().println(res);
    }

    private void handleSquareEq(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String var1 = req.getParameter("var1");
        long a = Long.parseLong(var1);

        String var2 = req.getParameter("var2");
        long b = Long.parseLong(var2);

        String var3 = req.getParameter("var3");
        long c = Long.parseLong(var3);

        String res;

        long discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            res = "Нет корней";
        } else {
            float r1 = (float)(-b + Math.sqrt(discriminant)) / (2 * a);
            float r2 = (float)(-b - Math.sqrt(discriminant)) / (2 * a);
            res = "Корни: " + String.format("%.4f %.4f", r1, r2);
        }
        resp.getWriter().println(res);
    }

    private void handleMatrix8to5(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int[][] nums = new int[8][5];
        Random random = new Random();

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                nums[i][j] = 10 + random.nextInt(90);   // interval [10;99]
            }
        }

        PrintWriter writer = resp.getWriter();
        printMatrix(writer, nums);
    }

    private void handleMatrix5to8(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int[][] nums = new int[5][8];
        Random random = new Random();
        int max = -100;

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                int val = -99 + random.nextInt(199);    // interval [-99;99]
                nums[i][j] = val;
                if (val > max) {
                    max = val;
                }
            }
        }

        PrintWriter writer = resp.getWriter();
        printMatrix(writer, nums);

        writer.println();
        writer.print("Наибольшее: " + max);
    }

    private void printMatrix(PrintWriter writer, int[][] nums) {
        StringBuilder sb = new StringBuilder();
        for (int[] a : nums) {
            sb.append("<p>");
            for (int i : a) {
                sb.append(String.format("%3d", i)).append(" ");
            }
            sb.append("</p>");
        }
        writer.print(sb.toString());
    }
}
