package net.financeiro.menus;

import com.williamcallahan.tui4j.compat.bubbles.textinput.TextInput;
import com.williamcallahan.tui4j.compat.bubbles.viewport.Viewport;
import com.williamcallahan.tui4j.compat.bubbletea.*;
import com.williamcallahan.tui4j.compat.lipgloss.Style;
import com.williamcallahan.tui4j.compat.lipgloss.color.AdaptiveColor;
import net.financeiro.menus.events.JdbcQueryResult;
import net.financeiro.model.Folha_Pagamento;
import net.financeiro.service.Folha_PagamentoService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Folha_PagamentoMenu implements TableMenu {
    //Paleta e styles
    private final AdaptiveColor RED = new AdaptiveColor("#ff1f31", "#ff1f31");

    private final Style WARN_STYLE = Style.newStyle()
            .foreground(RED);

    private final Folha_PagamentoService service = new Folha_PagamentoService();
    private enum Estado { VISUALIZANDO, EDITANDO, CRIANDO, DELETANDO }
    private final Viewport viewport;

    //estado do menu
    private Estado estado = Estado.VISUALIZANDO;

    //posicao na lista de entidades
    private int cursor = 0;

    //variaveis de input
    private int inputCursor = 0;
    private List<TextInput> inputs;

    //dados retornados por consultas
    private String errorMessage = "";
    List<Folha_Pagamento> itens = new ArrayList<>();

    public Folha_PagamentoMenu() {
        this.viewport = new Viewport();
    }

    @Override
    public String getTitle() {
        return "Folhas Pagamento";
    }

    @Override
    public Command init() {
        //reseta o menu toda vez que ele inicia
        estado = Estado.VISUALIZANDO;
        cursor = 0;
        errorMessage = "";

        return consultarBanco();
    }

    private Command consultarBanco() {
        return () -> {
            try {
                List<Folha_Pagamento> result = service.listarInfo();
                return new JdbcQueryResult(result, null);
            } catch (Exception e) {
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

        //executa ações das teclas pressionadas
        if(msg instanceof KeyPressMessage key) {
            String k = key.key();

            //ações caso o estado seja VISUALIZANDO
            if(estado == Estado.VISUALIZANDO) {
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
                        break;
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
                        break;
                    case "n":
                        //inicia a criação de uma nova entidade
                        //todo fazer verificação de permissão antes de continuar ação
                        estado = Estado.CRIANDO;
                        iniciarFormulario();
                        break;
                    case "f":
                        //inicia a edição da entidade selecionada
                        //todo fazer verificação de permissão antes de continuar ação
                        estado = Estado.EDITANDO;
                        iniciarFormulario();
                        break;
                    case "x":
                        //deleta a entidade selecionada;
                        //todo fazer verificação de permissão antes de continuar ação
                        estado = Estado.DELETANDO;
                        atualizarViewport();
                        break;
                    case "ctrl+r":
                        return consultarBanco();
                }
            }
            //Ações caso o estado seja EDITANDO ou CRIANDO
            else if(estado == Estado.EDITANDO || estado == Estado.CRIANDO) {
                switch(k) {
                    case "down", "tab":
                        //avança o cursor para o próximo input
                        inputs.get(inputCursor).blur();
                        inputCursor = (inputCursor + 1) % inputs.size();
                        inputs.get(inputCursor).focus();

                        atualizarViewport();
                        return null;
                    case "up", "shift+tab":
                        //volta o cursor para o input anterior
                        inputs.get(inputCursor).blur();
                        inputCursor = (inputCursor - 1 + inputs.size()) % inputs.size();
                        inputs.get(inputCursor).focus();

                        atualizarViewport();
                        return null;
                    case "ctrl+s":
                        //pega valor dos inputs e cria/atualiza o objeto e envia a service para realizar as alterações no banco
                        final int idSelecionado = cursor;
                        final Estado modoOperacao = estado;
                        final double desconto = Double.parseDouble(inputs.get(0).value());
                        final int horasTrabalhadas = Integer.parseInt(inputs.get(1).value());
                        final double valorHora = Double.parseDouble(inputs.get(2).value());
                        final long idFuncionario = Long.parseLong(inputs.get(3).value());

                        LocalDateTime now = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        final String data_entrada = now.format(formatter);

                        estado = Estado.VISUALIZANDO;

                        return () -> {
                            try {
                                Folha_Pagamento item = new Folha_Pagamento(idFuncionario, desconto, valorHora, horasTrabalhadas, data_entrada);
                                if(modoOperacao == Estado.CRIANDO) {
                                    service.inserir(item);
                                } else if(modoOperacao == Estado.EDITANDO) {
                                    final long idItem = itens.get(idSelecionado).getId_folha_pagamento();
                                    service.atualizar(item, idItem);
                                }

                                List<Folha_Pagamento> result = service.listarInfo();
                                return new JdbcQueryResult(result, null);
                            } catch(Exception e) {
                                return new JdbcQueryResult(null, e.getMessage());
                            }
                        };
                    case "ctrl+x":
                        //cancela a operação
                        estado = Estado.VISUALIZANDO;
                        atualizarViewport();
                        return null;
                }
            }
            else if(estado == Estado.DELETANDO) {
                switch(k) {
                    case "ctrl+s":
                        //realizar o delete
                        final int idSelecionado = cursor;
                        estado = Estado.VISUALIZANDO;

                        return () -> {
                            try {
                                //deleta a entidade
                                service.deletar(itens.get(idSelecionado).getId_folha_pagamento());

                                //atualiza a lista
                                List<Folha_Pagamento> result = service.listarInfo();
                                return new JdbcQueryResult(result, null);
                            } catch(Exception e) {
                                return new JdbcQueryResult(null, e.getMessage());
                            }
                        };
                    case "ctrl+x":
                        //cancela a operação
                        estado = Estado.VISUALIZANDO;
                        atualizarViewport();
                        return null;
                }
            }
        }

        if(estado == Estado.EDITANDO || estado == Estado.CRIANDO) {
            if(!inputs.isEmpty()) {
                UpdateResult<TextInput> res = inputs.get(inputCursor).update(msg);
                inputs.set(inputCursor, res.model());
                atualizarViewport();
                return res.command();
            }
        }

        //retorna null pois não temos nada para o Orquestrador processar
        return null;
    }

    @Override
    public void onDataReceived(Object data, String error) {
        if(data == null) {
            errorMessage = error;
        } else {
            if(data instanceof List<?>) {
                itens = (List<Folha_Pagamento>) data;
                cursor = 0;
                viewport.gotoTop();
                atualizarViewport();
            }
        }
    }

    private void atualizarViewport() {
        StringBuilder buffer = new StringBuilder();

        //atualiza o viewport para o estado atual
        switch(estado) {
            case Estado.DELETANDO:
                buffer.append(WARN_STYLE.render("   Deseja continuar e deletar?")).append("\n > ").append(itens.get(cursor).getId_folha_pagamento());
                break;
            case Estado.CRIANDO, Estado.EDITANDO:
                for(TextInput input : inputs) {
                    buffer.append("\n ").append(input.view()).append("\n");
                }
                break;
            case Estado.VISUALIZANDO:
                for(int i = 0; i < itens.size(); i++) {
                    Folha_Pagamento item = itens.get(i);
                    String prefix = (i == cursor) ? " > " : "   ";

                    String linha = String.format(
                            "%s%-5d %-15s %-6f %-5f %-4d %-15s",
                            prefix,
                            item.getId_folha_pagamento(),
                            item.getNome_funcionario(),
                            item.getDescontos(),
                            item.getValor_hora(),
                            item.getHoras_trabalhadas(),
                            item.getData_entrada()
                    );

                    buffer.append(linha);
                }
                break;
        }

        viewport.setContent(buffer.toString());
    }

    private void iniciarCampos() {
        TextInput descontosInput = new TextInput();
        descontosInput.setPlaceholder("Ex: 150.00");

        TextInput horasTrabalhadasInput = new TextInput();
        horasTrabalhadasInput.setPlaceholder("Ex: 30");

        TextInput valorHoraInput = new TextInput();
        valorHoraInput.setPlaceholder("Ex: 27");

        inputs = new ArrayList<>();
        inputs.add(descontosInput);
        inputs.add(horasTrabalhadasInput);
        inputs.add(valorHoraInput);
    }

    private void iniciarFormulario() {
        //reseta a posição do cursor
        inputCursor = 0;

        if(estado == Estado.EDITANDO) {
            //atualiza os campos para corresponderem aos valores da entidade
            Folha_Pagamento item = itens.get(cursor);
            inputs.get(0).setValue(String.valueOf(item.getDescontos()));
            inputs.get(1).setValue(String.valueOf(item.getHoras_trabalhadas()));
            inputs.get(2).setValue(String.valueOf(item.getValor_hora()));
        } else if(estado == Estado.CRIANDO) {
            //limpa os campos
            for(TextInput input : inputs) {
                input.setValue("");
            }
        }

        //garante que o sistema esta com foco no primeiro input
        for(int i = 0; i < inputs.size(); i++) {
            if(i == inputCursor) {
                inputs.get(i).focus();
            } else {
                inputs.get(i).blur();
            }
        }

        //atualiza o viewport
        atualizarViewport();
    }

    @Override
    public String render(boolean isLoading, String spinnerView) {
        if(isLoading) return "\n   Aguardando servidor " + spinnerView;

        if(!errorMessage.isEmpty()) {
            return WARN_STYLE.render("\n   ⚠️ " + errorMessage);
        }

        return viewport.view() + "\n\n";
    }
}
