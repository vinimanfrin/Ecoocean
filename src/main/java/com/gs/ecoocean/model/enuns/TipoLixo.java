package com.gs.ecoocean.model.enuns;

public enum TipoLixo {
    VIDRO(1,"Lixo do tipo vidros"),
    PAPEL(2,"Lixo do tipo papel"),
    PLASTICO(3,"Lixo do tipo plastico"),
    METAL(4,"Lixo do tipo metal"),
    ORGANICO(5,"Lixo do tipo orgânico");

    private int codigo;
    private String descricao;

    TipoLixo(int codigio,String descricao){
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

    public static TipoLixo toEnum(Integer codigo){
        if (codigo == null) return null;

        for (TipoLixo tipoLixo : TipoLixo.values()){
            if (codigo.equals(tipoLixo.getCodigo())){
                return tipoLixo;
            }
        }
        throw new IllegalArgumentException("Id do TipoLixo informado é inválido:"+codigo);
    }
}
