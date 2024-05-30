package com.gs.ecoocean.model.enuns;

public enum StatusPartida {
    ATIVA(1,"Partida está em andamento"),
    ENCERRADA(2,"Partida foi encerrada"),
    AGENDADA(3,"Partida está agendada e aguardando início"),
    CANCELADA(4,"Partida cancelada");

    private int codigo;
    private String descricao;

    StatusPartida(int codigio,String descricao){
        this.codigo = codigio;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static StatusPartida toEnum(Integer codigo){
        if (codigo == null) return null;

        for (StatusPartida statusPartida : StatusPartida.values()){
            if (codigo.equals(statusPartida.getCodigo())){
                return statusPartida;
            }
        }
        throw new IllegalArgumentException("Id do StatusPartida informado é inválido:"+codigo);
    }
}
