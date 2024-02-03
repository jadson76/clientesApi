package br.com.javaarquiteto.domain.enums;

public enum OperacaoEnum {
	
	INCLUSAO_COM_FOTO("Registro do cliente %s , cpf %s com foto incluido com sucesso."),
	INCLUSAO_SEM_FOTO("Registro do cliente %s , cpf %s sem foto incluido com sucesso."),
	EXCLUSAO("Registro do cliente %s , cpf %s excluido com sucesso."),
	ATUALIZACAO_INFORMACOES("Registro do cliente %s , cpf %s atualizado com sucesso"),
	ENVIO_EMAIL("Email de boas vindas enviado para o cliente %s , email %s com sucesso"),
	ATUALIZACAO_FOTO("Registro da foto do cliente %s , cpf %s atualizado com sucesso."),
	ATUALIZACAO_ENDERECO("Registro de endereço do cliente %s , cpf %s atualizada com sucesso.");	
	
	 private final String descricao;

	 OperacaoEnum(String descricao) {
	        this.descricao = descricao;
	    }

	    public String getDescricao() {
	        return descricao;
	    }

	   
	    public static String getDescricaoBySigla(String sigla) {
	        for (OperacaoEnum operacao : OperacaoEnum.values()) {
	            if (operacao.name().equalsIgnoreCase(sigla)) {
	                return operacao.descricao;
	            }
	        }
	        return null; 
	    }

}
