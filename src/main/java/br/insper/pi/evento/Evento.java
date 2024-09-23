package br.insper.pi.evento;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
public class Evento {

	@Id
	private String id;
	private String nome;
	private String descricao;
	private Integer maxConvidados;
	private String cpfCriador;

	private ArrayList<String> usuarios;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getMaxConvidados() {
		return maxConvidados;
	}

	public void setMaxConvidados(Integer maxConvidados) {
		this.maxConvidados = maxConvidados;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCpfCriador() {
		return cpfCriador;
	}

	public void setCpfCriador(String cpfCriador) {
		this.cpfCriador = cpfCriador;
	}

	public ArrayList<String> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<String> usuarios) {
		this.usuarios = usuarios;
	}

	public Evento(){
	}

}