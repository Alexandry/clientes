package com.socio.torcedor.domain.model;



import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Classe que representa a Entidade Campanha, esta classe é apenas auxiliar
 * sendo que a mesma é utilzada junto ao RestTemplate para deserializar
 * a chamada da API de campanhas e consequentemente atribuir uma nova campanha ao cliente
 * utilizando recursos do Spring como:
 * Anotacoes Hibernate que auxiliam na manipulacao de dados junto ao database
 * @author Alexandre
 */

@Entity
@Table(name = "tb_campanha")
public class Campanha {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCampanha;
	
	@Column(name = "nm_campanha")
	private String nomeCampanha;
	
	@ManyToOne
	@JoinColumn(name  = "clube_id", nullable = false)
	private Clube clube;
	

	@Column(name = "dt_inicio")
	private LocalDate dataInicio;
	
	@Column(name = "dt_vigencia")
	private LocalDate dataVigencia;
	
	
	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Long getIdCampanha() {
		return idCampanha;
	}

	public void setIdCampanha(Long idCampanha) {
		this.idCampanha = idCampanha;
	}

	public String getNomeCampanha() {
		return nomeCampanha;
	}

	public void setNomeCampanha(String nomeCampanha) {
		this.nomeCampanha = nomeCampanha;
	}


	
	

	public Clube getClube() {
		return clube;
	}

	public void setClube(Clube clube) {
		this.clube = clube;
	}

	public LocalDate getDataVigencia() {
		return dataVigencia;
	}

	public void setDataVigencia(LocalDate dataVigencia) {
		this.dataVigencia = dataVigencia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCampanha == null) ? 0 : idCampanha.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campanha other = (Campanha) obj;
		if (idCampanha == null) {
			if (other.idCampanha != null)
				return false;
		} else if (!idCampanha.equals(other.idCampanha))
			return false;
		return true;
	}
	
	

}
