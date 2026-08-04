package net.financeiro.menus;

import com.williamcallahan.tui4j.compat.bubbles.viewport.Viewport;
import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.WindowSizeMessage;
import com.williamcallahan.tui4j.compat.lipgloss.Style;
import com.williamcallahan.tui4j.compat.lipgloss.color.AdaptiveColor;
import net.financeiro.menus.events.JdbcQueryResult;
import net.financeiro.model.ViewNotaFiscal;
import net.financeiro.service.ViewNotaFiscalService;

import java.util.ArrayList;
import java.util.List;

public class ViewNotaFiscalMenu implements TableMenu {
    //Paleta e styles
    private final AdaptiveColor RED = new AdaptiveColor("#ff1f31", "#ff1f31");

    private final Style WARN_STYLE = Style.newStyle()
            .foreground(RED);

    private final ViewNotaFiscalService service = new ViewNotaFiscalService();
    private final Viewport viewport;

    //posicao na lista de entidades
    private int cursor = 0;

    //dados retornados por consultas
    private String errorMessage = "";
    List<ViewNotaFiscal> itens = new ArrayList<>();

    public ViewNotaFiscalMenu() {
        this.viewport = new Viewport();
    }

    @Override
    public String getTitle() {
        return "Nota Fiscal";
    }

    @Override
    public Command init() {
        cursor = 0;
        errorMessage = "";

        return consultarBanco();
    }

    private Command consultarBanco() {
        return () -> {
            try {
                List<ViewNotaFiscal> result = service.listarInfo();
                return new JdbcQueryResult(result, null);
            } catch(Exception e) {
                return new JdbcQueryResult(null, e.getMessage());
            }
        };
    }

    @Override
    public Command handleInput(Message msg) {
        //atualiza o tamanho do viewport
        if(msg instanceof WindowSizeMessage size) {
            int larguraUtil = Math.max(10, size.width() - 4);
            int alturaUtil = Math.max(5, size.height() - 2);

            viewport.setHeight(alturaUtil);
            viewport.setWidth(larguraUtil);
            atualizarViewport();
            return null;
        }

        if(msg instanceof KeyPressMessage key) {
            String k = key.key();

            switch(k) {
                case "s", "down":
                    //avança para a próxima entidade na lista
                    if(cursor < itens.size() - 1) {
                        cursor++;
                        atualizarViewport();

                        int linhaCursor = cursor + 2;
                        if(linhaCursor >= viewport.getYOffset() + viewport.getHeight()) {
                            viewport.scrollDown(1);
                        }
                    }
                    return null;
                case "w", "up":
                    //volta para a entidade anterior
                    if(cursor > 0) {
                        cursor--;
                        atualizarViewport();

                        int linhaCursor = cursor;
                        if(linhaCursor < viewport.getYOffset()) {
                            viewport.scrollUp(1);
                        }
                    }
                    return null;
                case "ctrl+r":
                    return consultarBanco();
            }
        }

        //não retornamos nada para o orquestrador processar
        return null;
    }

    @Override
    public void onDataReceived(Object data, String error) {
        if(data == null) {
            errorMessage = error;
        } else {
            if(data instanceof List<?>) {
                itens = (List<ViewNotaFiscal>) data;
                cursor = 0;
                viewport.gotoTop();
                atualizarViewport();
            }
        }
    }

    private void atualizarViewport() {
        StringBuilder buffer = new StringBuilder();

        for(int i = 0; i < itens.size(); i++) {
            ViewNotaFiscal item = itens.get(i);
            String prefix = (i == cursor) ? " > " : "   ";

            String linha = String.format(
                    "%s%-5d %-5d %-15s %-3d %-25s %-15s %-25s %-18s %-2d %-20s\n\n",
                    prefix, item.getNumero_nota_fiscal(), item.getId_fluxo_caixa(), item.getData_emissao(), item.getid_parceiro(), item.getnome_parceiro(), item.getDocumento_parceiro(),
                    item.getEmail_parceiro(), item.getForma_pagamento(), item.getParcelas(), item.getBanco_recebimento()
            );

            buffer.append(linha);
        }
        viewport.setContent(buffer.toString());
    }

    @Override
    public String render(boolean isLoading, String spinnerView) {
        if(isLoading) return "\n   Aguardando servidor " + spinnerView;

        if(!errorMessage.isEmpty()) {
            return WARN_STYLE.render("\n   ⚠️" + errorMessage);
        }

        return viewport.view() + "\n\n";
    }
}
