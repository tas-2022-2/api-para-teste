package br.edu.ifrs.riogrande.tads.cobaia.app.model;

// categoria hard coded
public enum Categoria {
	MOVEIS, // 0
	ROUPAS, // 1
	MATERIAIS_CONSTRUCAO; // 2

	public static Categoria fromInteger(Integer idCategoria) {
		try {
			return Categoria.values()[idCategoria];
		} catch (Exception e) {
			return null; // FIXME: não retornar null! Usar Optional
		}
	}
}
