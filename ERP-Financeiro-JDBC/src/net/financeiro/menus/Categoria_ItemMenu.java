package net.financeiro.menus;

import com.williamcallahan.tui4j.compat.bubbles.textinput.TextInput;
import com.williamcallahan.tui4j.compat.bubbles.viewport.Viewport;
import com.williamcallahan.tui4j.compat.bubbletea.*;
import com.williamcallahan.tui4j.compat.lipgloss.Position;
import com.williamcallahan.tui4j.compat.lipgloss.Style;
import com.williamcallahan.tui4j.compat.lipgloss.color.AdaptiveColor;
import net.financeiro.menus.events.JdbcQueryResult;
import net.financeiro.model.Categoria_Item;
import net.financeiro.service.Categoria_ItemService;

import java.util.ArrayList;
import java.util.List;

public class Categoria_ItemMenu implements TableMenu {
    //Paleta e styles
    private final AdaptiveColor RED = new AdaptiveColor("#ff1f31", "#ff1f31");
    private final AdaptiveColor GRAY = new AdaptiveColor("#878787", "#878787");

    private final Style WARN_STYLE = Style.newStyle()
            .foreground(RED);

    private final Categoria_ItemService service = new Categoria_ItemService();
    private enum Estado { VISUALIZANDO, EDITANDO, CRIANDO, DELETANDO };
    private final Viewport viewport;

    //estado do menu
    private Estado estado = Estado.VISUALIZANDO;

    //posicao na lista de entidades
    private int cursor = 0;

    //variaveis de inputs (Para Insert e Update)
    private int inputCursor = 0;
    private List<TextInput> inputs;

    //dados retornados por consultas
    private String errorMessage = "";
    List<Categoria_Item> itens = new ArrayList<>();

    public Categoria_ItemMenu() {
        this.viewport = new Viewport();
    }

    @Override
    public String getTitle() {
        return "Categorias";
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
        //inicia os campos para edição e inserção de entidades
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
                        final String nome = inputs.get(0).value();

                        estado = Estado.VISUALIZANDO;

                        return () -> {
                          try {
                              Categoria_Item item = new Categoria_Item(nome);
                              if(modoOperacao == Estado.CRIANDO) {
                                  service.inserir(item);
                              } else if(modoOperacao == Estado.EDITANDO) {
                                  final long idItem = itens.get(idSelecionado).getId_categoria_item();
                                  service.atualizar(item, idItem);
                              }

                              List<Categoria_Item> result = service.listarInfo();
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
                              service.deletar(itens.get(idSelecionado).getId_categoria_item());

                              //atualiza a lista
                              List<Categoria_Item> result = service.listarInfo();
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
                itens = (List<Categoria_Item>) data;
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
                buffer.append(WARN_STYLE.render("   Deseja continuar e deletar?")).append("\n > ").append(itens.get(cursor).getNome());
                break;
            case Estado.CRIANDO, Estado.EDITANDO:
                for(TextInput input : inputs) {
                    buffer.append("\n ").append(input.view()).append("\n");
                }
                break;
            case Estado.VISUALIZANDO:
                for(int i = 0; i < itens.size(); i++) {
                    Categoria_Item item = itens.get(i);
                    String prefix = (i == cursor) ? " > " : "   ";

                    String linha = String.format("%s%-5d %-25s\n", prefix, item.getId_categoria_item(), item.getNome());

                    buffer.append(linha);
                }
                break;
        }

        viewport.setContent(buffer.toString());
    }

    private void iniciarCampos() {
        TextInput nomeInput = new TextInput();
        nomeInput.setPrompt("Nome: ");
        nomeInput.setPlaceholder("Ex: Eletrônicos");
        nomeInput.setCharLimit(100);

        inputs = new ArrayList<>();
        inputs.add(nomeInput);
    }

    private void iniciarFormulario() {
        //reseta a posição do cursor
        inputCursor = 0;

        if(estado == Estado.EDITANDO) {
            //atualiza os campos para corresponderem aos valores da entidade
            Categoria_Item item = itens.get(cursor);
            inputs.get(0).setValue(item.getNome());
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
