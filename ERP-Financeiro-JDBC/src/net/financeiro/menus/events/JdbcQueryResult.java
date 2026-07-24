package net.financeiro.menus.events;

import com.williamcallahan.tui4j.compat.bubbletea.Message;

public record JdbcQueryResult(Object data, String error) implements Message {
    public JdbcQueryResult(Object data) {
        this(data, null);
    }

    public JdbcQueryResult(String error) {
        this(null, error);
    }
}

