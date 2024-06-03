package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class pcJDBCTemp {
    
	
    private JdbcTemplate jdbcTemplateObject;

    @Autowired
    public void setJdbcTemplateObject(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    public int insertPc(String marca , String tipologia, String modello,String descrizione, int qnt, String url, double prezzo) {
        String query = "INSERT INTO magazzino (marca, tipologia, modello, descrizione, qnt, url, prezzo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplateObject.update(query, marca, tipologia, modello, descrizione, qnt, url, prezzo);
    }

   public int updatePezzi(int pezzi, int id) {
        String query = "UPDATE magazzino SET qnt = qnt - ? WHERE id = ?";
        return jdbcTemplateObject.update(query, pezzi, id);
    }
/*
    public int updateMansione(String cognome, String mansione) {
        String query = "UPDATE dip SET mansione = ? WHERE cognome = ?";
        return jdbcTemplateObject.update(query, cognome, mansione);
    }

    public int updateNome(String nome, String cognome) {
        String query = "UPDATE dip SET nome = ? WHERE cognome = ?";
        return jdbcTemplateObject.update(query, nome, cognome);
    }*/

   /* 	public int deleteOrologio(String marca) {
        String query = "DELETE FROM Orologi WHERE modello = ?";
        return jdbcTemplateObject.update(query, marca);
    }*/
    
   public ArrayList<computer> ritornaPc(){
    	ResultSet rs1 = null;
    	
    	
            String query = "SELECT * FROM magazzino";
            return jdbcTemplateObject.query(query, new ResultSetExtractor<ArrayList<computer>>() {
                @Override
                public ArrayList<computer> extractData(ResultSet rs) throws SQLException {
                	ArrayList <computer> listaOrologi = new ArrayList<>();
                    while (rs.next()) {
                    	computer o1 = new computer();
                        o1.setId(rs.getInt("id"));
                        o1.setMarca(rs.getString("marca"));
                        o1.setTipologia(rs.getString("tipologia"));
                        o1.setModello(rs.getString("modello"));
                        o1.setDescrizione(rs.getString("descrizione"));
                        o1.setQnt(rs.getInt("qnt"));
                        o1.setUrl(rs.getString("url"));
                        o1.setPrezzo(rs.getDouble("prezzo"));
                        
                        
                        listaOrologi.add(o1);
                    }
                    return listaOrologi;
                }
            });
        }
    	
    	
    	
    	
    	
    	
    

    // Metodo per eseguire query DDL
    public void executeDDLQuery(String query) {
        jdbcTemplateObject.execute(query);
    }
}