package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;

@ServerEndpoint("/discipline/{code}")
@ApplicationScoped
public class DisciplineWebSocket {

    private final Map<String, List<Session>> rooms = new ConcurrentHashMap<>();

    public void init(IDiscipline... disciplines){
        for(IDiscipline discipline : disciplines){
            rooms.put(discipline.code(), new ArrayList<>());
        }
    }

    @OnOpen
    private void onOpen(Session session, @PathParam("code") String code) {
        var sessions = rooms.get(code);
        if(sessions != null){
            sessions.add(session);
            rooms.put(code, sessions);
        }
    }

    @OnMessage
    public void onMessage(String discipline, @PathParam("code") String code) {
        var sessions = rooms.get(code);
        if(sessions != null){
            sessions.forEach(session -> {
                session.getAsyncRemote().sendObject(discipline, result ->  {
                    if (result.getException() != null) {
                        System.out.println("Unable to send message: " + result.getException());
                    }
                });
            });
        }
    }
}
