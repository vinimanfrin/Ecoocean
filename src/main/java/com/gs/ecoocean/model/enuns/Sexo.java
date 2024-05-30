package com.gs.ecoocean.model.enuns;

public enum Sexo {
    MASCULINO(1,"Masculino"),
    FEMININO(2,"Feminino");

    private int codigo;
    private String descricao;

    Sexo(int codigo,String descricao){
        this.codigo = codigo;
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

    public static Sexo toEnum(Integer codigo){
        if (codigo == null) return null;

        for (Sexo sexo: Sexo.values()){
            if (codigo.equals(sexo.getCodigo())){
                return sexo;
            }
        }
        throw new IllegalArgumentException("Id do sexo informado é inválido:"+codigo);
    }
}
