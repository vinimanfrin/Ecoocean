package com.gs.ecoocean.model.enuns;

public enum TipoLixo {
    VIDRO(1,"Lixo do tipo vidros",5),
    PAPEL(2,"Lixo do tipo papel",1),
    PLASTICO(3,"Lixo do tipo plastico",2),
    METAL(4,"Lixo do tipo metal",3),
    ORGANICO(5,"Lixo do tipo orgânico",4);

    private int codigo;
    private String descricao;
    private Integer valor;

    TipoLixo(int codigio,String descricao,Integer valor){
        this.codigo = codigio;
        this.descricao = descricao;
        this.valor = valor;
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

    public Integer getValor(){
        return valor;
    }

    public void setValor(Integer valor){
        this.valor = valor;
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
