package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.websocket;

import com.google.gson.Gson;
import org.jboss.logging.Logger;
import org.ufabc.next.enrollmenteventtransmitter.application.discipline.service.DisciplineService;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/disciplines/discipline/{code}")
@ApplicationScoped
public class DisciplineWebSocket {
    private static final Logger LOGGER = Logger.getLogger(DisciplineWebSocket.class);

    private final Map<String, List<Session>> sessions;
    private final Gson gson;
    private final DisciplineService disciplineService;

    public DisciplineWebSocket(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
        sessions = new ConcurrentHashMap<>();
        disciplineService.findAll()
                .forEach(discipline -> sessions.put(discipline.code(), new ArrayList<>()));
        gson = new Gson();
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("code") String code) {
        if (code == null) {
            return;
        }

        var requesterSession = sessions.get(code);

        if (requesterSession == null) {
            return;
        }

        requesterSession.add(session);
        sessions.put(code, requesterSession);

        var discipline = disciplineService.findByCode(code);
        session.getAsyncRemote().sendObject(gson.toJson(DisciplineResponse.from(discipline.get())),
                sendResult -> {
                    if (sendResult.getException() != null) {
                        LOGGER.error(sendResult.getException().getMessage());
                    }
                });
    }

    @OnMessage
    public void onMessage(String ignore, @PathParam("code") String code) {
        if (code == null) {
            return;
        }

        var discipline = disciplineService.findByCode(code);
        discipline.ifPresent(iDiscipline -> broadcast(gson.toJson(DisciplineResponse.from(iDiscipline)), code));
    }

    private void broadcast(String message, String code) {
        var requesterSession = sessions.get(code);

        requesterSession.forEach(session -> session
                .getAsyncRemote()
                .sendObject(message, sendResult -> {
                    if (sendResult.getException() != null) {
                        LOGGER.error(sendResult.getException().getMessage());
                    }
                }));
    }
}
