package net.financeiro;

import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.ProgramOption;
import net.financeiro.menus.Orquestrador;

public class Main {
    public static void main(String[] args) {
        new Program(new Orquestrador(), ProgramOption.withAltScreen()).run();
    }
}