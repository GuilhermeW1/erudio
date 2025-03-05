package org.example.erudio.integrations.wrappers;

import java.io.Serializable;
import java.util.List;

import org.example.erudio.integrations.moks.Person2Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonEmbeddedVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("personVOList")
	private List<Person2Dto> persons;

	public PersonEmbeddedVO() {
	}

	public List<Person2Dto> getPersons() {
		return persons;
	}

	public void setPersons(List<Person2Dto> persons) {
		this.persons = persons;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((persons == null) ? 0 : persons.hashCode());
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
		PersonEmbeddedVO other = (PersonEmbeddedVO) obj;
		if (persons == null) {
			if (other.persons != null)
				return false;
		} else if (!persons.equals(other.persons))
			return false;
		return true;
	}
}
