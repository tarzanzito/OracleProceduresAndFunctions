package com.candal.oracle;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PessoasRowMapper implements RowMapper<Pessoa> {

	@Override
	public Pessoa mapRow(ResultSet rs, int rowNum) throws SQLException {

		Pessoa cso = new Pessoa();
		
		String bbb = rs.getString(1);  //init at 1 not at 0
		
		cso.setPersonid(rs.getInt("PERSONID"));
		cso.setLastname(rs.getString("LASTNAME"));
		cso.setFirstname(rs.getString("FIRSTNAME"));
		cso.setAddress(rs.getString("ADDRESS"));
		cso.setCity(rs.getString("CITY"));

		return cso;
	}

}
