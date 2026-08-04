package net.financeiro.model;

public class Forma_Pagamento {
    private Long id_forma_pagamento;
    private String nome;

    public Forma_Pagamento(String nome) {
        this.nome = nome;
    }

    public Forma_Pagamento(Long id_forma_pagamento, String nome) {
        this.id_forma_pagamento = id_forma_pagamento;
        this.nome = nome;
    }

    public Long getId_forma_pagamento() {
        return id_forma_pagamento;
    }

    public String getNome() {
        return nome;
    }

    public void setId_forma_pagamento(Long id_forma_pagamento) {
        this.id_forma_pagamento = id_forma_pagamento;
    }
}
