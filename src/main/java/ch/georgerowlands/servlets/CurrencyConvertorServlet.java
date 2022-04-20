package ch.georgerowlands.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Stream;

@WebServlet("/currency/convert")
public class CurrencyConvertorServlet extends HttpServlet {
    private static final long serialVersionUID = 1;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String amount = request.getParameter("amt");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

        String res = getConversion(amount, from, to);

        out.println("<html>");
        out.println("<head><title>Currency Converter</title></head>");
        out.println("<body bgcolor=\"white\">");
        out.println("<h1>Currency Converter</h1>");
        out.printf("%s %s = %s %s\n", amount, from, res, to);
        out.printf("</br><a href=\"%s/currency\">Curency Converter</a>\n", request.getContextPath());
        out.println("</body>");
        out.println("</html>");
    }

    static String getConversion(String amount, String from, String to) {
        final String TOKEN = "green";
        final String query = "https://www.calculator.net/currency-calculator.html?eamount=" + amount + "&efrom=" + from + "&eto=" + to + "&x=5";
        System.out.println(query);
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(query)).GET().build();
            HttpResponse<Stream<String>> response = client.send(request, HttpResponse.BodyHandlers.ofLines());
            return response.body()
                    .filter(line -> line.contains(TOKEN))
                    .findFirst()
                    .map(line -> {
                        int pos = line.indexOf(TOKEN, 0);
                        pos = line.indexOf("<b>", pos);
                        String res = line.substring(pos + 3);
                        return res.substring(0, res.indexOf("<"));
                    }).orElse("no result found");
        } catch (Exception e) {
            String msg = e.getMessage();
            return "".equals(msg) ? e.toString() : msg;
        }
    }
}
