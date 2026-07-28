package net.financeiro.menus;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.Message;

public interface TableMenu {
    String getTitle();

    default Command init() {
        return null;
    }
    default boolean requiresLoadingOnInit() { return true; }

    Command handleInput(Message msg);
    void onDataReceived(Object data, String error);
    String render(boolean isLoading, String spinnerView);
}
