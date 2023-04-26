package zehuay;
/**
 * Author: Bobby Yang
 * Email: zehuay@andrew.cmu.edu
 * Name: Servlet
 */

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.Document;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "helloServlet", value = { "/player", "/dashboard"})
public class Servlet extends HttpServlet {

    Model model = null;
    SaveToMongoDB save = new SaveToMongoDB();
    long fastest_api;
    long fast_android;
    long count;

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        model = new Model();
        fastest_api = Long.MAX_VALUE;
        fast_android = Long.MAX_VALUE;
        count = 0;
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        String nextView = null;
        save.connectMongoDB();
        if (request.getServletPath().contains("/player")) {
            String input = request.getQueryString();
            String responseMessage = model.playerInfo(input);
            response.setContentType("text/html");
            long endTime = System.currentTimeMillis();
            // Hello
            PrintWriter out = response.getWriter();
            out.write(responseMessage);
            // store the log info in MongoDB
            Document document = new Document();
            document.append("Start Time", model.startTime);
            document.append("End Time", model.endTime);
            document.append("Total Api Time", model.totalTime);
            document.append("Total Android Time", endTime - startTime);
            document.append("Search Input", model.searchInput);
            document.append("Result", model.responseMessage);
            fastest_api = Math.min(fastest_api, model.totalTime);
            fast_android = Math.min(fast_android, (endTime - startTime));
//            document.append("fastest_api", fastest_api);
//            document.append("fast_android", fast_android);
            save.saveDoc(document);
        } else {
            System.out.println(request.getServletPath());
            nextView = "dashboard.jsp";
            List<Document> documents = save.saveLog();
            if (documents != null) {
                request.getSession().setAttribute("logs", documents);
            } else {
                request.getSession().setAttribute("logs", new ArrayList<Document>());
            }

            request.getSession().setAttribute("fast_api", fastest_api);
            request.getSession().setAttribute("fast_android", fast_android);
            request.getSession().setAttribute("count", save.database.getCollection("Player").countDocuments());
            request.getSession().setAttribute("logs", documents);
            RequestDispatcher view = request.getRequestDispatcher(nextView);
            view.forward(request, response);
        }
    }
    public void destroy() {

    }
}
