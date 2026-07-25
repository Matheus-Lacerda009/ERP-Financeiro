package net.financeiro.menus;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.KeyPressMessage;

public interface TableMenu {
    String getTitle();

    default Command init() {
        return null;
    }

    Command handleInput(KeyPressMessage key);
    void onDataReceived(Object data, String error);
    String render(boolean isLoading, String spinnerView);
}
