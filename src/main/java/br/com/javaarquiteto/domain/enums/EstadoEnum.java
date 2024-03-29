package br.com.javaarquiteto.domain.enums;

public enum EstadoEnum {
	
    AC("Acre"),
    AL("Alagoas"),
    AP("Amapá"),
    AM("Amazonas"),
    BA("Bahia"),
    CE("Ceará"),
    DF("Distrito Federal"),
    ES("Espírito Santo"),
    GO("Goiás"),
    MA("Maranhão"),
    MT("Mato Grosso"),
    MS("Mato Grosso do Sul"),
    MG("Minas Gerais"),
    PA("Pará"),
    PB("Paraíba"),
    PR("Paraná"),
    PE("Pernambuco"),
    PI("Piauí"),
    RJ("Rio de Janeiro"),
    RN("Rio Grande do Norte"),
    RS("Rio Grande do Sul"),
    RO("Rondônia"),
    RR("Roraima"),
    SC("Santa Catarina"),
    SP("São Paulo"),
    SE("Sergipe"),
    TO("Tocantins");

    private final String descricao;

    EstadoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

   
    public static String getDescricaoBySigla(String sigla) {
        for (EstadoEnum estado : EstadoEnum.values()) {
            if (estado.name().equalsIgnoreCase(sigla)) {
                return estado.descricao;
            }
        }
        return null; 
    }
    
    public static EstadoEnum getEstadoEnum(String sigla) {
        for (EstadoEnum estado : EstadoEnum.values()) {
            if (estado.name().equalsIgnoreCase(sigla)) {
                return estado;
            }
        }
        return null; 
    }

}
