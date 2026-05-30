package com.agenda.adapters.out.persistence;

import org.springframework.data.jpa.domain.Specification;

public class ContatoEntitySpecifications {

    public static Specification<ContatoEntity> porCampo(String tipoBusca, String valor) {
        return (root, query, builder) -> {
            if (valor == null || valor.isBlank()) {
                return builder.conjunction();
            }

            String valorBusca = valor.toLowerCase();

            return switch (tipoBusca.toLowerCase()) {
                case "nome" -> builder.like(builder.lower(root.get("nome")), "%" + valorBusca + "%");
                case "email" -> builder.like(builder.lower(root.get("email")), "%" + valorBusca + "%");
                case "tel" -> builder.like(root.get("tel"), "%" + valorBusca + "%");
                case "tipo" -> builder.equal(builder.upper(root.get("tipo")), valor.toUpperCase());
                case "id" -> builder.equal(root.get("id"), Long.parseLong(valor));
                default -> builder.conjunction();
            };
        };
    }
}