package net.financeiro.menus;

import com.williamcallahan.tui4j.compat.bubbles.viewport.Viewport;
import com.williamcallahan.tui4j.compat.bubbletea.*;
import com.williamcallahan.tui4j.compat.lipgloss.Style;
import com.williamcallahan.tui4j.compat.lipgloss.border.StandardBorder;
import com.williamcallahan.tui4j.compat.lipgloss.color.AdaptiveColor;
import net.financeiro.menus.events.JdbcQueryResult;
import net.financeiro.model.Categoria_Item;
import net.financeiro.service.Categoria_ItemService;

import java.util.ArrayList;
import java.util.List;

public class CategoriaItemMenu implements TableMenu {
    private final AdaptiveColor RED = new AdaptiveColor("#ff1f31", "#ff1f31");
    private final AdaptiveColor GRAY = new AdaptiveColor("#878787", "#878787");

    private final Style ERROR_STYLE = Style.newStyle()
            .foreground(RED);

    private final Style PANEL_BORDER = Style.newStyle()
            .border(StandardBorder.RoundedBorder)
            .borderForeground(GRAY);

    private Categoria_ItemService service = new Categoria_ItemService();
    private enum Estado { EDITANDO, VISUALIZANDO };
    private Viewport viewport;

    private int cursor = 0;
    private Estado estado;
    private String inputBuffer = "";

    private String errorMessage = "";
    List<Categoria_Item> itens = new ArrayList<>();

    public CategoriaItemMenu() {
        this.viewport = new Viewport();
    }

    @Override
    public String getTitle() {
        return "Categorias";
    }

    @Override
    public Command init() {
        estado = Estado.VISUALIZANDO;
        return consultarBanco();
    }

    private Command consultarBanco() {
        return () -> {
            try {
                List<Categoria_Item> result = service.listarInfo();
                return new JdbcQueryResult(result, null);
            } catch(Exception e) {
                return new JdbcQueryResult(null, e.getMessage());
            }
        };
    }

    @Override
    public Command handleInput(Message msg) {
        if(msg instanceof WindowSizeMessage size) {
            int larguraUtil = Math.max(10, size.width() - 4);
            int alturaUtil = Math.max(5, size.height() - 6);

            viewport.setHeight(alturaUtil);
            viewport.setWidth(larguraUtil);
            atualizarViewport();
            return null;
        }

        if(msg instanceof KeyPressMessage key) {
            String k = key.key();

            if(estado == Estado.VISUALIZANDO) {
                switch(k) {
                    case "s", "down":
                        if(cursor < itens.size() - 1) {
                            cursor++;
                            atualizarViewport();

                            int linhaCursor = cursor + 2;
                            if(linhaCursor >= viewport.getYOffset() + viewport.getHeight()) {
                                viewport.scrollDown(1);
                            }
                        }
                        break;
                    case "w", "up":
                        if(cursor > 0) {
                            cursor--;
                            atualizarViewport();

                            int linhaCursor = cursor + 2;
                            if(linhaCursor < viewport.getYOffset()) {
                                viewport.scrollUp(1);
                            }
                        }
                        break;
                    case "enter":
                        break;
                    case "r":
                        return consultarBanco();
                }
            }
        }
        return null;
    }

    @Override
    public void onDataReceived(Object data, String error) {
        if(data == null) {
            errorMessage = error;
        } else {
            itens = (List<Categoria_Item>) data;
            atualizarViewport();
            cursor = 0;
            viewport.gotoTop();
        }
    }

    private void atualizarViewport() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("─".repeat(50)).append("\n");

        for(int i = 0; i < itens.size(); i++) {
            Categoria_Item item = itens.get(i);
            String prefix = (i == cursor) ? " > " : "   ";

            String linha = String.format("%s%-5d %-25s\n", prefix, item.getId_categoria_item(), item.getNome());

            buffer.append(linha);
        }

        viewport.setContent(buffer.toString());
    }

    @Override
    public String render(boolean isLoading, String spinnerView) {
        if(isLoading) return "\n   Aguardando servidor " + spinnerView;

        if(!errorMessage.isEmpty()) {
            return ERROR_STYLE.render("\n   ⚠️ " + errorMessage);
        }

//        StringBuilder buffer = new StringBuilder();
//
//        buffer.append("\n");
//        for(int i = 0; i < itens.size(); i++) {
//            String prefix = cursor == i ? " > " : "   ";
//            buffer.append(prefix).append(itens.get(i).getNome()).append("\n");
//        }
//
//        return buffer.toString();
        return PANEL_BORDER.render(viewport.view() + "\n\n");
    }
}
