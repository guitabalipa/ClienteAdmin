/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.clienteadmin.model;

import java.sql.Date;

/**
 *
 * @author Guilherme
 */
public class Avaliacao {
    
    private int avaliacaoid;
    private int avaliadoid;
    private int pessoaid;
    private int nota;
    private String descricao;
    private String tipoAvalicao;

    public int getAvaliacaoid() {
        return avaliacaoid;
    }

    public void setAvaliacaoid(int avaliacaoid) {
        this.avaliacaoid = avaliacaoid;
    }

    public int getAvaliadoid() {
        return avaliadoid;
    }

    public void setAvaliadoid(int avaliadoid) {
        this.avaliadoid = avaliadoid;
    }

    public int getPessoaid() {
        return pessoaid;
    }

    public void setPessoaid(int pessoaid) {
        this.pessoaid = pessoaid;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoAvalicao() {
        return tipoAvalicao;
    }

    public void setTipoAvalicao(String tipoAvalicao) {
        this.tipoAvalicao = tipoAvalicao;
    }
    
    public boolean hasAvaliacao(){
        if(this.avaliacaoid == 0 &&
           this.avaliadoid == 0 &&
           this.nota == 0 &&
           this.pessoaid == 0){
           return true;
        }
           return false;
    }
}
