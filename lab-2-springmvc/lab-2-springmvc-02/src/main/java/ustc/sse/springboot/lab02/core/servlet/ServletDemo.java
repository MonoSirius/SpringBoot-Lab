package ustc.sse.springboot.lab02.core.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author MonoSirius
 * @date 2023/6/1
 */
@WebServlet(urlPatterns = "/test/02")
public class ServletDemo extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("[doGet][uri: {}]", req.getRequestURI());
    }
}
