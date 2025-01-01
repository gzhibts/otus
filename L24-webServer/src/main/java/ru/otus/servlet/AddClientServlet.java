package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"java:S1989"})
public class AddClientServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;
    private static final String ADD_NEW_CLIENT_TEMPLATE = "add.html";
    private static final String PARAM_CLIENT_NAME = "name";
    private static final String PARAM_CLIENT_ADDRESS = "address";
    private static final String PARAM_CLIENT_PHONE = "phone";

    private final transient DBServiceClient dbServiceClient;
    private final transient Gson gson;
    private final transient TemplateProcessor templateProcessor;

    public AddClientServlet(DBServiceClient dbServiceClient, TemplateProcessor templateProcessor, Gson gson) {
        this.dbServiceClient = dbServiceClient;
        this.templateProcessor = templateProcessor;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.getWriter().write(templateProcessor.getPage(ADD_NEW_CLIENT_TEMPLATE, Map.of()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter(PARAM_CLIENT_NAME);
        String address = request.getParameter(PARAM_CLIENT_ADDRESS);
        String phone = request.getParameter(PARAM_CLIENT_PHONE);

        Client client = new Client();
        client.setName(name);
        client.setAddress(new Address(address));
        client.setPhones(List.of(new Phone(phone)));

        dbServiceClient.saveClient(client);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.sendRedirect("/clients");

    }

}
