package com.gs.ecoocean.model.enuns;

public enum PerfilUsuario {

    VOLUNTARIO(1, "ROLE_VOLUNTARIO"),
	ADMIN(2, "ROLE_ADMIN");

	private Integer codigo;
	private String descricao;

	private PerfilUsuario(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static PerfilUsuario toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}

		for (PerfilUsuario x : PerfilUsuario.values()) {
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + codigo);
	}
}
