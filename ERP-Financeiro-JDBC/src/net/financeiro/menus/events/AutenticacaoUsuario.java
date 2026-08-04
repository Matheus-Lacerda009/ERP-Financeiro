package net.financeiro.menus.events;

import com.williamcallahan.tui4j.compat.bubbletea.Message;

public record AutenticacaoUsuario(Boolean result, String error) implements Message {
    public AutenticacaoUsuario(Boolean result) {
        this(result, null);
    }

    public AutenticacaoUsuario(String error) {
        this(null, error);
    }
}
