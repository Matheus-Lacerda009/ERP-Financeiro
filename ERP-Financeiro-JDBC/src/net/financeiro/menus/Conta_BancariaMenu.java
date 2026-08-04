package net.financeiro.menus;

import com.williamcallahan.tui4j.compat.bubbles.textinput.TextInput;
import com.williamcallahan.tui4j.compat.bubbles.viewport.Viewport;
import com.williamcallahan.tui4j.compat.bubbletea.*;
import com.williamcallahan.tui4j.compat.lipgloss.Style;
import com.williamcallahan.tui4j.compat.lipgloss.color.AdaptiveColor;
import net.financeiro.connection.VerificarPermissao;
import net.financeiro.menus.events.JdbcQueryResult;
import net.financeiro.model.Conta_Bancaria;
import net.financeiro.service.Conta_BancariaService;

import java.util.ArrayList;
import java.util.List;

public class Conta_BancariaMenu implements TableMenu {
    //Paleta e styles
    private final AdaptiveColor RED = new AdaptiveColor("#ff1f31", "#ff1f31");

    private final Style WARN_STYLE = Style.newStyle()
            .foreground(RED);

    private Conta_BancariaService service = new Conta_BancariaService();
    private enum Estado { VISUALIZANDO, EDITANDO, CRIANDO, DELETANDO }
    private Viewport viewport;

    //estado do menu
    private Estado estado = Estado.VISUALIZANDO;

    //posicao na lista de entidades
    private int cursor = 0;

    //variaveis de input
    private int inputCursor = 0;
    private List<TextInput> inputs;

    //dados retornados por consultas
    private String errorMessage = "";
    List<Conta_Bancaria> itens = new ArrayList<>();

    public Conta_BancariaMenu() {
        this.viewport = new Viewport();
    }

    @Override
    public String getTitle() {
        return "Contas Bancarias";
    }

    @Override
    public Command init() {
        estado = Estado.VISUALIZANDO;
        cursor = 0;
        errorMessage = "";
        iniciarCampos();

        return consultarBanco();
    }

    private Command consultarBanco() {
        return () -> {
            try {
                List<Conta_Bancaria> result = service.listarInfo();
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
                    case "n":
                        //inicia a criação de uma nova entidade
                        if(VerificarPermissao.validar("W")) {
                            estado = Estado.CRIANDO;
                            iniciarFormulario();
                        }
                        return null;
                    case "f":
                        //inicia a edição da entidade selecionada
                        if(VerificarPermissao.validar("W")) {
                            estado = Estado.EDITANDO;
                            iniciarFormulario();
                        }
                        return null;
                    case "x":
                        //deleta a entidade selecionada
                        if(VerificarPermissao.validar("D")) {
                            estado = Estado.DELETANDO;
                            atualizarViewport();
                        }
                        return null;
                    case "ctrl+r":
                        return consultarBanco();
                }
            }
            else if(estado == Estado.CRIANDO || estado == Estado.EDITANDO) {
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
                        //pega valor dos inputs e cria/atualiza o objeto e envia a service para realizar alterações no banco
                        final int idSelecionado = cursor;
                        final Estado modoOperacao = estado;
                        final String nomeConta = inputs.get(0).value();
                        final int numeroConta = Integer.parseInt(inputs.get(1).value());

                        estado = Estado.VISUALIZANDO;

                        return () -> {
                            try {
                                Conta_Bancaria item = new Conta_Bancaria(nomeConta, numeroConta);
                                if (modoOperacao == Estado.CRIANDO) {
                                    service.inserir(item);
                                } else if (modoOperacao == Estado.EDITANDO) {
                                    final long idItem = itens.get(idSelecionado).getId_caixa();
                                    service.atualizar(item, idItem);
                                }

                                List<Conta_Bancaria> result = service.listarInfo();
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
                        //realiza o delete
                        final int idSelecionado = cursor;
                        estado = Estado.VISUALIZANDO;

                        return () -> {
                            try {
                                service.deletar(itens.get(idSelecionado).getId_caixa());

                                List<Conta_Bancaria> result = service.listarInfo();
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

                if(inputCursor == 1) {
                    String valorApenasNumerico = res.model().value().replaceAll("[^0-9]", "");
                    res.model().setValue(valorApenasNumerico);
                }

                inputs.set(inputCursor, res.model());
                atualizarViewport();
                return res.command();
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
                itens = (List<Conta_Bancaria>) data;
                cursor = 0;
                viewport.gotoTop();
                atualizarViewport();
            }
        }
    }

    private void atualizarViewport() {
        StringBuilder buffer = new StringBuilder();

        //atualiza o viewport de acordo com o estado atual do menu
        switch(estado) {
            case Estado.DELETANDO:
                buffer.append(WARN_STYLE.render("   Deseja continuar e deletar")).append("\n > ").append(itens.get(cursor).getNome_banco());
                break;
            case Estado.CRIANDO, Estado.EDITANDO:
                for(TextInput input : inputs) {
                    buffer.append("\n ").append(input.view()).append("\n");
                }
                break;
            case Estado.VISUALIZANDO:
                for(int i = 0; i < itens.size(); i++) {
                    Conta_Bancaria item = itens.get(i);
                    String prefix = (i == cursor) ? " > " : "   ";

                    String linha = String.format("%s%-5d %-25s %-5d\n", prefix, item.getId_caixa(), item.getNome_banco(), item.getNumero_conta());

                    buffer.append(linha);
                }
                break;
        }
        viewport.setContent(buffer.toString());
    }

    private void iniciarCampos() {
        TextInput nomeInput = new TextInput();
        nomeInput.setPrompt("Nome: ");
        nomeInput.setPlaceholder("Ex: Nubank");
        nomeInput.setCharLimit(100);

        TextInput numeroContaInput = new TextInput();
        numeroContaInput.setPrompt("Número Conta: ");
        numeroContaInput.setPlaceholder("Ex: 10101");
        numeroContaInput.setCharLimit(5);

        inputs = new ArrayList<>();
        inputs.add(nomeInput);
        inputs.add(numeroContaInput);
    }

    private void iniciarFormulario() {
        //reseta a posição do cursor
        inputCursor = 0;

        if(estado == Estado.EDITANDO) {
            //atualiza os campos para corresponderem aos valores da entidade
            Conta_Bancaria item = itens.get(cursor);
            inputs.get(0).setValue(item.getNome_banco());
            inputs.get(1).setValue(String.valueOf(item.getNumero_conta()));
        } else if(estado == Estado.CRIANDO) {
            //reseta os campos
            for(TextInput input : inputs) {
                input.setValue("");
            }
        }

        //garante o foco no primeiro input
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
            return WARN_STYLE.render("\n   ⚠️" + errorMessage);
        }

        return viewport.view() + "\n\n";
    }
}
